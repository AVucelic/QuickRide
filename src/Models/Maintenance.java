package Models;

import java.sql.Date;

public class Maintenance {
    private int maintenanceID;
    private int carID;
    private Date endDate;
    private String description;

    public Maintenance(int maintenanceID, int carID, Date endDate, String description) {
        this.maintenanceID = maintenanceID;
        this.carID = carID;
        this.endDate = endDate;
        this.description = description;
    }

    public int getMaintenanceID() {
        return maintenanceID;
    }

    public void setMaintenanceID(int maintenanceID) {
        this.maintenanceID = maintenanceID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
