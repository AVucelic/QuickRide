package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLException;

public class Insurances extends Model {

    @Override
    public ArrayList<Object> getData() throws DLException {
        ArrayList<Object> insuranceList = new ArrayList<>();
        String query = "SELECT * FROM Insurance";

        try {
            ArrayList<ArrayList<String>> result = db.executeQuery(query, new ArrayList<String>());
            for (ArrayList<String> row : result) {
                int carID = Integer.parseInt(row.get(1));
                String insuranceModel = row.get(2);
                Insurance insurance = new Insurance(carID, insuranceModel);
                insuranceList.add(insurance);
            }
        } catch (DLException e) {
            throw e;
        }
        return insuranceList;
    }

    @Override
    public boolean remove(int insuranceID) throws DLException {
        String query = "DELETE FROM Insurance WHERE insuranceID = ?";
        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(Integer.toString(insuranceID));
            return db.executeUpdate(query, params);
        } catch (DLException e) {
            throw e;
        }
    }

    @Override
    public boolean setData(Object newData) throws DLException {
        if (!(newData instanceof Insurance))
            throw new DLException(new IllegalArgumentException("Invalid data type for Insurance."));

        Insurance insurance = (Insurance) newData;
        String query = "INSERT INTO Insurance (carID, insurance_model) VALUES (?, ?)";
        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(Integer.toString(insurance.getCarID()));
            params.add(insurance.getInsuranceModel());
            System.out.println("Successfully inserted an insurance.");
            return db.executeUpdate(query, params);
        } catch (DLException e) {
            throw e;
        }
    }

    @Override
    public boolean modify(int bookingID, int newCarID, Timestamp newBookingTime) throws DLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modify'");
    }

    public Insurance getInsuranceForCar(int carID) throws DLException {
        String query = "SELECT * FROM Insurance WHERE carID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(Integer.toString(carID));

        try {
            ArrayList<ArrayList<String>> result = db.executeQuery(query, params);
            if (result.size() > 0) {
                ArrayList<String> row = result.get(1);
                String insuranceModel = row.get(2);
         
                return new Insurance(carID, insuranceModel);
            } else {
                return null; // No insurance found for the given carID
            }
        } catch (DLException e) {
            throw e;
        }
    }

}
