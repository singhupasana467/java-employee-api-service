
# Employee API Service

## Overview 

Employee API Service is a backend application built with Spring Boot that exposes RESTful endpoints to manage employee data. The project is containerized using Docker, automated with Jenkins, and deployed to a Kubernetes cluster running on Docker Desktop.

---

## Tech Stack

| Layer              | Tools & Technologies                          |
|-------------------|-----------------------------------------------|
| Backend            | Java (Spring Boot), Maven                     |
| Containerization   | Docker                                        |
| CI/CD              | Jenkins                                       |
| Orchestration      | Kubernetes (via Docker Desktop)               |
| Registry           | Docker Hub                                    |
| Version Control    | GitHub                                        |

---

## Project Structure

- `src/`: Java source code  
- `k8s/`: Kubernetes manifests (deployment and service)  
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

1. Clean and compile the project:
   ```
   mvn clean compile
   ```

2. Run tests (optional):
   ```
   mvn test
   ```

3. Package the application into a JAR:
   ```
   mvn package
   ```

4. The output JAR will be located in the `target/` directory.

---

## Local Development

After building the project, you can run the application locally using the packaged JAR file. Ensure Java 17+ is installed and Maven is configured.

---

## Dockerization

The application is containerized using a lightweight Java base image. The Dockerfile defines the working directory, copies the packaged JAR, exposes the necessary port, and sets the entry point.

After building the image, it can be run locally by mapping the container port to your host machine.

---

## CI/CD with Jenkins

The Jenkins pipeline includes three stages:

1. **Build**: Compiles and packages the application using Maven.  
2. **Docker Build & Push**: Authenticates with Docker Hub, builds the image, and pushes it to the registry.  
3. **Deploy to Kubernetes**: Applies the Kubernetes manifests using the local kubeconfig.

Ensure Jenkins has access to your Docker credentials and Kubernetes configuration file.

---

## Kubernetes Deployment

The deployment manifest defines a single replica of the application, using the Docker image pushed to Docker Hub. The service manifest exposes the application internally within the cluster.

To deploy, apply both manifests using `kubectl`.

---

## Stopping the Deployment

To gracefully stop and remove the application from your Kubernetes cluster:

- Delete the deployment:  
  `kubectl delete deployment employee-api-service`

- Delete the service (optional):  
  `kubectl delete service employee-api-service`

---

## Troubleshooting Highlights

| Issue                        | Fix                                                                 |
|-----------------------------|----------------------------------------------------------------------|
| Push access denied           | Ensure the image name includes your DockerHub username and repo exists |
| Validation errors in manifests | Correct typos such as `specs` instead of `spec`, or `repica` instead of `replicas` |
| Jenkins can't find kubeconfig | Confirm the path is correct and accessible on your system           |
| Image reappears after deletion | Kubernetes may recreate pods based on the deployment spec—delete the deployment to stop this |

---

## Validation Checklist

- API runs locally  
- Docker image builds and runs  
- Image pushed to Docker Hub  
- Jenkins pipeline executes all stages  
- Kubernetes manifests apply successfully  
- Service accessible via port-forward or NodePort  
- Deployment can be stopped with `kubectl delete`
  

---

## Final Notes

This project demonstrates a full DevOps lifecycle: build → containerize → automate → deploy → teardown.  
