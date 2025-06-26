# 💰 Expense Tracker API

A personal finance management backend application built with Spring Boot and MongoDB. The app allows users to securely manage their income, expenses, categories, and view analytics — helping them stay on top of their finances.

## 🧾 Features

- ✅ User registration and login (JWT or simple ID-based auth)
- ✅ Add, update, delete, and view expenses
- ✅ Filter expenses by category or date range
- ✅ Search expenses by title
- ✅ Get total expense
- ✅ Currency formatting support
- ✅ Role-based access ready (Planned)

## 🧰 Tech Stack

- **Java 17**
- **Spring Boot 3**
- **MongoDB**
- **Spring Data MongoDB**
- **Maven**
- **Lombok**
- **JUnit** (for testing)
- **Postman** / Swagger UI (for API testing)

## 📁 Project Structure



## 📦 API Endpoints

| Method | Endpoint                    | Description                  |
|--------|-----------------------------|------------------------------|
| POST   | `/api/v1/expenses`          | Add a new expense            |
| GET    | `/api/v1/expenses`          | Get all expenses             |
| GET    | `/api/v1/expenses/search`   | Search expenses by title     |
| GET    | `/api/v1/expenses/category` | Filter expenses by category  |
| GET    | `/api/v1/expenses/date`     | Filter by date range         |
| GET    | `/api/v1/expenses/total`    | Get total expense            |
| PUT    | `/api/v1/expenses/{id}`     | Update expense               |
| DELETE | `/api/v1/expenses/{id}`     | Delete expense               |

## 💻 Sample Request

```json
POST /api/v1/expenses
{
  "userId": "64a8fcbc8123abc901e",
  "title": "Internet Bill",
  "amount": 5000,
  "category": "Utilities",
  "currency": "NGN",
  "date": "2025-06-20"
}


{
  "message": "Expense added successfully",
  "expenseId": "66ac8913abc1234ef"
}


# Clone the repo
git clone https://github.com/mosesidowu5050/expense-tracker.git
cd expense-tracker

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run


🧪 Testing
Use Postman or Swagger to interact with the API. A Postman collection will be provided in /docs.

🧠 Future Enhancements
 Monthly summary/analytics

 PDF report generation

 Authentication with JWT

 Admin dashboard

 Mobile-friendly frontend (React or Vue)

👨‍💻 Author
Idowu Moses Babatunde @mosesidowu


---

Would you like Swagger UI setup for the API docs, Docker support, or frontend connection guide added next?
