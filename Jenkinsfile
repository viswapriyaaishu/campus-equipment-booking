
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'feature/equipment-lookup-validation',
                    url: 'https://github.com/viswapriyaaishu/campus-equipment-booking.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true,
                  testResults: '**/target/surefire-reports/*.xml'
        }

        success {
            echo 'Build, tests, and packaging passed!'
        }

        failure {
            echo 'Pipeline failed. Check Console Output.'
        }
    }
}

