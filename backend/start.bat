@echo off
echo Starting Simple API Server...

if not exist logs mkdir logs

call mvn clean package

java -jar target/backend-0.0.1-SNAPSHOT.jar
