# EDDB.IO 2

## Stations Service

This project is a Spring Boot application that implements a simple RESTful API for managing "Stations." The project follows a hexagonal architecture pattern and adheres to Domain-Driven Design (DDD) principles.

### Project Structure

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

#### Application Layer

The application layer contains the following components:

- **controller**: This package contains the REST controllers, which handle incoming HTTP requests and provide appropriate responses.
- **dto**: This package contains the Data Transfer Objects (DTOs) used for communication between the application layer and external clients.
- **mapper**: This package contains mappers responsible for converting between DTOs and domain models.
- **service**: This package the implementations of the use cases.
- **usecase**: This package contains use case classes that represent the core business logic of the application.

#### Configuration Layer

The config layer contains all the Bean configurations and annotations need to instantiate the beans and bootstrap the Spring boot application

#### Domain Layer

The domain layer contains the following components:

- **model**: This package contains the domain models (entities and value objects) that represent the core concepts of the problem domain.
- **repository**: This package contains the repository interfaces that define the contract for persisting and retrieving domain models.

#### Infrastructure Layer

The infrastructure layer contains the following components:

- **adapter**: This package contains adapter classes that implement the repository interfaces from the domain layer by utilizing the persistence layer's repositories.
- **eddn**: This package contains the [Elite Dangerous Data Network](https://github.com/EDCD/EDDN) components, such as message handlers and transformers. 
- **persistence**: This package contains the persistence-related components, such as JPA entities and Spring Data JPA repositories.

### Building and Running the Application

To build the application, run the following command in the project's root directory:

```
./mvnw clean install
```

To run the application, execute the following command in the project's root directory:

```
./mvnw spring-boot:run
```

The application will start and expose the RESTful API endpoints at `http://localhost:8080/api/stations`.

## API Endpoints (this section needs work as this is example code)

The following endpoints are available:

- `POST /api/stations`: Create a new station.
- `GET /api/stations`: Retrieve a list of all stations.
- `GET /api/stations/{id}`: Retrieve a station by its ID.
- `PUT /api/stations/{id}`: Update a station by its ID.
- `DELETE /api/stations/{id}`: Delete a station by its ID.

For detailed information about request and response payloads, please refer to the `StationRequest`, `StationResponse` classes and the `StationController` class.
