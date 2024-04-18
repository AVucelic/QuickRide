package Models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLException;

public class Maintenances extends Model {
    ArrayList<Object> maintenances;

    @Override
    public ArrayList<Object> getData() throws DLException {
        String statement = "Select * from Maintenance;";
        ArrayList<ArrayList<String>> arr = db.executeQuery(statement, new ArrayList<String>());
        for (int i = 0; i < arr.size(); i++) {
            Maintenance maintenance = new Maintenance(Integer.parseInt(arr.get(i).get(0)),
                    Integer.parseInt(arr.get(i).get(1)), Date.valueOf(arr.get(i).get(2)), arr.get(i).get(3));
            maintenances.add(maintenance);
        }
        return maintenances;
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
        Maintenance maintenance = (Maintenance) newData;
        String statement = "INSERT INTO Maintenance (carID ,  schedule_date , description) VALUES (?, ?, ?)";
        ArrayList<String> arr = new ArrayList<>();
        arr.add(maintenance.getCarID() + "");
        arr.add(maintenance.getEndDate() + "");
        arr.add(maintenance.getDescription());
        return db.executeUpdate(statement, arr);
    }

}
