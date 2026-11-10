# Task 4: Full Stack Web Application with Spring Boot and Microservices 🚀

**College Assessment**: CLA-II Task 4 (Team Work: 3 Members per Team)  
**Bloom’s Taxonomy Level**: **K6 (Create & Evaluate)**  
**Duration**: Self-Learning 5 Hours | Team Work 6 Hours  
**Tech Stack**: Java 21, Spring Boot 3.2, Microservices Architecture, Docker Compose  

---

## 🏗️ Distributed System Architecture
```
                                +---------------------------+
                                |  React / HTML5 Frontend   |
                                +---------------------------+
                                              │ (HTTP / JSON)
                                              ▼
                                +---------------------------+
                                |  API Gateway (Port 8080)   |
                                +---------------------------+
                                       │             │
                    /api/catalog/**    │             │   /api/interaction/**
                                       ▼             ▼
                 +--------------------------+   +------------------------------+
                 | Catalog Svc (Port 8081)  |   | Interaction Svc (Port 8082)  |
                 | - Track Metadata         |   | - User Favorites             |
                 | - Movie Catalog          |   | - Play Count Metrics         |
                 | - Audio Previews         |   | - Listening Analytics        |
                 +--------------------------+   +------------------------------+
```

---

## 📦 Microservices Breakdown

1. **`api-gateway` (Port 8080)**:
   - Single Entry Point (Reverse Proxy & Aggregator).
   - Routes `/api/catalog/**` to `catalog-service`.
   - Routes `/api/interaction/**` to `interaction-service`.
   - Manages unified Cross-Origin Resource Sharing (CORS).

2. **`catalog-service` (Port 8081)**:
   - Manages track discography for Harris Jayaraj's 16 songs.
   - Provides audio metadata, release years, and movie mappings.

3. **`interaction-service` (Port 8082)**:
   - Manages user engagement: favorites toggling, live play logging, and engagement analytics.
   - Database-per-service pattern with independent state.

---

## 🐳 How to Run with Docker Compose
```bash
cd Task-4_Microservices_FullStack
docker-compose up --build
```
All microservices will boot concurrently in isolated bridge networks!
- Gateway: `http://localhost:8080`
- Catalog Service: `http://localhost:8081/api/catalog/songs`
- Interaction Service: `http://localhost:8082/api/interaction/analytics`
