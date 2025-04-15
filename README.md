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
## 📸 Screenshots

[![Image 1](https://github.com/user-attachments/assets/565b1d05-1504-4d07-9a8f-ad541e24d6fc)](https://github.com/123wiseshaurya/spring-boot-job-scheduler/issues/2#issue-2995741904)
![Image 2](https://github.com/user-attachments/assets/6dbe4bce-e245-40c0-8ea9-d712532fdd6b)
![Image 3](https://github.com/user-attachments/assets/6e2712e5-7a0c-4062-a304-1d1647685684)

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

