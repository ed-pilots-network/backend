# JPA Best Practices Guide

This guide provides best practices for using the Java Persistence API (JPA) to handle entities, manage relationships, and avoid common pitfalls such as detached entities and lazy loading issues.

## Table of Contents

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


## Fetching Strategies

JPA provides two fetching strategies: `FetchType.LAZY` and `FetchType.EAGER`. Use `FetchType.LAZY` to improve performance by loading related entities only when they are explicitly accessed. Use `FetchType.EAGER` when you always need to access related entities.

### When to use FetchType.LAZY

- You don't always need related entities
- You have large datasets
- You want to optimize database queries

### When not to use FetchType.LAZY

- You always need related entities
- You have small datasets
- You encounter issues with detached entities

## Handling Detached Entities

A detached entity is an instance that is no longer associated with the persistence context (EntityManager). Detached entities can lead to issues like the LazyInitializationException when trying to access lazy-loaded relationships or potential loss of updates if detached entities are modified outside of the persistence context.

Here are some ways to handle or prevent detached entities:

### 1. Use extended persistence context

In a stateful environment like EJB, use an extended persistence context that spans multiple transactions to keep the EntityManager open until the stateful session bean is removed.

```java
@Stateful
public class MyStatefulBean {
@PersistenceContext(type = PersistenceContextType.EXTENDED)
private EntityManager em;

    // Your business methods here
}
```

### 2. Re-attach detached entities

Re-attach detached entities to a new persistence context using the `merge()` method. The `merge()` method will return a managed copy of the entity with the changes, and any further changes to the managed copy will be tracked by the EntityManager.

```java
public void updateEntity(Entity entity) {
    Entity managedEntity = em.merge(entity);
    // Make changes to managedEntity
}
```

### 3. Use FetchType.EAGER

Switch to FetchType.EAGER for required associations to avoid LazyInitializationException. Be cautious with this approach, as it can lead to performance issues due to loading all related data upfront.

### 4. Fetch data manually

Fetch the required data manually using JPQL, Criteria API, or native SQL queries with a fetch join clause to load the related data before the EntityManager is closed or the entity becomes detached.

```java
public List<Entity> getEntitiesWithRelatedData() {
    TypedQuery<Entity> query = em.createQuery("SELECT e FROM Entity e JOIN FETCH e.relatedData", Entity.class);
    return query.getResultList();
}
```

### 5. Use `@Transactional` annotation

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

