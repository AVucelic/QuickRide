import Controller.Controller;
import Models.Bookings;
import Models.Cars;
import Models.DropOffs;
import Models.Feedbacks;
import Models.Insurances;
import Models.Maintenances;
import Models.Model;
import Models.Payments;
import Models.PickUps;
import Models.Users;
import Models.Reports;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationRunner extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        View view = new View();
        view.start(primaryStage);
        Cars cars = new Cars();
        Bookings bookingModel = new Bookings(1);
        Users userModel = new Users();
        Insurances insuranceModel = new Insurances();
        Payments paymentModel = new Payments();
        Feedbacks feedbackModel = new Feedbacks();
        Maintenances maintenances = new Maintenances();
        Reports reports = new Reports();
        DropOffs dropOffs = new DropOffs();
        PickUps pickUps = new PickUps();

        Controller controller = new Controller(view, cars, bookingModel, userModel, insuranceModel, paymentModel,
                feedbackModel, maintenances, reports, pickUps, dropOffs,
                view.getPrimaryStage());
    }

}