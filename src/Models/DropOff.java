package Models;

import java.sql.Timestamp;

public class DropOff {
    private String address;
    private String city;
    private String postalCode;
    private Timestamp time;
    private int bookingId;

    public DropOff(String address, String city, String postalCode, Timestamp time, int bookingId) {
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.time = time;
        this.bookingId = bookingId;
    }

    // Getters and setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
}
