package Controller;

import ConnectivityLayers.DLExeption;
import Models.User;
import Models.Users;
import View.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HandleRegister implements EventHandler<ActionEvent> {
    private View view;
    private Users userModel;
    private Controller controller;

    public HandleRegister(Controller controller, View view, Users userModel) {
        this.controller = controller;
        this.view = view;
        this.userModel = userModel;
    }

    @Override
    public void handle(ActionEvent event) {
        String username = view.getTxtRegUsername().getText();
        String password = view.getTxtRegPassword().getText(); // Assume this is the registration password field
        String confirmPassword = view.getTxtRegConfirmPassword().getText();
        String email = view.getTxtRegEmail().getText();
        String firstname = view.getTxtRegFirstName().getText();
        String lastname = view.getTxtRegLastName().getText();

        try {
            if (password.equals(confirmPassword)) {
                if (!userModel.userExists(username)) {
                    userModel.addUser(new User(username, email, password, firstname, lastname)); // Simplified
                                                                                                 // constructor call
                    // controller.switchToSuccessView(null); // Redirect to success page or login
                    // page
                    Alert alert = new Alert(AlertType.CONFIRMATION,
                            "Registration successful, please go back to log in page.");
                    alert.showAndWait();
                } else {
                    // Handle case where user already exists
                    Alert alert = new Alert(AlertType.ERROR,
                            "User already exists.");
                    alert.showAndWait();
                }

            }

        } catch (DLExeption e) {
            e.printStackTrace();
        }
    }
}
