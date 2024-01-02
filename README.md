# Fitness Centre Microservices Project

This project, developed for a University subject titled "Software Components," aims to create a microservices application for a fitness centre. The application comprises three main services utilizing Java Spring: <br>

1. **User Service**
2. **Training Reservation Service**
3. **Notification Service**

A frontend interface is planned for integration after the completion of these three services.

## Running Locally

To run the application locally:

1. **Frontend**: From the [frontend](frontend) folder, run `npm run serve`.
2. **Backend**:
    1. Start the [MessageBroker](notif_servis/src/main/java/raf/fitness/notif_servis/MessageBroker.java).
    2. Start the [User service](user_servis/src/main/java/raf/fitness/user_servis/UserServisApplication.java).
    3. Start the [Reservation service](reservation_servis/src/main/java/raf/fitness/reservation_servis/ReservationServisApplication.java).
    4. Start the [Notification service](notif_servis/src/main/java/raf/fitness/notif_servis/NotifServisApplication.java).

Access databases and the Swagger UI for testing purposes using the following links:

**User Service**

| Name                     | Access link                                                                                                      |
|--------------------------|------------------------------------------------------------------------------------------------------------------|
| Database                 | [http://localhost:8081/user-service/h2-console](http://localhost:8081/user-service/h2-console)                   |
| JDBC Driver              | jdbc:h2:mem:user-service-db                                                                                      |
| Swagger UI for API calls | [http://localhost:8081/user-service/swagger-ui.html#/](http://localhost:8081/user-service/swagger-ui.html#/)     |

**Reservation Service**
| Name                     | Access link                                                                                                      |
|--------------------------|------------------------------------------------------------------------------------------------------------------|
| Database                 | [http://localhost:8082/reservation-service/h2-console](http://localhost:8082/reservation-service/h2-console)     |
| JDBC Driver              | jdbc:h2:mem:reservation-service-db                                                                               |
| Swagger UI for API calls | [http://localhost:8082/reservation-service/swagger-ui.html#/](http://localhost:8082/reservation-service/swagger-ui.html#/)|

**Notification Service**

| Name                     | Access link                                                                                                       |
|--------------------------|-------------------------------------------------------------------------------------------------------------------|
| Database                 | [http://localhost:8083/notif-service/h2-console](http://localhost:8083/notif-service/h2-console)                  |
| JDBC Driver              | jdbc:h2:mem:notif-service-db                                                                                      |
| Swagger UI for API calls | [http://localhost:8083/notif-service/swagger-ui.html#/](http://localhost:8083/notif-service/swagger-ui.html#/)    |
