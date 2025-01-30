# Använd en lättviktig OpenJDK-bild
FROM openjdk:21-jdk-slim

# Sätt arbetskatalogen i containern
WORKDIR /app

# Kopiera den kompilerade JAR-filen från target-mappen
COPY target/sportsbooking-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponera porten som Spring Boot kör på
EXPOSE 8080

# Startkommandot för Spring Boot-applikationen
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
