package ConnectivityLayers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLDatabase {
    private Connection connection;
    private String username;
    private String url;
    private String password;
    private String[] info = new String[4];
    private boolean isInTransaction = false;

    public MySQLDatabase(String username, String password) {
        this.url = "jdbc:mysql://localhost:3306/QuickRide";
        this.username = username;
        this.password = password;
    }

    public boolean connect() throws DLExeption {
        try {

            connection = DriverManager.getConnection(this.url, this.username, this.password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DLExeption(e, additionalDetails("Falied to Connect Driver. Line: 66 on MySQLDatabase class"));
        }
    }

    public boolean close() throws DLExeption {
        try {
            this.connection.close();
            return true;
        } catch (SQLException e) {
            throw new DLExeption(e,
                    additionalDetails("Falied to Close connection."));
        }
    }

    public PreparedStatement prepare(String statement, ArrayList<String> arr) throws DLExeption {
        try {
            // checking if the connection is closed or null, establishing connection
            if (this.connection == null || connection.isClosed()) {
                connect();
            }
            // instantiate the prepares statement
            PreparedStatement preparedStatement = this.connection.prepareStatement(statement);

            // Bind values
            for (int i = 0; i < arr.size(); i++) {
                preparedStatement.setString(i + 1, arr.get(i));
            }

            return preparedStatement;
        } catch (SQLException e) {
            throw new DLExeption(e, additionalDetails("Failed to prepare statement."));
        }
    }

    public ArrayList<ArrayList<String>> executeQuery(String statement, ArrayList<String> arr) throws DLExeption {

        PreparedStatement preparedStatement = prepare(statement, arr);

        try {
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<ArrayList<String>> twoD = new ArrayList<>();
            int columnCount = rs.getMetaData().getColumnCount();

            if (columnCount > 0) {
                ArrayList<String> columnNames = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(rs.getMetaData().getColumnName(i));
                }
                twoD.add(columnNames);
            }

            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                twoD.add(row);
            }

            preparedStatement.close();
            rs.close();
            return twoD;

        } catch (SQLException e) {
            throw new DLExeption(e, additionalDetails("Failed to execute getData with prepared statement"));
        }
    }

    public boolean executeUpdate(String statement, ArrayList<String> arr) throws DLExeption {
        try {
            PreparedStatement preparedStatement = prepare(statement, arr);
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Prints the full stack trace for better diagnosis
            throw new DLExeption(e, additionalDetails("Failed to execute update/delete statement."));
        }
    }

    private String[] additionalDetails(String reason) {
        info[0] = "Username: " + username;
        info[1] = "User Password: " + password;
        info[2] = "Database used: " + url;
        info[3] = "Additional Error feedback: " + reason;

        return info;
    }

    // Start a transaction
    public boolean startTrans() throws DLExeption {
        try {
            if (!isInTransaction && connection != null) {
                connection.setAutoCommit(false);
                isInTransaction = true;
                System.out.println("Transaction started.");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DLExeption(e, additionalDetails("Failed to start a transaction."));
        }
    }

    // End a transaction (commit changes)
    public boolean endTrans() throws DLExeption {
        try {
            if (isInTransaction && connection != null) {
                connection.commit();
                connection.setAutoCommit(true);
                isInTransaction = false;
                System.out.println("Transaction committed.");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DLExeption(e, additionalDetails("Failed to end a transaction."));
        }
    }

    // Rollback a transaction
    public boolean rollbackTrans() throws DLExeption {
        try {
            if (isInTransaction && connection != null) {
                connection.rollback();
                connection.setAutoCommit(true);
                isInTransaction = false;
                System.out.println("Transaction rolled back.");
                return true;
            } else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DLExeption(e, additionalDetails("Failed to rollback a transaction."));
        }
    }

}
