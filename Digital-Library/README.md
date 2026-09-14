# Digital Library Management System

A web-based Digital Library Management System developed using Java, Spring Boot, Thymeleaf, HTML, CSS, JavaScript, and MySQL.

The system provides separate functionality for **Admin** and **User** roles, including book management, borrowing and returning books, advance booking, fine management, member management, and contact queries.

## Features

### User Features

* User registration and login
* Browse available books
* Search books by title or author
* Filter books by category
* Issue available books
* Return borrowed books
* Automatic due-date tracking
* Automatic fine calculation of ₹5 per overdue day
* Advance booking for unavailable books
* View personal borrowing history
* Submit contact/query requests
* User dashboard

### Admin Features

* Admin login
* Admin dashboard
* Add new books
* Edit book details
* Delete books
* Manage book quantity and availability
* View registered members
* Remove members
* View issued books and due dates
* View fines
* Mark fines as paid
* View user contact queries

## Technology Stack

* **Language:** Java 17
* **Framework:** Spring Boot 4.1.1
* **Frontend:** HTML5, CSS3, JavaScript
* **Template Engine:** Thymeleaf
* **Database:** MySQL 8
* **Build Tool:** Maven
* **API:** RESTful APIs
* **ORM:** Spring Data JPA / Hibernate
* **Server:** Embedded Tomcat

## Project Structure

```text
Digital-Library/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/obisip/digital_library/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Database Setup

Create the MySQL database:

```sql
CREATE DATABASE digital_library;
```

The application uses the following database:

```text
Database: digital_library
Username: root
```

Update the MySQL credentials in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/digital_library
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.thymeleaf.cache=false
```

## Running the Application

Make sure MySQL is running, then open a terminal inside the `Digital-Library` folder.

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

Or run `DigitalLibraryApplication` directly from IntelliJ IDEA.

The application will start on:

```text
http://localhost:8080/
```

## Main Pages

```text
/
├── /login
├── /register
├── /books
├── /dashboard
├── /contact
└── /admin
    ├── /admin/books
    ├── /admin/members
    ├── /admin/borrowed
    └── /admin/queries
```

## REST API

### Authentication

```text
POST /api/auth/login
GET  /api/auth/me
POST /api/auth/logout
```

### Users

```text
POST   /api/users/register
GET    /api/users
GET    /api/users/{id}
DELETE /api/users/{id}
```

### Books

```text
GET    /api/books
GET    /api/books/{id}
POST   /api/books
PUT    /api/books/{id}
DELETE /api/books/{id}
```

### Borrowing

```text
POST /api/borrow/issue/{userId}/{bookId}
PUT  /api/borrow/return/{borrowId}
GET  /api/borrow
GET  /api/borrow/user/{userId}
PUT  /api/borrow/fine-paid/{borrowId}
```

### Advance Booking

```text
POST /api/bookings/book
GET  /api/bookings
GET  /api/bookings/user/{userId}
GET  /api/bookings/pending
```

### Contact Queries

```text
POST /api/contact
GET  /api/contact
GET  /api/contact/{id}
```

## Fine Calculation

The system calculates overdue fines automatically when a book is returned.

```text
Fine = Number of overdue days × ₹5
```

For example, if a book is returned 4 days after the due date:

```text
Fine = 4 × ₹5 = ₹20
```

## Security

The application uses session-based authentication and role-based access checks for Admin and User functionality.

For a production deployment, passwords and database credentials should be stored securely using environment variables or a secrets-management solution, and user passwords should be stored using a secure password-hashing mechanism.

## Learning Outcomes

This project demonstrates practical experience with:

* Java and Spring Boot
* REST API development
* Spring Data JPA
* Hibernate ORM
* MySQL database integration
* CRUD operations
* Session-based authentication
* Role-based access control
* Thymeleaf templates
* HTML, CSS and JavaScript
* Database relationships
* Business logic implementation
* Git and GitHub

## Author

**Mohammed Khan**

Computer Engineering | Software Developer

GitHub: https://github.com/knockITSME
