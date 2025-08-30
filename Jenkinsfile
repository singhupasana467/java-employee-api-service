pipeline {
  agent any
  environment {
    IMAGE = "techietech/employee-api-service:latest"
    KUBECONFIG = "C:/Users/timbe/.kube/config"
    TERRAFORM_DIR = "terraform-employee-api"
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
          bat 'docker build -t %IMAGE% .'
          bat 'docker push %IMAGE%'
        }
      }
    }
    stage('Terraform Deploy'){
      steps {
        dir("${TERRAFORM_DIR}"){
          bat 'terraform init'
          bat 'terraform apply -auto-approve'
        }
      }
    }
  }
}