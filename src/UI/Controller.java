package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private Button btnLogIn;

    @FXML
    private Button btnSignIn;

    private Stage mainWindow;

    @FXML
    void authenticate(ActionEvent event) {

    }

    @FXML
    void register(ActionEvent event) {

    }

    public void setMainWindow(Stage mainWindow) {
        this.mainWindow = mainWindow;
    }

}
