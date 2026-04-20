# 🎓 Student Tracker Pro

A modern, premium JavaFX application designed for tracking students and their academic performance. This application provides a streamlined interface for managing student records, grading, and advanced search operations.

## ✨ Features

- **Secure Login System**: Protected access with hashed password verification.
- **Student Management**: Full CRUD (Create, Read, Update, Delete) operations for student profiles.
- **Grade Tracking**: Manage grades per subject for every student with automated average calculation.
- **Advanced Search**: Filter students by ID, First Name, or Last Name with real-time results.
- **Premium UI/UX**: High-end "Glassmorphism" design with smooth transitions and professional aesthetics.
- **Database Seeding**: Built-in utilities to quickly populate the database with sample data.

## 🚀 Getting Started

### Prerequisites

- **Java JDK 17** or higher
- **Maven** 3.6+
- **PostgreSQL** database instance

### Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   ```

2. **Configure Environment Variables**:
   Create a `.env` file in the root directory and add your PostgreSQL connection string:
   ```env
   DATABASE_URL=postgresql://user:password@host:port/dbname
   ```

3. **Initialize Database**:
   The application automatically creates the necessary tables (`User`, `Student`, `Grade`) on the first run.

### Running the Application

To launch the main JavaFX application:
```bash
mvn clean javafx:run
```

## 🛠️ Development Tools

### Seeding Sample Data

To quickly test the application with realistic data, use the provided seeding scripts:

- **Add 30 Students**:
  ```bash
  mvn exec:java "-Dexec.mainClass=com.tracker.SeedStudents"
  ```

- **Add Random Grades**:
  (Run after seeding students to add 5-6 random grades per student)
  ```bash
  mvn exec:java "-Dexec.mainClass=com.tracker.SeedGrades"
  ```

## 📂 Project Structure

- `src/main/java/com/tracker`
  - `config`: Database connection and initialization.
  - `controller`: UI logic for FXML views.
  - `model`: Data entities (Student, Grade) and DAO requests.
  - `service`: Business logic and authentication services.
  - `ui`: Custom JavaFX components (StudentRow).
- `src/main/resources`
  - `com/tracker/ressources`: FXML view definitions.
  - `com/tracker/css`: Modern styling and themes.
  - `com/tracker/images`: Assets and logos.

## 🎨 Technologies Used

- **JavaFX 17**: Modern desktop UI framework.
- **Maven**: Project management and dependency handling.
- **PostgreSQL**: Robust relational database.
- **Dotenv-Java**: Secure environment variable management.
- **CSS3**: Advanced styling for a premium look and feel.
