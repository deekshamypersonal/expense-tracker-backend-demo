# Expense Tracker (Demo App)

An Expense Tracking application built with **Spring Boot** (backend) and **React** (frontend). Users can:
- Add expenses manually or by uploading a bill image (OCR-powered). The app automatically categorizes the expense.
- Manage budgets and receive alerts if spending exceeds the set budget for any category.
- View visual summaries via an interactive pie chart.
- Gain **AI-powered weekly insights** into their monthly expenditures for better financial planning.

---

## Table of Contents
- [Demo Credentials](#demo-credentials)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Screenshots](#screenshots)
- [Setup & Installation](#setup--installation)
- [Future Enhancements](#future-enhancements)

---

## Demo Credentials
Use the following credentials to log in:
- **Username (Email):** `userdemo@gmail.com`
- **Password:** `Demo2024!`

On the login screen, these demo credentials may be pre-filled for convenience.

---

## Features
- **User Authentication:** Demo user can log in (sign-up is disabled).
- **Add Expense Manually:** Provide description, category, and amount.
- **Visual Budget Comparison:** Detect over-budget usage.
- **Pie Chart Visualization:** Displays categorized expenses in an interactive chart.
- **Budget Management:** Set budgets for each category and get alerts for over-budget expenses.
- **OCR Bill Upload:** Extracts text from the uploaded image (PNG/JPEG) and  automatically assigns expenditure category.
- **Expense Management:** View and delete expenses in a sortable table.

---

## Tech Stack
- **Frontend:** React (JavaScript), Tailwind CSS / Basic CSS for styling, Chart.js for pie chart visualization.
- **Backend:** Spring Boot (Java), Spring Security (JWT-based authentication), SQL database.
- **OCR:** Tesseract (via Tess4J).
- **Build/Deployment:** Maven for Spring Boot backend and NPM/Yarn for React frontend.

---

## Screenshots
![Login Page](screenshots/login.png)
*Login page with pre-filled demo credentials.*

![Expense Dashboard](screenshots/dashboard.png)
*Dashboard showcasing pie chart and expense list.*

![OCR Bill Upload](screenshots/ocr_upload.png)
*Upload bills and auto-extract details using OCR.*

---

## Setup & Installation

### Prerequisites
1. **Java** (JDK 8 or higher)
2. **Node.js** and **npm/yarn**
3. Maven (for Spring Boot)

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/expense-tracker.git
cd backend
mvn clean install
mvn spring-boot:run

cd frontend
npm install
npm start
