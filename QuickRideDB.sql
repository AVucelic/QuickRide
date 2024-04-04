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

DROP TABLE IF EXISTS Car;
CREATE TABLE Car(
carID INT PRIMARY KEY AUTO_INCREMENT,
manufacturer VARCHAR(30),
model VARCHAR(30),
power INT,
year_of_production INT(4),
mileage INT,
status ENUM('available', 'unavailable')
);

DROP TABLE IF EXISTS Booking;
CREATE TABLE Booking(
    bookingID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT,
    carID INT,
    date DATE,
    time TIME,
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
    card_details INT(16),
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
    postal_Code VARCHAR(10)
);

DROP TABLE IF EXISTS Dropoff_location;
CREATE TABLE Dropoff_location(
    bookingID INT PRIMARY KEY, 
    FOREIGN KEY(bookingID) REFERENCES Booking(bookingID),
    address VARCHAR(30),
    city VARCHAR(30),
    postal_Code VARCHAR(10)
);

DROP TABLE IF EXISTS Insurance;
CREATE TABLE Insurance(
insuranceID INT primary key auto_increment,
carID int,
FOREIGN KEY(carID) references Car(carID),
insurance_model ENUM('basic', 'premium', 'standard')
);

DROP TABLE IF EXISTS Maintenance;
CREATE TABLE Maintenance(
maintenanceID INT PRIMARY KEY AUTO_INCREMENT,
carID int,
FOREIGN KEY(carID) REFERENCES Car(carID),
schedule_date DATE,
description VARCHAR(100)
);

DROP TABLE IF EXISTS Damage_report;
CREATE TABLE Damage_report(
reportID int primary key auto_increment,
carID INT,
FOREIGN KEY(carID) REFERENCES Car(carID),
report_message VARCHAR(100),
timestamp datetime
);

DROP TABLE IF EXISTS Feedback;
CREATE TABLE Feedback(
feedbackID INT PRIMARY KEY auto_increment,
carID INT,
userID INT,
FOREIGN KEY(carID) REFERENCES Car(carID),
FOREIGN KEY(userID) REFERENCES User(userID),
feedback_message VARCHAR(100),
rating INT(1),
timestamp datetime
);
