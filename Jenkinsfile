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
                                -Dsonar.projectKey=daniel413x_project-one-client \
                                -Dsonar.projectName=project-one-client-unit-tests \
                                -Dsonar.organization=daniel413x \
                                -Dsonar.sources=src \
                                -Dsonar.exclusions=**/__tests__/**,src/test/** \
                                -Dsonar.javascript.lcov.reportPaths=coverage/lcov.info
                            '''
                        }
                    }
                }
            }
            
            post {
                always {
                    archiveArtifacts artifacts: 'client/coverage/lcov-report/**/*', fingerprint: true
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'client/coverage/lcov-report',
                        reportFiles: 'index.html',
                        reportName: 'Test Report: Frontend Unit Testing'
                    ])
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
                            -Dsonar.organization=daniel413x \
                            -Dsonar.java.binaries=target/classes \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                        '''
                    }
                }
            }

            post {
                always {
                    archiveArtifacts artifacts: 'server/target/site/jacoco/**/*', fingerprint: true
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'server/target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'Test Report: Backend Unit Testing'
                    ])
                }
            }
        }

        stage('Perform Functional Tests') {
            steps {
                script {
                    def pids = startServers()

                    waitForService('http://localhost:5000', 'backend')
                    waitForService('http://localhost:3000', 'frontend')

                    withCredentials([string(credentialsId: 'CUCUMBER_PUBLISH_TOKEN', variable: 'CUCUMBER_TOKEN')]) {
                        dir('functional-tests') {
                            sh '''
                                mvn test -Dheadless=true -Dcucumber.publish.token=${CUCUMBER_TOKEN} -DserverApiUrl=http://localhost:5000/api
                            '''
                        }
                    }

                    stopServers(pids)
                }
            }

            post {
                always {
                    archiveArtifacts artifacts: 'functional-tests/target/extent-report/**/*', fingerprint: true
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'functional-tests/target/extent-report',
                        reportFiles: 'all-pages-report.html',
                        reportName: 'Test Report: Functional Testing'
                    ])
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'functional-tests/target/extent-report',
                        reportFiles: 'axe-core-accessibility-report.html',
                        reportName: 'Test Report: Accessibility Testing'
                    ])
                }
            }
        }

        stage('Perform Performance Tests') {
            steps {
                script {
                    def pids = startServers()

                    waitForService('http://localhost:5000', 'backend')
                    waitForService('http://localhost:3000', 'frontend')

                    dir('performance-tests') {
                    	bzt 'stepping.yaml'
                        archiveArtifacts artifacts: '*/**.jtl', allowEmptyArchive: true
                    }

                    stopServers(pids)
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}

// Utility function to start servers
def startServers() {
    def pids = [:]

    withCredentials([
        string(credentialsId: 'TEST_DB_USERNAME', variable: 'DB_USERNAME'),
        string(credentialsId: 'TEST_DB_PASSWORD', variable: 'DB_PASSWORD'),
        string(credentialsId: 'TEST_DB_URL', variable: 'DB_URL')]) {
        dir('server') {
            pids.backend = sh(script: '''
                mvn spring-boot:run -Dspring-boot.run.arguments="--DB_URL=${DB_URL} --DB_USERNAME=${DB_USERNAME} --DB_PASSWORD=${DB_PASSWORD}" &
                echo $!
            ''', returnStdout: true).trim()
        }
    }

    dir('client') {
        pids.frontend = sh(script: 'npx vite --mode test & echo $!', returnStdout: true).trim()
    }

    return pids
}

// Utility function to wait for a service to be ready
def waitForService(url, serviceName) {
    sh """#!/bin/bash
        TRIES_REMAINING=30
        echo "Waiting for ${serviceName} to be ready at ${url}..."
        while ! curl --output /dev/null --silent "${url}"; do
            TRIES_REMAINING=\$((TRIES_REMAINING - 1))
            if [ \$TRIES_REMAINING -le 0 ]; then
                echo "ERROR: ${serviceName} did not start within expected time."
                exit 1
            fi
            echo "waiting for ${serviceName}..."
            sleep 5
        done
        echo "***${serviceName} is ready***"
    """
}



// Utility function to stop servers
def stopServers(pids) {
    pids.each { key, pid ->
        sh "kill ${pid} || true"
    }
}
