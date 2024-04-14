package View;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.util.ArrayList;

import ConnectivityLayers.DLExeption;
import Models.*;

public class AdminView extends Application {

    private TableView<Car> tableView = new TableView<>();
    private Cars carsModel = new Cars();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin View");

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 800, 600);

        // Create table columns
        TableColumn<Car, Integer> carIDCol = new TableColumn<>("Car ID");
        carIDCol.setCellValueFactory(new PropertyValueFactory<>("carID"));

        TableColumn<Car, String> manufacturerCol = new TableColumn<>("Manufacturer");
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

        TableColumn<Car, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Car, Integer> powerCol = new TableColumn<>("Power");
        powerCol.setCellValueFactory(new PropertyValueFactory<>("power"));

        TableColumn<Car, Integer> yearCol = new TableColumn<>("Year of Production");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year_of_Production"));

        TableColumn<Car, Integer> mileageCol = new TableColumn<>("Mileage");
        mileageCol.setCellValueFactory(new PropertyValueFactory<>("mileage"));

        TableColumn<Car, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.getColumns().addAll(carIDCol, manufacturerCol, modelCol, powerCol, yearCol, mileageCol, statusCol);

        // Load data into the table
        refreshTable();

        // Create CRUD buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> addCar());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> deleteCar());

        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(event -> modifyCar());

        // Layout
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.add(addButton, 0, 0);
        buttonPane.add(deleteButton, 1, 0);
        buttonPane.add(modifyButton, 2, 0);

        borderPane.setCenter(tableView);
        borderPane.setBottom(buttonPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void refreshTable() {
        try {
            ArrayList<Object> carsDataObjects = carsModel.getData();
            ArrayList<Car> carsData = new ArrayList<>();

            // Convert ArrayList<Object> to ArrayList<Car>
            for (Object obj : carsDataObjects) {
                if (obj instanceof Car) {
                    carsData.add((Car) obj);
                }
            }

            ObservableList<Car> carsList = FXCollections.observableArrayList(carsData);
            tableView.setItems(carsList);
        } catch (DLExeption e) {
            e.printStackTrace();
        }
    }

    private void addCar() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Add New Car");

        // Create labels and text fields for each attribute of the Car class
        Label manufacturerLabel = new Label("Manufacturer:");
        TextField manufacturerField = new TextField();

        Label modelLabel = new Label("Model:");
        TextField modelField = new TextField();

        Label powerLabel = new Label("Power:");
        TextField powerField = new TextField();

        Label yearLabel = new Label("Year of Production:");
        TextField yearField = new TextField();

        Label mileageLabel = new Label("Mileage:");
        TextField mileageField = new TextField();

        Label statusLabel = new Label("Status:");
        TextField statusField = new TextField();

        // Create a button to confirm adding the new car
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            // Retrieve input values from text fields
            String manufacturer = manufacturerField.getText();
            String model = modelField.getText();
            int power = Integer.parseInt(powerField.getText());
            int year = Integer.parseInt(yearField.getText());
            int mileage = Integer.parseInt(mileageField.getText());
            String status = statusField.getText();

            // Create a new Car object with the input values
            Car newCar = new Car(0, manufacturer, model, power, year, mileage, status);

            try {
                // Add the new car to the database
                carsModel.setData(newCar);
                // Refresh the table to display the updated data
                refreshTable();
                // Close the pop-up window
                popupStage.close();
            } catch (DLExeption e) {
                e.printStackTrace();
            }
        });

        // Create a layout for the pop-up window
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, manufacturerLabel, manufacturerField);
        gridPane.addRow(1, modelLabel, modelField);
        gridPane.addRow(2, powerLabel, powerField);
        gridPane.addRow(3, yearLabel, yearField);
        gridPane.addRow(4, mileageLabel, mileageField);
        gridPane.addRow(5, statusLabel, statusField);
        gridPane.addRow(6, addButton);

        // Create the scene and set it to the pop-up window
        Scene scene = new Scene(gridPane, 300, 250);
        popupStage.setScene(scene);
        // Show the pop-up window
        popupStage.show();
    }

    private void deleteCar() {
        Car selectedCar = tableView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            try {
                carsModel.remove(selectedCar.getCarID());
                refreshTable();
            } catch (DLExeption e) {
                e.printStackTrace();
            }
        } else {
            // Show an error message or alert dialog
        }
    }

    private void modifyCar() {
        Car selectedCar = tableView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            // Create a pop-up window for modifying the car details
            Stage popupStage = new Stage();
            popupStage.setTitle("Modify Car Details");

            // Create labels and text fields for each attribute of the Car class
            Label manufacturerLabel = new Label("Manufacturer:");
            TextField manufacturerField = new TextField(selectedCar.getManufacturer());
            manufacturerField.setEditable(false);

            Label modelLabel = new Label("Model:");
            TextField modelField = new TextField(selectedCar.getModel());
            modelField.setEditable(false);
            Label powerLabel = new Label("Power:");
            TextField powerField = new TextField(String.valueOf(selectedCar.getPower()));
            powerField.setEditable(false);
            Label yearLabel = new Label("Year of Production:");
            TextField yearField = new TextField(String.valueOf(selectedCar.getYear_of_Production()));
            yearField.setEditable(false);
            Label mileageLabel = new Label("Mileage:");
            TextField mileageField = new TextField(String.valueOf(selectedCar.getMileage()));
            mileageField.setEditable(true);
            Label statusLabel = new Label("Status:");
            TextField statusField = new TextField(selectedCar.getStatus());
            statusField.setEditable(true);
            // Create a button to confirm the modifications
            Button modifyButton = new Button("Modify");
            modifyButton.setOnAction(event -> {
                // Retrieve updated values from text fields
                String manufacturer = manufacturerField.getText();
                String model = modelField.getText();
                int power = Integer.parseInt(powerField.getText());
                int year = Integer.parseInt(yearField.getText());
                int mileage = Integer.parseInt(mileageField.getText());
                String status = statusField.getText();

                // Update the selected car object with the new values
                selectedCar.setManufacturer(manufacturer);
                selectedCar.setModel(model);
                selectedCar.setPower(power);
                selectedCar.setYear_of_Production(year);
                selectedCar.setMileage(mileage);
                selectedCar.setStatus(status);

                try {
                    // Update the car details in the database
                    carsModel.modifyCar(selectedCar.getCarID(), selectedCar.getMileage(),
                            selectedCar.getStatus());
                    // Refresh the table to display the updated data
                    refreshTable();
                    // Close the pop-up window
                    popupStage.close();
                } catch (DLExeption e) {
                    e.printStackTrace();
                }
            });

            // Create a layout for the pop-up window
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.addRow(0, manufacturerLabel, manufacturerField);
            gridPane.addRow(1, modelLabel, modelField);
            gridPane.addRow(2, powerLabel, powerField);
            gridPane.addRow(3, yearLabel, yearField);
            gridPane.addRow(4, mileageLabel, mileageField);
            gridPane.addRow(5, statusLabel, statusField);
            gridPane.addRow(6, modifyButton);

            // Create the scene and set it to the pop-up window
            Scene scene = new Scene(gridPane, 300, 250);
            popupStage.setScene(scene);
            // Show the pop-up window
            popupStage.show();
        } else {
            // Show an error message or alert dialog if no car is selected
        }
    }

}
