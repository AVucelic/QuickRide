package Controller;

import java.util.ArrayList;

import ConnectivityLayers.DLException;
import Models.Model;
import Models.User;
import View.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HandleLogin implements EventHandler<ActionEvent> {
    private View view;
    private Model model;
    private User current;
    private Controller controller;

    public HandleLogin(Controller controller, View view, Model model) {
        this.view = view;
        this.model = model;
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            ArrayList<Object> data = model.getData();
            String username = this.view.getTxtUsername().getText();
            String password = this.view.getTxtPassword().getText();

            for (Object object : data) {
                User user = (User) object;
                if (user.getUsername().equals(username) && user.getPassoword().equals(password)) {
                    current = user;
                    switchViews(current);
                    controller.getBookingsModel().setId(current.getUserID());

                    return;
                }
            }
            Alert alert = new Alert(AlertType.ERROR,
                    "Incorrect Username or password.");
            alert.showAndWait();
            // If no match is found, you can handle invalid login here
        } catch (DLException e) {
            // Handle exception
            e.printStackTrace();
        }
    }

    public void switchViews(User user) {
        if (user.getRole().equals("Member")) {
            controller.switchToSuccessView(user);
        } else if (user.getRole().equals("Admin")) {
            controller.switchToAdminView(user);
        }
    }
}
