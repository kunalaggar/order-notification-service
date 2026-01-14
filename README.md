# Event-Driven Order Management System

A Spring Boot microservices application demonstrating event-driven architecture with Kafka.

## Architecture
- **Order Service**: REST API for order management, produces events to Kafka
- **Notification Service**: Consumes events and sends notifications
- **Apache Kafka**: Message broker for event streaming
- **H2 Database**: In-memory database for each service

## Prerequisites
- Docker and Docker Compose
- Java 21
- Maven 3.9+

## Running the Application

### Option 1: Using Docker Compose (Recommended)
```bash
# Clone the repository
git clone <repository-url>
cd challenge-event-driven

# Build and run all services
docker-compose up --build   or docker-compose up --build -d

# Services will be available at:
# - Order Service: http://localhost:8080
# - Notification Service: http://localhost:8081
# - Kafka: localhost:9092
# - Order Service H2 Console: http://localhost:8080/h2-console
# - Notification Service H2 Console: http://localhost:8081/h2-console

# Swagger url
* For order-service
 - http://localhost:8080/swagger-ui.html
 * For notification-service
 - http://localhost:8081/swagger-ui.html
