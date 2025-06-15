# 📌 Live Demo

The deployed application can be accessed via the following link:

🔗 [Expense Tracker App](https://willowy-licorice-a48823.netlify.app/login)


# 📌 GitHub Repositories

### Backend Repository
🔗 [Expense Tracker Backend](https://github.com/deekshamypersonal/expense-tracker-backend.git)

### Frontend Repository
🔗 [Expense Tracker Frontend](https://github.com/deekshamypersonal/expense-tracker-frontend.git)


# 📌 Expense Tracker

This Monthly Expense Tracking application built with **Spring Boot** (backend) and **React** (frontend). Users can:
- Add expenses manually or by uploading a bill image (OCR-powered). The app automatically categorizes the expense.
- View all monthly expenses in a sortable list and delete entries as needed.
- Manage budgets and receive alerts if spending exceeds the set budget for any category.
- View visual summaries via an interactive pie chart.
- Gain **AI-powered weekly insights** into their monthly expenditures for better financial planning.

---

# 📌 Table of Contents
- [Demo Credentials](#demo-credentials)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Screenshots](#screenshots)
- [Setup & Installation](#setup--installation)
- [Future Enhancements](#future-enhancements)

---

# 📌 Demo Credentials
Use the following credentials to log in:
- **Username (Email):** `userdemo@gmail.com`
- **Password:** `Demo2024!`

On the login screen, these demo credentials may be pre-filled for convenience.

---

# 📌 Features
- **User Authentication (jwt-based):** Demo user can log in and signup.
- **Add Expense Manually:** Provide description, category, and amount.
- **Pie Chart Visualization:** Displays categorized expenses in an interactive chart.
- **Budget Management:** Set budgets for each category and get alerts for over-budget expenses.
- **OCR Bill Upload:** Extracts text from the uploaded image (PNG/JPEG) and  automatically assigns expenditure category.
- **Expense Management:** View and delete expenses in a sortable table.

---

# 📌 Tech Stack
- **Backend:** Spring Boot (Java), Spring Security (JWT-based authentication), SQL database.
- **Frontend:** React (JavaScript), Tailwind CSS / Basic CSS for styling, Chart.js for pie chart visualization.
- **OCR:** Tesseract (via Tess4J).
- **Build/Deployment:** Maven for Spring Boot backend and NPM/Yarn for React frontend.

---

# 📌 Screenshots

### Login Page
![Login Page](https://github.com/user-attachments/assets/e55edc0a-402e-4c3b-a0e3-d7cc3c063d3f)
*Login page with pre-filled demo credentials.*

### Expense Dashboard
![Expense Dashboard](https://github.com/user-attachments/assets/53eb3e39-69ce-4a92-9443-d68c5817756b)

![Expense Dashboard](https://github.com/user-attachments/assets/3af3ab03-4ad5-4e52-a04d-a0917a73c427)
*Dashboard showcasing pie chart and expense list.*

### OCR Bill Upload
![OCR Bill Upload](https://github.com/user-attachments/assets/7ae09b00-0233-4314-9981-229685f3974a)
*Upload bills and auto-extract details using OCR.*


---

# 📌 Local Setup & Installation

## ✨ Backend Setup (With Docker)

### Prerequisites
1. Docker Desktop (Windows/Mac) or docker-engine (Linux)

### Backend Setup
1. Run the command. If the image isn’t on machine Docker pulls it from Docker Hub, then starts the container.
   ```bash
   docker run -p 8080:8080 deekshatrip1/expense-tracker:latest

2. The backend will be available at:
      [http://localhost:8080](http://localhost:8080)

3. Verify it’s running. open http://localhost:8080/ping in your browser and look for pong
   

## ✨ Backend Setup (Without Docker)

### Prerequisites
1. **Java** (JDK 17 or higher)
2. **Tesseract OCR** (optional, for bill upload api)

### Backend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/expense-tracker.git
   cd expense-tracker-backend
2. Build the project:
   ```bash
   ./mvnw clean install
3. Run the application:
   ```bash
   m./mvnw spring-boot:run
4. The backend will be available at:
      [http://localhost:8080](http://localhost:8080)
5. Verify it’s running. open http://localhost:8080/ping in your browser and look for pong

### Frontend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/expense-tracker.git
2. Install dependencies:
   ```bash
   npm install
3. Start the development server:
   ```bash
   npm start
4. The frontend will be available at:
      [http://localhost:8080](http://localhost:2156)


