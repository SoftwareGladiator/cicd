pipeline {
    agent any
    options {
          buildDiscarder(logRotator( numToKeepStr:'5'))
    }
    environment {
        DOCKERHUB_CREDENTIALS = credentials('softwaregladiator-dockerhub')
    }
    stages {
        stage('Build') {
            steps {
                dir("c:/cicd/cicd") {
                    powershell 'docker build -t softwaregladiator/cicd:latest .'
                }
            }
        }
        stage('Login') {
            steps {
                powershell 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                powershell 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u softwaregladiator -p A34kl339$#pol'

            }
        }
        stage('Push') {
            steps {
                dir("c:/cicd/cicd") {
                    powershell 'docker push softwaregladiator/cicd:latest'
                }
            }
        }
    }
    post {
        always {
            powershell 'docker logout'
        }
    }
}
