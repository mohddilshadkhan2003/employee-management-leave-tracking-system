# Project Report: EmployeePulse HRMS

## Abstract

EmployeePulse HRMS is a web-based employee management and leave tracking system designed to manage employee records and staff leave workflows in an organized digital environment. The system allows administrators to add employees, assign departments, approve or reject leave requests, and monitor key HR statistics from a dashboard. Employees can log in, submit leave requests, and track the status of their applications.

This project is built using Java, Spring Boot, MySQL, and frontend web technologies. It demonstrates practical concepts in software engineering such as database design, REST API development, business logic implementation, validation, and responsive UI interaction.

## Objectives

The main objectives of this project are:

- streamline employee management in a centralized system
- simplify leave request tracking and approval
- reduce manual administrative tasks
- create a professional Java-based project suitable for portfolio and academic evaluation
- demonstrate backend and frontend integration in a real workflow

## Problem Statement

Traditional HR systems often rely on spreadsheets, manual forms, and verbal communication, which leads to duplication, delays, and poor record tracking. Organizations require a secure and centralized method to manage employee information, departments, and leave schedules. EmployeePulse HRMS addresses this need by providing a digital platform that supports efficient employee and leave operations.

## Scope of the Project

The project includes:

- employee record management
- department management
- leave application workflow
- leave approval and rejection by admin
- dashboard reporting
- role-based access for admin and employee users

The project excludes advanced features such as multi-tenant architecture, payroll processing, biometric attendance, and advanced reporting modules, though these can be added in future versions.

## Methodology

The project follows a layered architecture using:

- Controller layer for API endpoints
- Service layer for business logic and validation
- Repository layer for database interaction using JDBC
- MySQL for persistent data storage
- Static web pages and JavaScript for UI interaction

This structure keeps the project organized and readable while helping developers understand how real-world systems are built.

## Functional Modules

### 1. Authentication Module
This module manages user login and role-based access. It verifies credentials and redirects users according to their role.

### 2. Employee Module
This module handles employee creation, record display, search, and deletion. It ensures data consistency and prevents duplicate employee email entries.

### 3. Department Module
This module allows the admin to manage departments and assign employees to the proper department.

### 4. Leave Module
This module supports employee leave requests and admin approval flow. It validates leave dates and updates request status dynamically.

### 5. Dashboard Module
This module displays key stats such as total employees, pending requests, approved requests, and rejected requests.

## Tools and Technologies

- Java
- Spring Boot
- Spring MVC
- MySQL
- Maven
- HTML
- CSS
- JavaScript

## System Flow

```text
Admin / Employee Login
         |
         v
Authentication Check
         |
         v
Role Based Redirection
         |
         +------> Admin Dashboard
         |              |
         |              +--> Employee Management
         |              +--> Department Management
         |              +--> Leave Approval
         |
         +------> Employee Portal
                        |
                        +--> Leave Application
                        +--> Leave History
```

## Advantages of the System

- easy to use and maintain
- low cost to develop and run locally
- good for learning Java-based full-stack development
- role-based workflow improves accountability
- dynamically updated dashboard improves visibility

## Limitations

- no advanced security features like JWT or encrypted passwords yet
- no multi-role hierarchy beyond admin and employee
- no advanced charts or analytics
- no advanced reporting generation

## Future Enhancements

- add user profile management
- upgrade security with JWT and password hashing
- implement pagination and sorting
- add charts and visual analytics
- allow employee self-service updates
- add leave calendar view
- integrate notifications and email alerts
- support advanced reporting and export options

## Conclusion

EmployeePulse HRMS is a robust and practical project that demonstrates modern software development practices in a manageable scope. It combines the core concepts of Java backend development, database operations, and interactive front-end design into a working business application. The project is well-suited for a final-year project, internship portfolio, or training exercise in full-stack Java development.

This system also provides a strong foundation for future enhancement into a larger enterprise HR platform.
