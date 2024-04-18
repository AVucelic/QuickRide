package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLException;

public class Payments extends Model {
    ArrayList<Object> payments;

    public Payments() {
        payments = new ArrayList<>();
    }

    public ArrayList<Object> getData() throws DLException {
        this.payments = new ArrayList<>();
        String statement = "SELECT * FROM Payment";
        ArrayList<ArrayList<String>> data = db.executeQuery(statement, new ArrayList<>());
        // Start the loop from index 1 to skip the first row (column names)
        for (int i = 1; i < data.size(); i++) {
            int paymentId = Integer.parseInt(data.get(i).get(0)); // paymentId
            int userID = Integer.parseInt(data.get(i).get(1)); // userID
            int carID = Integer.parseInt(data.get(i).get(2)); // carID
            double amount = Double.parseDouble(data.get(i).get(3)); // amount
            String method = data.get(i).get(4); // method
            String cardDetailsStr = data.get(i).get(5); // cardDetails
            String cardType = data.get(i).get(6); // cardType
            Timestamp timestamp = Timestamp.valueOf(data.get(i).get(7)); // timestamp

            // Check if cardDetails is null
            String cardDetails = null;
            if (cardDetailsStr != null && !cardDetailsStr.equalsIgnoreCase("null")) {
                cardDetails = cardDetailsStr;
            }

            // Create Payment object and add to payments list
            Payment payment = new Payment(paymentId, userID, carID, amount, method, cardDetails, cardType, timestamp);
            this.payments.add(payment);
        }
        return this.payments;
    }

    @Override
    public boolean setData(Object newData) throws DLException {
        Payment payment = (Payment) newData;
        String statement = "INSERT INTO Payment (carID, userID, amount, method, card_details, card_type, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)";
        ArrayList<String> arr = new ArrayList<>();
        arr.add(payment.getcarID() + "");
        arr.add(payment.getUserID() + "");
        arr.add(payment.getAmount() + "");
        arr.add(payment.getMethod());
        arr.add(payment.getCardDetails() + "");
        arr.add(payment.getCardType());
        arr.add(payment.getTimestamp().toString()); // Convert Timestamp to String
        return db.executeUpdate(statement, arr);

    }

    @Override
    public boolean modify(int bookingID, int newCarID, Timestamp newBookingTime) throws DLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modify'");
    }

    @Override
    public boolean remove(int ID) throws DLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    public Payment getPaymentForBooking(int userID, int carID) throws DLException {
        String query = "SELECT * FROM Payment WHERE userID = ? AND carID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(Integer.toString(userID));
        params.add(Integer.toString(carID));

        try {
            ArrayList<ArrayList<String>> result = db.executeQuery(query, params);
            if (!result.isEmpty()) {
                ArrayList<String> row = result.get(1); // Assuming only one payment per booking
                double amount = Double.parseDouble(row.get(3));
                String method = row.get(4);
                String cardDetails = row.get(5) != null ? row.get(5) : String.valueOf(0); // Handle null case
                String cardType = row.get(6);
                Timestamp timestamp = Timestamp.valueOf(row.get(7));

                return new Payment(userID, carID, amount, method, cardDetails, cardType, timestamp);
            } else {
                return null; // No payment found for the given userID and carID
            }
        } catch (DLException e) {
            throw e;
        }
    }

}