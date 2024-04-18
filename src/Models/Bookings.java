package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLException;

public class Bookings extends Model {
    ArrayList<Object> bookings;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bookings(int userid) {
        bookings = new ArrayList<>();
        id = userid;
    }

    public ArrayList<Object> getData() throws DLException {
        ArrayList<String> params = new ArrayList<>();
        params.add(id + "");
        params.add("active");
        this.bookings = new ArrayList<>();
        String statement = "Select * from Booking where userID = ? and status = ?";
        ArrayList<ArrayList<String>> data = db.executeQuery(statement, params);

        for (int i = 1; i < data.size(); i++) {
            Booking booking = new Booking(Integer.parseInt(data.get(i).get(0)), Integer.parseInt(data.get(i).get(1)),
                    Integer.parseInt(data.get(i).get(2)), Timestamp.valueOf(data.get(i).get(3)));
            this.bookings.add(booking);
        }
        return this.bookings;
    }

    /*
     * Modifies the booking with the specified booking ID by updating the car ID and
     * booking time
     */
    public boolean modify(int bookingID, int newCarID, Timestamp newBookingTime) throws DLException {
        String updateQuery = "UPDATE Booking SET carID = ?, timestamp = ? WHERE bookingID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(Integer.toString(newCarID));
        params.add(newBookingTime.toString());
        params.add(Integer.toString(bookingID));

        return db.executeUpdate(updateQuery, params);
    }

    /*
     * Cancels the booking with the specified booking ID by updating its status to
     * 'cancelled'
     */
    public boolean remove(int bookingID) throws DLException {
        String updateQuery = "UPDATE Booking SET status = 'cancelled' WHERE bookingID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(Integer.toString(bookingID));
        return db.executeUpdate(updateQuery, params);
    }

    /** just for testing */
    public ArrayList<Object> getAllBookings() throws DLException {
        String query = "SELECT * FROM Booking";
        ArrayList<ArrayList<String>> data = db.executeQuery(query, new ArrayList<>());
        ArrayList<Object> bookings = new ArrayList<>();

        // Skip the first row (header row) containing column names
        for (int i = 1; i < data.size(); i++) {
            ArrayList<String> row = data.get(i);
            int bookingID = Integer.parseInt(row.get(0));
            int userID = Integer.parseInt(row.get(1));
            int carID = Integer.parseInt(row.get(2));
            Timestamp timestamp = Timestamp.valueOf(row.get(3));

            Booking booking = new Booking(bookingID, userID, carID, timestamp);
            bookings.add(booking);
        }

        return bookings;
    }

    @Override
    public boolean setData(Object newData) throws DLException {
        Booking booking = (Booking) newData;
        String insertQuery = "INSERT INTO Booking (userID, carID, timestamp) VALUES (?, ?, ?)";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(booking.getUserID())); // user ID parameter
        params.add(String.valueOf(booking.getCarID())); // car ID parameter
        params.add(booking.getTimeBooked().toString()); // booking timestamp parameter
        if (db.executeUpdate(insertQuery, params)) {
            System.out.println("Car booked successfully!");
            String updateQuery = "UPDATE Car SET isAvailable = 0 WHERE carID = ?";
            ArrayList<String> params2 = new ArrayList<>();
            params2.add(booking.getCarID() + "");
            db.executeUpdate(updateQuery, params2);
            return true;
        } else {
            System.out.println("Failed to book the car.");
            return false;
        }
    }

    public void bookACar(int carID, int userID) throws DLException {
        // Assuming db is accessible here and represents the database connectivity
        Timestamp bookingTime = new Timestamp(System.currentTimeMillis());
        String insertQuery = "INSERT INTO Booking (userID, carID, timestamp) VALUES (?, ?, ?)";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(userID)); // user ID parameter
        params.add(String.valueOf(carID)); // car ID parameter
        params.add(bookingTime.toString()); // booking timestamp parameter

        if (db.executeUpdate(insertQuery, params)) {
            System.out.println("Car booked successfully!");
            String updateQuery = "UPDATE Car SET isAvailable = 0 WHERE carID = ?";
            ArrayList<String> params2 = new ArrayList<>();
            params2.add(carID + "");
            db.executeUpdate(updateQuery, params2);
        } else {
            System.out.println("Failed to book the car.");
        }
    }

    public Booking getBookingByID(int bookingID) throws DLException {
        for (Object obj : bookings) {
            if (obj instanceof Booking) {
                Booking booking = (Booking) obj;
                if (booking.getBookingID() == bookingID) {
                    return booking;
                }
            }
        }
        return null; // Return null if the booking is not found
    }

}
