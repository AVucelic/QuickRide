package Models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLExeption;

public class Payments extends Model {
    ArrayList<Object> payments;

    public Payments() {
        payments = new ArrayList<>();
    }

    public ArrayList<Object> getData() throws DLExeption {
        this.payments = new ArrayList<>();
        String statement = "SELECT * FROM Payment";
        ArrayList<ArrayList<String>> data = db.executeQuery(statement, new ArrayList<>());

        for (int i = 1; i < data.size(); i++) {
            Payment payment = new Payment(Integer.parseInt(data.get(i).get(0)),
                    Integer.parseInt(data.get(i).get(1)), Double.parseDouble(data.get(i).get(2)),
                    data.get(i).get(3), Integer.parseInt(data.get(i).get(4)), data.get(i).get(5),
                    Timestamp.valueOf(data.get(i).get(6)));
            this.payments.add(payment);
        }
        return this.payments;
    }

    @Override
    public boolean setData(Object newData) throws DLExeption {
        Payment payment = (Payment) newData;
        String statement = "INSERT INTO Payment (bookingID,  amount, method, card_details,card_type, timestamp ) VALUES (?, ?, ?, ?, ?, ?)";
        ArrayList<String> arr = new ArrayList<>();
        arr.add(payment.getBookingId() + "");
        arr.add(payment.getAmount() + "");
        arr.add(payment.getMethod());
        arr.add(payment.getCardDetails() + "");
        arr.add(payment.getCardType());
        arr.add(payment.getTimestamp()+"");
        db.executeUpdate(statement,arr);

        return true;

    }

    @Override
    public boolean modify(int bookingID, int newCarID, Timestamp newBookingTime) throws DLExeption {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modify'");
    }

    @Override
    public boolean remove(int ID) throws DLExeption {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
}