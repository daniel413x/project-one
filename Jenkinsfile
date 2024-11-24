pipeline {
    agent any

    environment {
        MAJOR_VERSION = '0'
        MINOR_VERSION = '2'
        PATCH_VERSION = "${env.BUILD_NUMBER}"
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
                sh 'cd frontend && npm install && npm run build'
            }
        }

        stage('Test and Analyze Frontend') {
            steps {
                script {
                    dir('frontend') {
                        sh '''
                        npm run test -- --coverage
                        '''
                    }
                }
            }
        }

        stage('Build Backend') {
            steps {
                sh 'cd backend && mvn clean install -DskipTests=true -Dspring.profiles.active=build'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
