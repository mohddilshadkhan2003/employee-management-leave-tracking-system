# EmployeePulse HRMS

EmployeePulse HRMS is a professional employee management and leave tracking system built using Java, Spring Boot, and MySQL. It helps organizations manage employee records, departmental structures, and leave requests in a simple and efficient way. The project is designed as a realistic business application that mirrors common HR workflows used in modern companies.

This project is built for students and developers who want a polished, portfolio-ready Java full-stack application. It demonstrates practical concepts such as backend development, database design, REST API creation, form handling, and role-based system workflows.

Developed by: Mohd Dilshad Khan

## Project Vision

The goal of EmployeePulse HRMS is to replace manual employee and leave management processes with a centralized digital system. Instead of using spreadsheets, emails, and paper records, the application provides a structured platform for managing employee data and leave approvals.

The project is meant to be simple to understand, yet realistic enough to demonstrate how modern business software is designed and implemented. It covers essential HR operations while keeping the codebase clean, modular, and developer-friendly.

## Why This Project Matters

Organizations depend on efficient employee tracking and leave approval systems to reduce delays and improve transparency. Without a proper system, many HR teams face:

- inconsistent employee records
- delayed leave approvals
- poor visibility into staff availability
- repetitive manual processing
- administrative inefficiencies

EmployeePulse HRMS addresses these challenges by centralizing employee management and automating leave workflows.

## Core Features

### Admin Features
- Admin login and secure access
- Add new employees
- Manage employee records
- Search employees by name or ID
- Delete employee records
- Add and manage departments
- View all leave requests
- Approve or reject leave requests
- Dashboard summary statistics

### Employee Features
- Employee login
- Apply for leave
- Select leave type and date range
- Enter leave summary/reason
- View leave request history
- Monitor current leave status

### General Features
- Spring Boot backend with REST APIs
- MySQL database integration
- JDBC-based repository layer
- Dynamic dashboard updates
- Role-based workflow management
- Validation for duplicate email and invalid leave date entries
- Responsive web interface

## Technology Stack

- Java 8+
- Spring Boot 2.7.x
- Spring MVC
- JDBC / JdbcTemplate
- MySQL
- Maven
- HTML
- CSS
- JavaScript

## System Architecture

```text
+-------------------------+       +-----------------------------+       +---------------------+
| Frontend (HTML/CSS/JS) | ----> | Spring Boot Application     | ----> | MySQL Database      |
| Admin Dashboard        |       | Controllers + Services      |       | Employees           |
| Employee Portal        |       | Repositories + JDBC        |       | Departments         |
| Department Module      |       | Validation + Business Logic |       | Leave Requests      |
+-------------------------+       +-----------------------------+       +---------------------+
```

## Request Processing Flow

```text
User Action
   |
   v
Frontend Form / Browser
   |
   v
HTTP Request to REST API
   |
   v
Controller Layer
   |
   v
Service Layer
   |  - validates request
   |  - applies business rules
   |  - handles data processing
   v
Repository Layer
   |
   v
MySQL Database
   |
   v
JSON Response to Frontend
```

## Workflow Overview

```text
1. User opens the application and logs in.
2. System validates credentials and identifies the role.
3. Admin is redirected to dashboard; Employee is redirected to portal.
4. Admin adds employees and departments, reviews leave requests.
5. Employee submits leave application with details.
6. Leave request is stored in MySQL.
7. Admin approves or rejects request.
8. Dashboard updates dynamically based on current records.
```

## Project Structure

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
├── PROJECT_REPORT.md
└── database
    └── schema.sql
```

## Database Design

```sql
CREATE DATABASE employee_management;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE departments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20),
    department_id INT,
    salary DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

CREATE TABLE leave_requests (
    id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT NOT NULL,
    leave_type VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    reason VARCHAR(255),
    applied_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);
```

## Prerequisites

Before running the project, make sure your system has:

- Java 8 or higher
- Maven
- MySQL Server
- IntelliJ IDEA / Eclipse / VS Code

## Step-by-Step Local Setup

### 1. Create database
Open MySQL and execute:

```sql
CREATE DATABASE employee_management;
```

### 2. Update database configuration
Open:

```properties
src/main/resources/application.properties
```

Set your local database values:

```properties
spring.application.name=employee-management-leave-tracking-system
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/employee_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 3. Install dependencies
From project root:

```bash
mvn clean install
```

### 4. Run the app
```bash
mvn spring-boot:run
```

### 5. Open application
Visit:

```text
http://localhost:8080
```

### 6. Login
Default admin credentials:

```text
Username: admin
Password: admin123
```

## REST API Endpoints

### Authentication
- `POST /api/auth/login`

### Employee APIs
- `GET /api/employees`
- `POST /api/employees`
- `GET /api/employees/{id}`
- `PUT /api/employees/{id}`
- `DELETE /api/employees/{id}`
- `GET /api/employees/search?keyword=...`

### Department APIs
- `GET /api/departments`
- `POST /api/departments`

### Leave APIs
- `POST /api/leaves`
- `GET /api/leaves`
- `GET /api/leaves/{id}`
- `PUT /api/leaves/{id}/status?status=APPROVED`
- `DELETE /api/leaves/{id}`

### Dashboard APIs
- `GET /api/dashboard`

## Sample API Calls

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

## Future Scope

- employee edit profile pages
- PDF/CSV export reports
- activation/deactivation of employees
- leave analytics charts
- email notifications
- JWT based authentication
- role-based route security enhancements
- pagination and advanced filters

## Impact and Learning Outcome

This project allows a developer to understand real-world application design, database integration, and backend logic in a practical environment. It is ideal for building a strong final-year engineering project portfolio because it combines backend, frontend, and database work into one manageable application.

## Conclusion

EmployeePulse HRMS is a practical HR management system designed to solve real employee and leave tracking needs in organizations. It brings together modern Java development practices, SQL-based data storage, and a responsive dashboard interface in a clean and easy-to-understand architecture.

This project is a good representation of a production-style Java full-stack application and provides a strong foundation for future enhancements and enterprise-level expansion.

Developed by Mohd Dilshad Khan
