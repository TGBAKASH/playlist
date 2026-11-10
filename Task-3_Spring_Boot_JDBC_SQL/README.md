# Task 3: Database Connectivity using SQL, JDBC and Spring Boot ☕ 🗄️

**College Assessment**: CLA-II Task 3 (Team Work: 3 Members per Team)  
**Bloom’s Taxonomy Level**: **K3 (Apply)**, **K4 (Analyze)**  
**Tech Stack**: Java 21, Spring Boot 3.2, Raw JDBC API, H2 In-Memory / File Database  

---

## 🎯 Syllabus Alignment & Concepts Demonstrated
1. **Raw JDBC Implementation**:
   - `java.sql.Connection` & `DataSource` connection pooling.
   - `java.sql.PreparedStatement` to prevent SQL Injection attacks.
   - `java.sql.ResultSet` for row-by-row object mapping.
   - Programmatic JDBC Transaction Management (`conn.setAutoCommit(false)`, `conn.commit()`, `conn.rollback()`).
2. **SQL Schema & Relational Integrity**:
   - DDL in `src/main/resources/schema.sql` (`songs`, `favorites`, `play_logs`).
   - DML in `src/main/resources/data.sql` pre-populating all 16 tracks.
3. **RESTful Service APIs**:
   - `GET /api/songs` — Retrieves all songs from SQL database via JDBC.
   - `POST /api/songs/{id}/play` — Transactional update incrementing play count.
   - `POST /api/songs/{id}/favorite` — Toggles favorite status in database.
   - `GET /api/songs/stats` — SQL aggregate analytics (`COUNT`, `SUM`, `MAX`).

---

## 🚀 How to Run Task 3
```bash
cd Task-3_Spring_Boot_JDBC_SQL
# If Maven is installed:
mvn spring-boot:run
```
- Open `http://localhost:8080/api/songs` to view JSON output.
- Open `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:musicdb`, user: `sa`, password: `password`) to interactively query the SQL database!
