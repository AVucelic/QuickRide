import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Runner extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("database.fxml"));
        Parent loginRoot = loginLoader.load();
        Database dbController = loginLoader.getController();
        dbController.setMainWindow(primaryStage);
        primaryStage.setTitle("MySQL Database Login");
        primaryStage.setScene(new Scene(loginRoot));
        primaryStage.getIcons().add(new Image("./images/icon.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
