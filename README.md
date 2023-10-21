# Wex API

Wex API is an application designed for financial transaction management, allowing for transaction recording and currency conversion.

## Technologies Used

- **Java 17**
- **Spring Boot**: Framework for creating standalone Java applications.
- **Spring Data JPA**: Provides support for creating JPA repositories.
- **Spring Web**: Spring Framework module for building web applications.
- **Lombok**: Java library that helps to eliminate boilerplate code.
- **H2 Database**: In-memory database, used for development purposes.
- **Flyway**: Database migration tool.
- **JUnit 5**: Testing framework for Java.
- **Mockito**: Mocking framework for Java tests.
- **MapStruct**: Code source mapping library for Java.
- **OpenAPI (Swagger)**: Framework for designing, building, documenting, and using REST services.

## Features

1. **Save a Purchase Transaction**:
2. **Convert  a purchase amount from Dollar to a specific currency**:

## API Endpoints

1. Save a transaction: POST `/api/v1/transactions`
2. Get a transaction with conversion: GET `/api/v1/transactions/{uuid}/convert?currency=[currency_code]`

## Setup Instructions

1. Clone this repository.
2. Install the dependencies using Maven: `mvn install`.
3. Start the application using `mvn spring-boot:run`.
4. Visit Swagger UI at `http://localhost:8080/swagger-ui/index.html` to view and test the endpoints.

## Contribution

Feel free to contribute to this project by forking and submitting a pull request.
