@echo off
echo ========================================
echo Reiniciando servidor Spring Boot...
echo ========================================

echo.
echo [1/3] Deteniendo el servidor actual...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080 ^| findstr LISTENING') do (
    echo Deteniendo proceso: %%a
    taskkill /F /PID %%a
)

echo.
echo [2/3] Esperando 3 segundos...
timeout /t 3 /nobreak > nul

echo.
echo [3/3] Iniciando el servidor...
echo.
start "Spring Boot Server" cmd /k "cd /d %~dp0 && mvnw.cmd spring-boot:run"

echo.
echo ========================================
echo Servidor reiniciado!
echo Abre tu navegador en: http://localhost:8080
echo Los logs apareceran en la nueva ventana
echo ========================================
pause

