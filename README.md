# 🧩 Task Management System  
### **Kaiburr Assessment – Task 1 (Java Backend and REST API Example)**  

A Spring Boot REST API application for managing and executing tasks with command validation and execution tracking.  

---

## 📘 Overview
This application provides a RESTful API for managing shell-based tasks that can be executed securely.  
Each task stores its execution history, timestamps, and output.  

> **Part of Kaiburr Assessment 2025 – Task 1: Java Backend and REST API Example.**

---

## ⚙️ Features
- Create, Read, Update, and Delete (CRUD) tasks  
- Execute shell commands and capture their output  
- Maintain execution history (start time, end time, output)  
- Command validation to prevent unsafe operations  
- Search tasks by name  
- Works on Windows (PowerShell) and Unix/Linux systems  

---

## 🧰 Technologies Used
- **Java 21**  
- **Spring Boot**  
- **MongoDB**  
- **Gradle** (or Gradle Wrapper)  

---

## 📁 Project Structure
```
src/
├── main/
│   ├── java/com/task1/task1/
│   │   ├── controller/
│   │   │   ├── HealthController.java
│   │   │   ├── RestExceptionHandler.java
│   │   │   └── TaskController.java
│   │   ├── model/
│   │   │   ├── Task.java
│   │   │   └── TaskExecution.java
│   │   ├── repository/
│   │   │   └── TaskRepository.java
│   │   ├── service/
│   │   │   ├── CommandValidator.java
│   │   │   ├── InMemoryTaskService.java
│   │   │   └── TaskService.java
│   │   └── Task1Application.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/task1/task1/
        └── Task1ApplicationTests.java
```

---

## 🚀 How to Run the Project

### 🧱 Option 1: Using Gradle Wrapper
```bash
# Build the application
./gradlew build

# Run the application
./gradlew bootRun
```

### 🪶 Option 2: Using JAR file
```bash
gradlew bootJar
java -jar build/libs/task1-0.0.1-SNAPSHOT.jar
```

### 💻 Option 3: Using IDE
1. Import the project into IntelliJ IDEA, Eclipse, or VS Code  
2. Run the main class `Task1Application.java`

---

## ⚙️ Configuration
Update your MongoDB URI in `src/main/resources/application.properties`:
```properties
spring.application.name=task1
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster.mongodb.net/<database>
```

---

## 🌐 API Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/tasks` | Get all tasks |
| GET | `/tasks?id={id}` | Get a task by ID |
| PUT | `/tasks` | Create or update a task |
| DELETE | `/tasks/{id}` | Delete a task |
| GET | `/tasks/search?name={query}` | Search tasks by name |
| PUT | `/tasks/{id}/execute` | Execute a task command |

---

## 🧾 Example JSON Object
```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World!",
  "taskExecutions": []
}
```

---

## 🧪 Example curl Commands
```bash
# Create a task
curl -X PUT http://localhost:8080/tasks   -H "Content-Type: application/json"   -d '{"id":"123","name":"Print Hello","owner":"John Smith","command":"echo Hello"}'

# Execute the task
curl -X PUT http://localhost:8080/tasks/123/execute

# Get all tasks
curl http://localhost:8080/tasks
```

---

## ⚠️ Command Validation
The application blocks unsafe commands for security reasons.

### 🚫 Blocked Commands
- File deletion: `rm`, `rm -rf`  
- Privilege escalation: `sudo`  
- System shutdown: `shutdown`, `reboot`, `poweroff`  
- Command chaining: `;`, `&&`, `||`, `|`  
- Redirection: `>`, `>>`, `<`  
- Command substitution: `$(`, `` ` ``  
- Background processes: `&`  

## 📸 Screenshots Submission
<img width="1311" height="494" alt="Screenshot 2025-10-19 094325" src="https://github.com/user-attachments/assets/533a48fd-9080-4497-ae29-50aa969f61be" />


<img width="1627" height="931" alt="Screenshot 2025-10-18 002205" src="https://github.com/user-attachments/assets/4b76fa6b-ff30-4af1-b5f2-1678a5bb8dc5" />

<img width="1627" height="931" alt="Screenshot 2025-10-18 002205" src="https://github.com/user-attachments/assets/aae45cd2-2ed0-4301-b141-947146913dbc" />

<img width="1615" height="933" alt="Screenshot 2025-10-18 001906" src="https://github.com/user-attachments/assets/53cb042d-68f9-42e1-84e2-695846ed8f88" />

<img width="1108" height="524" alt="Screenshot 2025-10-18 001012" src="https://github.com/user-attachments/assets/42fbd822-0252-439e-8793-981f31745fe5" />

<img width="1145" height="496" alt="Screenshot 2025-10-18 000916" src="https://github.com/user-attachments/assets/3eb43453-f509-4574-abd2-a5d4c455ee38" />

<img width="1147" height="547" alt="Screenshot 2025-10-18 000807" src="https://github.com/user-attachments/assets/06823707-d252-419c-aff2-a3a1e7209206" />

<img width="1087" height="537" alt="Screenshot 2025-10-18 000656" src="https://github.com/user-attachments/assets/2f1da372-3e42-4e4b-adb3-f61c58f1a10c" />

<img width="1108" height="519" alt="Screenshot 2025-10-18 000619" src="https://github.com/user-attachments/assets/5022b11b-12a6-4587-8f7e-a5dc697657d6" />

<img width="1122" height="534" alt="Screenshot 2025-10-18 000509" src="https://github.com/user-attachments/assets/0b89503e-af72-496f-b9f1-b00d17e8ac3c" />

<img width="1591" height="833" alt="Screenshot 2025-10-18 000353" src="https://github.com/user-attachments/assets/17091f32-1f3e-4576-bc04-15c4c94b317c" />

<img width="1142" height="863" alt="Screenshot 2025-10-17 235725" src="https://github.com/user-attachments/assets/2a2e3d67-6815-4d6e-9b61-408bbe412821" />

<img width="827" height="462" alt="Screenshot 2025-10-17 235406" src="https://github.com/user-attachments/assets/809f5e06-ff65-4c16-a62d-187046db6870" />

<img width="832" height="515" alt="Screenshot 2025-10-17 235255" src="https://github.com/user-attachments/assets/6df36c25-c284-4574-b59a-fd5118f86925" />
