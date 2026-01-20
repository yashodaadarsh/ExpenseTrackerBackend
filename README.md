
---

# Expense Tracker Backend – Microservices Architecture

**ExpenseTrackerBackend** is a backend system designed to support a modern expense tracking application using a **microservices architecture**.
This project demonstrates real-world backend development skills including **distributed services, API routing, messaging systems, containerization, and database management**.

---

##  Project Overview

An **Expense Tracker** helps users log, view and analyze their financial transactions.
This implementation focuses on a **highly scalable microservices backend** where individual services handle distinct responsibilities such as:

* User authentication
* Expense management
* Authorization and routing of API calls

Each backend component runs independently and can scale separately — similar to how large distributed systems work in enterprise environments.

---

##  Why This Architecture?

Modern backend systems often avoid single monolithic servers because:

* **Scalability** — Services can be scaled independently
* **Flexibility** — Teams can develop or update small parts without affecting the whole system
* **Resilience** — A failure in one service doesn’t bring the entire system down

An **API Gateway** acts as a centralized entry point for client requests masking the underlying complexity of services. ([Kong Inc.][1])

---

##  Project Components

This project includes the following key components (microservices):

| Component                | Description                                             |
| ------------------------ | ------------------------------------------------------- |
| **AuthService**          | Handles user authentication and authorization           |
| **UserService**          | Manages user account data                               |
| **ExpenseService**       | Records and returns expense entries                     |
| **DSService**            | Coordinates data workflows (e.g., events)               |
| **Docker Configuration** | Builds images and enables orchestration                 |
| **Docker Compose**       | Connects services and dependencies (Kafka, MySQL, Kong) |

---

##  Integrated Technologies

This project uses the following major technologies:

###  Docker & Docker Compose

Used to containerize services and deploy them together with supporting infrastructure such as databases and messaging systems.

###  Kafka

Asynchronous message broker used to exchange event data between services.

###  MySQL

Relational database used for persistent storage of application data.

###  Kong (API Gateway)

Acts as the **single entry point** for API requests and routes them to the appropriate backend service. Kong also provides additional capabilities such as authentication, rate limiting, and monitoring. ([Kong Inc.][1])

---

##  High-Level Architecture Diagram

```
         ┌────────────────┐
         │ Client (UI/App)│
         └───────┬────────┘
                 │
             [ API Gateway ]
                 │
    ┌────────────┼────────────┐
    │            │            │
Auth Service  User Service  Expense Service
    │            │            │
   MySQL       MySQL        MySQL
                 │
               Kafka
```

---

##  How It Works (Summary)

1. **Client calls the API Gateway** (Kong) to perform an action.
2. Kong forwards the request to the right microservice. ([Kong Inc.][1])
3. That service processes data and stores information in MySQL.
4. If a task requires communication with other services, Kafka sends a message allowing decoupled communication.

---

##  Setup & Run (Simplified)

>  You will need **Docker** and **Docker Compose** installed locally.

### 1. Clone the repo

```bash
git clone https://github.com/yashodaadarsh/ExpenseTrackerBackend.git
cd ExpenseTrackerBackend
```

### 2. Start Services

Run this from the root directory:

```bash
docker compose up --build
```

This will start all services, including:

* AuthService
* UserService
* ExpenseService
* Kafka
* MySQL
* Kong API Gateway

---

##  Key Skills Demonstrated

✔ Microservices design and development
✔ Containerization with Docker
✔ Inter-service communication using Kafka
✔ API routing using Kong API Gateway
✔ Database integration with MySQL

This project is an excellent example of backend engineering competencies required for scalable, production-ready distributed systems.

---

