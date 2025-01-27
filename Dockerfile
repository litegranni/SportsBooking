
FROM openjdk:21-jdk-slim

# Sätt arbetskatalogen i containern
WORKDIR /app

# Kopiera byggd JAR-fil från Maven/Gradle
ARG JAR_FILE=target/sportsbooking-0.0.1-SNAPSHOT.jar
COPY ./target/sportsbooking-0.0.1-SNAPSHOT.jar /app.jar

# Ange kommando för att köra applikationen
ENTRYPOINT ["java", "-jar", "/app.jar"]
