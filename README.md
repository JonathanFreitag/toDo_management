# README #

This README would normally document whatever steps are necessary to get your application up and running.

## What is this repository for? ##

### Quick summary
ToDo is a backend service allowing basic management of a simple to-do list.

### Notes
* When create new item, it initializes its status to not done and creation date
* Mark item as done, when that item is not done yet
* Mark item as not done, when that item either done or past due, so need to change  it's due date-time.
* Automatically change status of item that are past their due date-time as past due (compare due date-time each 10 
  seconds)

### Tech Stack ###


                   Language        |   Databse           |  library           |    Build tool
                  -------------    |   --------------    |  --------------    | --------------
                  Java 11          |   H2                |   Hibernate        |    Maven
                                   |                     |   Jpa              | 
                                   |                     |   OpenAPI          | 
                                   |                     |   Swagger          | 
                                   |                     |   lombok           | 
                                   |                     |   Junit            |
                                   |                     |   Mockito          |           
                                   |                     |   Liquibase        |
### Build the service  ###
```
mvn clean install
```
* jar file is: ToDo.jar under /target folder
### Run the service  ###
```
mvn spring-boot:run
```

### Run tests  ###
```
mvn test
```

### Run in Docker Server  ###
* Build docker file
```
docker build -t todo .
```
* Run docker file
```
docker run -p 9090:8080 todo 
```
Application will be Running in Port: 9090

### H2 link

[H2 Login](http://localhost:8080/h2-console)
#### Database Credentials
* JDBC URL: jdbc:h2:mem:testdb
* User Name: dhia
* Password: todo2023

### Swagger link

[Swagger UI](http://localhost:8080/swagger-ui/index.html#/)









