package Models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLExeption;

public class Cars extends Model {
    ArrayList<Object> cars;

    public Cars() {
        cars = new ArrayList<>();
    }

    public ArrayList<Object> getData() throws DLExeption {
        this.cars = new ArrayList<>();
        String statement = "SELECT * FROM Car";
        ArrayList<ArrayList<String>> data = db.executeQuery(statement, new ArrayList<>());

        for (int i = 1; i < data.size(); i++) {
            Car car = new Car(Integer.parseInt(data.get(i).get(0)), data.get(i).get(1), data.get(i).get(2),
                    Integer.parseInt(data.get(i).get(3)), Integer.parseInt(data.get(i).get(4)),
                    Integer.parseInt(data.get(i).get(5)), data.get(i).get(6));
            this.cars.add(car);
        }
        return this.cars;
    }

    @Override
    public boolean remove(int ID) throws DLExeption {
        String statement = "DELETE FROM Car WHERE carID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(ID));
        return db.executeUpdate(statement, params);
    }

    public boolean modifyCar(int carID, int mileage, String status) throws DLExeption {
        String statement = "UPDATE Car SET mileage = ?, isAvailable = ? WHERE carID = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(mileage));
        params.add(String.valueOf(status));
        params.add(String.valueOf(carID));
        return db.executeUpdate(statement, params);
    }

    @Override
    public boolean setData(Object newData) throws DLExeption {
        if (newData instanceof Car) {
            Car newCar = (Car) newData;
            String statement = "INSERT INTO Car (manufacturer, model, power, year_of_Production, mileage, isAvailable) VALUES (?, ?, ?, ?, ?, ?)";
            ArrayList<String> params = new ArrayList<>();
            params.add(newCar.getManufacturer());
            params.add(newCar.getModel());
            params.add(String.valueOf(newCar.getPower()));
            params.add(String.valueOf(newCar.getYear_of_Production()));
            params.add(String.valueOf(newCar.getMileage()));
            params.add(newCar.getStatus());

            // Execute the insert statement
            boolean success = db.executeUpdate(statement, params);

            if (success) {
                // If insertion was successful, retrieve the generated carID
                String selectLastInsertId = "SELECT LAST_INSERT_ID()";
                ArrayList<ArrayList<String>> result = db.executeQuery(selectLastInsertId, new ArrayList<>());
                if (result != null && result.size() > 1 && result.get(1) != null && result.get(1).size() > 0) {
                    // Update the new car object with the generated carID
                    int generatedCarID = Integer.parseInt(result.get(1).get(0));
                    newCar.setCarID(generatedCarID);
                }
            }

            return success;
        } else {
            throw new IllegalArgumentException("Invalid data type. Expected Car object.");
        }
    }

    @Override
    public boolean modify(int bookingID, int newCarID, Timestamp newBookingTime) throws DLExeption {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modify'");
    }

}