# Employee Management & Leave Tracking System

The Employee Management & Leave Tracking System is a full-stack Java project designed to automate everyday HR operations such as employee onboarding, department management, leave application, and approval workflows. It helps organizations manage employee records efficiently while improving transparency and decision-making. This project combines Spring Boot, MySQL, and a responsive frontend to create a realistic business application. It is suitable for internship portfolios, final-year projects, and practical Java learning. The system is structured in a clean layered architecture to make the code maintainable and easy to extend.

## Project Overview

This system is built for organizations that want a simple yet effective way to manage employee information and leave requests. The application allows administrators to add employees, assign departments, review leave requests, and approve or reject them based on business rules. Employees can log in to submit leave requests, check their history, and monitor the status of requests in real time. The project emphasizes role-based access, data validation, and real-world workflow automation. By using Java, Spring Boot, and JDBC, it demonstrates how backend systems interact with a database and frontend interface in a production-like environment.

## Why This Project Matters

- It simulates a real HR and admin workflow used by modern companies.
- It demonstrates practical Java programming with database connectivity.
- It combines backend APIs, business logic, and frontend interface development.
- It helps freshers build a strong portfolio project with real-world relevance.
- It is easy to understand, scale, and improve with additional modules.

## Key Features

### Admin Features
- Admin login with role-based access
- Add new employees
- View all employee details
- Search employees by ID or name
- Delete employee records
- Manage departments
- View all pending and processed leave requests
- Approve or reject leave applications
- Dashboard summary with employee and leave metrics

### Employee Features
- Employee login
- Apply for leave with selected leave type
- Enter leave start and end dates
- Provide leave reason
- View leave history and current status
- Track approvals and rejections

### General Features
- Spring Boot MVC architecture
- MySQL database integration using JDBC
- REST API-driven communication
- Clean and responsive dashboard UI
- Duplicate email validation
- Leave date validation
- Error handling with meaningful API responses

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
+------------------+        +--------------------+        +---------------------+
|   Frontend UI    | ----> | Spring MVC REST    | ----> | MySQL Database      |
| HTML/CSS/JS      |        | Controllers        |        | employees           |
| admin.html       |        | Services           |        | departments        |
| employee.html    |        | Repositories       |        | leave_requests     |
| department.html  |        | JDBC / JdbcTemplate|        | users               |
+------------------+        +--------------------+        +---------------------+
```

## Data Flow Diagram

```text
User Action
   |
   v
Frontend Form / Browser
   |
   v
REST API Request
   |
   v
Controller Layer
   |
   v
Service Layer (Validation & Business Logic)
   |
   v
Repository Layer (SQL Queries)
   |
   v
MySQL Database
   |
   v
Response back to UI
```

## System Workflow

```text
1. Admin or Employee opens the login page.
2. Credentials are validated in the backend.
3. Based on role, user is redirected to admin or employee dashboard.
4. Admin can add employees, manage departments, and approve leave requests.
5. Employee can apply for leave and view leave history.
6. The request is saved in MySQL and reflected in the dashboard.
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
└── database
    └── schema.sql
```

## Prerequisites

Before running the project, make sure you have the following installed on your system:

- Java 8 or above
- Maven
- MySQL Server
- IntelliJ IDEA or VS Code
- Internet access for Maven dependencies

## How This Project Runs on Your System

Follow these steps carefully to run the project successfully on your local machine:

### Step 1: Install and Start MySQL
Open MySQL Workbench or terminal and create the required database:

```sql
CREATE DATABASE employee_management;
```

### Step 2: Update Database Configuration
Open the file:

```properties
src/main/resources/application.properties
```

Update the database username, password, and URL according to your system setup:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### Step 3: Build the Project
Open the terminal in the project root and run:

```bash
mvn clean install
```

### Step 4: Run the Spring Boot Application
Now start the project:

```bash
mvn spring-boot:run
```

### Step 5: Open the Application in Browser
Once the project starts successfully, open:

```text
http://localhost:8080
```

### Step 6: Login to the Application
Use the default admin account:

```text
Username: admin
Password: admin123
```

## Default Login Credentials

```text
Admin User
Username: admin
Password: admin123
```

## Database Design

```sql
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

## REST API Endpoints

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

## Example API Usage

### Login Call

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Add Employee

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

### Apply Leave

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

## How the Project Works

The application begins with a login page where the user chooses their role. The backend checks credentials and returns the appropriate role. Once the user is authorized, they are redirected to either the admin dashboard or the employee portal. Admins can manage employee records, departments, and leave approvals, while employees can submit leave requests and check status. Every action interacts with the MySQL database through JDBC queries, and the response is sent back to the frontend for display.

## Future Enhancements

- Pagination and sorting for large employee data
- Role-based route security improvements
- Leave statistics charts and analytics
- Email notifications for approval and rejection
- Better validation and exception handling
- Advanced reporting features

## Conclusion

The Employee Management & Leave Tracking System is a practical and professional Java project that helps users understand how real-world HR and employee management applications are built. It combines backend logic, database management, API development, and frontend interaction into a coherent software solution. This project is ideal for students, beginners, and developers who want to create a portfolio-worthy application that is both scalable and easy to understand.

If you are learning Java full-stack development, this project gives you a strong foundation in Spring Boot, MySQL integration, business logic, and web application development.
