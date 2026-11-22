# Project Report: EmployeePulse HRMS

## 1. Abstract

EmployeePulse HRMS is a full-stack web application developed to manage employee records, departments, and leave workflows in an organized digital environment. It provides separate experiences for administrators and employees through a responsive HTML, CSS, and JavaScript interface backed by Java and Spring Boot REST APIs.

The system is built with Java 8+, Spring Boot 2.7.18, Spring JDBC, JdbcTemplate, and MySQL. It demonstrates practical software engineering concepts including layered architecture, database design, REST API development, request validation, repository pattern implementation, environment-based configuration, and backend–frontend integration.

## 2. Objectives

The objectives of this project are:

- Centralize employee and department records in a single application.
- Simplify employee leave application and administrator approval workflows.
- Reduce manual follow-ups and spreadsheet-based record keeping.
- Display dynamically updated HR summary information through a dashboard.
- Provide a maintainable Java 8-compatible project suitable for academic evaluation and portfolio presentation.
- Demonstrate integration between Spring MVC controllers, service classes, JDBC repositories, MySQL, and a browser interface.

## 3. Problem Statement

Traditional HR administration often depends on spreadsheets, physical forms, and verbal communication. These methods may lead to duplicate records, delayed approvals, poor status visibility, and difficult record retrieval.

An organization requires a centralized application that can:

- maintain accurate employee and department information;
- manage leave requests in a consistent sequence;
- provide role-specific screens and operations;
- expose reliable data through REST endpoints; and
- remain understandable and deployable in a local or academic environment.

EmployeePulse HRMS addresses these needs using a lightweight layered architecture and a MySQL-backed data model.

## 4. Scope

### Included

- Admin and employee login views
- Employee creation, listing, searching, updating, and deletion
- Department creation and listing
- Employee leave submission and leave history
- Leave approval and rejection
- Admin dashboard metrics
- REST API endpoints for application operations
- Configurable database and server settings
- Java 8-compatible Maven build

### Excluded

The current version does not include payroll processing, biometric attendance, multi-tenant organizations, JWT authentication, password hashing, advanced analytics, or automated notifications.

## 5. Technology Stack

| Area | Technology |
| --- | --- |
| Language | Java 8+ |
| Backend framework | Spring Boot 2.7.18 / Spring MVC |
| Database access | Spring JDBC / JdbcTemplate |
| Database | MySQL 8.x |
| Frontend | HTML, CSS, JavaScript |
| Build tool | Maven |
| Runtime | Embedded Apache Tomcat |
| Configuration | `application.properties` and environment variables |

## 6. System Architecture

The application follows a layered architecture:

```text
Browser
  |
  v
Static HTML / CSS / JavaScript
  |
  v
REST Controllers
  |
  v
Service Layer
  |
  v
Repository / JdbcTemplate Layer
  |
  v
MySQL Database
```

### Architectural Layers

- **Controller layer:** Handles HTTP requests and exposes REST endpoints.
- **Service layer:** Implements business rules and validation.
- **Repository layer:** Executes SQL through `JdbcTemplate`.
- **Model and DTO layer:** Represents application entities and request payloads.
- **Exception handling:** Converts application failures into meaningful HTTP responses.
- **Frontend layer:** Calls REST APIs and renders dynamic dashboard, employee, department, and leave data.

## 7. Functional Modules

### 7.1 Authentication Module

The authentication module validates submitted credentials and returns the associated role and username. The frontend uses the returned role to route users between the admin and employee interfaces.

> The current implementation is suitable for demonstrations but should be replaced with hashed passwords and server-enforced authentication, authorization, sessions, or JWT before production use.

### 7.2 Employee Module

This module provides:

- Employee creation
- Employee listing
- Employee lookup by ID
- Employee updating
- Employee deletion
- Keyword search by name or ID
- Duplicate-email validation

### 7.3 Department Module

Administrators can create departments and retrieve the department list. Employees are associated with departments through a department identifier.

### 7.4 Leave Module

The leave module supports:

