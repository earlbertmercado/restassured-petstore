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
            name: 'TEST_CLASS'
            choices: ['All Tests', 'PetTest', 'StoreTest', 'UserTest']
            description: 'Select which test class to execute'
        )
    }

    stages {
        stage('Install Dependencies / Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh "mvn clean install -DskipTests"
                    } else {
                        bat "mvn clean install -DskipTests"
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

                    if (isUnix()) {
                        sh mvnCmd
                    } else {
                        bat mvnCmd
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Tests executed successfully'
        }
        failure {
            echo 'Build failed. Please review test reports.'
        }
    }
}

