# JobRec - AI-Based Job Recommendation System

> A full-stack Java web application for intelligent job recommendation based on user preferences and keyword extraction, using Tomcat, MySQL, and external AI APIs.

---

## 🔧 Tech Stack

- **Backend:** Java, Servlet API, JDBC, MySQL
- **Frontend:** HTML, JSP, CSS, JavaScript
- **Server:** Apache Tomcat 11.0.2
- **Database:** MySQL 8.x
- **External APIs:** OpenAI (EdenAIClient), SerpAPI
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA

---

## 📁 Project Structure

jobrec/
├── .idea/                     # IntelliJ project config
├── src/
│   └── main/
│       └── java/
│           └── com.example.jobrec/
│               ├── db/                  # MySQL connection, table creation, and utilities
│               │   ├── MySQLConnection.java
│               │   ├── MySQLDBUtil.java
│               │   └── MySQLTableCreation.java
│               ├── entity/             # Data transfer objects (DTOs)
│               │   ├── Item.java
│               │   ├── LoginRequestBody.java
│               │   ├── LoginResponseBody.java
│               │   ├── RegisterRequestBody.java
│               │   └── ResultResponse.java
│               ├── external/           # External API integrations
│               │   ├── EdenAIClient.java
│               │   └── SerpAPIClient.java
│               ├── recommendation/     # Job recommendation logic
│               │   └── Recommendation.java
│               └── servlet/            # Web endpoints
│                   ├── HistoryServlet.java
│                   ├── LoginServlet.java
│                   ├── LogoutServlet.java
│                   ├── RecommendationServlet.java
│                   ├── RegisterServlet.java
│                   └── SearchServlet.java
├── webapp/
│   ├── scripts/
│   │   └── main.js
│   ├── styles/
│   │   └── main.css
│   ├── WEB-INF/
│   │   └── web.xml
│   └── index.jsp
├── test/                     # (Optional) Test code
├── target/                   # Build artifacts
├── pom.xml                   # Maven dependencies and config
└── README.md

---

## 🚀 Features

- 🔐 **User Authentication**: Register & login with password verification.
- ❤️ **Favorite Jobs**: Users can save or remove jobs from their favorites.
- 🔍 **Job Search**: Integrated with SerpAPI to search real-time job data.
- 🧠 **Keyword Extraction**: Uses EdenAI to extract keywords from job descriptions.
- 🎯 **Personalized Recommendation**: Recommends jobs based on saved preferences.
- 📊 **MySQL Integration**: Stores user info, job history, and extracted keywords.

---

## 🔌 API Endpoints (Servlets)

| Endpoint                  | Description                           |
|--------------------------|---------------------------------------|
| `/LoginServlet`          | Handles login requests                |
| `/RegisterServlet`       | Handles user registration             |
| `/SearchServlet`         | Job search using external API         |
| `/RecommendationServlet` | Returns personalized job suggestions  |
| `/HistoryServlet`        | Add/remove/view favorite jobs         |
| `/LogoutServlet`         | Clears session                        |

---

## ⚙️ Setup Instructions

   1. **Clone the repository**
   ```bash
   git clone https://github.com/bangbangjii/jobrec.git
   2.	Database setup
	•	Create a MySQL database.
	•	Run the schema creation from MySQLTableCreation.java or use provided .sql files.
	•	Update MySQLDBUtil.java with your DB credentials and URL.
	3.	Build & Run
	•	Import into IntelliJ as a Maven project.
	•	Deploy jobrec.war to Tomcat 11.
	•	Access via http://localhost:8080/jobrec
