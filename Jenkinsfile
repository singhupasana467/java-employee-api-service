pipeline {
  agent any
  environment {
    IMAGE = "techietech/employee-api-service:latest"
  }
  stages {
    stage('Checkout') {
      steps {
        git branch: 'main', url: 'https://github.com/singhupasana467/java-employee-api-service.git'
      }
    }
    stage('Build') {
      steps {
        bat 'mvn clean package -DskipTests'
      }
    }
    stage('Docker Build & Push') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          bat 'echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin'
          bat 'docker build -t techietech/employee-api-service:latest .'
          bat 'docker push techietech/employee-api-service:latest'
        }
      }
    }
    stage('Deploy to Kubernetes') {
      steps {
        bat 'kubectl apply -f k8s/'
      }
    }
  }
}