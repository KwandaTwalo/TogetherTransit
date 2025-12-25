# TogetherTransit 🚐  
### Student Transport Management System

TogetherTransit is a **real-world, backend-focused student transport management system** designed to handle **monthly school transport subscriptions**, flexible schedules, exam-only travel, and secure digital payments.

Unlike ride-hailing platforms (e.g. Uber or Bolt), TogetherTransit is built around **long-term transport contracts**, where parents pay drivers **monthly**, with additional support for exams, extra classes, weekends, and multiple children under one booking.

---

## 📌 Problem Statement

Many parents rely on private drivers to transport learners to and from school. Existing solutions are often:
- Informal and manual
- Difficult to manage for exam periods
- Unable to support siblings under one contract
- Lacking structured payment tracking and accountability
- Missing a trusted rating and moderation system

TogetherTransit solves these problems by introducing a **structured, secure, and scalable transport management platform**.

---

## 🎯 Project Goals

- Support **monthly transport contracts**, not per-ride bookings
- Allow **one booking to cover multiple children**
- Handle **exam periods** without changing monthly contracts
- Support **weekend and extra class transport**
- Ensure **secure payments** without storing sensitive card data
- Provide **driver accountability** through moderated ratings
- Design a backend that can scale to **mobile and web clients**

---

## 🧩 System Overview

TogetherTransit connects the following key actors:

- **Parents** – manage children, bookings, payments, and ratings  
- **Drivers** – manage routes, vehicles, and transport schedules  
- **Children (Learners)** – assigned to routes and bookings  
- **Schools** – linked to routes and exam schedules  
- **Admins** – oversee users, permissions, and moderation  

The system is designed using **clean domain-driven principles**, with strong data normalization and well-defined entity relationships.

---

## 🏗️ Architecture & Design Principles

- **Spring Boot** for backend services
- **JPA / Hibernate** for ORM and persistence
- **Relational database design** (MySQL)
- **Factory pattern** for controlled object creation
- **Validation helpers** to enforce business rules
- **Lazy vs Eager fetching** used intentionally for performance
- **No sensitive payment data stored** (token-based approach)

---

## 👤 User Management

### User Roles
- Parent
- Driver
- Admin

Each role extends a base `User` model and has role-specific responsibilities and permissions.

### Authentication & Validation
- Email format validation
- Password strength validation
- Phone number validation (South African format supported)
- Account lockout after multiple failed login attempts

---

## 👶 Child Management

- Parents register children using **date of birth**
- Age is **calculated dynamically**, not stored
- Eliminates the need for yearly age updates
- Each child belongs to one parent
- Children are linked to transport routes via bookings

---

## 🗺️ Routes & Scheduling

Routes represent the **transport path from a child’s home to their school**, not a GPS navigation route.

### Route Features
- Pickup and drop-off locations
- GPS coordinates + address support
- Assigned driver and school
- Service days (Monday–Sunday)
- Multiple daily time slots (morning, afternoon, evening)
- Route status lifecycle (Scheduled, Active, Completed, Cancelled)

---

## ⏰ Time Slots

A route can have multiple time slots to support:
- Morning school runs
- Afternoon return trips
- Extra classes
- Late knock-off times

This allows maximum flexibility without duplicating routes.

---

## 📅 Exam Transport Handling

Exams are handled **without modifying monthly contracts**.

### ExamPeriod
- Defines an exam window (e.g. June Exams)
- Linked to a specific route
- Contains multiple exam sessions

### ExamSession
- Represents a specific exam day
- Supports different paper times (Morning / Afternoon)
- Only schedules transport on exam days
- No transport on non-writing days

This design allows **fine-grained control** over exam transport schedules.

---

## 📘 Booking System

- One booking represents a **monthly contract**
- A booking can include **multiple children (siblings)**
- Bookings define:
  - Start and end date of service
  - Assigned route
  - Pricing and status
- Bookings are linked to payments and ratings

---

## 💳 Payment System

Payments are **one-to-one with bookings**, ensuring traceability.

### Key Principles
- No card numbers or CVV stored in the database
- Uses **token-based payment method storage**
- Supports multiple payment methods:
  - Card
  - Google Pay
  - Apple Pay
  - PayPal
  - EFT

### Payment
- Tracks payment amount and currency
- Stores the service period covered by the payment
- Handles payment status (Pending, Success, Failed, Refunded)

### PaymentMethod
- Linked to a parent
- Stores only provider tokens
- Displays user-friendly info (e.g. Visa ****1234)
- Allows enabling/disabling payment methods

---

## ⭐ Rating System

- Parents can rate drivers **only after a completed booking**
- Rating categories:
  - Punctuality
  - Safety
  - Communication
- Optional written feedback
- One rating per booking
- Overall score calculated (not stored redundantly)

### Moderation
- Ratings go through an approval process
- Admins can approve or reject inappropriate feedback

---

## 🔐 Admin Responsibilities

Admins are responsible for:
- User management
- Permission control
- Rating moderation
- System oversight and integrity

Admin permissions are managed through structured roles rather than hard-coded logic.

---

## 🧪 Validation & Business Rules

- Factory classes enforce entity creation rules
- Helper utilities validate:
  - Email
  - Phone numbers
  - Password strength
  - Rating scores
  - Feedback length
- Prevents invalid data from entering the system

---

## 🛠️ Technology Stack

- **Java**
- **Spring Boot**
- **JPA / Hibernate**
- **MySQL**
- **RESTful APIs**
- **Git & GitHub**

---

## 🚀 Future Enhancements

- OTP-based phone verification
- Push notifications for drivers and parents
- Mobile app integration
- Automated invoicing and receipts
- Driver availability calendars
- Real-time trip tracking (optional)

---

## 📌 Conclusion

TogetherTransit is a **production-ready backend system** designed around real-world school transport needs.  
It demonstrates:
- Strong system design
- Clean domain modeling
- Secure payment practices
- Scalable architecture
- Practical business logic

This project showcases my ability to design and build complex backend systems aligned with real operational requirements.

---

## 👨‍💻 Author

**[Kwanda Twalo]**  

  
