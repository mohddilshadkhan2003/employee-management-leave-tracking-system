# Employee Manager

Employee Manager is a Java-based employee management and leave tracking system designed to simplify HR operations in a modern organization. It helps businesses manage employee records, departments, and leave requests in a single, easy-to-use platform.

This project is built using Java, Spring Boot, and MySQL, and it demonstrates a practical full-stack workflow for managing employee data and approval-based leave requests.

Developed by: Mohd Dilshad Khan

## Project Overview

Employee Manager helps organizations replace manual employee tracking and leave approval methods with a structured digital process. The system allows administrators to manage employee records, add departments, view leave requests, and approve or reject leave applications.

Employees can log in, apply for leave, and track their leave history without needing to rely on manual communication or paperwork.

## Features

### Admin Features
- Admin login
- Add new employees
- View employee records
- Search employees by name or ID
- Remove employee records
- Manage departments
- View leave requests
- Approve or reject leave requests
- Dashboard summary statistics

### Employee Features
- Employee login
- Apply for leave
- Choose leave type and date range
- Submit leave reason
- View leave history
- Track leave status

### General Features
- Java backend using Spring Boot
- MySQL database integration
- JDBC repository layer
- Role-based workflow
- Responsive web interface
- Form validation and business rules

## Tech Stack

- Java 8+
- Spring Boot 2.7.x
- Spring MVC
- JDBC / JdbcTemplate
- MySQL
- Maven
- HTML
- CSS
- JavaScript

## Project Architecture

```text
Employee Manager
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── employee
│   │   │           └── management
│   │   │               ├── controller
│   │   │               ├── dto
│   │   │               ├── model
│   │   │               ├── repository
│   │   │               ├── service
│   │   │               └── EmployeeManagementApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       └── static
│   │           ├── css
│   │           ├── js
│   │           ├── index.html
│   │           ├── admin.html
│   │           ├── employee.html
│   │           └── department.html
│   └── test
├── pom.xml
├── README.md
├── PROJECT_REPORT.md
└── database
    └── schema.sql
```

## Database Setup

Create the database:

```sql
CREATE DATABASE employee_management;
```

Update your configuration in:

```properties
src/main/resources/application.properties
```

Example:

```properties
spring.application.name=employee-management-leave-tracking-system
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/employee_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## Run Locally

```bash
mvn clean install
mvn spring-boot:run
```

Then open:

```text
http://localhost:8080
```

## Default Admin Login

```text
Username: admin
Password: admin123
```

## Project Purpose

Employee Manager is designed to make employee and leave management easier, more efficient, and more transparent. It provides a practical business workflow that can be used for academic projects, learning, and portfolio work.

## Conclusion

Employee Manager is a simple yet practical HR system that covers the essential workflows of employee administration and leave approval. It is an ideal final-year or internship project for Java developers who want to build a real-world, full-stack application.

Developed by Mohd Dilshad Khan
