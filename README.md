## Live Demo

The deployed application can be accessed via the following link:

🔗 [Expense Tracker App](https://willowy-licorice-a48823.netlify.app/login)


## GitHub Repositories

### Backend Repository
🔗 [Expense Tracker Backend](https://github.com/deekshamypersonal/expense-tracker-backend.git)

### Frontend Repository
🔗 [Expense Tracker Frontend](https://github.com/deekshamypersonal/expense-tracker-frontend.git)


# Expense Tracker

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

## Setup & Installation

### Prerequisites
1. **Java** (JDK 8 or higher)
2. **Node.js** and **npm/yarn**
3. Maven (for Spring Boot)

### Backend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/expense-tracker.git
2. Build the project using Maven:
   ```bash
   mvn clean install
3. Run the application:
   ```bash
   mvn spring-boot:run
4. The backend will be available at:
      [http://localhost:8080](http://localhost:8080)

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


