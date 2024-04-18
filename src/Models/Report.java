package Models;

import java.sql.Timestamp;

public class Report {
    private int reportID;
    private int carID;
    private Timestamp date;
    private String description;

    public Report(int reportID, int carID, Timestamp date, String description) {
        this.reportID = reportID;
        this.carID = carID;
        this.date = date;
        this.description = description;
    }

    public Report(int carID, Timestamp date, String description) {
        this.carID = carID;
        this.date = date;
        this.description = description;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
