# Garage Management Service (Renault)

## Overview

This project is a **full‑stack monolithic application** developed in the context of the Renault network.  
It provides a complete solution for managing **garages, vehicles, and accessories**, combining a **Spring Boot REST API** with a modern **Angular frontend**.

---

## Technical Choices
### Backend
- **Java 21** 
- **Spring Boot 3.3.5**
- **Gradle**
- **Swagger / OpenAPI**
- **Messaging** : KAFKA + ZOOKEEPER
- **Testing** : Unit and integration Tests
- **Database**:
    - **PostgreSQL** (runtime)
    - **Flyway** for reproducible database schema and initial data.
    - **H2** for fast and isolated integration testing. 
### Frontend
- **Angular 21**
- **AG Grid** for advanced data tables

---

## Implemented Features

### Garages
- CRUD operations on garages
- Pagination and sorting on garage listing
- Management of opening hours using a structured domain model
- Retrieve garages by **vehicle brand** that contain **at least one accessory**
- Enforcement of a maximum capacity of **50 vehicles per garage**

### Vehicles
- CRUD operations for vehicles associated with a garage
- Ability to retrieve vehicles per brand, garage
- **Kafka event publication** (`vehicle.created`) on vehicle creation

### Accessories
- CRUD operations on accessories linked to a vehicle
- Retrieval of accessories per vehicle

---



## Project Structure
```
src/main/java/com/example/garage_test
├── accessory
├── garage
│   ├── controller
│   ├── dto
│   ├── mapper
│   ├── model
│   ├── repository
│   ├── service
│   └── utils
├── vehicle
├── security
└── GarageTestApplication

src/main/resources
├── db.migration
├── application.properties
```
---

## Running the Application

### Build & Run
- **Backend(Gradle):**
```bash
./gradlew clean build
./gradlew bootRun
```

- **Frontend(Angular):**
```bash
npm install
ng serve
```

---

## API Documentation

Swagger UI is available at:

```
http://localhost:8082/swagger-ui/index.html
```

---

## Kafka Setup & Verification

### Start Kafka (Ubuntu)

```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

### Consume Vehicle Creation Events  
**Topic: vehicle.created**

```bash
bin/kafka-console-consumer.sh --topic vehicle.created --from-beginning --bootstrap-server 172.21.214.38:9092
```

---

## Testing

```bash
./gradlew test
```

- Integration tests run on **H2**
- Flyway migrations are executed automatically

---

