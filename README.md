# DEMO CRUD

Project [Spring Boot](http://projects.spring.io/spring-boot/) app.

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.firman.demo.crud.DemoCrudApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

# API SPEC
## GET ALL USER

Request :
- Method : GET
- Endpoint : `/crud/user`
- Header :
    - Content-Type: `application/json`
    - Accept: `application/json`

Response Body :

```json
{
    "status": "0",
    "message": "Success",
    "timestamp": "2021-08-11T15:03:31.832131Z",
    "data": [
        {
            "id": 1,
            "fullName": "Firman Hanafi",
            "username": "hanafifirman",
            "email": "hanafi.fh.firman@gmail.com",
            "address": "Karawang",
            "status": "ACTIVE"
        },
        {
            "id": 3,
            "fullName": "Peter Petrucci",
            "username": "peter11",
            "email": "peter@gmail.com",
            "address": "HONGKONG",
            "status": "ACTIVE"
        }
    ]
}
```

## GET USER BY ID

Request :
- Method : GET
- Endpoint : `/crud/user/{id}`
- Header :
    - Content-Type: `application/json`
    - Accept: `application/json`

Response Body :

```json
{
    "status": "0",
    "message": "Success",
    "timestamp": "2021-08-11T15:57:15.833998Z",
    "data": {
        "id": 4,
        "fullName": "Peter Petrucci",
        "username": "peter11",
        "email": "peter@gmail.com",
        "address": "HONGKONG",
        "status": "ACTIVE"
    }
}
```

## SAVE USER

Request :
- Method : POST
- Endpoint : `/crud/user`
- Header :
    - Content-Type: `application/json`
    - Accept: `application/json`

Request Body :

```json
{
    "fullName":"Peter Petrucci",
    "phoneNumber":"089618012225",
    "username":"peter11",
    "email":"peter@gmail.com",
    "address":"HONGKONG"
}
```

Response Body :

```json
{
    "status": "0",
    "message": "Success",
    "timestamp": "2021-08-11T15:59:31.803534Z",
    "data": {
        "id": 8,
        "fullName": "Peter Petrucci",
        "username": "peter11",
        "email": "peter@gmail.com",
        "phoneNumber": "089618012225",
        "address": "HONGKONG",
        "status": "ACTIVE"
    }
}
```

## UPDATE USER

Request :
- Method : PUT
- Endpoint : `/crud/user/{id}`
- Header :
    - Content-Type: `application/json`
    - Accept: `application/json`

Request Body :

```json
{
    "fullName":"Firman Hanafi",
    "username":"FirmanH11",
    "email":"firmanh@gmail.com",
    "phoneNumber":"089618012225",
    "salary":30000000,
    "address":"Karawang"
}
```

Response Body :

```json
{
    "status": "0",
    "message": "Success",
    "timestamp": "2021-08-11T15:57:15.833998Z"
}
```

## DELETE USER BY ID

Request :
- Method : DELETE
- Endpoint : `/crud/user/{id}`
- Header :
    - Content-Type: `application/json`
    - Accept: `application/json`

Response Body :

```json
{
    "status": "0",
    "message": "Success",
    "timestamp": "2021-08-11T15:57:15.833998Z"
}
```
