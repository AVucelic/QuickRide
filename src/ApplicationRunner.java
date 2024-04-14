import Controller.Controller;
import Models.Bookings;
import Models.Cars;
import Models.Model;
import Models.Users;
import View.View;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ApplicationRunner extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        View view = new View();
        view.start(primaryStage);
        primaryStage.getIcons().add(new Image("./images/icon.png"));
        Model cars = new Cars();
        Model bookingModel = new Bookings(1);
        Users userModel = new Users();

        Controller controller = new Controller(view, cars, bookingModel, userModel, view.getPrimaryStage());
    }

}