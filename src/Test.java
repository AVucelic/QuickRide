// import java.sql.Timestamp;
// import java.util.ArrayList;

// // this class is just for testing since we do not have gui.
// public class Test {

//     public static void main(String[] args) {
//         try {
//             // Initialize your Booking system with a user ID (assuming user ID 1 for
//             // testing)
//             int userID = 1;
//             Bookings bookings = new Bookings(userID);

//             // Testing viewing bookings
//             System.out.println("Viewing Bookings:");
//             for (Object obj : bookings.getData()) {
//                 Booking booking = (Booking) obj;
//                 System.out.println("Booking ID: " + booking.getBookingID() + ", Car ID: " + booking.getCarID()
//                         + ", Time Booked: " + booking.getTimeBooked());
//             }

//             // Testing modifying a booking (Adjust bookingID, newCarID, and newTimeBooked as
//             // needed)
//             System.out.println("\nModifying Booking:");
//             int bookingIDToModify = 1; // Adjust this as needed
//             int newCarID = 2; // Adjust this as needed
//             Timestamp newTimeBooked = Timestamp.valueOf("2024-04-11 10:00:00"); // Adjust this as needed
//             boolean modifyResult = bookings.modifyBooking(bookingIDToModify, newCarID, newTimeBooked);
//             System.out.println(modifyResult ? "Booking modified successfully." : "Failed to modify booking.");
//             System.out.println("Viewing Bookings:");
//             for (Object obj : bookings.getData()) {
//                 Booking booking = (Booking) obj;
//                 System.out.println("Booking ID: " + booking.getBookingID() + ", Car ID: " + booking.getCarID()
//                         + ", Time Booked: " + booking.getTimeBooked());
//             }

//             // Testing cancelling a booking (Adjust bookingID as needed)
//             System.out.println("\nCanceling Booking:");
//             int bookingIDToCancel = 1; // Adjust this as needed
//             boolean cancelResult = bookings.cancelBooking(bookingIDToCancel);
//             System.out.println(cancelResult ? "Booking canceled successfully." : "Failed to cancel booking.");

//             Bookings bookings2 = new Bookings(1); // Use any user ID, it won't matter for this query
//             ArrayList<ArrayList<String>> allBookings = bookings2.getAllBookings();

//             // Print column names
//             for (String columnName : allBookings.get(0)) {
//                 System.out.print(columnName + "\t");
//             }
//             System.out.println();

//             // Print each booking
//             for (int i = 1; i < allBookings.size(); i++) {
//                 for (String value : allBookings.get(i)) {
//                     System.out.print(value + "\t");
//                 }
//                 System.out.println();
//             }
//         } catch (DLExeption e) {
//             e.printStackTrace();
//         }
//     }
// }
