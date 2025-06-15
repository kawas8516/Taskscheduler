<h1 align="center">📅 Task Scheduler with Automated Reminders</h1>
<p align="center">
  <strong>A secure, modular Java web app to manage tasks, track deadlines, and send automated reminders.</strong><br>
  <em>Built for real-world productivity, designed with good coding practices.</em>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Tech%20Stack-Java%20EE%2C%20JSP%2C%20PostgreSQL-green?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Build-Passing-brightgreen?style=for-the-badge" />
  <img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge" />
</p>

---

## 🛠️ Features

- 🔐 **Secure Authentication** using BCrypt
- 📋 CRUD for tasks with priority, type, tags, and checklist
- ⏰ **Email reminders** (1 hour before deadline) via multithreaded scheduler
- 📊 Dynamic JSTL-based dashboard
- 🔍 Filter tasks by type with color coding
- 📅 Planned integration with FullCalendar.js
- 🔒 Modular, scalable, and beginner-friendly codebase

---

## 🏗️ Tech Stack

| Layer      | Technology                          |
|------------|--------------------------------------|
| Backend    | Java 17, Servlets, JSP 3.0, JSTL 2.0 |
| Frontend   | HTML, Bootstrap 5.3, FullCalendar.js |
| Database   | PostgreSQL 15                        |
| Scheduling | Java `ScheduledExecutorService`      |
| Auth       | jBCrypt                              |
| Mail       | JavaMail API                         |
| Server     | Apache Tomcat 10                     |
| IDE        | Eclipse IDE for Enterprise Java      |

---

## ⚙️ Setup Instructions

### ✅ Prerequisites

- Java 17
- Apache Tomcat 10
- PostgreSQL 15+
- Eclipse IDE for Enterprise Java
- Internet (for Bootstrap, FullCalendar CDN)

### 🧱 Build & Deploy

```bash
git clone https://github.com/<your-username>/TaskScheduler.git
```

1. Open the project in Eclipse as a **Dynamic Web Project**
2. Add the following JARs to `WEB-INF/lib/`:
   - `postgresql-42.6.0.jar`
   - `jBCrypt-0.4.jar`
   - `javax.mail.jar`
   - `jakarta.servlet.jsp.jstl-2.0.0.jar`, `standard.jar`
3. Configure `DBUtil.java`:
   ```java
   private static final String URL = "jdbc:postgresql://localhost:5432/taskscheduler";
   private static final String USER = "your_db_user";
   private static final String PASSWORD = "your_password";
   ```
4. Start Tomcat and visit:  
   `http://localhost:8080/TaskScheduler/login.jsp`

---

## 🗃️ Sample SQL Setup

```sql
-- Create test user
INSERT INTO users (username, email, password)
VALUES ('testuser', 'testuser@example.com', 'password123');

-- Add tasks
INSERT INTO tasks (user_id, title, description, due_date, type, priority, status, tags)
VALUES
(1, 'Doctor Appointment', 'Annual check-up', '2025-06-20 14:00:00', 'REMINDER', 'HIGH', 'PENDING', 'health,important'),
(1, 'Project Deadline', 'Phase 1 milestone', '2025-06-22 17:00:00', 'TASK', 'HIGH', 'PENDING', 'work,urgent'),
(1, 'Grocery Ideas', 'Try pesto pasta', '2025-06-25 18:00:00', 'IDEA', 'LOW', 'PENDING', 'cooking,home');
```

---

## 🗂️ Project Structure

```
TaskScheduler/
├── src/
│   └── main/java/com/taskscheduler/
│       ├── controller/     # Servlets
│       ├── dao/            # DAO classes
│       ├── model/          # POJOs
│       ├── util/           # Helpers (DBUtil, EmailService)
│       └── listener/       # Context listeners
├── webapp/
│   ├── WEB-INF/
│   │   ├── lib/            # External dependencies
│   │   └── web.xml         # Deployment descriptor
│   └── views/              # JSP pages
```

---

## 🚧 Project Status

| Feature              | Status   | Notes                                                            |
|----------------------|----------|------------------------------------------------------------------|
| Task CRUD            | ✅ Done  | Fully functional using JDBC                                      |
| Email Scheduler      | ✅ Done  | Sends reminders 1 hour before due date                           |
| Calendar Integration | ⚠️ WIP   | FullCalendar not rendering due to JSTL + LocalDateTime issues    |
| Mobile UI            | ⚠️ Proto | Needs better responsive layout                                   |
| Login Feedback       | ⚠️ Basic | Minimal user feedback on login failure                           |

---

## 🔮 Future Enhancements

- 📱 Fully mobile-responsive layout
- 📲 SMS reminders (Twilio)
- 📌 Drag-and-drop task reordering (Sortable.js)
- 🤖 AI-powered task suggestions
- 🗓️ Full interactive calendar editing

---

## 📚 References

- [JavaMail API Docs](https://javaee.github.io/javamail/)
- [PostgreSQL Docs](https://www.postgresql.org/docs/)
- [Bootstrap 5](https://getbootstrap.com/)
- [FullCalendar.js](https://fullcalendar.io/)
- [Apache Tomcat 10](https://tomcat.apache.org/tomcat-10.0-doc/)
- [jBCrypt GitHub](https://github.com/jeremyh/jBCrypt)

---

## 🤝 Contributing

New to open source or Java web apps? We welcome beginners and experts alike!

Check out our [CONTRIBUTING.md](CONTRIBUTING.md) to learn:

- ✅ How to set up the project in **Eclipse IDE for Enterprise Java**
- ✅ Git basics with **step-by-step visual resources**
- ✅ Our **code structure, style, and commit** guidelines
- ✅ How to submit your **first Pull Request** with confidence

---

## 👨‍💻 Author

Developed by **kawas8516**  
Open to contributions, ideas, and improvements. Fork it, build on it, and share back!

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).  
Free to use, fork, and modify for non-commercial and academic purposes — with attribution.

---
