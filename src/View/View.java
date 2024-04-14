package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class View extends Application {
    private Button btnLogin = new Button("Login");
    private Button btnRegister = new Button("Register");;
    private TextField txtUsername;
    private PasswordField txtPassword;
    private Stage primaryStage;
    private TextField txtRegUsername;
    private PasswordField txtRegPassword;
    private PasswordField txtRegConfirmPassword;
    private TextField txtRegEmail; // New field for email
    private TextField txtRegFirstName; // New field for first name
    private TextField txtRegLastName;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Login");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);

        // StackPane for the welcome label
        StackPane welcomePane = new StackPane();
        Label lblWelcome = new Label("Welcome to QUICK RIDE");
        lblWelcome.setFont(Font.font("Arial", FontWeight.BOLD, 54));
        welcomePane.getChildren().add(lblWelcome);

        // Main grid for login
        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10); // Increased vertical gap for better spacing

        // Username and password fields
        Label lblUsername = new Label("Username:");
        lblUsername.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Bigger font
        txtUsername = new TextField();
        txtUsername.setPrefWidth(300); // Larger text field
        Label lblPassword = new Label("Password:");
        lblPassword.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Bigger font
        txtPassword = new PasswordField();
        txtPassword.setPrefWidth(300); // Larger text field
        Hyperlink linkRegister = new Hyperlink("Register");

        loginGrid.add(lblUsername, 0, 0);
        loginGrid.add(txtUsername, 0, 1);
        loginGrid.add(lblPassword, 0, 2);
        loginGrid.add(txtPassword, 0, 3);
        loginGrid.add(btnLogin, 0, 4);
        loginGrid.add(linkRegister, 0, 5);

        // Set the welcome label on top of the login grid
        welcomePane.setAlignment(Pos.TOP_CENTER);
        welcomePane.getChildren().add(loginGrid);

        Scene loginScene = new Scene(welcomePane);

        // Registration form elements
        GridPane regGrid = new GridPane();
        regGrid.setAlignment(Pos.CENTER);
        regGrid.setHgap(10);
        regGrid.setVgap(20); // Increased vertical gap for better spacing

        txtRegUsername = new TextField();
        Label lblRegUsername = new Label("Username:");
        lblRegUsername.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Bigger font
        txtRegPassword = new PasswordField();
        Label lblRegPassword = new Label("Password:");
        lblRegPassword.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Bigger font
        txtRegConfirmPassword = new PasswordField();
        txtRegEmail = new TextField(); // Initialize new text field for email
        txtRegFirstName = new TextField(); // Initialize new text field for first name
        txtRegLastName = new TextField();

        Label lblRegConfirmPassword = new Label("Confirm Password:");
        lblRegConfirmPassword.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Bigger font

        Hyperlink linkLogin = new Hyperlink("Back to Login");

        regGrid.add(new Label("Username:"), 0, 0);
        regGrid.add(txtRegUsername, 1, 0);
        regGrid.add(new Label("Password:"), 0, 1);
        regGrid.add(txtRegPassword, 1, 1);
        regGrid.add(new Label("Confirm Password:"), 0, 2);
        regGrid.add(txtRegConfirmPassword, 1, 2);
        regGrid.add(new Label("Email:"), 0, 3);
        regGrid.add(txtRegEmail, 1, 3);
        regGrid.add(new Label("First Name:"), 0, 4);
        regGrid.add(txtRegFirstName, 1, 4);
        regGrid.add(new Label("Last Name:"), 0, 5);
        regGrid.add(txtRegLastName, 1, 5);
        regGrid.add(btnRegister, 1, 6);
        regGrid.add(linkLogin, 0, 6);

        Scene registerScene = new Scene(regGrid);

        // Set action for the register hyperlink
        linkRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(registerScene);
            }
        });

        // Set action for the login hyperlink in the register page
        linkLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(loginScene);
            }
        });
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public TextField getTxtRegEmail() {
        return txtRegEmail;
    }

    public TextField getTxtRegFirstName() {
        return txtRegFirstName;
    }

    public TextField getTxtRegLastName() {
        return txtRegLastName;
    }

    public TextField getTxtRegUsername() {
        return txtRegUsername;
    }

    public PasswordField getTxtRegPassword() {
        return txtRegPassword;
    }

    public PasswordField getTxtRegConfirmPassword() {
        return txtRegConfirmPassword;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public TextField getTxtUsername() {
        return txtUsername;
    }

    public PasswordField getTxtPassword() {
        return txtPassword;
    }

    public void handleLogin(EventHandler<ActionEvent> e) {
        btnLogin.setOnAction(e);
    }

    public void handleRegister(EventHandler<ActionEvent> e) {
        btnRegister.setOnAction(e);
    }

}
