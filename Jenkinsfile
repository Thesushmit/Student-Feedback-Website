pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Run Selenium') {
            steps {
                echo 'Running FeedbackFormTest via Maven'
                bat 'mvn -B -Dtest=com.selenium.test.FeedbackFormTest test'
            }
        }
    }

    post {
        success {
            echo 'Build succeeded: Selenium tests passed.'
        }
        failure {
            echo 'Build failed: check test output and failures.'
        }
    }
}
