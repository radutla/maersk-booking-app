## Maersk Container Booking SpringBoot Application

##### A container booking application built on Java 11 with spring boot framework and reactive cassandra, Swagger API specification can be seen on the 
url - http://localhost:8080/swagger-ui/index.html

### Environment:

- Java version: 1.11
- Maven version: 3.*
- Spring Boot version: 2.7.5

### Data:

Example of a booking data JSON object:

```
{
             “containerType” : “DRY”,
             “containerSize” : 20,
             “origin” : “Southampton”,
             “destination” : “Singapore”,
             “quantity” : 5
}
```

### Requirements:

The REST service exposed the `/container/book` endpoint, which allows customers to make container booking.

**POST** request to `/container/book`:

- creates a new booking
- expects a valid booking json object.
- adds the given book to the collection.
- the response code is 201, and the response body is the created booking object.

**GET** request to `/findAll`:

- return a collection of all the booking
- the response code is 200, and the response body is an array of all booking objects

### Notes
The application use cassandra datastax astra database which is deployed on GCP, establishing a connection datastax comes with setup of prerequistes.
1. secure-connect-maersk.zip which has the jks and truststore
2. connection details such as username, password and token.


### Commands

- run:

```bash
mvn clean package; java -jar target/maersk-booking-app-1.0.jar
```

- install:

```bash
mvn clean install
```

- test:

```bash
mvn clean test
```
