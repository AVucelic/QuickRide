package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLException;

public class Reports extends Model {

    ArrayList<Object> reports;

    @Override
    public ArrayList<Object> getData() throws DLException {
        String statement = "Select * from Damage_report;";
        ArrayList<ArrayList<String>> arr = db.executeQuery(statement, new ArrayList<String>());
        for (int i = 0; i < arr.size(); i++) {
            Report damage = new Report(Integer.parseInt(arr.get(i).get(0)), Integer.parseInt(arr.get(i).get(1)),
                    Timestamp.valueOf(arr.get(i).get(2)), arr.get(i).get(3));
            reports.add(damage);
        }
        return reports;
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
        Report report = (Report) newData;
        String statement = "INSERT INTO Damage_report (carID , report_message, timestamp) VALUES (?, ?, ?)";
        ArrayList<String> param = new ArrayList<>();
        param.add(report.getCarID() + "");
        param.add(report.getDescription());
        param.add(report.getDate() + "");

        db.executeUpdate(statement, param);
        return true;

    }

}
