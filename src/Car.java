import java.sql.Date;

public class Car {
    private int carID;
    private String manufacturer;
    private String model;
    private int power;
    private java.sql.Date year_of_Production;
    private int mileage;
    private boolean status;

    public Car(int carID, String manufacturer, String model, int power, Date year_of_Production, int mileage,
            boolean status) {
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

    public java.sql.Date getYear_of_Production() {
        return year_of_Production;
    }

    public void setYear_of_Production(java.sql.Date year_of_Production) {
        this.year_of_Production = year_of_Production;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
