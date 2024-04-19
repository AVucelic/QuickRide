package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLException;

public class PickUps extends Model {

    ArrayList<Object> pickUps;

    public PickUps() {
        pickUps = new ArrayList<>();
    }

    public ArrayList<Object> getData() throws DLException {
        this.pickUps = new ArrayList<>();
        String statement = "SELECT * FROM Pickup_location";
        ArrayList<ArrayList<String>> data = db.executeQuery(statement, new ArrayList<>());
        // Start the loop from index 1 to skip the first row (column names)
        for (int i = 1; i < data.size(); i++) {
            int bookingID = Integer.parseInt(data.get(i).get(0));
            String address = data.get(i).get(1);
            String city = data.get(i).get(2);
            String postalCode = data.get(i).get(3);
            Timestamp time = Timestamp.valueOf(data.get(i).get(4));

            // Create PickUp object and add to pickUps list
            PickUp pickUp = new PickUp(address, city, postalCode, time, bookingID);
            this.pickUps.add(pickUp);
        }
        return this.pickUps;
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

    @Override
    public boolean setData(Object newData) throws DLException {
        if (!(newData instanceof PickUp)) {
            throw new IllegalArgumentException("setData expects a PickUp object.");
        }

        PickUp newPickUp = (PickUp) newData;
        String statement = "INSERT INTO Pickup_location (bookingID, address, city, postal_Code, time) VALUES (?, ?, ?, ?, ?)";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(newPickUp.getBookingId()));
        params.add(newPickUp.getAddress());
        params.add(newPickUp.getCity());
        params.add(newPickUp.getPostalCode());
        params.add(newPickUp.getTime().toString()); // Convert Timestamp to String
        System.out.println("success - pick up");

        return db.executeUpdate(statement, params);
    }

    public PickUp getPickUpByBookingID(int bookingID) throws DLException {
        String statement = "SELECT * FROM Pickup_location WHERE bookingID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(Integer.toString(bookingID));

        ArrayList<ArrayList<String>> data = db.executeQuery(statement, params);
        if (data.size() > 1) { // Check if data is found
            String address = data.get(1).get(1);
            String city = data.get(1).get(2);
            String postalCode = data.get(1).get(3);
            Timestamp time = Timestamp.valueOf(data.get(1).get(4));

            return new PickUp(address, city, postalCode, time, bookingID);
        } else {
            return null; // No pick-up location found for the given booking ID
        }
    }
}
