# Popcorn Palace - Movie Ticket Booking System
# Author: Adiel Rosenfeld

## Project Overview
Popcorn Palace is a backend movie ticket booking system developed using **Spring Boot**. It supports managing movies, showtimes, and ticket bookings. It allows users to book tickets for showtimes in theaters, check for seat availability, and handle overlapping showtimes.

## Setup

### Prerequisites
- **Java 17** or later
- **Maven 3.x** or later
- **Docker** (optional, for PostgreSQL database)

### Installation Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/adielro/popcorn-palace
   cd popcorn-palace
    ```
2. Build the project:
    ```bash
    mvn clean install
    ```
3. Run the application:
    ```bash
    mvn spring-boot:run
    ```
4. The application will start at `http://localhost:8080`.

### Testing
1. Run the tests:
    ```bash
    mvn test
    ```
2. Manual testing:
    - Use the Swagger UI at `http://localhost:8080/swagger-ui.html` to test the API endpoints.
    - By using swagger UI, you can see all the endpoints and test them.