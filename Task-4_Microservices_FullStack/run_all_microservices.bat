@echo off
title Task 4 - Spring Boot Microservices Orchestrator
echo ====================================================================
echo Starting Task 4: Full Stack Spring Boot Microservices Suite
echo ====================================================================
echo.
echo [1/3] Launching Catalog Microservice on port 8081...
start "Catalog Microservice (Port 8081)" cmd /c "cd /d %~dp0catalog-service && mvn spring-boot:run"
timeout /t 5 >nul

echo [2/3] Launching Interaction Microservice on port 8082...
start "Interaction Microservice (Port 8082)" cmd /c "cd /d %~dp0interaction-service && mvn spring-boot:run"
timeout /t 5 >nul

echo [3/3] Launching Unified API Gateway on port 8080...
start "API Gateway (Port 8080)" cmd /c "cd /d %~dp0api-gateway && mvn spring-boot:run"

echo.
echo ====================================================================
echo ALL 3 MICROSERVICES ARE BOOTING IN PARALLEL!
echo.
echo - API Gateway:         http://localhost:8080
echo - Catalog Service:     http://localhost:8081/api/catalog/songs
echo - Interaction Service: http://localhost:8082/api/interaction/analytics
echo ====================================================================
pause
