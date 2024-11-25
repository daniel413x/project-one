pipeline {
    agent any
    
    tools {
        nodejs '18.0.0'
    }
    
    environment {
        MAJOR_VERSION = '0'
        MINOR_VERSION = '2'
        PATCH_VERSION = "${env.BUILD_NUMBER}"
        VITE_APP_API_URL = 'http://localhost:5000/api'
    }

    stages {
        stage('Prepare Version') {
            steps {
                script {
                    def newPatchVersion = PATCH_VERSION.toInteger() + 1
                    env.VERSION = "${MAJOR_VERSION}.${MINOR_VERSION}.${newPatchVersion}"
                    echo "Updated version to: ${env.VERSION}"
                }
            }
        }

        stage('Build Frontend') {
            steps {
                sh '''
                node -v
                cd client && npm install && npm run build
                '''
            }
        }

        stage('Build Backend') {
            steps {
                sh 'cd server && mvn clean install -DskipTests=true -Dspring.profiles.active=build'
            }
        }
        
        stage('Perform Functional Tests') {
            steps {

                script {
                    // capture IDs to later terminate pipeline project test servers
                    def backendPid
                    def frontendPid
                    withCredentials([
                        string(credentialsId: 'TEST_DB_USERNAME', variable: 'DB_USERNAME'),
                        string(credentialsId: 'TEST_DB_PASSWORD', variable: 'DB_PASSWORD'),
                        string(credentialsId: 'TEST_DB_URL', variable: 'DB_URL')]) {
                            dir('server') {
                                backendPid = sh(script: '''
                                    mvn spring-boot:run -Dspring-boot.run.arguments="--DB_URL=${DB_URL} --DB_USERNAME=${DB_USERNAME} --DB_PASSWORD=${DB_PASSWORD}" &
                                    echo \$!
                                ''', returnStdout: true).trim()
                            }
                        }

                    dir('client') {
                        frontendPid = sh(script: 'npx vite --mode test & echo $!', returnStdout: true).trim()
                    }

                    // wait for the backend and frontend to be ready
                    sh '''
                        TRIES_REMAINING=16

                        echo 'Waiting for backend to be ready...'
                        while ! curl --output /dev/null --silent http://localhost:5000; do
                            TRIES_REMAINING=$((TRIES_REMAINING - 1))
                            if [ $TRIES_REMAINING -le 0 ]; then
                                echo 'backend did not start within expected time.'
                                exit 1
                            fi
                            echo 'waiting for backend...'
                            sleep 5
                        done
                        echo '***backend is ready***'

                        TRIES_REMAINING=16

                        echo 'Waiting for frontend to be ready...'
                        while ! curl --output /dev/null --silent http://localhost:3000; do
                            TRIES_REMAINING=$((TRIES_REMAINING - 1))
                            if [ $TRIES_REMAINING -le 0 ]; then
                                echo 'frontend did not start within expected time.'
                                exit 1
                            fi
                            echo 'waiting for frontend...'
                            sleep 5
                        done
                        echo '***frontend is ready***'
                    '''

                    withCredentials([string(credentialsId: 'CUCUMBER_PUBLISH_TOKEN', variable: 'CUCUMBER_TOKEN')]) {
                        sh '''
                            cd functional-tests
                            mvn test -Dheadless=true -Dcucumber.publish.token=${CUCUMBER_TOKEN} -DserverApiUrl=http://localhost:5000/api
                        '''
                    }

                    // kill backend and frontend processes
                    sh "kill ${backendPid} || true"
                    sh "kill ${frontendPid} || true"
                }
            }
        }
        
        stage('Publish HTML Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'functional-tests/target/extent-report',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Axe-core Accessibility Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true
                ])
            }
        }
        
        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'functional-tests/target/extent-report/**/*', fingerprint: true
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
