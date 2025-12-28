## About
Football Fan Management API is a Spring Boot RESTful backend application designed to manage football clubs, users, fans, and their profiles. It supports user registration, authentication, role-based access control, and fan profile management, with data persisted in PostgreSQL.

## Features
- User registration and login (JWT-based authentication)
- Role-based authorization (ADMIN / USER)
- Create and manage fan profiles
- Associate fans with football clubs
- Cascade delete handling between users, persons, and fan profiles
- Secure endpoints with Spring Security
- Database persistence using PostgreSQL
- Integration and repository testing with Spring Data JPA

## Tech Stack
- Java 25
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JSON Web Tokens)
- PostgreSQL
- Hibernate
- JUnit 5 & Mockito
- Maven

## Domain Model
- AppUser – handles authentication and authorization
- Person – represents user personal information
- FanProfile – stores fan-related data (age, nationality, date joined)
- Club – football club association
