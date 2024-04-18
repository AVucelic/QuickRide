package Controller;

import java.sql.Date;

import javax.swing.Action;

import ConnectivityLayers.DLException;
import Models.Car;
import Models.Cars;
import Models.Maintenance;
import Models.Maintenances;
import View.AdminView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HandleMaintenance implements EventHandler<ActionEvent> {
    private AdminView view;
    private Maintenances maintenancesModel;
    private Cars carsModel;
    private Controller controller;

    public HandleMaintenance(AdminView view, Maintenances maintenancesModel, Cars carsModel, Controller controller) {
        this.view = view;
        this.maintenancesModel = maintenancesModel;
        this.carsModel = carsModel;
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent event) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Maintenance Details");

        Car selectedItem = this.view.getTableView().getSelectionModel().getSelectedItem();

        // Create components
        Label carIdLabel = new Label("Car ID:");
        String carId = selectedItem.getCarID() + "";
        Label carIdValueLabel = new Label(carId);

        Label endDateLabel = new Label("End Date:");
        DatePicker endDatePicker = new DatePicker();

        Label descriptionLabel = new Label("Description:");
        TextArea descriptionTextArea = new TextArea();

        descriptionTextArea.setPrefRowCount(5);
        descriptionTextArea.setWrapText(true);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(submitEvent -> {
            String description = descriptionTextArea.getText();
            Maintenance m = new Maintenance(selectedItem.getCarID(), Date.valueOf(endDatePicker.getValue()),
                    description);
            try {
                if (maintenancesModel.setData(m)) {
                    carsModel.modifyCar(selectedItem.getCarID(), selectedItem.getMileage(), "0");
                    Alert a = new Alert(AlertType.CONFIRMATION, "Car sent for Maintenance Check");
                    a.showAndWait();
                    controller.refreshTable();
                } else {
                    Alert a = new Alert(AlertType.ERROR, "Car wasn't sent successfully for Maintenance Check");
                    a.showAndWait();
                }
            } catch (DLException e) {
                e.printStackTrace();
            }
            popupStage.close();
        });

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(carIdLabel, 0, 0);
        gridPane.add(carIdValueLabel, 1, 0);

        gridPane.add(endDateLabel, 0, 1);
        gridPane.add(endDatePicker, 1, 1);

        gridPane.add(descriptionLabel, 0, 2);
        GridPane.setColumnSpan(descriptionTextArea, 2);
        gridPane.add(descriptionTextArea, 0, 3);

        gridPane.add(submitButton, 0, 4);

        // Add layout to scene
        Scene scene = new Scene(gridPane, 300, 250);
        popupStage.setScene(scene);

        // Show the pop-up window
        popupStage.show();
    }
}
