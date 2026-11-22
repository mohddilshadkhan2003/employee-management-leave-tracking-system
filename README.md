# Employee Management & Leave Tracking System

A full-stack HR workflow application for managing employee records, departments, and leave requests. The backend is built with Java and Spring Boot, stores data in MySQL through Spring JDBC, and serves a browser interface made with HTML, CSS, and JavaScript.

## Features

- Admin and employee login flows with role-based page routing
- Employee records: create, update, delete, list, and keyword search
- Department creation and listing
- Leave request submission, employee leave history, and status review
- Dashboard metrics for employees and leave request statuses
- MySQL persistence through `JdbcTemplate`
- Configurable server and database settings using environment variables

> **Demo scope:** This project is intended for learning and demonstration. The current login and browser-side role routing are not a replacement for production authentication or server-side authorization. Do not expose a production deployment without adding appropriate security controls.

## Technology stack

| Area | Technology |
| --- | --- |
| Backend | Java 8+, Spring Boot 2.7.18, Spring MVC |
| Database access | Spring JDBC / `JdbcTemplate` |
| Database | MySQL 8.x |
| Frontend | HTML, CSS, vanilla JavaScript |
| Build | Maven |
| Web server | Embedded Tomcat |

## Requirements

- JDK 8 or newer
- Maven 3.6+
- MySQL 8.x (or a compatible MySQL server)

## Quick start

### 1. Clone the project

```bash
git clone https://github.com/mohddilshadkhan2003/employee-management-leave-tracking-system.git
cd employee-management-leave-tracking-system
```

### 2. Create the database

```sql
CREATE DATABASE employee_management;
```

### 3. Configure the connection

The defaults in `src/main/resources/application.properties` are suitable only for a local development setup (`root` / `root`). Override them for your environment rather than committing credentials:

```bash
export DB_URL='jdbc:mysql://localhost:3306/employee_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
export DB_USERNAME='your_mysql_user'
export DB_PASSWORD='your_mysql_password'
```

On Windows PowerShell, use `$env:DB_USERNAME="your_mysql_user"` and `$env:DB_PASSWORD="your_mysql_password"` (set `DB_URL` similarly if needed).

### 4. Build and run

```bash
mvn clean verify
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080).

To run the packaged application instead:

```bash
mvn clean package
java -jar target/employee-management-leave-tracking-system-0.0.1-SNAPSHOT.jar
```

## Configuration

| Environment variable | Default | Description |
| --- | --- | --- |
| `PORT` | `8080` | HTTP port |
| `DB_URL` | Local `employee_management` MySQL URL | JDBC connection URL |
| `DB_USERNAME` | `root` | MySQL username |
| `DB_PASSWORD` | `root` | MySQL password (change for anything beyond local development) |
| `DB_POOL_MAX` | `10` | Maximum Hikari connection pool size |
| `DB_POOL_MIN_IDLE` | `2` | Minimum idle connections |
| `SQL_INIT_MODE` | `always` | Spring SQL initialization mode; use `never` to disable startup initialization |

The schema is maintained in `src/main/resources/schema.sql`. By default, Spring initializes it at application startup.

## API overview

All endpoints use the `/api` base path.

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `POST` | `/api/auth/login` | Submit login credentials |
| `GET` | `/api/dashboard` | Read dashboard summary |
| `GET`, `POST` | `/api/departments` | List or create departments |
| `GET`, `POST` | `/api/employees` | List or create employees |
| `GET`, `PUT`, `DELETE` | `/api/employees/{id}` | Read, update, or delete an employee |
| `GET` | `/api/employees/search?keyword=...` | Search employees by name or ID |
| `GET`, `POST` | `/api/leaves` | List leave requests or submit one |
| `GET`, `DELETE` | `/api/leaves/{id}` | Read or delete a leave request |
| `GET` | `/api/employees/{employeeId}/leaves` | List an employee's leave requests |
| `PUT` | `/api/leaves/{id}/status?status=APPROVED` | Approve or reject a leave request |

Example leave request:

```json
{
  "employeeId": 1,
  "leaveType": "Annual",
  "startDate": "2026-12-01",
  "endDate": "2026-12-03",
  "reason": "Personal time off"
}
```

## Project layout

```text
src/main/java/com/employee/management/
├── controller/    REST endpoints
├── dto/            Request payloads
├── exception/      API exception handling
├── model/          Application data models
├── repository/     JDBC persistence
└── service/        Business rules

src/main/resources/
├── application.properties
├── schema.sql
└── static/         HTML pages, CSS, and JavaScript
```

## Java 8 compatibility

The Maven build targets Java 8 bytecode (`1.8`) and uses Spring Boot 2.7.18, which supports Java 8. Source changes should continue to avoid APIs introduced after Java 8. Verify locally with a JDK 8 installation:

```bash
mvn clean verify
```

## License

No license file is currently included. Please contact the repository owner before redistributing this project.
