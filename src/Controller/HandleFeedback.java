package Controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import ConnectivityLayers.DLException;
import Models.Feedback;
import Models.Feedbacks;
import Models.User;
import View.SuccessView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HandleFeedback implements EventHandler<ActionEvent> {
    private SuccessView view;
    private Feedbacks feedbackModel;
    private User current;

    public HandleFeedback(SuccessView view, Feedbacks feedbacks, User currentUser) {
        this.view = view;
        this.feedbackModel = feedbacks;
        this.current = currentUser;
    }

    @Override
    public void handle(ActionEvent event) {
        // Create a new stage for the pop-up window
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Feedback");

        // Create components
        Label ratingLabel = new Label("Rating:");
        ComboBox<Integer> ratingComboBox = new ComboBox<>();
        ratingComboBox.getItems().addAll(1, 2, 3, 4, 5);

        Label feedbackDescriptionLabel = new Label("Feedback Description:");
        TextArea feedbackDescriptionTextArea = new TextArea();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(submitEvent -> {
            // Handle feedback submission here
            int rating = ratingComboBox.getValue();
            String description = feedbackDescriptionTextArea.getText();
            LocalDateTime now = LocalDateTime.now();
            if (!(rating <= 0 && description.equals(""))) {

                Timestamp timestamp = Timestamp.valueOf(now);
                Feedback feedback = new Feedback(current.getUserID(), description, rating, timestamp);
                try {
                    if (feedbackModel.setData(feedback)) {
                        Alert alert = new Alert(AlertType.CONFIRMATION,
                                "You have successfully submitted you feedback. We appriciate you taking the time to do so!");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(AlertType.ERROR, "Your Feedback Submission wasnt Successful!!");
                        alert.showAndWait();
                    }
                } catch (DLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR, "Please fill all the fields.");
                alert.showAndWait();

            }

            popupStage.close();
        });

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, ratingLabel, ratingComboBox);
        gridPane.addRow(1, feedbackDescriptionLabel);
        GridPane.setColumnSpan(feedbackDescriptionTextArea, 2);
        gridPane.addRow(2, feedbackDescriptionTextArea);
        gridPane.addRow(3, submitButton);

        // Add layout to scene
        Scene scene = new Scene(gridPane, 300, 250);
        popupStage.setScene(scene);

        // Show the pop-up window
        popupStage.show();
    }
}
