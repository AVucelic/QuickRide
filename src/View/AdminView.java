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
import Controller.Controller;
import Models.*;

public class AdminView extends Application {

    private Cars carsModel = new Cars();
    private TableView<Car> tableView = new TableView<>();

    Button addButton = new Button("Add");
    Button deleteButton = new Button("Delete");
    Button modifyButton = new Button("Modify");
    TableColumn<Car, Integer> carIDCol = new TableColumn<>("Car ID");
    TableColumn<Car, String> manufacturerCol = new TableColumn<>("Manufacturer");
    TableColumn<Car, String> modelCol = new TableColumn<>("Model");
    TableColumn<Car, Integer> powerCol = new TableColumn<>("Power");
    TableColumn<Car, Integer> yearCol = new TableColumn<>("Year of Production");
    TableColumn<Car, Integer> mileageCol = new TableColumn<>("Mileage");
    TableColumn<Car, String> statusCol = new TableColumn<>("Status");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin View");

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 800, 600);

        tableView.getColumns().addAll(carIDCol, manufacturerCol, modelCol, powerCol, yearCol, mileageCol, statusCol);

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

    public Button getModifyButton() {
        return modifyButton;
    }

    public void setModifyButton(Button modifyButton) {
        this.modifyButton = modifyButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public void setAddButton(Button addButton) {
        this.addButton = addButton;
    }

    public TableView<Car> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<Car> tableView) {
        this.tableView = tableView;
    }

    public Cars getCarsModel() {
        return carsModel;
    }

    public void setCarsModel(Cars carsModel) {
        this.carsModel = carsModel;
    }

    public TableColumn<Car, Integer> getCarIDCol() {
        return carIDCol;
    }

    public TableColumn<Car, String> getManufacturerCol() {
        return manufacturerCol;
    }

    public TableColumn<Car, String> getModelCol() {
        return modelCol;
    }

    public TableColumn<Car, Integer> getPowerCol() {
        return powerCol;
    }

    public TableColumn<Car, Integer> getYearCol() {
        return yearCol;
    }

    public TableColumn<Car, Integer> getMileageCol() {
        return mileageCol;
    }

    public TableColumn<Car, String> getStatusCol() {
        return statusCol;
    }

    public void setCarIDCol(TableColumn<Car, Integer> carIDCol) {
        this.carIDCol = carIDCol;
    }

    public void setManufacturerCol(TableColumn<Car, String> manufacturerCol) {
        this.manufacturerCol = manufacturerCol;
    }

    public void setModelCol(TableColumn<Car, String> modelCol) {
        this.modelCol = modelCol;
    }

    public void setPowerCol(TableColumn<Car, Integer> powerCol) {
        this.powerCol = powerCol;
    }

    public void setYearCol(TableColumn<Car, Integer> yearCol) {
        this.yearCol = yearCol;
    }

    public void setMileageCol(TableColumn<Car, Integer> mileageCol) {
        this.mileageCol = mileageCol;
    }

    public void setStatusCol(TableColumn<Car, String> statusCol) {
        this.statusCol = statusCol;
    }

}
