
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
    }

    post {
        always {
            junit allowEmptyResults: true,
                  testResults: '**/target/surefire-reports/*.xml'
        }

        success {
            echo 'Build and tests passed!'
        }

        failure {
            echo 'Build or tests failed. Check Console Output.'
        }
    }
}

