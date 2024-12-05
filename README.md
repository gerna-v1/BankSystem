# BankSystem

BankSystem is a comprehensive banking application built using Spring Boot, Spring Security, MongoDB, and React. It provides a secure and efficient platform for managing banking operations, including account management, transactions, and user authentication.

## Getting Started

These instructions will give you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Requirements for the software and other tools to build, test, and push:

- [Java 17 or higher](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Node.js and npm](https://nodejs.org/)
- [MongoDB](https://www.mongodb.com/)

#### or

- [Docker](https://www.docker.com/)

### Installing (with Docker)

A step-by-step series of examples that tell you how to get a development environment running:

1. Clone this repository:

```bash
git clone https://github.com/yourusername/BankSystem.git
cd BankSystem
```

2. Run your terminal at the root of the project:

```bash
docker-compose up
```

3. After building, open `localhost:3000` in your browser to access the interface.

## Running the tests

You can run tests to check if all code works as it should, exampled belowed:

```bash
./gradlew test
```

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [MongoDB](https://www.mongodb.com/)
- [Redis](https://redis.io/)
- [React](https://reactjs.org/)

## Authors

- **gerna-v1** - *Initial work* - [gerna-v1](https://github.com/gerna-v1)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## TODO

- Create documentation
- Rework build system
- Deploy this project
