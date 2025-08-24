pipeline {
  agent any
  environment {
    IMAGE = "techietech/employee-api-service"
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
        withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'techietech', passwordVariable: 'Techie@123')]) {
          bat 'echo $PASS | docker login -u $USER --password-stdin'
          bat "docker build -t $IMAGE ."
          bat "docker pubat $IMAGE"
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