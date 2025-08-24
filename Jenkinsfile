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
        sh 'mvn clean package -DskipTests'
      }
    }
    stage('Docker Build & Push') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'techietech', passwordVariable: 'Techie@123')]) {
          sh 'echo $PASS | docker login -u $USER --password-stdin'
          sh "docker build -t $IMAGE ."
          sh "docker push $IMAGE"
        }
      }
    }
    stage('Deploy to Kubernetes') {
      steps {
        sh 'kubectl apply -f k8s/'
      }
    }
  }
}