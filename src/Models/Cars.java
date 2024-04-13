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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean modify(int bookingID, int newCarID, Timestamp newBookingTime) throws DLExeption {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modify'");
    }
}