- Leave request submission
- Leave type and reason capture
- Start and end date validation
- Default pending status
- Leave history lookup by employee
- Administrator approval or rejection
- Leave request deletion

### 7.5 Dashboard Module

The dashboard module summarizes:

- Total employee count
- Pending leave requests
- Approved leave requests
- Rejected leave requests

## 8. Database Design

The application uses a relational MySQL database. The primary entities are users, departments, employees, and leave requests.

```sql
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NOT NULL
);

CREATE TABLE IF NOT EXISTS departments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS employees (
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

CREATE TABLE IF NOT EXISTS leave_requests (
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

The schema is initialized from `src/main/resources/schema.sql` during startup by default.

## 9. REST API Overview

The backend exposes operations under the `/api` base path.

| Method | Endpoint | Purpose |
| --- | --- | --- |
| POST | `/api/auth/login` | Authenticate a user |
| GET | `/api/dashboard` | Retrieve dashboard metrics |
| GET, POST | `/api/departments` | List or create departments |
| GET, POST | `/api/employees` | List or create employees |
| GET, PUT, DELETE | `/api/employees/{id}` | Read, update, or delete an employee |
| GET | `/api/employees/search?keyword=...` | Search employees |
| GET, POST | `/api/leaves` | List or submit leave requests |
| GET, DELETE | `/api/leaves/{id}` | Read or delete a leave request |
| GET | `/api/employees/{employeeId}/leaves` | Read an employee's leave history |
| PUT | `/api/leaves/{id}/status?status=APPROVED` | Approve or reject a leave request |

## 10. System Workflow

### Administrator Workflow

1. Sign in as an administrator.
2. Open the dashboard.
3. Manage departments.
4. Create, search, update, or delete employees.
5. Review pending leave requests.
6. Approve or reject requests.
7. Monitor dynamically updated summary metrics.

### Employee Workflow

1. Sign in as an employee.
2. Open the employee leave interface.
3. Enter employee information and leave details.
4. Submit the leave request.
5. Review the resulting leave status.

## 11. Java 8 Compatibility

The project is configured to compile Java 8-compatible bytecode. Its Maven configuration declares Java source and target levels as `1.8` and uses Spring Boot 2.7.18, which supports Java 8.

Database credentials and runtime settings are configurable through environment variables. These values may be set without modifying source code or checked-in configuration:

- `PORT`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `DB_POOL_MAX`
- `DB_POOL_MIN_IDLE`
- `SQL_INIT_MODE`

## 12. Advantages

- Uses a clear layered architecture.
- Requires only Java, Maven, and MySQL for local execution.
- Provides complete employee and leave-management demonstrations.
- Uses database-backed data instead of temporary in-memory storage.
- Separates business logic, persistence, and HTTP handling.
- Provides dynamically updated dashboards and request states.
- Can be used for academic projects, portfolio demonstrations, and internship previews.

## 13. Limitations

- Authentication is demonstration-level and does not use hashed passwords or server-side session enforcement.
- Browser-side role routing alone should not be treated as authorization.
- The interface has a basic presentation and does not provide advanced charts.
- Pagination, sorting, and advanced filtering are not implemented.
- No notification or reporting export system is included.
- The project does not currently provide automated test coverage for every module.

## 14. Future Enhancements

- Add Spring Security with password hashing and session or JWT authentication.
- Enforce authorization on all protected API endpoints.
- Implement pagination and sorting for employees and leave requests.
- Add employee profile management and self-service updates.
- Add leave calendars and working-day calculations.
- Add charts and data visualizations.
- Add email or in-app notifications.
- Add CSV/PDF report exports.
- Add automated unit and integration tests.
- Containerize the application with Docker and Docker Compose.

## 15. Conclusion

EmployeePulse HRMS is a practical full-stack project for demonstrating employee, department, and leave-management workflows using Java 8, Spring Boot, MySQL, and browser technologies. It combines REST API development, JDBC persistence, layered application design, and responsive user interaction in a manageable scope.

The project provides a strong foundation for academic evaluation and future enhancement into a production-oriented HR platform, provided that authentication, authorization, secure password storage, validation, testing, and deployment controls are strengthened.
