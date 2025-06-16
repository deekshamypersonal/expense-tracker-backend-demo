# 📌 Live Demo

The deployed application can be accessed via the following link:

🔗 [Expense Tracker App](https://willowy-licorice-a48823.netlify.app/login)

Demo credentials will be pre-filled for convenience. This account is for demo and has small limits (e.g., you can add up to 10 expenses)


# 📌 GitHub Repositories

### Backend Repository
🔗 [Expense Tracker Backend](https://github.com/deekshamypersonal/expense-tracker-backend-demo.git)

### Frontend Repository
🔗 [Expense Tracker Frontend](https://github.com/deekshamypersonal/expense-tracker-frontend-demo.git)


# 📌 Expense Tracker

This Monthly Expense Tracking application is built with **Spring Boot** (backend) and **React** (frontend). Users can:
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

On the login screen, these demo credentials will be pre-filled for convenience. This account is for demo and has small limits (e.g., you can add up to 10 expenses). For full access, signup.

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

Screenshots available at [Screenshots](https://docs.google.com/document/d/1mvHq8YTXcNLxeuCg57eiQ69Ypc3N_aELmJsEsDH9bPc/edit?tab=t.0)
https://github.com/user-attachments/assets/94470169-4596-4f49-8ece-cca5b607a9e1

---

# 📌 Local Setup & Installation

## ✨ Backend Setup (With Docker)

### Prerequisites
1. Docker Desktop (Windows/Mac) or docker-engine (Linux) running

### Steps:
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

### Steps:
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

## ✨ Frontend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/expense-tracker.git
   cd expense-tracker-frontend
2. Override backend url:
    ```bash
   echo "VITE_API_URL=http://localhost:8080" > .env.local
3. Install dependencies:
   ```bash
   npm install
4. Start the development server:
   ```bash
   npm start
4. The frontend will be available at:
      [http://localhost:5173](http://localhost:5173)


---


# 📌 API at a Glance

| # | Method | Path | Auth? | Body (JSON example) | Purpose |
|---|--------|------|-------|---------------------|---------|
| 1 | `POST` | `/users/login` | **No** | `{ "email": "userdemo@gmail.com", "password": "Demo2024!" }` | Authenticate and receive a JWT (token is returned in the **Authorization** response header). |
| 2 | `GET` | `/getExpense` | **Yes** | — | List every individual expense. |
| 3 | `GET` | `/getGroupedExpense` | **Yes** | — | Get totals grouped by category (for pie-chart view). |
| 4 | `GET` | `/getBudgets` | **Yes** | — | List all budget limits you’ve set. |
| 5 | `GET` | `/getTotal` | **Yes** | — | Overall spending total across all expenses. |
| 6 | `GET` | `/api/insights` | **Yes** | — | Fetch an AI-generated spending insight for the logged-in user. |
| 7 | `POST` | `/setExpense` | **Yes** | `{ "amount": 42.75, "category": "Food", "date": "2025-06-13" }` | Add a single expense (OCR is used if an image is attached). |
| 8 | `POST` | `/setBudget` | **Yes** | `{ "category": "Food", "limit": 300 }` | Create or update a budget for a category. |
| 9 | `DELETE` | `/deleteExpense/{id}` | **Yes** | — | Remove one expense by its ID. |
| 10 | `DELETE` | `/deleteBudget/{id}` | **Yes** | — | Remove a budget limit by its ID. |
| 11 | `POST` | `/register` | No | `{ "email": "...", "password": "...", "name": "..." }` | Create a new user. |


---

# 📌 Test With Postman

1. **Download** the collection and environment files from the following location:  
   [https://github.com/deekshamypersonal/expense-tracker-backend-demo/tree/main/docs/postman](https://github.com/deekshamypersonal/expense-tracker-backend-demo/tree/main/docs/postman)

2. **Import** both files into Postman:
   - `Expense Tracker API.postman_collection.json`
   - `ExpenseTracker-Local.postman_environment.json`

3. **Send the login request**  
   - Demo credentials are already filled.  
   - The token will be automatically added to the environment after login.

4. **Test other APIs** using the imported collection.





