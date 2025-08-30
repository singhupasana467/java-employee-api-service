
# Employee API Service

## Overview 

Employee API Service is a backend application built with Spring Boot that exposes RESTful endpoints to manage employee data. The project is containerized using Docker, automated with Jenkins, and deployed to a Kubernetes cluster running on Docker Desktop. Recent updates include Terraform-based deployment, GitHub webhook integration, and automated cleanup.

---

## Tech Stack

| Layer              | Tools & Technologies                          |
|-------------------|-----------------------------------------------|
| Backend            | Java (Spring Boot), Maven                     |
| Containerization   | Docker                                        |
| CI/CD              | Jenkins                                       |
| Orchestration      | Kubernetes (via Docker Desktop)               |
| Infrastructure     | Terraform                                     |
| Registry           | Docker Hub                                    |
| Version Control    | GitHub                                        |

---

## Project Structure

- `src/`: Java source code  
- `k8s/`: Legacy Kubernetes manifests (replaced by Terraform)  
- `terraform-employee-api/`: Terraform configuration for Kubernetes deployment  
- `Dockerfile`: Instructions to build the container image  
- `Jenkinsfile`: CI/CD pipeline definition  
- `pom.xml`: Maven build configuration  
- `README.md`: Project documentation  

---

## API Endpoints

- **POST** `/api/employees`: Create a new employee  
- **GET** `/api/employees/{id}`: Retrieve employee by ID  
- **DELETE** `/api/employees/{id}`: Delete employee by ID  

All endpoints are exposed via port 8080.

---

## Build Commands

To build and package the application:

```bash
mvn clean compile
mvn test         # optional
mvn package
```

The output JAR will be located in the `target/` directory.

---

## Local Development

After building the project, you can run the application locally using the packaged JAR file. Ensure Java 17+ is installed and Maven is configured.

---

## Dockerization

The application is containerized using a lightweight Java base image. The Dockerfile defines the working directory, copies the packaged JAR, exposes the necessary port, and sets the entry point.

To build and run the image locally:

```bash
docker build -t techietech/employee-api-service:latest .
docker run -p 8080:8080 techietech/employee-api-service:latest
```

---

## CI/CD with Jenkins

The Jenkins pipeline includes:

1. **Checkout**: Pulls code from GitHub  
2. **Build**: Compiles and packages the application using Maven  
3. **Docker Build & Push**: Authenticates with Docker Hub, builds the image, and pushes it to the registry  
4. **Terraform Deploy**: Uses Terraform to provision Kubernetes resources  
5. **Wait and Cleanup**: Waits 15 minutes, then deletes the Kubernetes deployment

### Jenkinsfile Highlights

- Uses `terraform apply` instead of `kubectl apply`
- Includes environment variables for image name, kubeconfig path, deployment name, and namespace
- Uses `githubPush()` trigger for classic pipeline jobs
- Docker credentials are securely injected via Jenkins credentials
- Cleanup logic ensures temporary deployments are removed automatically

---

## GitHub Integration

- GitHub webhook configured to trigger Jenkins builds on push events
- Webhook URL:  
  ```
(https://c891d9557e35.ngrok-free.app/github-webhook/)
  ```
- Jenkins exposed via ngrok for external webhook delivery
- Jenkins root URL updated to match ngrok domain
- Jenkins security settings adjusted to allow anonymous webhook access and bypass CSRF for `/github-webhook/`

---

## Kubernetes Deployment

Terraform provisions the following resources:

- Namespace: `employee-api`
- Deployment: `employee-api-service` with 1 replica
- Service: ClusterIP or NodePort exposing port 8080

To manually deploy or destroy:

```bash
cd terraform-employee-api
terraform init
terraform apply -auto-approve
terraform destroy -auto-approve   # for teardown
```

---

## Stopping the Deployment

To manually stop and remove the application from your Kubernetes cluster:

```bash
kubectl delete deployment employee-api-service --namespace=employee-api
kubectl delete service employee-api-service --namespace=employee-api   # optional
```

---

## Troubleshooting Highlights

| Issue                          | Fix                                                                 |
|-------------------------------|----------------------------------------------------------------------|
| Push access denied             | Ensure the image name includes your DockerHub username and repo exists |
| Validation errors in manifests | Correct typos such as `specs` instead of `spec`, or `repica` instead of `replicas` |
| Jenkins can't find kubeconfig  | Confirm the path is correct and accessible on your system           |
| Image reappears after deletion | Kubernetes may recreate pods based on the deployment spec—delete the deployment to stop this |
| Jenkins not triggering builds  | Ensure webhook is configured and `githubPush()` is present in Jenkinsfile |

---

## Validation Checklist

- API runs locally  
- Docker image builds and runs  
- Image pushed to Docker Hub  
- Jenkins pipeline executes all stages  
- Terraform provisions Kubernetes resources  
- Service accessible via port-forward or NodePort  
- Deployment auto-deletes after 15 minutes  
- GitHub webhook triggers Jenkins build  

---

## Final Notes

This project demonstrates a full DevOps lifecycle:  
**build → containerize → automate → deploy → teardown**
