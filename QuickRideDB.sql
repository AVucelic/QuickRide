DROP DATABASE IF EXISTS QuickRide; 
CREATE DATABASE QuickRide; 
USE QuickRide;

DROP TABLE IF EXISTS User;
CREATE TABLE User(
    userID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NOT NULL
);

DROP TABLE IF EXISTS Member;
CREATE TABLE Member(
    userID INT,
    permissions VARCHAR(100),
    PRIMARY KEY(userID),
    FOREIGN KEY(userID) REFERENCES User(userID)
);

DROP TABLE IF EXISTS Admin;
CREATE TABLE Admin(
    userID INT,
    permissions VARCHAR(100),
    PRIMARY KEY(userID),
    FOREIGN KEY(userID) REFERENCES User(userID)
);

GRANT ALL PRIVILEGES ON QuickRide.Admin TO 'root'@'localhost'; 
REVOKE ALL PRIVILEGES ON QuickRide.Member FROM 'root'@'localhost';


DROP TABLE IF EXISTS Car;
CREATE TABLE Car(
    carID INT PRIMARY KEY AUTO_INCREMENT,
    manufacturer VARCHAR(30),
    model VARCHAR(30),
    power INT,
    year_of_production INT(4),
    mileage INT,
    isAvailable BOOLEAN
);

DROP TABLE IF EXISTS Booking;
CREATE TABLE Booking(
    bookingID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT,
    carID INT,
    timestamp DATETIME,
    FOREIGN KEY(userID) REFERENCES User(userID),
    FOREIGN KEY(carID) REFERENCES Car(carID)
);

DROP TABLE IF EXISTS Payment;
CREATE TABLE Payment(
    paymentID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT,  
    bookingID INT,
    amount DOUBLE(8,2),
    method ENUM('Card','Cash'),
    card_details BIGINT,
    card_type VARCHAR(10),
    timestamp DATETIME,
    FOREIGN KEY(userID) REFERENCES User(userID), 
    FOREIGN KEY(bookingID) REFERENCES Booking(bookingID)
);

DROP TABLE IF EXISTS Pickup_location;
CREATE TABLE Pickup_location(
    bookingID INT PRIMARY KEY,  
    FOREIGN KEY(bookingID) REFERENCES Booking(bookingID),
    address VARCHAR(30),
    city VARCHAR(30),
    postal_Code VARCHAR(10),
    time DATETIME
);

DROP TABLE IF EXISTS Dropoff_location;
CREATE TABLE Dropoff_location(
    bookingID INT PRIMARY KEY, 
    FOREIGN KEY(bookingID) REFERENCES Booking(bookingID),
    address VARCHAR(30),
    city VARCHAR(30),
    postal_Code VARCHAR(10),
    time DATETIME
);

DROP TABLE IF EXISTS Insurance;
CREATE TABLE Insurance(
    insuranceID INT PRIMARY KEY AUTO_INCREMENT,
    carID INT,
    FOREIGN KEY(carID) REFERENCES Car(carID),
    insurance_model ENUM('basic', 'premium', 'standard')
);

DROP TABLE IF EXISTS Maintenance;
CREATE TABLE Maintenance(
    maintenanceID INT PRIMARY KEY AUTO_INCREMENT,
    carID INT,
    FOREIGN KEY(carID) REFERENCES Car(carID),
    schedule_date DATE,
    description VARCHAR(100)
);

DROP TABLE IF EXISTS Damage_report;
CREATE TABLE Damage_report(
    reportID INT PRIMARY KEY AUTO_INCREMENT,
    carID INT,
    FOREIGN KEY(carID) REFERENCES Car(carID),
    report_message VARCHAR(100),
    timestamp DATETIME
);

DROP TABLE IF EXISTS Feedback;
CREATE TABLE Feedback(
    feedbackID INT PRIMARY KEY AUTO_INCREMENT,
    carID INT,
    userID INT,
    FOREIGN KEY(carID) REFERENCES Car(carID),
    FOREIGN KEY(userID) REFERENCES User(userID),
    feedback_message VARCHAR(100),
    rating INT(1),
    timestamp DATETIME
);
INSERT INTO User (username, email, password, firstname, lastname) 
VALUES 
    ('john_doe', 'john@example.com', 'password123', 'John', 'Doe'),
    ('jane_smith', 'jane@example.com', 'secret456', 'Jane', 'Smith');
INSERT INTO Member (userID, permissions)
VALUES
    (1, 'can_view_bookings'),
    (2, 'can_view_profile');
INSERT INTO Admin (userID, permissions)
VALUES
    (1, 'all_permissions');
INSERT INTO Car (manufacturer, model, power, year_of_production, mileage, isAvailable)
VALUES
    ('Toyota', 'Corolla', 120, 2018, 35000, TRUE),
    ('Honda', 'Civic', 150, 2019, 25000, FALSE);
INSERT INTO Booking (userID, carID, timestamp)
VALUES
    (1, 1, NOW()),
    (2, 2, NOW());
INSERT INTO Payment (userID, bookingID, amount, method, card_details, card_type, timestamp)
VALUES
    (1, 1, 50.00, 'Card', 1234567812345678, 'Visa', NOW()),
    (2, 2, 40.00, 'Cash', NULL, NULL, NOW());
INSERT INTO Pickup_location (bookingID, address, city, postal_Code, time)
VALUES
    (1, '123 Main St', 'Cityville', '12345', NOW()),
    (2, '456 Elm St', 'Townsville', '67890', NOW());
INSERT INTO Feedback (carID, userID, feedback_message, rating, timestamp)
VALUES
    (1, 1, 'Great car, smooth ride!', 5, NOW()),
    (2, 2, 'Nice experience overall.', 4, NOW());
INSERT INTO Insurance (carID, insurance_model)
VALUES
    (1, 'basic'),
    (2, 'premium');
INSERT INTO Maintenance (carID, schedule_date, description)
VALUES
    (1, '2024-04-10', 'Routine maintenance check'),
    (2, '2024-04-15', 'Oil change and inspection');
INSERT INTO Damage_report (carID, report_message, timestamp)
VALUES
    (1, 'Minor scratch on the rear bumper', NOW()),
    (2, 'Broken side mirror', NOW());
