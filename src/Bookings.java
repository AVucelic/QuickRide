// import java.sql.Date;
// import java.sql.SQLException;
// import java.sql.Timestamp;
// import java.util.ArrayList;

// public class Bookings extends Model {
//     ArrayList<Object> bookings;
//     private int id;

//     public Bookings(int userid) {
//         bookings = new ArrayList<>();
//         id = userid;
//     }

//     public ArrayList<Object> getData() throws DLExeption {
//         ArrayList<String> params = new ArrayList<>();
//         params.add(id + "");
//         this.bookings = new ArrayList<>();
//         String statement = "Select * from Booking where userID = ?";
//         ArrayList<ArrayList<String>> data = db.executeQuery(statement, params);

//         for (int i = 1; i < data.size(); i++) {
//             Booking booking = new Booking(Integer.parseInt(data.get(i).get(0)), Integer.parseInt(data.get(i).get(1)),
//                     Integer.parseInt(data.get(i).get(2)), Timestamp.valueOf(data.get(i).get(3)));
//             this.bookings.add(booking);
//         }
//         return this.bookings;
//     }

//     /*
//      * Modifies the booking with the specified booking ID by updating the car ID and
//      * booking time
//      */
//     public boolean modifyBooking(int bookingID, int newCarID, Timestamp newBookingTime) throws DLExeption {
//         String updateQuery = "UPDATE Booking SET carID = ?, timestamp = ? WHERE bookingID = ?";
//         ArrayList<String> params = new ArrayList<>();
//         params.add(Integer.toString(newCarID));
//         params.add(newBookingTime.toString());
//         params.add(Integer.toString(bookingID));

//         return db.executeUpdate(updateQuery, params);
//     }

//     /*
//      * Cancels the booking with the specified booking ID by updating its status to
//      * 'cancelled'
//      */
//     public boolean cancelBooking(int bookingID) throws DLExeption {
//         String updateQuery = "UPDATE Booking SET status = 'cancelled' WHERE bookingID = ?";
//         ArrayList<String> params = new ArrayList<>();
//         params.add(Integer.toString(bookingID));
//         return db.executeUpdate(updateQuery, params);
//     }

//     /** just for testing */
//     public ArrayList<ArrayList<String>> getAllBookings() throws DLExeption {
//         String query = "SELECT * FROM Booking";
//         return db.executeQuery(query, new ArrayList<>());
//     }

// }
