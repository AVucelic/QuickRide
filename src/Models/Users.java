package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLExeption;

public class Users extends Model {
    ArrayList<Object> users;

    public Users() {
        users = new ArrayList<>();
    }

    @Override
    public ArrayList<Object> getData() throws DLExeption {
        this.users = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>();
        String statement = "Select * from User";
        ArrayList<ArrayList<String>> data = db.executeQuery(statement, params);
        for (int i = 1; i < data.size(); i++) {
            User user = new User(Integer.parseInt(data.get(i).get(0)), data.get(i).get(1), data.get(i).get(2),
                    data.get(i).get(3), data.get(i).get(4), data.get(i).get(5));
            users.add(user);
        }
        return users;
    }

    public boolean userExists(String username) throws DLExeption {
        ArrayList<String> params = new ArrayList<>();
        params.add(username);
        // ArrayList<ArrayList<String>> data = db.executeQuery("SELECT * FROM User WHERE
        // username = ?", params);
        System.out.println("hello");
        // return data.size() > 0;
        ArrayList<Object> data = this.getData();
        for (Object obj : data) {
            User user = (User) obj;
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;

    }

    public void addUser(User user) throws DLExeption {
        ArrayList<String> params = new ArrayList<>();
        params.add(user.getUsername());
        params.add(user.getEmail());
        params.add(user.getPassoword());
        params.add(user.getFirstName());
        params.add(user.getLastName());
        db.executeUpdate("INSERT INTO User (username, email, password, firstname, lastname) VALUES (?, ?, ?, ?, ?)",
                params);
    }

    @Override
    public boolean remove(int id) throws DLExeption {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean modify(int bookingID, int newCarID, Timestamp newBookingTime) throws DLExeption {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modify'");
    }

    @Override
    public boolean setData(Object newData) throws DLExeption {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setData'");
    }

}
