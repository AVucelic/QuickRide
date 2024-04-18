
package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLException;
import ConnectivityLayers.MySQLDatabase;

public abstract class Model {
    protected MySQLDatabase db = new MySQLDatabase("root", "ritcroatia");

    public abstract ArrayList<Object> getData() throws DLException;

    public abstract boolean modify(int bookingID, int newCarID, Timestamp newBookingTime) throws DLException;

    public abstract boolean remove(int ID) throws DLException;

    public abstract boolean setData(Object newData) throws DLException;


}
