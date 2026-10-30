# Employee Management & Leave Tracking System

A modern full-stack Java application for managing employee records, departments, and leave approvals in an organization. It is designed as a realistic project for Java learners and internship portfolios.

## Features

### Admin Features
- Admin login
- Add employee records
- View all employee records
- Search employees by ID or name
- Delete employee records
- Manage departments
- View leave requests
- Approve or reject leave requests
- Dashboard with summary metrics

### Employee Features
- Employee login
- Apply for leave
- Select leave type and date range
- View leave history
- Track leave status

### General Features
- Java-based backend with Spring Boot and JDBC
- MySQL database integration
- Role-based access between admin and employee
- Clean and responsive frontend
- REST API support
- Validation for duplicate emails and invalid leave dates

## Tech Stack

- Java 8+
- Spring Boot 2.7.x
- Spring MVC
- JDBC / JdbcTemplate
- MySQL
- HTML
- CSS
- JavaScript
- Maven

## Project Architecture

```text
employee-management-leave-tracking-system
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── employee
│   │   │           └── management
│   │   │               ├── controller
│   │   │               ├── dto
│   │   │               ├── exception
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
├── .gitignore
├── README.md
└── database
    └── schema.sql
```

## Database Setup

Create the database:

```sql
CREATE DATABASE employee_management;
```

Then configure it in:

```properties
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## Default Login

```text
Username: admin
Password: admin123
```

## Run the Project

```bash
mvn clean install
mvn spring-boot:run
```

Open the app in browser:

```text
http://localhost:8080
```

## API Endpoints

### Authentication
- `POST /api/auth/login`

### Employees
- `GET /api/employees`
- `POST /api/employees`
- `GET /api/employees/{id}`
- `PUT /api/employees/{id}`
- `DELETE /api/employees/{id}`
- `GET /api/employees/search?keyword=...`

### Departments
- `GET /api/departments`
- `POST /api/departments`

### Leave Requests
- `POST /api/leaves`
- `GET /api/leaves`
- `GET /api/leaves/{id}`
- `PUT /api/leaves/{id}/status?status=APPROVED`
- `DELETE /api/leaves/{id}`

### Dashboard
- `GET /api/dashboard`

## Sample Usage

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Add employee

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name":"John Doe",
    "email":"john@example.com",
    "phone":"9876543210",
    "departmentId":1,
    "salary":45000
  }'
```

### Apply leave

```bash
curl -X POST http://localhost:8080/api/leaves \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId":1,
    "leaveType":"SICK",
    "startDate":"2026-11-10",
    "endDate":"2026-11-12",
    "reason":"Medical checkup"
  }'
```

## Future Improvements

- Pagination and sorting
- Chart-based dashboard analytics
- Role-based route guards
- Improved validation and error messages
- Better audit trail for leave updates
- Modern UI polish with Bootstrap or React

## Conclusion

This project provides a strong foundation for a professional Java full-stack application and is suitable for academic projects, internship portfolios, and real-world learning.
