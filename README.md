# Top Waypoints Project

## [Backend App](https://github.com/doge95/top-waypoints-backend)
- Use Java [Spring RestTemplate](https://spring.io/guides/gs/rest-service/) to create the backend that consumes RESTful Web Services.
- Use [Lombok library](https://projectlombok.org/) to faster the deployment without the need ofwriting Getters/Setters.
- Implement MVC architecture and use "Procedure" to represent both SID and STAR for efficient code re-use.
- Containerize the backend application with the [Dockefile](https://github.com/doge95/top-waypoints-backend/blob/master/Dockerfile).

## [Frontend App](https://github.com/doge95/top-waypoints-frontend)
- Use React-Bootstrap framework for the frondend.
- Populate the airports dropdown list by calling the backend.
- Fetch the top waypoints from the backend.
- Containerize the frontend application with the [Dockefile](https://github.com/doge95/top-waypoints-frontend/blob/main/Dockerfile).

## CI/CD with Github Actions
### CI Workflow
- Use Github Actions to build images and push to the artifactory repository when new commits pushed or new tags created. 
- Github Actions to push to [Docker Hub](https://github.com/doge95/top-waypoints-backend/blob/master/.github/workflows/maven.yml).
- Github Actions to push to [Google Artifactory Repository](https://github.com/doge95/top-waypoints-backend/blob/master/.github/workflows/google.yml).
- Package the application to [Helm chart](https://github.com/doge95/helm-charts). Push to [GAR](https://github.com/doge95/helm-charts/blob/main/.github/workflows/tb-google.yml) as well.

### CD Workflow
Using Helm to deploy the applications to GKE.
#### **Helm Chart**
Triger [deployment to GKE](https://github.com/doge95/helm-charts/blob/main/.github/workflows/tb-google.yml#L66-L71) when any changes made to the Helm chart. 
#### **Image**
Triger deployment to GKE when [new images](https://github.com/doge95/top-waypoints-frontend/blob/main/.github/workflows/google.yml#L67-L72) are pushed to GAR. 