# Architecture & Technical Documentation

This document provides a technical overview of how the **University Library Management System** is built and how its different layers interact.

## 1. High-Level Architecture
The application follows a classic **MVC (Model-View-Controller)** pattern, which is the standard for Spring Boot applications using Server-Side Rendering (SSR).

- **View:** Thymeleaf Templates (HTML/CSS)
- **Controller:** Spring MVC Controllers
- **Model:** JPA Entities & Services

---

## 2. Backend (The Logic)
The backend is built with **Spring Boot 3** and Java 17.

### Core Components:
- **Controllers (`.controller`):** Handle HTTP requests (GET/POST), manage form binding, and return Thymeleaf views.
- **Services (`.service`):** Contains the "Business Logic." For example, when a loan is created, the service checks if the book is available and automatically decrements the stock quantity.
- **Repositories (`.repository`):** Interfaces that use **Spring Data JPA** to talk to the database without writing complex SQL queries.
- **Security (`.config`):** Uses **Spring Security** to protect routes. 
    - `ADMIN` role can manage books and members.
    - `USER` role can view the dashboard and manage loans.

### Key Features:
- **Transaction Management:** Uses `@Transactional` to ensure that if a loan is saved but the book stock update fails, the entire operation is rolled back to prevent data inconsistency.
- **Scheduled Tasks:** Includes a `@Scheduled` job that runs automatically to check for overdue loans and update their status to `EN_RETARD` (Late).

---

## 3. Frontend (The User Interface)
The UI is rendered on the server side, ensuring fast initial loads and SEO-friendly pages.

- **Thymeleaf:** The templating engine used to inject dynamic data (like book lists or member names) into HTML.
- **Layout Dialect:** A `layout.html` file acts as a master template containing the Navbar and Sidebar. Other pages (like `list.html` or `form.html`) inject their specific content into this layout.
- **Bootstrap 5:** Used for the responsive grid system, buttons, and form styling.
- **Validation:** Uses JSR-303 annotations (like `@NotBlank`, `@Min`) to validate user input. Errors are caught in the controller and displayed back to the user in the UI.

---

## 4. Database (The Storage)
The system uses an **H2 Database** (in-memory) for easy deployment and testing, but it is fully compatible with **MySQL**.

### Data Model:
- **Livre (Book):** Stores title, author, ISBN, and stock quantities (`quantiteTotal` vs `quantiteDisponible`).
- **Membre (Member):** Stores personal info and inscription date.
- **Emprunt (Loan):** The "Join Table" that links a Member to a Book. It tracks the loan date, expected return date, and current status (`EN_COURS`, `RENDU`, `EN_RETARD`).

---

## 5. Deployment (Railway)
When deployed to **Railway**, the following happens:
1. Railway detects the `pom.xml` and runs a Maven build.
2. The `spring-boot-maven-plugin` packages the app into an executable `.jar`.
3. The app starts on a dynamic port assigned by Railway, and the H2 database is initialized (often with demo data via `DataInitializer.java`).
