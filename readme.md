# 📚 University Library Management System

A modern, robust, and secure web application for managing university libraries, built with **Spring Boot 3**, **Thymeleaf**, and **Spring Security**.

## 🚀 Features

- **Dashboard:** Real-time summary of total books, active members, ongoing loans, and overdue alerts.
- **Book Management:** Full CRUD operations with ISBN validation and category filtering.
- **Member Management:** Track student/faculty registrations and status.
- **Loan System:** 
    - Automated stock management (check availability before loan).
    - Return processing with automatic stock restoration.
    - Automated detection of late returns via scheduled tasks.
- **Security:** Role-based access control (RBAC).
    - **Admin:** Full access to all management features.
    - **User:** Access to dashboard and loan management.
- **Responsive UI:** Clean and professional interface built with Bootstrap 5.

## 🛠️ Tech Stack

- **Backend:** Spring Boot 3, Spring Data JPA, Spring Security.
- **Frontend:** Thymeleaf, Bootstrap 5, HTML5, CSS3.
- **Database:** H2 (In-Memory) / MySQL compatible.
- **Build Tool:** Maven.

## 📦 Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/KhayredineG/bu_gestion.git
   ```

2. **Configure Database:**
   By default, the app uses an in-memory H2 database. To use MySQL, update `src/main/resources/application.properties`.

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the App:**
   Open `http://localhost:8080` in your browser.
   - **Default Admin:** `admin` / `admin123`
   - **Default User:** `user` / `user123`

---
