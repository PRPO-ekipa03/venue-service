# Venue Service Microservice

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Setup and Installation](#setup-and-installation)
  - [Prerequisites](#prerequisites)
  - [Clone the Repository](#clone-the-repository)
  - [Build the Project](#build-the-project)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
  - [Locally](#locally)
  - [Using Docker](#using-docker)
- [API Documentation](#api-documentation)
- [Endpoints Overview](#endpoints-overview)
  - [Reservations Endpoints](#reservations-endpoints)
  - [Venues & Ratings Endpoints](#venues--ratings-endpoints)
- [Error Handling](#error-handling)

## Overview
The **Venue Service** is a Spring Boot-based microservice designed to handle venue reservations and ratings. It includes features for managing reservations, integrating with external services, and providing a user-friendly API. The service utilizes PostgreSQL for persistent storage, RabbitMQ for message queuing, and OpenAPI for API documentation.

## Features
- Create, update, retrieve, and cancel reservations.
- Manage venue details and retrieve available venues by filters.
- Add and retrieve user ratings for venues.
- RabbitMQ-based message handling for asynchronous operations, specifically receiving payment confirmation from payment service.
- Comprehensive API documentation via Swagger.
- Integration with external user services for user validation.

## Technologies Used
- **Java 21** with **Spring Boot**
- **Spring Data JPA** for ORM
- **PostgreSQL** as the database
- **RabbitMQ** for messaging
- **MapStruct** for DTO mapping
- **OpenAPI/Swagger** for documentation
- **Maven** for build management
- **Docker** for containerization

## Architecture
The microservice is built on a layered architecture:
- **Controller Layer**: Handles REST API requests.
- **Service Layer**: Contains business logic.
- **Repository Layer**: Interfaces with the database.
- **Messaging Layer**: Manages RabbitMQ interactions.
- **Mapper Layer**: Converts between entities and DTOs.

## Setup and Installation

### Prerequisites
- Java Development Kit (JDK) 21
- Maven 3.9+
- PostgreSQL
- RabbitMQ
- Docker (optional)

### Clone the Repository
```bash
git clone https://github.com/PRPO-ekipa03/venue-service.git
cd venueservice
```

### Build the Project
To build the project, use Maven:
```bash
mvn clean package -DskipTests
```
This command compiles the code and packages the application into a JAR file located in the target/ directory.

## Configuration
The application reads configuration from environment variables or a properties file. Below are the essential configuration parameters:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# JPA Settings
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

# RabbitMQ Configuration
spring.rabbitmq.host=${MQ_HOST:localhost}
spring.rabbitmq.port=5672
spring.rabbitmq.username=${MQ_USERNAME}
spring.rabbitmq.password=${MQ_PASSWORD}

# User Service
user.service.url=http://${USER_SERVICE_HOST:localhost}:${USER_SERVICE_PORT:8081}/api/users/

# Server Port
server.port=8084
```
Set these as environment variables or modify the application.properties file.

## Running the Application

### Locally
Run the application using the packaged JAR file:  
``bash
java -jar target/venue-service.jar
``  
The service will start on port 8084 or the port specified in your configuration.

### Using Docker
The project includes a `Dockerfile` to containerize the application. Follow these steps to run the service in a Docker container:

1. **Build the Docker Image**:  
```bash
docker build -t venue-service .
```

2. **Run the Docker Container**:  
```bash
docker run -p 8084:8084 \
  -e DB_HOST=your_db_host \
  -e DB_PORT=your_db_port \
  -e DB_NAME=your_db_name \
  -e DB_USERNAME=your_db_username \
  -e DB_PASSWORD=your_db_password \
  -e MQ_HOST=your_mq_host \
  -e MQ_USERNAME=your_mq_username \
  -e MQ_PASSWORD=your_mq_password \
  -e USER_SERVICE_HOST=your_user_service_host \
  -e USER_SERVICE_PORT=your_user_service_port \
  venue-service
```

---

## API Documentation
The service provides interactive API documentation through Swagger UI. Access it at:  
``  
http://localhost:8084/venues/swagger-ui  
``  

---

## Endpoints Overview

### Reservations Endpoints
- **Create a Reservation**: `POST /api/reservations`
- **Update a Reservation**: `PUT /api/reservations/{reservationId}`
- **Cancel a Reservation**: `DELETE /api/reservations/{reservationId}`
- **Retrieve a Reservation by ID**: `GET /api/reservations/{reservationId}`
- **List Reservations for a Venue**: `GET /api/reservations/venues/{venueId}`
- **Get Reservations within a Date Range**: `GET /api/reservations/venues/{venueId}/range`
- **Retrieve User Reservations**: `GET /api/reservations/user`

### Venues & Ratings Endpoints
- **Create a Venue**: `POST /api/venues`
- **Update a Venue**: `PUT /api/venues/{venueId}`
- **Delete a Venue**: `DELETE /api/venues/{venueId}`
- **Get a Venue by ID**: `GET /api/venues/{venueId}`
- **List All Venues**: `GET /api/venues`
- **Add a Rating to a Venue**: `POST /api/venues/{venueId}/ratings`
- **Get Ratings for a Venue**: `GET /api/venues/{venueId}/ratings`

---

## Error Handling
The service employs global exception handling for consistent and meaningful error responses. Common errors include:

- **400 Bad Request**: Validation errors or invalid input.
- **404 Not Found**: Resource not found (e.g., reservation or venue).
- **409 Conflict**: Data conflicts (e.g., duplicate reservations).
- **500 Internal Server Error**: Unexpected server-side errors.
- **503 Service Unavailable**: Issues with external services.

