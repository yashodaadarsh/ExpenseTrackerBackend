

# Expense Tracker Backend – Microservices Architecture

![Java](https://img.shields.io/badge/Java-Spring%20Boot-orange?style=flat-square&logo=java)
![Python](https://img.shields.io/badge/Python-Data%20Science-blue?style=flat-square&logo=python)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square&logo=docker)
![Kafka](https://img.shields.io/badge/Kafka-Event%20Streaming-black?style=flat-square&logo=apachekafka)
![Kong](https://img.shields.io/badge/Kong-API%20Gateway-green?style=flat-square&logo=kong)

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


### **Core Microservices**
| Service Name | Tech Stack | Responsibility |
| :--- | :--- | :--- |
| **Auth Service** | Java (Spring Boot) | Handles JWT authentication, authorization, and security protocols. |
| **User Service** | Java (Spring Boot) | Manages user account details. |
| **Data Science Service** | Python | Process the message received from the mobile. |

---

### **Infrastructure Components**
* **Kong API Gateway:** Centralizes traffic, handles rate limiting, and routes requests to appropriate microservices.
* **Apache Kafka:** Acts as the event backbone, enabling real-time data streaming between the User/Expense services and the Data Science engine.
* **MySQL:** Persistent relational storage for transactional data.
* **Docker Compose:** unified deployment configuration for spinning up the entire stack with a single command.


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

### 2. Create and Start Services

Run this from the root directory:

**1.** Create the jar file of each microservice and build the image with the name specified in expense_tracker_services.yml.

**2.** Go to the Docker folder and run the docker compose file of kong.yml and expense_tracker_services.yml by using the command.
```bash
    docker compose -f kong.yml up -d
    docker compose -f expense_tracker_services.yml

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

