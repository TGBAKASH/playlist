# Task 4: Full Stack Web Application with Spring Boot and Microservices 🚀

**Academic Assessment**: Web Technology Term Work / CLA–II  
**Bloom’s Taxonomy Level**: **K6 (Create & Evaluate)**  
**Collaboration Model**: **Team Work (Strictly 3 Members per Team)**  
**Duration**: Self-Learning: 5 Hours | Team Work: 6 Hours  
**Theme**: Harris Jayaraj Discography & High-Performance Audio Streaming Platform  

---

## 📑 Table of Contents
1. [Executive Summary & K6 Alignment](#-executive-summary--k6-alignment)
2. [Distributed Architectural Topology](#-distributed-architectural-topology)
3. [Microservices Cluster Breakdown](#-microservices-cluster-breakdown)
4. [Enterprise Design Patterns & Evaluation](#-enterprise-design-patterns--evaluation)
5. [Resilience4j Circuit Breaker & Chaos Engineering](#-resilience4j-circuit-breaker--chaos-engineering)
6. [Team Work Responsibility Matrix (3 Members)](#-team-work-responsibility-matrix-3-members)
7. [Docker Compose Orchestration Guide](#-docker-compose-orchestration-guide)
8. [Live API Reference & Verification](#-live-api-reference--verification)

---

## 🎯 Executive Summary & K6 Alignment

In accordance with Bloom's Taxonomy **Level K6 (Create & Evaluate)**, this task transitions our music streaming platform from a monolithic web architecture to a **loosely-coupled, fault-tolerant, horizontally scalable distributed microservices mesh**.

### Why Not a Monolith? (Architectural Evaluation)
| Evaluation Metric | Monolithic Architecture | Microservices Architecture (Our System) |
| :--- | :--- | :--- |
| **Blast Radius** | High: An error in analytics takes down audio playback | **Zero**: Downstream failure isolated via Circuit Breakers |
| **Scalability** | Scale the entire monolith uniformly | **Elastic**: Scale `interaction-service` 10x during peak traffic |
| **Deployment Risk** | High: Redeploy whole JAR for minor catalog change | **Independent**: CI/CD deployment per container |
| **Data Coupling** | Shared database locks & schema bottleneck | **Database-per-Service**: Decoupled domain datastores |

---

## 🏗️ Distributed Architectural Topology

```
                                      +------------------------------------+
                                      |     React 18 / HTML5 Frontend      |
                                      |   (Port 443 / Production HTTPS)    |
                                      +------------------------------------+
                                                         │
                                                         ▼ (HTTP / JSON / Reverse Proxy)
                                      +------------------------------------+
                                      |       Unified API Gateway          |
                                      |       (Spring Boot 3.2:8080)       |
                                      +------------------------------------+
                                                         │
                             ┌───────────────────────────┴───────────────────────────┐
                             │ (Reverse Proxy Routing)                               │
                             ▼                                                       ▼
               +---------------------------+                           +---------------------------+
               |     Catalog Service       |                           |    Interaction Service    |
               |  (Spring Boot 3.2:8081)   |                           |  (Spring Boot 3.2:8082)   |
               +---------------------------+                           +---------------------------+
                             │                                                       │
               ┌─────────────┴─────────────┐                                         ▼
               ▼                           ▼                           +---------------------------+
        +--------------+            +--------------+                   |  Neon Cloud PostgreSQL    |
        | In-Memory    |            | Apple iTunes |                   |  (Port 5432 / PgBouncer)  |
        | Track Cache  |            | Audio CDN    |                   +---------------------------+
        +--------------+            +--------------+
```

---

## 📦 Microservices Cluster Breakdown

### 1. `api-gateway` (Port 8080)
- **Role**: Single Ingress Point / Reverse Proxy.
- **Responsibilities**:
  - Centralizes Cross-Origin Resource Sharing (`@CrossOrigin(origins = "*")`).
  - Routes `/api/catalog/**` traffic to `catalog-service:8081`.
  - Routes `/api/interaction/**` traffic to `interaction-service:8082`.
  - Terminates TLS/SSL and prevents client-to-microservice direct chatter.

### 2. `catalog-service` (Port 8081)
- **Role**: Media & Discography Domain Microservice.
- **Responsibilities**:
  - Serves immutable track metadata for Harris Jayaraj's 16 songs.
  - Interfaces with Apple iTunes Search API for 256kbps audio preview streams.
  - In-memory cache layer to maintain `< 15ms` response latencies.

### 3. `interaction-service` (Port 8082)
- **Role**: User Engagement & Telemetry Microservice.
- **Responsibilities**:
  - Tracks user favorites, play count metrics, and real-time listening logs.
  - Connects to Neon Cloud PostgreSQL via JDBC connection pool.
  - Implements event-driven fire-and-forget logging to isolate analytics from playback.

---

## 🛡️ Enterprise Design Patterns & Evaluation

### 1. API Gateway Pattern
Direct client-to-microservice communication is a critical anti-pattern causing:
- Security perimeter vulnerabilities (exposing internal ports).
- Protocol mismatches and excessive round-trips over cellular networks.
- Difficult refactoring when services split or merge.  
**Solution**: Our API Gateway acts as a reverse proxy, insulating clients from cluster changes.

### 2. Database-per-Service Pattern
Catalog Service and Interaction Service maintain strict data isolation:
- Catalog Service operates an in-memory cached repository.
- Interaction Service commands the transactional Neon PostgreSQL database.
- **Result**: No single database lock can cascade across the cluster.

---

## ⚡ Resilience4j Circuit Breaker & Chaos Engineering

In a distributed environment, transient network timeouts must not cascade. Our API Gateway implements a **Resilience4j Circuit Breaker State Machine**:

```
 [ CLOSED (Healthy) ] ──(Failure Rate > 50%)──> [ OPEN (Degraded Fallback) ]
         ▲                                                │
         │                                         (Sleep Window 10s)
         │                                                ▼
         └─────────────(Success Rate > 80%)──── [ HALF-OPEN (Testing) ]
```

### Degraded Fallback Response:
When the Interaction Microservice is simulated offline:
```json
{
  "circuitBreaker": { "state": "OPEN", "failureRateThreshold": "50%" },
  "status": "DEGRADED_FALLBACK_ENGAGED",
  "targetService": "interaction-service:8082",
  "gracefulDegradation": {
    "catalogServiceAvailable": true,
    "audioPlaybackActive": true,
    "telemetryQueue": "In-Memory Event RingBuffer"
  }
}
```

---

## 👥 Team Work Responsibility Matrix (3 Members)

To strictly fulfill the **3 members per team** syllabus requirement, engineering deliverables were partitioned as follows:

| Member | Academic Role | Deliverables & CLA-II Rubrics |
| :--- | :--- | :--- |
| **Member 1 (Lead Architect)** | Gateway & Devops | • Built `api-gateway` reverse proxy with unified CORS.<br>• Authored `docker-compose.yml` multi-container bridge network.<br>• Designed Resilience4j circuit breaker fallback policies. |
| **Member 2 (Backend Engineer)** | Catalog Microservice | • Engineered `catalog-service` (Spring Boot 3.2, Port 8081).<br>• Structured 16-song Harris Jayaraj entity models.<br>• Integrated Apple iTunes CDN audio pipeline. |
| **Member 3 (Data Engineer)** | Interaction & Cloud DB | • Developed `interaction-service` (Spring Boot 3.2, Port 8082).<br>• Engineered Neon PostgreSQL connection pooling.<br>• Implemented raw JDBC `PreparedStatement` & `ResultSet` mapping. |

---

## 🐳 Docker Compose Orchestration Guide

### Prerequisites
- Docker Engine 24+ & Docker Compose v2+
- Open Ports: 8080, 8081, 8082

### Run Commands
```bash
# Navigate to the microservices root
cd Task-4_Microservices_FullStack

# Build images and boot all 3 containers concurrently:
docker-compose up --build

# Run in background (detached mode):
docker-compose up -d

# Scale the interaction service to 3 replicas for high-throughput listening:
docker-compose up --scale interaction-service=3

# Stop all containers and clean up bridge networks:
docker-compose down
```

---

## 🌐 Live API Reference & Verification

All endpoints can be invoked via the **Live Microservices Console** on the deployed web platform or via standard `curl`:

```bash
# 1. Fetch entire catalog via API Gateway:
curl -X GET http://localhost:8080/api/catalog/songs

# 2. Query individual song metadata:
curl -X GET http://localhost:8080/api/catalog/songs/0

# 3. Retrieve user engagement analytics:
curl -X GET http://localhost:8080/api/interaction/analytics

# 4. Asynchronously log a song play:
curl -X POST http://localhost:8080/api/interaction/plays/0

# 5. Toggle favorite status:
curl -X POST http://localhost:8080/api/interaction/favorites/0

# 6. Actuator Distributed Health Check:
curl -X GET http://localhost:8080/actuator/health
```
