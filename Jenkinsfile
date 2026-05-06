pipeline {
    agent any

    tools {
        maven 'Maven3'   // configure Maven in Jenkins global tools
        jdk 'JDK17'      // configure JDK in Jenkins global tools
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/Bakare1/SpringSecuredAppWith0auth.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
        stage('Deploy') {
            steps {
                sh 'java -jar target/*.jar'
            }
        }
    }
}
