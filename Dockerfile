# =========================================================
# Production Dockerfile for Render Deployment
# Serves All 4 College Tasks (Frontend + REST API + PostgreSQL)
# =========================================================

# Stage 1: Build Application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven Project
COPY Task-3_Spring_Boot_JDBC_SQL/pom.xml .
COPY Task-3_Spring_Boot_JDBC_SQL/src ./src

# Ensure static directory exists and copy master frontend
RUN mkdir -p ./src/main/resources/static
COPY index.html ./src/main/resources/static/index.html
COPY style.css ./src/main/resources/static/style.css
COPY app.js ./src/main/resources/static/app.js

# Build standalone JAR
RUN mvn clean package -DskipTests

# Stage 2: Production JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render injects PORT dynamically
EXPOSE 8080
ENV PORT=8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
