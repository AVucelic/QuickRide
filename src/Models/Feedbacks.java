package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLExeption;

public class Feedbacks extends Model {
    ArrayList<Object> feedbacks;

    @Override
    public ArrayList<Object> getData() throws DLExeption {
        this.feedbacks = new ArrayList<>();
        String statement = "SELECT * FROM Feedback";
        ArrayList<ArrayList<String>> data = db.executeQuery(statement, new ArrayList<>());

        for (int i = 1; i < data.size(); i++) {
            Feedback feedback = new Feedback(Integer.parseInt(data.get(i).get(0)), Integer.parseInt(data.get(i).get(1)),
                    data.get(i).get(2), Integer.parseInt(data.get(i).get(3)), Timestamp.valueOf(data.get(i).get(4)));
            this.feedbacks.add(feedback);
        }
        return feedbacks;
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

    @Override
    public boolean setData(Object newData) throws DLExeption {
        Feedback feedback = (Feedback) newData;
        String statement = "INSERT INTO Feedback (userID ,  feedback_message, rating , timestamp) VALUES (?, ?, ?, ?)";
        ArrayList<String> arr = new ArrayList<>();
        arr.add(feedback.getUserID() + "");
        arr.add(feedback.getFeedBack());
        arr.add(feedback.getRating() + "");
        arr.add(feedback.getTime() + "");
        db.executeUpdate(statement, arr);
        return true;

    }

}
