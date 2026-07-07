# 🚖 Ride Booking Application

A console-based **Ride Booking Application** developed using **Core Java**, **Hibernate ORM**, **Maven**, and **PostgreSQL**. The application simulates a real-world ride-hailing platform by providing separate modules for Riders, Drivers, and Admins with complete ride lifecycle management.

---

## 📌 Features

### 👤 Rider Module
- Register and Login
- Update Profile
- Book Ride
- View Current Ride
- Cancel Ride
- View Ride History
- Make Payment
- Rate & Review Driver

### 🚗 Driver Module
- Register and Login
- Update Profile
- Manage Vehicle
- Change Availability (Online/Offline)
- View Available Ride Requests
- Accept Ride
- Start Ride
- Complete Ride
- View Current Ride
- View Ride History

### 🛠️ Admin Module
- Manage Riders
- Manage Drivers
- Manage Vehicles
- Manage Rides
- View Payments
- View Ratings
- View System Analytics
  - Total Riders
  - Total Drivers
  - Completed Rides
  - Cancelled Rides
  - Total Revenue
  - Average Ride Fare
  - Top Rated Driver

---

# 🏗️ Technologies Used

- Java 17
- Hibernate ORM
- JPA
- PostgreSQL
- Maven
- Eclipse IDE

---

# 📂 Project Structure

```
RideBookingApplication-Hibernate
│
├── controller
├── dao
│   ├── interfaces
│   └── implementation
├── entity
├── enums
├── exception
├── service
├── util
└── App.java
```

---

# 🗄️ Database Design

The application consists of the following entities:

- User
- Rider
- Driver
- Ride
- Vehicle
- Payment
- Rating
- Admin

### Relationships

- User → Rider (Inheritance)
- User → Driver (Inheritance)
- Driver ↔ Vehicle (One-to-One)
- Rider → Ride (One-to-Many)
- Driver → Ride (One-to-Many)
- Ride → Payment (One-to-One)
- Ride → Rating (One-to-One)

---

# 🚕 Ride Lifecycle

```
Ride Requested
        │
        ▼
Ride Accepted
        │
        ▼
Ride Started
        │
        ▼
Ride Completed
        │
        ▼
Payment
        │
        ▼
Driver Rating
```

---

# 💳 Payment Flow

```
Ride Completed
        │
        ▼
Select Payment Method
        │
        ▼
Payment Successful
```

Supported payment methods:
- Cash
- Card
- UPI

---

# ⭐ Rating Flow

```
Ride Completed
        │
        ▼
Payment Successful
        │
        ▼
Rate Driver (1-5)
        │
        ▼
Review Submitted
```

---

# 📊 Admin Analytics

The Admin dashboard provides system-wide insights such as:

- Total Drivers
- Total Riders
- Completed Rides
- Cancelled Rides
- Total Revenue
- Average Ride Fare
- Top Rated Driver

---

# ⚙️ Key Concepts Implemented

- Hibernate ORM
- JPA Entity Relationships
- JPQL Queries
- DAO Design Pattern
- Layered Architecture
- Exception Handling
- Optimistic Locking using `@Version`
- Inheritance Mapping
- One-to-One Mapping
- One-to-Many Mapping
- Transactions
- CRUD Operations

---


# 🚀 How to Run

1. Clone the repository

```bash
git clone https://github.com/sanidhyabokade/RideBookingApplication-Hibernate.git
```

2. Open the project in Eclipse.

3. Create a PostgreSQL database.

4. Update database credentials in the Hibernate configuration.

5. Run:

```
App.java
```

---

# 📈 Future Enhancements

- REST APIs using Spring Boot
- JWT Authentication
- Email Notifications
- Live Ride Tracking
- Wallet Integration
- Docker Deployment

---

# 👨‍💻 Author

**Sanidhya Rajesh Bokade**

- GitHub: https://github.com/sanidhyabokade
- LinkedIn: *(Add your LinkedIn profile URL here)*

---

# 📄 License

This project is created for educational purposes and learning Hibernate ORM.

---

## ⭐ If you found this project helpful, consider giving it a Star!