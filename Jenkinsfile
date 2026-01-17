pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    options {
        timestamps()
    }

    parameters {
        choice(
            name: 'TEST_CLASS',
            choices: ['All Tests', 'PetTest', 'StoreTest', 'UserTest'],
            description: 'Select which test class to execute'
        )
    }

    stages {
        stage('Check Docker Availability') {
            steps {
                script {
                    try {
                        if (isUnix()) {
                            sh 'docker --version'
                            env.USE_DOCKER = 'true'
                            echo 'Docker is available. Using Docker for test execution.'
                        } else {
                            bat 'docker --version'
                            env.USE_DOCKER = 'true'
                            echo 'Docker is available. Using Docker for test execution.'
                        }
                    } catch (Exception e) {
                        env.USE_DOCKER = 'false'
                        echo 'Docker is not available. Falling back to traditional Maven execution.'
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    if (env.USE_DOCKER == 'true') {
                        if (isUnix()) {
                            sh 'docker build -t restassured-petstore:latest .'
                        } else {
                            bat 'docker build -t restassured-petstore:latest .'
                        }
                    } else {
                        if (isUnix()) {
                            sh "mvn clean install -DskipTests"
                        } else {
                            bat "mvn clean install -DskipTests"
                        }
                    }
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    def mvnCmd = "mvn clean test"

                    if (params.TEST_CLASS != 'All Tests') {
                        mvnCmd = "${mvnCmd} -Dtest=${params.TEST_CLASS}"
                    }

                    if (env.USE_DOCKER == 'true') {
                        if (isUnix()) {
                            sh """
                                docker run --rm \
                                    -v \${PWD}/reports:/app/reports \
                                    -v \${PWD}/logs:/app/logs \
                                    restassured-petstore:latest ${mvnCmd}
                            """
                        } else {
                            bat """
                                docker run --rm -v %CD%\\reports:/app/reports -v %CD%\\logs:/app/logs restassured-petstore:latest ${mvnCmd}
                            """
                        }
                    } else {
                        if (isUnix()) {
                            sh mvnCmd
                        } else {
                            bat mvnCmd
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Tests executed successfully'
            archiveArtifacts artifacts: 'reports/PetStore_Test_Report.html', allowEmptyArchive: true
            archiveArtifacts artifacts: 'logs/test-logs.log', allowEmptyArchive: true
        }
        failure {
            echo 'Build failed. Please review test reports.'
            archiveArtifacts artifacts: 'reports/PetStore_Test_Report.html', allowEmptyArchive: true
            archiveArtifacts artifacts: 'logs/test-logs.log', allowEmptyArchive: true
        }
    }
}

