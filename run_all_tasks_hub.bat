@echo off
:menu
cls
echo ====================================================================
echo        VIBZZ STUDIO - COLLEGE PROJECT LAUNCHER (CLA-II)
echo ====================================================================
echo.
echo   [1] Run Task 1: Responsive Web Page (HTML5, CSS3, 8D Audio, 200%% Gain)
echo   [2] Run Task 2: Interactive ReactJS Application (React 18 + Vite)
echo   [3] Run Task 3: Spring Boot JDBC & SQL Database (Port 8080)
echo   [4] Run Task 4: Full Stack Spring Boot Microservices Suite (3 Services)
echo   [5] Exit
echo.
echo ====================================================================
set /p choice="Enter your choice (1-5): "

if "%choice%"=="1" goto task1
if "%choice%"=="2" goto task2
if "%choice%"=="3" goto task3
if "%choice%"=="4" goto task4
if "%choice%"=="5" exit
goto menu

:task1
cls
echo Opening Task 1 in default web browser...
start "" "%~dp0index.html"
pause
goto menu

:task2
cls
echo Starting Task 2 React Development Server...
cd /d "%~dp0Task-2_React_Application"
call npm run dev
pause
goto menu

:task3
cls
echo Starting Task 3 Spring Boot + JDBC + H2 SQL Database...
cd /d "%~dp0Task-3_Spring_Boot_JDBC_SQL"
call mvn spring-boot:run
pause
goto menu

:task4
cls
echo Launching Task 4 Microservices Architecture...
cd /d "%~dp0Task-4_Microservices_FullStack"
call run_all_microservices.bat
goto menu
