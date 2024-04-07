import java.sql.Timestamp;

public class Booking {
    private int bookingID;
    private int carID;
    private int userID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    private Timestamp timeBooked;

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public Timestamp getTimeBooked() {
        return timeBooked;
    }

    public void setTimeBooked(Timestamp timeBooked) {
        this.timeBooked = timeBooked;
    }

    public Booking(int bookingID, int userID, int carID, Timestamp timeBooked) {
        this.bookingID = bookingID;
        this.timeBooked = timeBooked;
        this.carID = carID;
        this.userID = userID;
    }
}
