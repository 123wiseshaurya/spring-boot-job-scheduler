🌍 Deployment
You can access the live demo of the project here:

🔗 Live URL: https://fe43-2401-4900-4e74-f37-4cac-3d5d-744c-5ad.ngrok-free.app


# 🕒 Job Scheduler App

A full-stack **Job Scheduler** web application built using **Spring Boot**, **React**, **Kafka**, **YugabyteDB**, and **MinIO**. It supports scheduled and immediate job execution for emails, Kafka messages, and binaries (like `.jar`/`npm` scripts), with recurring, delayed, and timezone-aware execution.

---

## 🚀 Features

- 📧 Schedule and send emails
- 🛠️ Run shell commands or binary scripts (`.jar`, `npm`, etc.)
- 📨 Produce and consume Kafka messages
- ♻️ Supports one-time and recurring jobs (hourly, daily, weekly, monthly)
- 🕰️ Supports delayed execution
- 🌐 Time zone-aware scheduling
- 💾 Binary upload to MinIO and execution via presigned URLs
- 📋 View scheduled jobs with status tracking (`PENDING`, `SUCCESS`, `FAILURE`)
- ⚙️ REST APIs + React frontend (hosted via Spring Boot's static resources)

---

## 🧱 Tech Stack

| Layer        | Technology            |
|--------------|------------------------|
| Backend      | Spring Boot, Java      |
| Frontend     | React.js               |
| Messaging    | Apache Kafka           |
| Database     | YugabyteDB (PostgreSQL-compatible) |
| File Storage | MinIO (S3-compatible)  |
| Dev Tools    | IntelliJ, Docker, Postman |

---

## 📁 Project Structure
spring-boot-job-scheduler/
├── src/
│   ├── main/
│   │   ├── java/           # Spring Boot backend---

## ⚙️ Setup Instructions

### 1. 🐳 Start Dependencies
```bash

docker-compose up -d
│   │   └── resources/
│   │       └── static/     # React build (frontend)
├── docker-compose.yml      # MinIO, Kafka, Yugabyte setup
├── pom.xml                 # Maven dependencies
└── README.md               # This file
2. 🧪 Run Locally

Backend (Spring Boot)

cd spring-boot-job-scheduler
./mvnw spring-boot:run

Frontend (React)

cd job-scheduler-ui
npm install
npm run build
# Copy build to static folder or serve separately
📸 Screenshots
<img width="1440" alt="Screenshot 2025-04-15 at 10 55 49 AM" src="https://github.com/user-attachments/assets/ed68a725-383e-4c64-8782-d956d7ca52da" />
[<img width="1440" alt="Screenshot 2025-04-15 at 10 56 02 AM" src="https://github.com/user-attachments/assets/7c6806db-e885-46df-b430-0152ef549709" />](https://github.com/123wiseshaurya/spring-boot-job-scheduler/issues/1#issue-2995206108)
<img width="1440" alt="Screenshot 2025-04-15 at 10 56 09 AM" src="https://github.com/user-attachments/assets/3950d034-ac38-4d49-a41f-5ae532d9c11e" />


Include frontend UI screenshots or a short demo GIF here.
Deployment
	•	The frontend can be served either:
	•	via Spring Boot static resources, OR
	•	hosted separately on Netlify/Vercel/etc.
	•	The backend can be Dockerized or deployed to Railway, AWS, etc.

⸻

🧪 Testing
	•	✅ Unit + Integration tests using JUnit, Mockito, Testcontainers
	•	✅ Kafka and DB tested using Dockerized environments

⸻

🙌 Author

Built with 💻 by Shaurya Raghuwanshi
	•	GitHub: @123wiseshaurya
	•	LinkedIn: https://www.linkedin.com/in/shaurya-r-a8a08a346/

