# 📝 Blog Web Application

This is a simple blog application built with Spring Boot. It allows users to register, log in, browse posts, and add comments. Admin users can create, edit, and delete posts and manage comments.

## 🚀 Getting Started

### ✅ 1. Prerequisites

Make sure you have the following installed:

- [ ] Java Development Kit (**JDK 21** recommended, **17** supported)
- [ ] **MySQL Server** (8.0+ recommended)
- [ ] An IDE like IntelliJ IDEA or VSCode (optional but helpful)

### ⚙️ 2. Configure Application

Create a new **MySQL** database (e.g., `blog_app`) and then update the `application.properties` file located at:


Example configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blog_app
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false

# Optional - Multipart config for image upload
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
```

### ▶️ 3. Run the Application

Open the project in your IDE.

Run the main class:
com.web.proje.springboot.SpringbootApplication

Spring Boot will auto-create all necessary tables in the database.

### 📥 4. Insert Default Roles
Once the tables are created, run the following SQL query on your database to insert the required roles:

INSERT INTO roles(name) VALUES ('ROLE_ADMIN'), ('ROLE_GUEST');
⚠️ Without this step, user registration and authentication will not work correctly.

💡 Features
✅ User Registration & Login
✅ Profile Picture Upload (stored as byte[] in the database)
✅ Role-Based Access Control (ROLE_ADMIN, ROLE_GUEST)
✅ Admin Dashboard for Managing Posts & Comments
✅ Public Blog Feed with Search
✅ Form Validation with Error Feedback
✅ Secure Password Encryption (BCrypt)

🛠️ Tech Stack
- Java 21 / 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- MySQL
- Thymeleaf
- Bootstrap 5
- Hibernate ORM

📄 License
This project is open-source and was built for educational purposes.

🤝 Contributions
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
