# InterviewAI - Backend Service ⚙️

Spring Boot 3.x REST API service powering the **InterviewAI** platform.

---

## 🛠️ Tech Stack & Architecture

- **Java Version**: 17
- **Framework**: Spring Boot 3.x
- **Data Persistence**: Spring Data JPA & Hibernate
- **Security**: Spring Security & BCrypt Password Encoder
- **Databases Supported**:
  - **H2 (Default Dev)**: File-based persistence under `./data/interviewaidb`
  - **MySQL 8.0**: Activated via `--spring.profiles.active=mysql`
- **Testing**: JUnit 5, Mockito

---

## 🚀 How to Run Backend

### Option 1: Using Maven Wrapper (Recommended)
From this `backend` directory:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / Mac
./mvnw spring-boot:run
```

The server will start on: **`http://localhost:8080`**

### Option 2: Running with MySQL
Ensure MySQL is running on port 3306 and execute:
```bash
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=mysql
```

---

## 🧪 Running Tests
```bash
.\mvnw.cmd test
```

---

## 📦 Building JAR
```bash
.\mvnw.cmd clean package -DskipTests
```
The resulting executable JAR will be located in `target/interviewai-0.0.1-SNAPSHOT.jar`.

---

## 📡 API Endpoints

### 1. Authentication (`/api/auth`)
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/register` | Register new user `{ name, email, password }` |
| `POST` | `/api/auth/login` | Login user `{ email, password }` |
| `GET` | `/api/auth/user?email=...` | Get user profile |

### 2. Interview Tracks (`/api/interviews`)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/interviews` | List all interview tracks (auto-seeds defaults) |
| `POST` | `/api/interviews` | Create custom track `{ title, description }` |
| `GET` | `/api/interviews/{id}` | Get interview details |
| `PUT` | `/api/interviews/{id}` | Update interview track |
| `DELETE` | `/api/interviews/{id}` | Delete interview and cascaded data |
| `GET` | `/api/interviews/{id}/start` | Start session & get first AI question |
| `GET` | `/api/interviews/{id}/next-question` | Generate & retrieve next AI question |
| `GET` | `/api/interviews/{id}/detailed-report` | Full evaluation scorecard with scores & feedback |
| `GET` | `/api/interviews/{id}/report` | Formatted plain text scorecard |
| `POST` | `/api/interviews/{id}/reset` | Reset all recorded answers for interview |
| `POST` | `/api/interviews/seed` | Reset/seed default 6 tracks |

### 3. Answers & Evaluations (`/api/answers`)
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/answers` | Submit answer `{ questionId, answerText }` (AI graded out of 10) |
| `GET` | `/api/answers/interview/{id}` | Retrieve all answers for interview |

### 4. Direct AI Utilities (`/api/ai`)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/ai/question?topic=...` | Generate technical interview question |
| `POST` | `/api/ai/answer` | Generate model answer `{ topic, question }` |
| `POST` | `/api/ai/score` | Score custom response `{ question, answer }` |
