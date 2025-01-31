# SportsBooking - ReadMe

## **Förklaring av kod**

SportsBooking är en **Spring Boot-applikation** som hanterar och visar information om två resurser: **idrottsanläggningar** och **bokningar**. Applikationen erbjuder ett **RESTful API**, vilket gör det möjligt för användare att **skapa, hämta, uppdatera och radera** data kopplade till dessa resurser.

## **Funktioner**

- **Hantering av idrottsanläggningar**: CRUD-operationer för att hantera idrottsanläggningar.
- **Hantering av bokningar**: CRUD-operationer för att hantera bokningar kopplade till anläggningarna.
- **RESTful API**: API-endpoints för att interagera programmatiskt med applikationen.

## **Struktur**

- **Controller-lagret**: Hanterar HTTP-förfrågningar och svar.
- **Service-lagret**: Innehåller affärslogik.
- **Repository-lagret**: Sköter datahantering via Spring Data JPA.

## **Diagram**

Ett **UML-diagram** som visar applikationens användningsfall finns i mappen `docs`.

## **BDD-tester**

SportsBooking använder **Behavior-Driven Development (BDD)** för att säkerställa att applikationen fungerar som förväntat ur ett användarperspektiv.

- BDD-testerna är implementerade med **Cucumber** och definieras i `booking_step_definitions.py`.
- Dessa tester följer **Given-When-Then-strukturen**.
- BDD-testfallen finns i `tests`-mappen och exekveras automatiskt via CI/CD-pipelinen.

## **Kör applikationen lokalt**

### **Klona projektet**

```bash
git clone https://github.com/litegranni/SportsBooking.git
cd SportsBooking
```

### **Bygg och starta applikationen**

```bash
mvn spring-boot:run
```

## **API-endpoints**

### **Idrottsanläggningar**

- `GET /api/facilities` – Hämta alla anläggningar
- `POST /api/facilities` – Skapa en ny anläggning
- `PUT /api/facilities/{id}` – Uppdatera en anläggning
- `DELETE /api/facilities/{id}` – Radera en anläggning

### **Bokningar**

- `GET /api/bookings` – Hämta alla bokningar
- `POST /api/bookings` – Skapa en ny bokning
- `PUT /api/bookings/{id}` – Uppdatera en bokning
- `DELETE /api/bookings/{id}` – Radera en bokning

## **Docker-distribution**

En **Dockerfil** finns för att containerisera applikationen.

### **1. Bygg Docker-imagen**

```bash
docker build -t sportsbooking:latest .
```

### **2. Starta Docker-containern**

```bash
docker run -p 8080:8080 sportsbooking:latest
```

Applikationen är nu tillgänglig på **http://localhost:8080**.

## **Continuous Integration**

Projektet använder **Continuous Integration (CI)** via **GitHub Actions**. Testerna körs automatiskt vid varje commit för att säkerställa kodkvalitet.

## **Reflektion**

- **Spring Boot** möjliggör snabb utveckling och enkel integration.
- **Docker** säkerställer att applikationen körs konsekvent i olika miljöer.
- **TDD och BDD** förbättrar kodkvaliteten och gör projektet lätt att underhålla.

## **Förbättringsområden**

- **Mer avancerad felhantering**: Vid mer komplexa fel bör detaljerad loggning och specifika undantag användas istället för generiska felmeddelanden.
- **Säkerhet**: Införa bättre autentisering och auktorisering för att skydda API:et.
- **Testtäckning**: Utöka testtäckningen genom att inkludera fler case-scenarion och integrationstester för att säkerställa robusthet i hela systemet.

