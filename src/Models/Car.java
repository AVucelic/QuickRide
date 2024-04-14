package Models;

import java.sql.Date;

public class Car {
    private int carID;
    private String manufacturer;
    private String model;
    private int power;
    private int year_of_Production;
    private int mileage;
    private String status;

    public Car(int carID, String manufacturer, String model, int power, int year_of_Production, int mileage,
            String status) {
        this.carID = carID;
        this.manufacturer = manufacturer;
        this.model = model;
        this.power = power;
        this.year_of_Production = year_of_Production;
        this.mileage = mileage;
        this.status = status;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getYear_of_Production() {
        return year_of_Production;
    }

    public void setYear_of_Production(int year_of_Production) {
        this.year_of_Production = year_of_Production;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String isStatus() {
        if (this.status.equals("1")) {
            this.status = "True";
        } else {
            this.status = "False";
        }
        return this.status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Brand: " + manufacturer + ", Model: " + model +
                ", Power: " + power + "hp" +
                ", Year of Production: " + year_of_Production +
                ", Mileage: " + mileage + " km" +
                ", Availability: " + isStatus();
    }

}