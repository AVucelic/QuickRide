# QuickRide

QuickRide is a desktop application developed in Java and JavaFX, with MySQL as the database backend. The application aims to simplify ride-sharing by connecting drivers and passengers seamlessly. 

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Database Configuration](#database-configuration)

## Features
- User authentication (registration and login)
- Ride creation and management for drivers
- Ride search and booking for passengers
- User profile management
- Ride history tracking

## Prerequisites
Before you begin, ensure you have met the following requirements:
- Java Development Kit (JDK) 8 or later
- MySQL Server
- Maven
- An IDE with JavaFX support (e.g., IntelliJ IDEA, Eclipse)

## Installation
1. **Clone the repository:**
    ```sh
    git clone https://github.com/AVucelic/QuickRide.git
    cd QuickRide
    ```

2. **Set up the MySQL database:**
    - Create a new MySQL database.
    - Execute the SQL scripts provided in the `db` directory to create the necessary tables and schema.

3. **Configure the database connection:**
    - Update the `db.properties` file in the `src/main/resources` directory with your MySQL database credentials.

4. **Build the project using Maven:**
    ```sh
    mvn clean install
    ```

5. **Run the application:**
    ```sh
    mvn javafx:run
    ```

## Usage
- Launch the application using the command provided above.
- Register a new user account or log in with existing credentials.
- If you are a driver, you can create new rides and manage your existing ones.
- If you are a passenger, you can search for available rides and book them.
- Manage your profile and view your ride history through the user dashboard.

## Database Configuration
Ensure your `db.properties` file is correctly set up with the following properties:
```properties
db.url=jdbc:mysql://localhost:3306/quickride
db.username=your-username
db.password=your-password
Replace your-username and your-password with your actual MySQL credentials.

