# Elite Dangerous Pilot Network Backend (EDPN Backend)
The Elite Dangerous Pilot Network Backend (EDPN Backend) project provides a REST API that consumes data from the Elite Dangerous Data Network (EDDN) message stream. The data is then processed and stored in a database for use by other applications.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
    - [Code structure](#code-structure)
- [Installation](#installation)
- [Data flow](#data-flow)
- [Reporting Issues](#reporting-issues)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)
- [Further info](#further-info)

___
## Technologies Used
The EDPN Backend project is built using the following technologies:

- [Spring Boot](https://spring.io/projects/spring-boot): An open-source Java-based framework used to create stand-alone, production-grade Spring applications quickly and easily.
- [Maven](https://maven.apache.org/): A build automation tool used to manage dependencies and build Java projects.
- [Kafka](https://kafka.apache.org/): A distributed streaming platform used to build real-time data pipelines and streaming applications.
- [Postgres](https://www.postgresql.org/): An open-source relational database management system used to store data for the EDPN Backend project.
- [MyBatis](https://mybatis.org/): A persistence framework that simplifies the development of Java applications working with relational databases.
- [Liquibase](https://www.liquibase.org/): An open-source database schema migration tool used to track, manage, and apply database changes.
- [Docker](https://www.docker.com/): A platform used to build, ship, and run distributed applications.

___
## Project Structure
The EDPN Backend project consists of several independent projects contained within the root project to separate out the different parts:

- `message-listener`: The project used to consume the EDDN message stream. It will consume the messages, split them out per type and send them to a Kafka.
- `rest`: The project that provides the REST API.
- `message-processors`: A containing folder for the message processor projects that consume the messages from the Kafka.
- `messageprocessor-lib`: A project inside `message-processors` that provides a shared library for the other message processors.
- `commodityv3-processor`: A project that consumes the commodity messages from the Kafka, processes the data, and stores it in the database.

### Code structure
The projects follow a hexagonal architecture pattern and adheres to Domain-Driven Design (DDD) principles.

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

#### Application Layer

The application layer contains the following components:

- **controller**: This package contains the REST controllers, which handle incoming HTTP requests and provide appropriate responses.
- **dto**: This package contains the Data Transfer Objects (DTOs) used for communication between the application layer and external clients.
- **mapper**: This package contains mappers responsible for converting between DTOs and domain models.
- **service**: This package the implementations of the use cases.
- **usecase**: This package contains use case classes that represent the core business logic of the application.

#### Configuration Layer

The config layer contains all the Bean configurations and annotations needed to instantiate the beans and bootstrap the Spring boot application

### Domain Layer

The domain layer contains the following components:

- **model**: This package contains the domain models (entities and value objects) that represent the core concepts of the problem domain.
- **repository**: This package contains the repository interfaces that define the contract for persisting and retrieving domain models.

### Infrastructure Layer

The infrastructure layer contains the following components:

- **adapter**: This package contains adapter classes that implement the repository interfaces from the domain layer by utilizing the persistence layer's repositories.
- **persistence**: This package contains the persistence-related components, such as Mybatis mappers and repositories.

___
## Data flow
The EDPN Backend project, follow the following data flow:

1. The `message-listener` project to consumes the EDDN message stream and store it in a Kafka
2. The message processor projects consume and process the data from the Kafka stream and store it in a Postgres database
3. The REST APIs access the Postgres database to serve the data

___
## Installation
To install and run the EDPN Backend project locally, follow these steps:

1. Install Java 17 or higher
2. Install Maven
2. Install Docker
3. Clone the EDPN Backend project from GitHub
4. run `mvn clean install -f message-processors/messageprocessor-lib/pom.xml` to install the library jar in your local Maven
5. run `docker-compose -f docker-compose.yml up` in terminal to launch the needed local needed infrastructure in docker
6. run the projects with the local profiles:
   1. `mvn spring-boot:run -Dspring-boot.run.profiles=local -f message-listener/pom.xml`
   2. `mvn spring-boot:run -Dspring-boot.run.profiles=local -f message-processors/commodityv3-processor/pom.xml`
   3. `mvn spring-boot:run -Dspring-boot.run.profiles=local -f rest/pom.xml`

___
## Reporting Issues
To report an issue with the EDPN Backend project or to request a feature, please open an issue on the project's GitHub repository. You can also join the [discord](https://discord.gg/RrhRmDQD) and make a suggestion there in `ideas` section.

___
## Contributing
How to contribute to the project (and much more) is explained in our [charter](https://github.com/ed-pilots-network/charter).

___
## License
We are still in license discussion, but we are sure to use a copyleft open source license like ApacheV2 or MIT

___
## Contact
The best way to contact us would be to join our [discord](https://discord.gg/RrhRmDQD) and ping the @Admin or @Developer groups

___
## Further info
### MyBatis
MyBatis is a popular persistence framework that offers support for custom SQL, stored procedures, and advanced mappings. We chose MyBatis over JPA for this project due to its flexibility in handling complex database operations.

#### Annotation-based Configuration
In our project, MyBatis configuration is achieved entirely through annotations. Mappers are marked with the `@Mapper` annotation, and queries are specified within the interfaces using annotations. For more details on the bean configuration, refer to the `configuration.io.edpn.edpnbackend.commoditymessageprocessor.MyBatisConfiguration` class.

### Liquibase
Liquibase is a powerful open-source, database-independent library used for tracking, managing, and applying database schema changes. In this project, Liquibase is responsible for handling database migrations and changes.

#### XML-based Configuration

Liquibase's configuration is defined in XML format. The master file, `src/main/resources/db/changelog/db.changelog-master.xml`, contains a list of changeSets that need to be applied in a specific order. Each changeSet is an individual XML file that specifies the database changes to be executed.

For every changeSet, a unique ID and an author must be provided. The unique ID is a UUID, and the author should be your GitHub username. This information helps track the history of database schema changes and the responsible contributors.

For more information on MyBatis and Liquibase, refer to their official documentation:

- [MyBatis Documentation](https://mybatis.org/mybatis-3/index.html)
- [Liquibase Documentation](https://www.liquibase.org/documentation/index.html)
