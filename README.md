# Employee Management & Leave Tracking System

This is a Java-based full-stack project focused on employee management and leave tracking. It uses:
- Java 8+
- Spring Boot 2.7.x
- Spring MVC
- JDBC with MySQL
- HTML, CSS and JavaScript for frontend

## Features
- Employee login and admin login
- Employee CRUD operations
- Leave application workflow
- Admin approval / rejection of leave requests
- Search employees by name or ID
- Dashboard summary API
- Organized architecture with MVC layering

## Project Structure
```text
employee-management-leave-tracking-system
├── src
│   ├── main
│   │   ├── java/com/employee/management
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   ├── exception
│   │   │   ├── model
│   │   │   ├── repository
│   │   │   ├── service
│   │   │   └── EmployeeManagementApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       ├── static
│   │       │   ├── css
│   │       │   ├── js
│   │       │   └── index.html
│   └── test
├── pom.xml
├── README.md
└── .gitignore
```

## Database Setup
Create the MySQL database:
```sql
CREATE DATABASE employee_management;
```

Then configure the database details in `src/main/resources/application.properties`.

## Default Login
```text
Username: admin
Password: admin123
```

## Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

Open the app in the browser:
```text
http://localhost:8080
```

## API Examples

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Get all employees
```bash
curl http://localhost:8080/api/employees
```

### Apply leave
```bash
curl -X POST http://localhost:8080/api/leaves \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": 1,
    "leaveType": "SICK",
    "startDate": "2026-11-10",
    "endDate": "2026-11-12",
    "reason": "Medical leave"
  }'
```

## Notes
This project is intentionally structured as a clean foundation for a Java fresher project and can be expanded with additional pages, validations, authentication improvements, and richer dashboard screens.
