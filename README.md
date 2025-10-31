# Expense Service Microservice
A Spring Boot microservice that allows users to manage and track expenses, with inter-service communication to Budget and Notification services. notifications as part of a personal finance management application.

---
# Features
- RESTful APIs for creating and retrieving expenses

- Checks budget exceedance by calling Budget Service via REST

- Sends notifications to Notification Service when budget exceedance occurs

- Uses H2 in-memory database with Spring Data JPA

- OpenAPI (Swagger) documentation for easy API exploration

- Postman collection for API testing

- API-First design with well-defined OpenAPI spec


---
# Project Structure
expense-service/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │     ├── controller/
│   │   │     ├── model/
│   │   │     ├── repository/
│   │   │     ├── config/
│   │   │     └── ExpenseServiceApplication.java
│   │   └── resources/
│   │         ├── application.properties
│   │         └── expense.yaml
├── postman/expense-service.postman_collection.json
├── pom.xml
└── README.md

---
# Getting Started
Prerequisites
Java 17+

Maven 3.5+

(Port 9092 used by default; can be changed in application.properties)

---
# Build and Run
1. Clone the repository

    git clone https://github.com/raviclalwani/expense-service.git

    cd expense

2. Build the project 
   - mvn clean install

3. Run the application 
   - ./mvn spring-boot:run

---
# API Documentation
 - Swagger UI: http://localhost:9092/swagger-ui.html

 - OpenAPI/Swagger Spec: src/main/resources/expense.yaml

---
# H2 Database Console
http://localhost:9092/h2-console

JDBC URL: jdbc:h2:mem:expensedb

Username: sa, Password: (leave blank)

---
# Using Inter-Service Communication
- When creating an expense, the service calls Budget Service (localhost:9090/budgets/{id}) to verify if the expense exceeds the budget.

- If exceeded, a notification is sent to Notification Service (localhost:9091/notifications).

- These calls use synchronous HTTP RestTemplate.

---
# Using the API with Postman

1. Import Collection

    - Go to Postman → Import

    - Select postman/expense-service.postman_collection.json

2. Explore Endpoints

    - Create expenses with POST /expenses

    - View expenses with GET /expenses and /expenses/{id}

---
| Method | URL            | Description        |
| ------ |----------------|--------------------|
| POST   | /expenses      | Create new expense |
| GET    | /expenses | List all expenses  |

---
# Configuration
    Example application.properties:
    server.port=9092
    spring.datasource.url=jdbc:h2:mem:expensedb
    spring.h2.console.enabled=true
    spring.jpa.hibernate.ddl-auto=update

---
# Extending the Service

- Integrate with Expense microservices via REST calls

- Switch to persistent DB by changing datasource configuration

---
# License
Open-source. See LICENSE file (if included).



