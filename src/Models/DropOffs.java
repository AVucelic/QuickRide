package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLException;

public class DropOffs extends Model {

    ArrayList<Object> dropOffs;

    public DropOffs() {
        dropOffs = new ArrayList<>();
    }

    public ArrayList<Object> getData() throws DLException {
        this.dropOffs = new ArrayList<>();
        String statement = "SELECT * FROM Dropoff_location";
        ArrayList<ArrayList<String>> data = db.executeQuery(statement, new ArrayList<>());
        // Start the loop from index 1 to skip the first row (column names)
        for (int i = 1; i < data.size(); i++) {
            int bookingID = Integer.parseInt(data.get(i).get(0));
            String address = data.get(i).get(1);
            String city = data.get(i).get(2);
            String postalCode = data.get(i).get(3);
            Timestamp time = Timestamp.valueOf(data.get(i).get(4));

            // Create DropOff object and add to dropOffs list
            DropOff dropOff = new DropOff(address, city, postalCode, time, bookingID);
            this.dropOffs.add(dropOff);
        }
        return this.dropOffs;
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
        DropOff dropOff = (DropOff) newData;
        String statement = "INSERT INTO Dropoff_location (bookingID, address, city, postal_Code, time) VALUES (?, ?, ?, ?, ?)";
        ArrayList<String> params = new ArrayList<>();
        params.add(Integer.toString(dropOff.getBookingId()));
        params.add(dropOff.getAddress());
        params.add(dropOff.getCity());
        params.add(dropOff.getPostalCode());
        params.add(dropOff.getTime().toString()); // Convert Timestamp to String
        System.out.println("success - drop off");
        return db.executeUpdate(statement, params);
    }

    public DropOff getDropOffByBookingID(int bookingID) throws DLException {
        String statement = "SELECT * FROM Dropoff_location WHERE bookingID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(Integer.toString(bookingID));

        ArrayList<ArrayList<String>> data = db.executeQuery(statement, params);
        if (data.size() > 1) { // Check if data is found
            String address = data.get(1).get(1);
            String city = data.get(1).get(2);
            String postalCode = data.get(1).get(3);
            Timestamp time = Timestamp.valueOf(data.get(1).get(4));
            System.out.println(address);
            System.out.println(city);
            System.out.println(postalCode);
            System.out.println(time);
            return new DropOff(address, city, postalCode, time, bookingID);
        } else {
            return null; // No drop-off location found for the given booking ID
        }
    }

}
