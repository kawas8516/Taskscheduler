# 🧑‍💻 Contributing to Task Scheduler with Automated Reminders

Welcome! If you're a beginner or this is your first open-source contribution — you're in the right place 💡

This guide will help you:

- Set up the project in **Eclipse IDE for Enterprise Java**
- Learn how to use **Git** (step by step)
- Submit your first **Pull Request**
- Understand the **project structure and coding style**

We **encourage students, hobbyists, and beginners** to contribute!

---

## 🧰 Tools You’ll Need

| Tool            | Version / Notes                                      |
|-----------------|------------------------------------------------------|
| Java            | 17                                                   |
| Apache Tomcat   | 10                                                   |
| PostgreSQL      | 15 or higher                                         |
| IDE             | [Eclipse IDE for Enterprise Java](https://www.eclipse.org/downloads/packages/) |
| Git             | [Install Git](https://git-scm.com/downloads)         |

---

## 🚀 How to Set Up the Project Locally

### 1. Fork the Repository

Click the **Fork** button at the top-right of this page to make your own copy.

### 2. Clone It

```bash
git clone https://github.com/YOUR-USERNAME/TaskScheduler.git
cd TaskScheduler
```

### 3. Import into Eclipse

- Open Eclipse → File → **Import**
- Choose **"Existing Projects into Workspace"**
- Navigate to your `TaskScheduler` folder
- Make sure it's a **Dynamic Web Project**

### 4. Add JARs to `WEB-INF/lib/`

Download and add:

- `postgresql-42.6.0.jar`
- `javax.mail.jar`
- `jBCrypt-0.4.jar`
- `jakarta.servlet.jsp.jstl-2.0.0.jar`
- `standard.jar`

### 5. Configure the Database

- Install PostgreSQL and pgAdmin
- Create a DB: `taskscheduler`
- Add tables using the SQL from the [README](README.md)
- Update `DBUtil.java` with your credentials:
```java
private static final String URL = "jdbc:postgresql://localhost:5432/taskscheduler";
private static final String USER = "postgres";
private static final String PASSWORD = "your_password";
```

### 6. Run the App

- Right-click → Run on Server → Choose Apache Tomcat
- Go to `http://localhost:8080/TaskScheduler/login.jsp`

---

## 🔄 Git Basics: For First-Time Contributors

### 🔧 Setup

```bash
git config --global user.name "Your Name"
git config --global user.email "you@example.com"
```

### 🛠️ Common Commands

| Task                            | Command                                  |
|----------------------------------|------------------------------------------|
| Check your branch                | `git branch`                             |
| Create a new feature branch      | `git checkout -b feature/my-task`        |
| Add changes                      | `git add .`                              |
| Commit changes                   | `git commit -m "Add: my feature"`        |
| Push to GitHub                   | `git push origin feature/my-task`        |
| Pull latest changes              | `git pull origin main`                   |

📚 Want to learn Git visually? Try:

- [Git Cheat Sheet (PDF)](https://education.github.com/git-cheat-sheet-education.pdf)
- [GitHub Hello World Guide](https://guides.github.com/activities/hello-world/)
- [Visual Git Tutorial](https://learngitbranching.js.org/)

---

## 🌱 How to Submit a Pull Request (PR)

1. Push your changes to a **new branch**
2. Go to your GitHub repo
3. Click **“Compare & Pull Request”**
4. Fill in:
   - What you changed
   - Related issue (if any)
   - Screenshot if it’s a UI change
5. Submit!

---

## 🧹 Code Style

- Java 17 syntax
- 4-space indentation
- Use meaningful variable names
- Add comments for clarity (especially for logic blocks)
- Avoid hardcoding credentials or values

---

## 🧠 Good First Issues

Check the [Issues tab](https://github.com/YOUR-USERNAME/TaskScheduler/issues) for labels like:

- `good first issue`
- `help wanted`

If you’re not sure where to start, open a question as an issue. We’d be happy to help.

---

## 📜 Code of Conduct

Be kind, inclusive, and constructive. No spam, trolling, or offensive behavior.

---

## 🙌 You're All Set

We’re excited to have you contribute! 🎉  
Whether you’re fixing a typo or building a whole new module — we value your input.

Thanks for helping make **Task Scheduler** better.

– The Maintainers 👨‍💻
