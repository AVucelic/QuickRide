package Controller;

import java.util.ArrayList;

import ConnectivityLayers.DLExeption;
import Models.Model;
import Models.User;
import View.UserView;
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
    private UserView userView;

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
                    userView = new UserView();
                    userView.setUserID(current.getUserID());
                    controller.switchToSuccessView(current);
                    return;
                }
            }
            Alert alert = new Alert(AlertType.ERROR,
                    "Incorrect Username or password.");
            alert.showAndWait();
            // If no match is found, you can handle invalid login here
        } catch (DLExeption e) {
            // Handle exception
            e.printStackTrace();
        }
    }
}