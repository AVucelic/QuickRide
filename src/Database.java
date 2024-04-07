import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Database {

    @FXML
    private Button btnDBLogIn;

    @FXML
    private TextField dbPassField;

    @FXML
    private TextField dbUserField;

    private Connection connection;
    private String username;
    private String url = "jdbc:mysql://localhost:3306/QuickRide";
    private String password;
    private String[] info = new String[4];
    private Stage primaryStage;

    public boolean connect() throws DLExeption {
        try {
            this.username = dbUserField.getText();
            this.password = dbPassField.getText();
            connection = DriverManager.getConnection(this.url, this.username, this.password);
            return true;
        } catch (SQLException e) {
            throw new DLExeption(e, additionalDetails("Failed to Connect Driver. Line: 66 on MySQLDatabase class"));
        }
    }

    public boolean close() throws DLExeption {
        try {
            this.connection.close();
            return true;
        } catch (SQLException e) {
            throw new DLExeption(e, additionalDetails("Failed to Close connection."));
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

    @FXML
    void login(ActionEvent event) {
        try {
            if (connect()) {
                FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("./UI/login.fxml"));
                Parent mainRoot = mainLoader.load();
                primaryStage.setScene(new Scene(mainRoot));
            }
        } catch (DLExeption | IOException e) {
            displayAlert("Incorrect username or password.");
        }
    }

    private void displayAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setMainWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
