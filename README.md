# rewards-api

Spring Boot REST API that calculates customer reward points from purchase transactions over a three-month period.

## Overview

This project implements a small REST service that:

- Loads transaction data into an in-memory H2 database (see `src/main/resources/data.sql`).
- Calculates reward points per transaction using these business rules:
  - No points for amounts <= $50
  - 1 point per dollar for each dollar spent over $50 up to $100
  - 2 points per dollar for each dollar spent over $100 (plus the 50 points for the $50-$100 portion)
- Aggregates points per customer into three time buckets relative to the current date: 0-30 days, 31-60 days and 61-90 days. Transactions older than 90 days are ignored.

The service exposes a single GET endpoint to retrieve aggregated reward summaries per customer.

## Tech stack

- Java 17+ (project uses Jakarta/JPA and Spring Boot)
- Spring Boot (Web, JPA)
- H2 (in-memory) for sample data
- Maven wrapper included (`mvnw`, `mvnw.cmd`)

## Project layout (important files)

- `src/main/java` - application source
  - `com.rewards.rewards_api.controller.RewardsController` - REST controller exposing `/rewards` endpoint
  - `com.rewards.rewards_api.service.RewardsService` - business logic and aggregation
  - `com.rewards.rewards_api.entity.Transaction` - JPA entity
  - `src/main/resources/data.sql` - sample data populated into H2 on startup
  - `src/main/resources/application.properties` - application configuration (H2 console enabled)

## Prerequisites

- JDK 17 or later installed and JAVA_HOME set
- (Optional) Maven installed. The project includes the Maven wrapper so Maven is not required.

## Build and run (Windows - cmd.exe)

From the project root (`D:\assesment\rewards-api`) you can build and run the application using the included wrapper:

```cmd
:: build
.\mvnw.cmd -DskipTests clean package

:: run from the generated jar
java -jar target\rewards-api-0.0.1-SNAPSHOT.jar
```

Or run directly using Spring Boot via the wrapper:

```cmd
.\mvnw.cmd spring-boot:run
```

The application will start on port 8080 by default.

## H2 Console

The H2 console is enabled and available at: http://localhost:8080/h2-console

JDBC URL (from `application.properties`):

```
jdbc:h2:mem:rewardsdb
```

Username: `sa`, password: (empty)

## API

GET /rewards

- Description: Returns a list of reward summaries aggregated per customer for the last 90 days (0-30, 31-60, 61-90 days) and the total.
- Response: JSON array of objects with the following fields:
  - `customerId` (string)
  - `within1month` (long) — points for 0-30 days
  - `within1to2months` (long) — points for 31-60 days
  - `within2to3months` (long) — points for 61-90 days
  - `totalPoints` (long)

Example (using curl / Windows cmd):

```cmd
curl -s -X GET "http://localhost:8080/rewards" -H "Accept: application/json"
```

Example response (trimmed):

```json
[{
  "customerId": "C1",
  "within1month": 260,
  "within1to2months": 180,
  "within2to3months": 210,
  "totalPoints": 650
},
{
  "customerId": "C2",
  "within1month": 300,
  "within1to2months": 210,
  "within2to3months": 220,
  "totalPoints": 730
}]
```

Note: The concrete numbers will depend on the current date because the service computes buckets relative to LocalDate.now(). The project includes sample data in `data.sql` to exercise the calculations.

## Running tests

Run unit and integration tests with the wrapper:

```cmd
.\mvnw.cmd test
```

## Packaging

A packaged fat jar is produced by `mvnw.cmd -DskipTests package` and appears in `target/rewards-api-0.0.1-SNAPSHOT.jar`.

## Error handling

- If there are no transactions in the repository, the API returns a 404 (handled by `TransactionNotFoundException` and `GlobalExceptionHandler`).

## Optimized data retrieval using date‑range queries

To improve performance and scalability, the service layer retrieves only the
required transaction data instead of loading all records.

- The repository method `findByTransactionDateBetween(startDate, endDate)` is
  used to fetch **transactions from the last three months only**.
- This ensures:
  - Database‑level filtering instead of in‑memory filtering
  - Reduced memory usage in the application
  - Faster response times as the dataset grows

## Scope for Future Improvement: Database‑Side Aggregation / Stored Procedures

This current design performs reward calculation and aggregation in the
service layer, a potential enhancement for very large datasets would be to move
part or all of this logic to the database.

Where we can use **stored procedure** for this.

- Filter transactions by date (e.g., last three months)
- Compute reward points based on transaction amounts
- Aggregate results per customer and per month directly in SQL

#### Why this approach

- Databases are highly optimized for aggregation and grouping operations
- Reduces the volume of data transferred from the database to the application
- Minimizes JVM memory usage and garbage collection pressure
- Improves performance when processing millions of transaction records

