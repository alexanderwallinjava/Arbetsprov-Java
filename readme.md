# Arbetsprov-java
## en enklare REST-lösning byggd med Spring Boot

Kräver Maven och java 8.
Huvudsakliga dependencies är Spring, delar av Apache Commons, m.m.


## Quick readme

### test

mvn test

Test körs på port 8080

### bygga

klient och server byggs med sh-scriptet runme.sh
den kör de två maven-scripten för att skapa en klient och en server

### starta

servern kräver application.properties-filen i samma folder

java -jar server.jar

java -jar client.jar

körs på port 8080