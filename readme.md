# Arbetsprov-java
## en enklare REST-lösning byggd med Spring Boot

Kräver Maven och java 8.
Huvudsakliga dependencies är Spring, delar av Apache Commons, m.m.


## Quick readme

### test

mvn test

Systemtest körs på port 8080
Enhetstest körs utan port exponeras

### bygga

klient och server byggs med sh-scriptet runme.sh
den kör de två maven-scripten för att skapa en klient och en server

### starta

servern kräver application.properties-filen i samma folder

java -jar server-1.0.jar

java -jar client-1.0.jar

servern kör på port 8080
obs! Klienten ansluter endast mot localhost