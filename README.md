# Campus Equipment Booking Service

Java 17 and Spring Boot REST API for campus equipment booking.

## Build and Test
mvn clean test
mvn clean package

## Docker
docker build -t campus-equipment-booking:set-c .
docker run -d --name campus-equipment-booking -p 8081:8080 campus-equipment-booking:set-c

Health check: http://localhost:8081/actuator/health

## Kubernetes
minikube start
minikube image load campus-equipment-booking:set-c
kubectl apply -f k8s/
kubectl rollout status deployment/campus-equipment-booking
kubectl scale deployment campus-equipment-booking --replicas=2

Health check: http://192.168.49.2:30081/actuator/health

## Changes
Fixed booking capacity validation and added regression tests. Unknown equipment IDs should return HTTP 404 without creating a booking or changing inventory.

## Git
Branch: feature/equipment-lookup-validation

