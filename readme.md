# Ecommerce Scalable Backend

This repository contains the backend implementation for a scalable ecommerce application. The architecture is designed using microservices to ensure modularity, scalability, and maintainability.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Microservices Overview](#microservices-overview)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Environment Variables](#environment-variables)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features
- **Microservices Architecture**: Decoupled services for user management, product handling, orders, shopping cart, notifications, and a gateway for API routing.
- **Scalability**: Each service can be scaled independently to handle increased load.
- **Dockerized Services**: Containerized using Docker for consistent environments and easy deployment.
- **API Gateway**: Centralized entry point for managing and routing requests to appropriate services.

## Tech Stack
- **Java**: Java runtime for building scalable network applications.
- **Docker**: Containerization platform for packaging applications.
- **MongoDB**: NoSQL database for data storage.
- **PostgreSQL**: Sql database for data storage.
- **Redis**: For caching.
- **ELK**: For service log monitoring.
- **Kafka**: For messaging queue.
- **JWT**: JSON Web Tokens for authentication.
- **JavaMailSender**: Module for sending emails.
- **Websockets**: For Chat management between users and sellers.

## Microservices Overview

1. **User Service**: Manages user registration, authentication, and profile management.
2. **Product Service**: Handles product listings, categories, and inventory management.
3. **Order Service**: Manages order creation, status updates, and order history.
4. **Shopping Cart Service**: Manages users' shopping carts, including adding/removing items.
5. **Notification Service**: Sends email and SMS notifications to users.
7. **ChatService**: Manages conversation between customers and sellers.
8. **Delivery service**: Manages delivery with real-time tracking using GPS.

## Additional Components:
In addition to the core microservices, the following components were included to enhance the scalability, reliability, and manageability of the e-commerce platform:

1. **API Gateway**: Serves as the entry point for all client requests, routing them to the appropriate microservice. Used Spring Cloud Gateway for this purpose.
2. **Service Discovery**: Automatically detects and manages service instances. I used Eureka for this purpose.
3. **Centralized Logging**: Aggregates logs from all microservices for easy monitoring and debugging. Used the ELK stack (Elasticsearch, Logstash, Kibana) for this purpose.
4. **Docker & Docker Compose**: Containerize each microservice and manage their orchestration, networking, and scaling. Docker Compose can be used to define and manage multi-container applications.
5. **CI/CD Pipeline**: Automates the build, test, and deployment process of each microservice. Used GitHub Actions for this purpose.
6. **Caching**: Caching data for the most made request to minimise database queries and improve efficiency. Redis is used for this purpose.

## Architecture

The application follows a microservices architecture, with each service running independently and communicating through HTTP. Docker is used to containerize each service, ensuring consistency across different environments.

## Prerequisites

- **Docker**: Ensure Docker is installed on your system. You can download it from the [official website](https://www.docker.com/get-started).

## Installation

1. **Clone the repository**:
   ```bash
   git clone git@github.com:jumapaul/E-commerce-Backend-App.git
   cd E-commerce-Backend-App
   ```
2. **Variables addition**
- Add the necessary environment variables to Docker Compose and the various service configurations in the config server, and run.
```bash
docker compose up -d
```
This project is an implementation of an idea taken from Roadmap.sh
https://roadmap.sh/projects/scalable-ecommerce-platform



