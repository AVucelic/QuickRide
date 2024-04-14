
package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLExeption;
import ConnectivityLayers.MySQLDatabase;

public abstract class Model {
    protected MySQLDatabase db = new MySQLDatabase("root", "ritcroatia");

    public abstract ArrayList<Object> getData() throws DLExeption;

    public abstract boolean modify(int bookingID, int newCarID, Timestamp newBookingTime) throws DLExeption;

    public abstract boolean remove(int ID) throws DLExeption;

    public abstract boolean setData(Object newData) throws DLExeption;

}
