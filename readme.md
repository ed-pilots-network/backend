# EDDB.IO 2

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
## Lombok and JPA Best Practices Coding Guide

This coding guide is based on the best practices described in the article: [Lombok and JPA: What may go wrong?](https://www.jpa-buddy.com/blog/lombok-and-jpa-what-may-go-wrong/). The goal of this guide is to provide a set of recommendations to avoid common pitfalls when using Lombok and JPA together in Java projects.

### Table of Contents

1. [Avoiding `@EqualsAndHashCode` on Entities](#avoiding-equalsandhashcode-on-entities)
2. [Using `@ToString` with Caution](#using-tostring-with-caution)
3. [Using `@NoArgsConstructor` Properly](#using-noargsconstructor-properly)
4. [Avoiding `@Data` on Entities](#avoiding-data-on-entities)
5. [Utilizing Lombok's `@Builder` Wisely](#utilizing-lomboks-builder-wisely)

### Avoiding `@EqualsAndHashCode` on Entities

- Lombok's `@EqualsAndHashCode` generates `equals()` and `hashCode()` methods based on all non-static fields of the class. However, using this annotation on JPA entities can cause problems when an entity has a bidirectional relationship with another entity.
- Instead of using `@EqualsAndHashCode`, it's better to manually implement `equals()` and `hashCode()` methods using the entity's primary key (usually the `id` field) as the basis for comparison. See section [Guidelines for Implementing Equals and HashCode in JPA Entities](#Guidelines for Implementing Equals and HashCode in JPA Entities)

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

---
## JPA entity management Best Practices and guidelines

This guide provides best practices for using the Java Persistence API (JPA) to handle entities, manage relationships, and avoid common pitfalls such as detached entities and lazy loading issues.

In general preference goes to annotating the method which initiates 'a unit of work', which opens a connection to the database during which the connection should not be closed. In most cases this is a service or facade that fetches, updates entities. A service might touch on multiple entities, should the method fail, all of the changes should be rolled back in one DB transaction.

### Table of Contents

1. [Entity Relationships](#entity-relationships)
2. [Fetching Strategies](#fetching-strategies)
3. [Handling Detached Entities](#handling-detached-entities)

## Entity Relationships

JPA supports three types of entity relationships:

1. One-to-One
2. One-to-Many / Many-to-One
3. Many-to-Many

Use the appropriate JPA annotations to define these relationships:

- `@OneToOne`
- `@OneToMany` and `@ManyToOne`
- `@ManyToMany`

For detailed examples on how to use these annotations, refer to the [official JPA documentation](https://javaee.github.io/tutorial/persistence-intro005.html).


### Fetching Strategies

JPA provides two fetching strategies: `FetchType.LAZY` and `FetchType.EAGER`. Use `FetchType.LAZY` to improve performance by loading related entities only when they are explicitly accessed. Use `FetchType.EAGER` when you always need to access related entities.

#### When to use FetchType.LAZY

- You don't always need related entities
- You have large datasets
- You want to optimize database queries

#### When not to use FetchType.LAZY

- You always need related entities
- You have small datasets
- You encounter issues with detached entities

### Handling Detached Entities

A detached entity is an instance that is no longer associated with the persistence context (EntityManager). Detached entities can lead to issues like the LazyInitializationException when trying to access lazy-loaded relationships or potential loss of updates if detached entities are modified outside of the persistence context.

Here are some ways to handle or prevent detached entities:

#### 1. Use extended persistence context

In a stateful environment like EJB, use an extended persistence context that spans multiple transactions to keep the EntityManager open until the stateful session bean is removed.

```java
@Stateful
public class MyStatefulBean {
@PersistenceContext(type = PersistenceContextType.EXTENDED)
private EntityManager em;

    // Your business methods here
}
```

#### 2. Re-attach detached entities

Re-attach detached entities to a new persistence context using the `merge()` method. The `merge()` method will return a managed copy of the entity with the changes, and any further changes to the managed copy will be tracked by the EntityManager.

```java
public void updateEntity(Entity entity) {
    Entity managedEntity = em.merge(entity);
    // Make changes to managedEntity
}
```

#### 3. Use FetchType.EAGER

Switch to FetchType.EAGER for required associations to avoid LazyInitializationException. Be cautious with this approach, as it can lead to performance issues due to loading all related data upfront.

#### 4. Fetch data manually

Fetch the required data manually using JPQL, Criteria API, or native SQL queries with a fetch join clause to load the related data before the EntityManager is closed or the entity becomes detached.

```java
public List<Entity> getEntitiesWithRelatedData() {
    TypedQuery<Entity> query = em.createQuery("SELECT e FROM Entity e JOIN FETCH e.relatedData", Entity.class);
    return query.getResultList();
}
```

#### 5. Use `@Transactional` annotation

In a Spring-based application, use the `@Transactional` annotation to manage transactions and persistence contexts automatically. The `@Transactional` annotation can be applied at the class or method level, and it ensures that the EntityManager remains open within the scope of the annotated method or class.

```java
@Service
public class MyService {
@Autowired
private EntityManager em;

    @Transactional
    public void updateEntity(Entity entity) {
        // Make changes to the entity
    }
}
```

---
## JPA Entities Equals and HashCode Best Practises and guidelines 

Implementing `equals()` and `hashCode()` in JPA entities can be tricky due to the way JPA handles lazy loading and detached entities.

In summary, prefer using natural keys when available, and handle `null` cases carefully when using primary keys. Avoid using relationships in `equals()` and `hashCode()` to ensure proper lazy loading behavior.

### 1. Use a natural/business key if possible

If your entity has a natural key (a unique attribute or combination of attributes that identifies an entity), use it to implement `equals()` and `hashCode()`. This approach works well for detached entities and lazy loading since the natural key doesn't depend on the entity's state.

Example:

```java
@Entity
public class Person {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @Column(unique = true, nullable = false)
    private String socialSecurityNumber;

    // Other fields, getters, and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person that = (Person) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(socialSecurityNumber)
                .toHashCode();
    }
}
```

### 2. If there's no natural/business key, use the primary key (with caution)

Using the primary key (`id` in most cases) is not ideal because it can cause issues when working with detached entities and new (transient) instances. However, you can still use it as a last resort. Make sure you handle the `null` cases properly.

Example:

```java
import java.util.Optional;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Other fields, getters, and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person that = (Person) o;
        if (id == null || person.id == null) {
            return false;
        }
        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return Optional.ofNullable(id)
                .map(id -> new HashCodeBuilder(17, 37)
                        .append(id)
                        .toHashCode())
                .orElse(0);
    }
}
```

Be aware that using primary keys in `equals()` and `hashCode()` may cause issues when working with collections containing new or detached entities. So, use this approach with caution.

### 3. Avoid using any relationships in `equals()` and `hashCode()`

Using relationships in these methods might cause unnecessary loading of related entities, and it can lead to performance issues. Stick to using simple, unique attributes when implementing `equals()` and `hashCode()`.

---