# Fitness Centre Microservices Project

This project, developed for a University subject titled "Software Components," aims to create a microservices application for a fitness centre. The application comprises three main services utilizing Java Spring: <br>

1. **User Service**
2. **Training Reservation Service**
3. **Notification Service**

A frontend interface is planned for integration after the completion of these three services.

## Running Locally

To run the application locally, execute the `{service_name}ServisApplication` file for each of the three services. Access databases and the Swagger UI for testing purposes using the following links: <br>

**User Service**

| Name                     | Access link                                                                                                      |
|--------------------------|------------------------------------------------------------------------------------------------------------------|
| Database                 | [http://localhost:8081/user-service/h2-console](http://localhost:8081/user-service/h2-console)                   |
| JDBC Driver              | jdbc:h2:mem:user-service-db                                                                                      |
| Swagger UI for API calls | [http://localhost:8081/user-service/swagger-ui.html#/](http://localhost:8081/user-service/swagger-ui.html#/)     |

**Notification Service**

| Name                     | Access link                                                                                                       |
|--------------------------|-------------------------------------------------------------------------------------------------------------------|
| Database                 | [http://localhost:8083/notif-service/h2-console](http://localhost:8083/notif-service/h2-console)                  |
| JDBC Driver              | jdbc:h2:mem:notif-service-db                                                                                      |
| Swagger UI for API calls | [http://localhost:8083/notif-service/swagger-ui.html#/](http://localhost:8083/notif-service/swagger-ui.html#/)    |
