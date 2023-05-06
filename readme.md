# EDPN.IO

## Table of Contents

1. [purpose and architecture](#purpose-and-architecture)
2. [project structure](#project-structure)
3. [API Endpoints](#api-endpoints)
4. [Lombok and JPA Best Practices Coding Guide](#Lombok-and-JPA-Best-Practices-Coding-Guide)
5. [JPA entity management Best Practices and guidelines](#JPA-entity-management-Best-Practices-and-guidelines)

---
## Purpose and architecture 

This project is a Spring Boot application that receives data in the form of messages form [EDDN](https://github.com/EDCD/EDDN). These messages are transformed and store in a database. This project also implements a RESTful API for retrieving this stored data stored.  

The project follows a hexagonal architecture pattern and adheres to Domain-Driven Design (DDD) principles.

---
## Project Structure

The project is structured into the following packages:

```
src
└── main
├── java
│   └── com
│       └── example
│           └── stations
│               ├── application
│               │   ├── controller
│               │   ├── dto
│               │   ├── mapper
│               │   └── usecase
│               ├── configuration
│               ├── domain
│               │   ├── model
│               │   └── repository
│               │   └── util
│               ├── infrastructure
│               │   ├── adapter
│               │   └── persistence
│               │       ├── entity
│               │       └── repository
│               └── StationsApplication.java
└── resources
├── application.properties
└── ...
```

---
### Application Layer

The application layer contains the following components:

- **controller**: This package contains the REST controllers, which handle incoming HTTP requests and provide appropriate responses.
- **dto**: This package contains the Data Transfer Objects (DTOs) used for communication between the application layer and external clients.
- **mapper**: This package contains mappers responsible for converting between DTOs and domain models.
- **service**: This package the implementations of the use cases.
- **usecase**: This package contains use case classes that represent the core business logic of the application.

---
### Configuration Layer

The config layer contains all the Bean configurations and annotations needed to instantiate the beans and bootstrap the Spring boot application

---
### Domain Layer

The domain layer contains the following components:

- **model**: This package contains the domain models (entities and value objects) that represent the core concepts of the problem domain.
- **repository**: This package contains the repository interfaces that define the contract for persisting and retrieving domain models.
- **repository**: This package contains utility classes and interfaces that do not directly relate to the program functionality, like custom map or list implementations.

---
### Infrastructure Layer

The infrastructure layer contains the following components:

- **adapter**: This package contains adapter classes that implement the repository interfaces from the domain layer by utilizing the persistence layer's repositories.
- **zmq**: This package contains the message handlers components for [Elite Dangerous Data Network](https://github.com/EDCD/EDDN), such as message handlers transformers and selectors. 
- **persistence**: This package contains the persistence-related components, such as Mybatis mappers and repositories.

### Building and Running the Application

Prior to running the application some infrastructure needs to be added. 
Run the following command in the project's root directory:
```
docker compose up
```

Edit your runtime configuration environment variables with:
```
EDPN_DB_URL=jdbc:postgresql://localhost:5432/postgres;EDPN_MONGO_AUTHENTICATION_DATABASE=admin;EDPN_MONGO_DATABASE_NAME=mongo;EDPN_MONGO_HOST=localhost;EDPN_MONGO_PASSWORD=mongodb;EDPN_MONGO_PORT=27017;EDPN_MONGO_USERNAME=mongodb;EDPN_PASSWORD=postgres;EDPN_USERNAME=postgres;KAFKA_URL=localhost:9092
```


To build the application, run the following command in the project's root directory:

```
./mvnw clean install
```

To run the application, execute the following command in the project's root directory:

```
./mvnw spring-boot:run
```

The application will start and expose the RESTful API endpoints at `http://localhost:8080/api/stations`.

---
## API Endpoints

todo: this section needs work as this is example code

The following endpoints are available:

- `POST /api/stations`: Create a new station.
- `GET /api/stations`: Retrieve a list of all stations.
- `GET /api/stations/{id}`: Retrieve a station by its ID.
- `PUT /api/stations/{id}`: Update a station by its ID.
- `DELETE /api/stations/{id}`: Delete a station by its ID.

For detailed information about request and response payloads, please refer to the `StationRequest`, `StationResponse` classes and the `StationController` class.

---
## # MyBatis and Liquibase Configuration

This section provides an overview of the MyBatis and Liquibase configuration in our project. Both technologies play a vital role in handling database operations.

### MyBatis Configuration

MyBatis is a popular persistence framework that offers support for custom SQL, stored procedures, and advanced mappings. We chose MyBatis over JPA for this project due to its flexibility in handling complex database operations.

#### Annotation-based Configuration

In our project, MyBatis configuration is achieved entirely through annotations. Mappers are marked with the `@Mapper` annotation, and queries are specified within the interfaces using annotations. For more details on the bean configuration, refer to the `configuration.io.edpn.edpnbackend.commoditymessageprocessor.MyBatisConfiguration` class.

### Liquibase Configuration

Liquibase is a powerful open-source, database-independent library used for tracking, managing, and applying database schema changes. In this project, Liquibase is responsible for handling database migrations and changes.

#### XML-based Configuration

Liquibase's configuration is defined in XML format. The master file, `src/main/resources/db/changelog/db.changelog-master.xml`, contains a list of changeSets that need to be applied in a specific order. Each changeSet is an individual XML file that specifies the database changes to be executed.

For every changeSet, a unique ID and an author must be provided. The unique ID is a UUID, and the author should be your GitHub username. This information helps track the history of database schema changes and the responsible contributors.

For more information on MyBatis and Liquibase, refer to their official documentation:

- [MyBatis Documentation](https://mybatis.org/mybatis-3/index.html)
- [Liquibase Documentation](https://www.liquibase.org/documentation/index.html)
