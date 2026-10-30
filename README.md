# EmployeePulse HRMS

EmployeePulse HRMS is a modern employee management and leave tracking system built with Java, Spring Boot, MySQL, and a responsive frontend. It is designed to simplify human resource administration by providing a centralized system for managing employee records, departments, and leave requests with role-based access.

This project is created to reflect a real-world HR operations workflow and is suitable for final-year Java projects, internship portfolios, and practical learning. It demonstrates how backend APIs, database interaction, and frontend behavior work together in an enterprise-grade application.

Developed by: Mohd Dilshad Khan

## Why This Project

This project addresses a common business need: managing people and time efficiently. In real organizations, HR teams need a structured way to handle onboarding, leave requests, approvals, and employee records. EmployeePulse brings this workflow into a clean digital system where employees can apply for leave and admins can review and act on requests in a streamlined way.

It is more than a CRUD application because it reflects real process flow, data validation, and decision-making logic that organizations depend on every day.

## Business Problem Solved

Traditional employee management often depends on spreadsheets, emails, or paper records, which leads to:

- delayed leave approvals
- data duplication and employee record errors
- poor visibility into employee status
- manual HR overhead
- lack of centralized employee tracking

EmployeePulse solves these issues by providing:

- centralized employee management
- automated leave request tracking
- role-based access control
- faster approval workflows
- dashboard-based operational visibility

## Core Features

### Admin Features
- Secure admin login
- Add employee records
- View all employees
- Search employee by name or ID
- Delete employee records
- Manage departments
- View all leave requests
- Approve or reject leave requests
- Track summary metrics in dashboard

### Employee Features
- Employee login
- Apply for leave
- Choose leave type and date range
- Provide reason for leave
- View leave history
- Check current leave status

### Application Features
- Professional dashboard interface
- Responsive design for desktop and tablet screens
- MySQL database integration
- Role-based access control
- Duplicate email validation
- Leave date validation
- Rest API-driven architecture
- Clean backend service structure

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

## Architecture Overview

```text
+--------------------------+        +---------------------------+        +----------------------+
| Frontend (HTML/CSS/JS)  | ----> | Spring Boot REST API      | ----> | MySQL Database       |
| Login, Admin Dashboard  |        | Controller + Service      |        | employees            |
| Employee Portal          |        | Repository + JDBC         |        | departments          |
| Department Module       |        | Validation + Business     |        | leave_requests       |
+--------------------------+        | Logic                    |        | users                |
                                    +---------------------------+        +----------------------+
```

## Request Flow

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
   |   - validates payload
   |   - checks duplicate data
   |   - applies business rules
   v
Repository Layer
   |
   v
MySQL Database
   |
   v
Response Returned to UI
```

## Workflow

```text
1. User logs in to the system.
2. Backend validates login credentials.
3. Role is identified as Admin or Employee.
4. Admin can manage employees, departments, and leave approvals.
5. Employee can submit leave requests and view leave history.
6. Leave request is stored in database and visible in dashboard.
7. Admin approves or rejects the request.
8. Data is refreshed dynamically on the dashboard.
```

## System Modules

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
- A working local environment for Java projects

## How to Run This Project on Your System

### 1. Create the database
Open MySQL and run:

```sql
CREATE DATABASE employee_management;
```

### 2. Configure the database in application.properties
Open:

```properties
src/main/resources/application.properties
```

Update with your local MySQL configuration:

```properties
spring.application.name=employee-management-leave-tracking-system
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/employee_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 3. Build the project
From the root folder of the project, run:

```bash
mvn clean install
```

### 4. Run the application
```bash
mvn spring-boot:run
```

### 5. Open the app in browser
Visit:

```text
http://localhost:8080
```

### 6. Login using the default admin account
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

## How It Works

The application follows a structured flow:

1. User enters login credentials.
2. The backend checks the username and password against the database.
3. The role is used to route the user to either the admin or employee dashboard.
4. Admins can manage employees, departments, and leave approvals.
5. Employees can submit leave requests and track their own leave history.
6. Data is stored in MySQL and returned dynamically to the interface.
7. Dashboard values refresh as employee and leave records change.

## Future Enhancements

- Employee edit modal and profile page
- Pagination for large employee data
- Department editing and deletion
- Charts and visual analytics
- Export reports to PDF/CSV
- Role-based route protection
- Email notifications for leave approval status
- Better audit logging and activity tracking

## Project Impact

This project highlights practical Java backend development and data-driven business logic. It is useful for demonstrating essential software engineering concepts such as:

- REST API development
- JDBC database operations
- role-based systems
- model-view-controller architecture
- web application integration
- validation and exception handling

## Conclusion

EmployeePulse HRMS is a practical, portfolio-ready project that mirrors real HR management workflows. It is designed to be easy to understand, visually clean, and technically meaningful. As a Java full-stack project, it offers strong learning value and showcases how a real-world employee management platform can be built with Spring Boot and MySQL.

This project is a good representation of professional software development and is organized in a way that can be extended into a larger enterprise application in the future.

Made with focus on mentorship, growth, and portfolio quality.

Developed by Mohd Dilshad Khan
