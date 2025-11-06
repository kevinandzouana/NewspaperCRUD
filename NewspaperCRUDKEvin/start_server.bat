@echo off
echo ========================================
echo Iniciando servidor Spring Boot...
echo ========================================
echo.

cd /d "%~dp0"

echo Verificando Java...
java -version
echo.

echo Iniciando el servidor...
echo El servidor estara disponible en: http://localhost:8080
echo Presiona Ctrl+C para detener el servidor
echo.

mvnw.cmd spring-boot:run -DskipTests

pause

