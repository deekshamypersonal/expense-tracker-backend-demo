Expense Tracker (Demo App)
An Expense Tracking application built with Spring Boot (backend) and React (frontend). Users can add expenses manually or by uploading a bill image (OCR-powered), manage budgets, and view visual summaries via a pie chart.

Note: This is a demo app using an in-memory H2 database. Data resets on every new application start. For demonstration, sign up is disabled, and only a demo account can be used.

Table of Contents
Demo Credentials
Features
Tech Stack
Screenshots
Setup & Installation
Running Locally
Usage
OCR Bill Upload
Limitations & Demo Constraints
Future Enhancements
License
Demo Credentials
Username (Email): userdemo
Password: userdemo
(Or any credentials you’ve specified, e.g. userdemo@gmail.com / Demo2024! if that’s what you set.)

On the Login screen, these demo credentials may be pre-filled for convenience.

Features
User Authentication: Demo user can log in (sign up is disabled).
Add Expense Manually:
Provide description, category, and amount.
Visual budget comparison to detect over-budget usage.
Pie Chart Visualization:
Displays categorized expenses in a chart.
Budget Management:
Set budgets for each category.
Alerts if an expense causes you to exceed a budget.
Bill Upload with OCR:
Automatically extracts text from the uploaded image (PNG/JPEG).
Classifies merchant or keywords to assign a category.
Extracts amount from the OCR’d text.
View & Delete Expenses:
Sort by date or amount.
Simple table layout to manage and delete existing records.
Tech Stack
Frontend: React (JavaScript), Tailwind CSS / basic CSS for styling, Chart.js (Pie chart).
Backend:
Spring Boot (Java)
Spring Security (JWT-based authentication)
H2 in-memory database (for the demo)
Tesseract (via Tess4J) for OCR
Build/Deployment:
Maven for the Spring Boot backend
NPM/Yarn for the React frontend
Screenshots
Include any relevant screenshots or animated GIFs here. For example:

Home / Dashboard	Budget Page	View Expense
screenshot	screenshot	screenshot
Setup & Installation
Clone the Repository:

bash
Copy
Edit
git clone https://github.com/your-username/expense-tracker-demo.git
cd expense-tracker-demo
Backend Configuration (Spring Boot):

By default, it uses an H2 in-memory DB. No additional setup needed.
For OCR, eng.traineddata should be available in src/main/resources (Tess4J requirement).
If deploying to Heroku or another cloud service, ensure environment variables (if any) are set.
Frontend Configuration (React):

Navigate to the frontend/ folder (or wherever your React code lives).
Install dependencies:
bash
Copy
Edit
npm install
Update the .env file (if using environment variables) to point to your backend URL, e.g.:
bash
Copy
Edit
REACT_APP_API_URL=http://localhost:8080
Run the dev server with:
bash
Copy
Edit
npm start
Running Locally
Run the Backend:

bash
Copy
Edit
# From the root of the Spring Boot project
mvn spring-boot:run
# or
mvn clean install && java -jar target/expensetracker-0.0.1-SNAPSHOT.jar
This starts your Spring Boot app at http://localhost:8080.

Run the Frontend:

In a separate terminal, navigate to your React project folder and:
bash
Copy
Edit
npm start
Access the React frontend at http://localhost:3000.
Login:

Open your browser to http://localhost:3000.
Enter the Demo Credentials or use pre-filled ones.
Usage
Login
The login page might already have the demo username and password pre-filled.
Click Login to authenticate.
Adding an Expense (Manual)
Enter a short description (max 30 chars).
Select a category from the dropdown.
Enter a positive amount.
Click Add Expense.
Viewing Expenses
Navigate to View Expense.
Expenses for the current month appear in a table.
You can sort by Date or Amount.
You can also Delete an expense.
Setting Budgets
Go to Budget.
Choose a category, enter an amount, and click Set Budget.
The budgets display in a table below.
If a newly added expense exceeds the budget, you get an alert.
OCR Bill Upload
On the Dashboard, scroll down to find Upload a PNG/JPEG File.
Click Choose File and select your bill image.
Click Upload.
The backend processes the bill (using Tesseract) and automatically categorizes the expense + extracts the amount.
The expense is then saved automatically.
Note: This is a demo. OCR might be imperfect if the bill text is unclear or if you upload a random image.

Limitations & Demo Constraints
The H2 in-memory DB resets on every application restart; data isn’t persistent.
Demo user is limited to 10 expenses total. If you exceed it, the UI alerts you to delete an existing expense first.
Sign-up is disabled. You can only log in with the demo credentials.
Basic OCR heuristics; unrecognized or non-receipt images may produce odd results.
Future Enhancements
Persistent Database: Switch to MySQL or PostgreSQL for permanent data.
More Robust OCR: Validate receipt text more thoroughly, handle more currencies, etc.
UI Enhancements: Additional charts, advanced filtering, improved design.
Mobile Responsiveness: The app layout is partially responsive but can be further optimized for smaller devices.
Notifications: Email or push notifications when budgets are exceeded.
