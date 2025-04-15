🌍 Deployment
You can access the live demo of the project here:

🔗 Live URL: https://fe43-2401-4900-4e74-f37-4cac-3d5d-744c-5ad.ngrok-free.app
"this is a deployment from my local system not cloud"

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
📁 Project Structure
src/main/java/
Contains the main Spring Boot backend code, including controllers, services, and configuration.

src/main/resources/
Holds application properties, static files, templates (like Thymeleaf or Freemarker), and other resources.

pom.xml
Maven build file — manages project dependencies and plugins.

docker-compose.yml
Docker configuration for setting up services like MinIO, Kafka, and YugabyteDB.

README.md
Project documentation, setup instructions, and usage guidelines.

Other files/folders
May include test files, Git configs, or additional support scripts depending on the project setup.


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
Images are located inside the output images directory.

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
MIT License

Copyright (c) 2025 Shaurya Raghuwanshi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.


