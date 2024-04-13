package Controller;

import java.util.ArrayList;

import ConnectivityLayers.DLExeption;
import Models.Car;
import Models.Model;
import Models.User;
import Models.Users;
import View.View;
import View.AdminView;
import View.SuccessView; // Import the SuccessView class
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class Controller implements EventHandler<ActionEvent> {
    private View view;
    private Model carsModel;
    private Model bookingsModel;
    private Users userModel;
    private Stage primaryStage; // We need access to the primary stage for view switching
    private User currentUser;
    private SuccessView successView = new SuccessView();;
    private AdminView adminView = new AdminView();

    public Controller(View view, Model carsModel, Model bookingModel, Users userModel, Stage primaryStage)
            throws DLExeption {
        this.view = view;
        this.carsModel = carsModel;
        this.bookingsModel = bookingModel;
        this.userModel = userModel;
        this.primaryStage = primaryStage;

        HandleLogin login = new HandleLogin(this, view, userModel);
        this.view.handleLogin(login);
        // this.view.handleRegister(register);
        HandleRegister register = new HandleRegister(this, view, userModel);
        this.view.handleRegister(register);
        // ArrayList<Object> cars = carsModel.getData();
    }

    @Override
    public void handle(ActionEvent event) {
        // Handle other events if needed
    }

    // Method to switch to the success view
    public void switchToSuccessView(User user) {
        currentUser = user;
        if (user.getUserRole().equals("Member")) {
            successView.start(primaryStage);
        } else {
            adminView.start(primaryStage);
        }
    }

}
