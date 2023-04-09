# EDDB.IO 2

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

The config layer contains all the Bean configurations and annotations need to instantiate the beans and bootstrap the Spring boot application

---
### Domain Layer

The domain layer contains the following components:

- **model**: This package contains the domain models (entities and value objects) that represent the core concepts of the problem domain.
- **repository**: This package contains the repository interfaces that define the contract for persisting and retrieving domain models.

---
### Infrastructure Layer

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

---
## API Endpoints (this section needs work as this is example code)

The following endpoints are available:

- `POST /api/stations`: Create a new station.
- `GET /api/stations`: Retrieve a list of all stations.
- `GET /api/stations/{id}`: Retrieve a station by its ID.
- `PUT /api/stations/{id}`: Update a station by its ID.
- `DELETE /api/stations/{id}`: Delete a station by its ID.

For detailed information about request and response payloads, please refer to the `StationRequest`, `StationResponse` classes and the `StationController` class.

---
## Lombok and JPA Best Practices Coding Guide

This coding guide is based on the best practices described in the article: [Lombok and JPA: What may go wrong?](https://www.jpa-buddy.com/blog/lombok-and-jpa-what-may-go-wrong/). The goal of this guide is to provide a set of recommendations to avoid common pitfalls when using Lombok and JPA together in Java projects.

### Table of Contents

1. [Introduction](#introduction)
2. [Avoiding `@EqualsAndHashCode` on Entities](#avoiding-equalsandhashcode-on-entities)
3. [Using `@ToString` with Caution](#using-tostring-with-caution)
4. [Using `@NoArgsConstructor` Properly](#using-noargsconstructor-properly)
5. [Avoiding `@Data` on Entities](#avoiding-data-on-entities)
6. [Utilizing Lombok's `@Builder` Wisely](#utilizing-lomboks-builder-wisely)
7. [Conclusion](#conclusion)

### Introduction

Lombok is a widely used Java library that helps to reduce boilerplate code in Java projects. JPA (Java Persistence API) is a popular framework for mapping Java objects to relational databases. While both Lombok and JPA are powerful tools, there are certain pitfalls to watch out for when using them together. This guide will help you avoid these pitfalls and use Lombok and JPA effectively.

### Avoiding `@EqualsAndHashCode` on Entities

- Lombok's `@EqualsAndHashCode` generates `equals()` and `hashCode()` methods based on all non-static fields of the class. However, using this annotation on JPA entities can cause problems when an entity has a bidirectional relationship with another entity.
- Instead of using `@EqualsAndHashCode`, it's better to manually implement `equals()` and `hashCode()` methods using the entity's primary key (usually the `id` field) as the basis for comparison.

### Using `@ToString` with Caution

- Lombok's `@ToString` generates a `toString()` method that includes all non-static fields of the class. However, using this annotation on JPA entities with relationships may cause unintended side effects such as LazyInitializationException or performance issues due to unintended fetching of related entities.
- To avoid these issues, use `@ToString` with the `exclude` parameter to exclude related entities or manually implement a `toString()` method that only includes the desired fields.

### Using `@NoArgsConstructor` Properly

- JPA requires a no-args constructor for all entities, and Lombok's `@NoArgsConstructor` provides a convenient way to generate one. However, using this annotation with the default `access` parameter can cause issues when combined with other Lombok annotations.
- To avoid these issues, set the `access` parameter to `AccessLevel.PROTECTED` or `AccessLevel.PACKAGE`. This allows JPA to use the constructor without exposing it unnecessarily to other classes.

### Avoiding `@Data` on Entities

- Lombok's `@Data` is a convenient shortcut that combines several Lombok annotations, including `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode`, and `@RequiredArgsConstructor`. However, using this annotation on JPA entities can cause problems due to the inclusion of `@EqualsAndHashCode` and `@ToString`.
- Instead of using `@Data`, apply the required Lombok annotations individually and follow the recommendations from previous sections for `@EqualsAndHashCode` and `@ToString`.

### Utilizing Lombok's `@Builder` Wisely

- Lombok's `@Builder` simplifies the creation of complex objects by providing a fluent builder pattern. However, using this annotation on JPA entities may cause issues with JPA's requirement for a no-args constructor and other lifecycle callbacks.
- To use `@Builder` effectively with JPA entities, consider the following steps:
    1. Add `@NoArgsConstructor(access = AccessLevel.PROTECTED)` or `@NoArgsConstructor(access = AccessLevel.PACKAGE)` to your JPA entity.
    2. If your entity has fields with default values, use `@Builder.Default` on those fields to ensure the builder sets the default values correctly.
    3. If your entity has any lifecycle callbacks (e.g., `@PrePersist`, `@PreUpdate`, etc.), create a private/protected method for each callback, and call these methods explicitly in the builder's `build()` method. This ensures that the lifecycle callbacks are executed when using the builder.

### Conclusion

Lombok and JPA are powerful tools that can simplify Java development significantly. However, when using them together, it's essential to follow best practices to avoid common pitfalls. By following the recommendations in this guide, you can use Lombok and JPA effectively and minimize potential issues in your projects.

