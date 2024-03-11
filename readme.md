# Elite Dangerous Pilot Network Backend (EDPN Backend)
The Elite Dangerous Pilot Network Backend (EDPN Backend) project provides a REST API that consumes data from the Elite Dangerous Data Network (EDDN) message stream. The data is then processed and stored in a database for use by other applications.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
    - [Code structure](#code-structure)
- [Installation](#installation)
- [Local Development](#local-development)
- [Deploying](#deploying)
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
The EDPN Backend project consists of several independent modules which are then used by the `boot` module to server them to the end user:

### Code structure
The projects follow a hexagonal architecture pattern and adheres to Domain-Driven Design (DDD) principles.

```
src
├── main
│   ├── java
│   │   └── com
│   │      └── example
│   │          └── stations
│   │              ├── adapter
│   │              │   ├── config
│   │              │   ├── kafka
│   │              │   │   ├── dto
│   │              │   │   ├── processor
│   │              │   │   └── sender
│   │              │   ├── persistence
│   │              │   │   └── entity
│   │              │   └── web
│   │              │       └── dto
│   │              └── application
│   │                  ├── domain
│   │                  │   └── exception
│   │                  ├── port
│   │                  │   └── incomming
│   │                  │   └── outgoing
│   │                  ├── service
│   │                  └── validation
│   └── resources
└── test
```

#### Adapter Layer

The adapter layer serves as a bridge between the external components and the core application logic. It contains the following components:

- **config**: This package contains all the configurations related to the application.
- **kafka**:
    - **dto**: This package contains the Data Transfer Objects (DTOs) specific to the kafka integration.
    - **processor**: This package contains the processors for handling kafka messages.
    - **sender**: This package contains classes for sending messages to kafka topics.
- **persistence**:
    - **entity**: This package contains the entity objects used for database interaction.
- **web**:
    - **dto**: This package contains the Data Transfer Objects (DTOs) used for communication between the web layer and the application layer.

#### Application Layer

The application layer is the heart of the hexagonal architecture, containing the core logic and rules of the application. It contains the following components:

- **domain**:
    - **exception**: This package contains custom exceptions related to the domain logic.
- **dto**: This package contains the main Data Transfer Objects (DTOs) used within the application layer.
- **port**:
    - **incomming**: This package defines the incoming ports, which expose the application's core functionality to the adapter layer.
    - **outgoing**: This package defines the outgoing ports, which delegate external concerns, such as persistence, to the adapter layer.
- **service**: This package contains the service implementations, encapsulating the core business logic of the application.
- **validation**: This package contains validation classes for ensuring the integrity of the data within the application layer.

### Explanation of Hexagonal Architecture in the Context

In this architecture, the Application Layer encapsulates the core business logic, and it's isolated from external concerns, allowing for flexibility and maintainability. The Adapter Layer acts as the interface between external dependencies (such as databases, message queues, and web controllers) and the core logic within the Application Layer.

Through this structure, the system can easily adapt to changes in external dependencies or technologies without affecting the core business rules and logic. The separation of concerns provides a clear and maintainable path for both development and future evolution.

This design also aligns well with Domain-Driven Design principles by emphasizing a rich and expressive domain model and placing the primary focus on the core problem domain.

___
## Data flow
The EDPN Backend project, follow the following data flow:

1. The `eddn-message-listener` project to consumes the EDDN message stream and store it in a Kafka
2. The message processor projects consume and process the data from the Kafka stream and store it in a Postgres database
3. The REST APIs access the Postgres database to serve the data

___
## Installation
To install and run the EDPN Backend project, follow these steps:

1. Install Docker
2. install Docker-compose
3. run the following command from the root project folder: `docker compose -f docker-compose.yml up -d`. This command will:
   - create a stack which contains a Zookeeper, a Kafka, and a postgres
   - pull and run initialize the `eddn-message-listener` container
   - containerize the code via the dockerfile included, and run it in the stack

___
## Local Development
to run the stack for local development, follow these steps:

1. Install Docker
2. install Docker-compose
3. run the following command from the root project folder: `docker compose -f docker-compose-localdev.yml up -d`. This command will:
    - create a stack which contains a Zookeeper, a Kafka, and a postgres with the needed ports exposed on localhost
4. Jigsaw modulartiy prevents from running / debuggin directly from IDE, so we wil need to run / debug from gradle
    - either create gradle a run config in IDE or run in terminal from project root the following command
    - `gradle clean bootRun --args='--spring.profiles.active=local'`

**keep in mind that the EddnMessageListener application needs to be run separately to receive messages to process**

in short: docker compose up the stack, run both of the projects from IDE

---
## Deploying
Note to Ops, you need the following files/folders from the repository:

- docker-compose.yml
- .env
- env/dev (if deploying to a hosted dev environment)

Save the files, using the existing folder structure from this repository, starting at `/opt/edpn-backend` - set file permissions appropriately (ops group, and 660 on files). Next, edit the `.env` file, setting `ENVIRONMENT=dev`. 

Finally, in that directory, `docker compose up`. Everything should come up and show healthy within a few minutes.

In future, we should automate this via pipeline, etc.
___
## Reporting Issues
To report an issue with the EDPN Backend project or to request a feature, please open an issue on the project's GitHub repository. You can also join the [discord](https://discord.gg/RrhRmDQD) and make a suggestion there in `ideas` section.

___
## Contributing
How to contribute to the project (and much more) is explained in our [charter](https://github.com/ed-pilots-network/charter).

___
## License
We are  using [Apache-2.0](https://opensource.org/license/apache-2-0/).

___
## Contact
The best way to contact us would be to join our [discord](https://discord.gg/RrhRmDQD) and ping the @Admin or @Developer groups

___
## Further info
### MyBatis
MyBatis is a popular persistence framework that offers support for custom SQL, stored procedures, and advanced mappings. We chose MyBatis over JPA for this project due to its flexibility in handling complex database operations.

#### Annotation-based Configuration
In our project, MyBatis configuration is achieved entirely through annotations. Mappers are marked with the `@Mapper` annotation, and queries are specified within the interfaces using annotations. For more details on the bean configuration, refer to the `io.edpn.backend.trade.cnfiguration.MyBatisConfiguration` class for an example.

### Liquibase
Liquibase is a powerful open-source, database-independent library used for tracking, managing, and applying database schema changes. In this project, Liquibase is responsible for handling database migrations and changes.

#### XML-based Configuration

Liquibase's configuration is defined in XML format. The master file, `src/main/resources/db/changelog/db.changelog-master.xml`, contains a list of changeSets that need to be applied in a specific order. Each changeSet is an individual XML file that specifies the database changes to be executed.

For every changeSet, a unique ID and an author must be provided. The unique ID is a UUID, and the author should be your GitHub username. This information helps track the history of database schema changes and the responsible contributors.

For more information on MyBatis and Liquibase, refer to their official documentation:

- [MyBatis Documentation](https://mybatis.org/mybatis-3/index.html)
- [Liquibase Documentation](https://www.liquibase.org/documentation/index.html)
