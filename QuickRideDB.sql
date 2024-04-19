DROP DATABASE IF EXISTS QuickRide; 
CREATE DATABASE QuickRide; 
USE QuickRide;
SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS User;
CREATE TABLE User(
    userID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NOT NULL,
    userRole ENUM('Admin', 'Member') NOT NULL DEFAULT ('Member')
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

-- GRANT ALL PRIVILEGES ON QuickRide.Admin TO 'root'@'localhost'; 
-- REVOKE ALL PRIVILEGES ON QuickRide.Member FROM 'root'@'localhost';


DROP TABLE IF EXISTS Car;
CREATE TABLE Car(
    carID INT PRIMARY KEY AUTO_INCREMENT,
    manufacturer VARCHAR(30),
    model VARCHAR(30),
    power INT,
    year_of_production INT(4),
    mileage INT,
    isAvailable BOOLEAN,
    price DOUBLE(6,2)
);

DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Booking;
CREATE TABLE Booking(
    bookingID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT,
    carID INT,
    timestamp DATETIME,
    status ENUM('active', 'cancelled') DEFAULT 'active', 
    FOREIGN KEY(userID) REFERENCES User(userID),
    FOREIGN KEY(carID) REFERENCES Car(carID) ON DELETE CASCADE  -- Add ON DELETE CASCADE here
);


DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS Payment;
CREATE TABLE Payment(
    paymentID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT,  
    carID INT,
    amount DOUBLE(8,2),
    method ENUM('Card','Cash'),
    card_details BIGINT,
    card_type VARCHAR(20),
    timestamp DATETIME,
    FOREIGN KEY(carID) REFERENCES Car(carID) ON DELETE CASCADE  -- Add ON DELETE CASCADE here
);


DROP TABLE IF EXISTS Pickup_location;
DROP TABLE IF EXISTS Pickup_location;
CREATE TABLE Pickup_location(
    bookingID INT AUTO_INCREMENT PRIMARY KEY,  
    address VARCHAR(30),
    city VARCHAR(30),
    postal_Code VARCHAR(10),
    time DATETIME
);


DROP TABLE IF EXISTS Dropoff_location;
CREATE TABLE Dropoff_location(
    bookingID INT AUTO_INCREMENT PRIMARY KEY, 
    address VARCHAR(30),
    city VARCHAR(30),
    postal_Code VARCHAR(10),
    time DATETIME
);

DROP TABLE IF EXISTS Insurance;
CREATE TABLE Insurance(
    insuranceID INT PRIMARY KEY AUTO_INCREMENT,
    carID INT,
    FOREIGN KEY(carID) REFERENCES Car(carID) ON DELETE CASCADE,
    insurance_model ENUM('Basic', 'Premium', 'Standard')
);

DROP TABLE IF EXISTS Maintenance;
CREATE TABLE Maintenance(
    maintenanceID INT PRIMARY KEY AUTO_INCREMENT,
    carID INT,
    FOREIGN KEY(carID) REFERENCES Car(carID) ON DELETE CASCADE,
    schedule_date DATE,
    description VARCHAR(100)
);

DROP TABLE IF EXISTS Damage_report;
CREATE TABLE Damage_report(
    reportID INT PRIMARY KEY AUTO_INCREMENT,
    carID INT,
    FOREIGN KEY(carID) REFERENCES Car(carID) ON DELETE CASCADE,
    report_message VARCHAR(100),
    timestamp DATETIME
);

DROP TABLE IF EXISTS Feedback;
CREATE TABLE Feedback(
    feedbackID INT PRIMARY KEY AUTO_INCREMENT,
    carID INT,
    userID INT,
    FOREIGN KEY(carID) REFERENCES Car(carID) ON DELETE CASCADE,
    FOREIGN KEY(userID) REFERENCES User(userID) ON DELETE CASCADE,
    feedback_message VARCHAR(100),
    rating INT(1),
    timestamp DATETIME
);
INSERT INTO User (username, email, password, firstname, lastname, userRole) 
VALUES 
    ('john_doe', 'john@example.com', 'password123', 'John', 'Doe', 'Member'),
    ('jane_smith', 'jane@example.com', 'secret456', 'Jane', 'Smith', 'Admin');
INSERT INTO Member (userID, permissions)
VALUES
    (1, 'can_view_bookings'),
    (2, 'can_view_profile');
INSERT INTO Admin (userID, permissions)
VALUES
    (1, 'all_permissions');
INSERT INTO Car (manufacturer, model, power, year_of_production, mileage, isAvailable, price)
VALUES
   ('Toyota', 'Corolla', 120, 2018, 35000, TRUE, 50.00),
   ('Honda', 'Civic', 150, 2019, 25000, FALSE, 60.00),
   ('Ford', 'Focus', 110, 2017, 40000, TRUE, 45.00),
   ('Chevrolet', 'Cruze', 140, 2018, 30000, TRUE, 55.00),
   ('Volkswagen', 'Golf', 130, 2019, 20000, FALSE, 65.00),
   ('BMW', '3 Series', 180, 2020, 15000, TRUE, 120.00),
   ('Mercedes-Benz', 'C-Class', 170, 2019, 25000, FALSE, 110.00),
   ('Audi', 'A4', 160, 2018, 30000, TRUE, 100.00),
   ('Hyundai', 'Elantra', 120, 2017, 35000, TRUE, 50.00),
   ('Kia', 'Optima', 140, 2018, 30000, FALSE, 55.00),
   ('Nissan', 'Altima', 150, 2019, 20000, TRUE, 65.00),
   ('Subaru', 'Impreza', 140, 2017, 40000, TRUE, 60.00),
   ('Mazda', 'Mazda3', 130, 2018, 25000, FALSE, 70.00),
   ('Volvo', 'S60', 150, 2019, 20000, TRUE, 90.00),
   ('Lexus', 'IS', 170, 2020, 15000, TRUE, 110.00),
   ('Tesla', 'Model S', 400, 2021, 10000, TRUE, 250.00),
   ('Jeep', 'Wrangler', 260, 2020, 20000, TRUE, 150.00),
   ('Porsche', '911', 450, 2021, 8000, TRUE, 400.00),
   ('Land Rover', 'Range Rover', 300, 2020, 15000, FALSE, 350.00),
   ('Ferrari', '488 GTB', 670, 2021, 5000, TRUE, 800.00),
   ('Lamborghini', 'Huracan', 640, 2021, 6000, TRUE, 900.00),
   ('Aston Martin', 'Vantage', 503, 2020, 12000, TRUE, 700.00),
   ('Bentley', 'Continental GT', 626, 2021, 10000, FALSE, 1000.00),
   ('Rolls-Royce', 'Ghost', 563, 2021, 9000, TRUE, 1200.00),
   ('Maserati', 'GranTurismo', 454, 2020, 15000, TRUE, 600.00);

INSERT INTO Payment (userID, carID, amount, method, card_details, card_type, timestamp)
VALUES
    (1, 1, 50.00, 'Card', 1234567812345678, 'Visa', NOW()),
    (2, 2, 40.00, 'Cash', NULL, NULL, NOW());
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
