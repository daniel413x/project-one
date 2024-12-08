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
                dir('client') {
                    sh '''
                    node -v
                    npm install && npm run build
                    '''
                }
            }
        }

        stage('Test Frontend') {
            steps {
                script {
                    withSonarQubeEnv('SonarCloud') {
                        dir('client') {
                            sh '''
                            npm run test -- --coverage
                            npx sonar-scanner \
                                -Dsonar.projectKey=daniel413x_project-one \
                                -Dsonar.projectName=project-one-client-unit-tests \
                                -Dsonar.sources=src \
                                -Dsonar.exclusions=**/__tests__/**,src/test/** \
                                -Dsonar.javascript.lcov.reportPaths=coverage/lcov.info
                            '''
                        }
                    }
                }
            }
        }

        stage('Build Backend') {
            steps {
                dir('server') {
                    sh 'mvn clean install -DskipTests=true -Dspring.profiles.active=build'
                }
            }
        }
        
        stage('Test Backend') {
            steps {
                dir('server') {
                    sh 'mvn clean verify -Dspring.profiles.active=test'

                    withSonarQubeEnv('SonarCloud') {
                        sh '''
                        mvn sonar:sonar \
                            -Dsonar.projectKey=daniel413x_project-one \
                            -Dsonar.projectName=project-one-server-unit-tests \
                            -Dsonar.java.binaries=target/classes \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                        '''
                    }
                }
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
                        dir('functional-tests') {
                            sh '''
                                mvn test -Dheadless=true -Dcucumber.publish.token=${CUCUMBER_TOKEN} -DserverApiUrl=http://localhost:5000/api
                            '''

                            // withSonarQubeEnv('SonarCloud') {
                            //     sh '''
                            //     mvn sonar:sonar \
                            //         -Dsonar.organization=daniel413x \
                            //         -Dsonar.projectKey=daniel413x_project-two-functional-tests \
                            //         -Dsonar.projectName=project-two-functional-tests \
                            //         -Dsonar.junit.reportPaths=target/extent-report/car-create-page-report.xml
                            //     '''
                            // }
                        }
                    }

                    // kill backend and frontend processes
                    sh "kill ${backendPid} || true"
                    sh "kill ${frontendPid} || true"
                }
            }
        }
        
        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'functional-tests/target/extent-report/**/*.html, server/target/site/jacoco/index.html', fingerprint: true
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
