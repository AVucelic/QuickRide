package View;

import javax.swing.Action;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SuccessView extends Application {

    private ListView<String> listView = new ListView<>();
    private TextField searchTF = new TextField();
    private ComboBox<String> sortByComboBox = new ComboBox<>(FXCollections.observableArrayList(
            "Manufacturer", "Model", "Power", "Year of Production", "Mileage", "Status", "Price"));
    private ComboBox<String> sortOrderComboBox = new ComboBox<>(FXCollections.observableArrayList(
            "Ascending", "Descending"));
    private CheckBox showAllCarsCheckBox = new CheckBox("Show all cars");
    private Button firstPageButton = new Button("First Page");
    private Button lastPageButton = new Button("Last Page");
    private Button nextPageButton = new Button("Next Page");
    private Button previousPageButton = new Button("Previous Page");
    private static final int ITEMS_PER_PAGE = 10;
    private int currentPage = 1;
    private AnchorPane root = new AnchorPane();
    private Button bookingBtn = new Button("Bookings");
    private Button bookACar = new Button("Book a car");
    private Button feedback = new Button("Leave Feedback");

    public void HandleFeedback(EventHandler<ActionEvent> event) {
        feedback.setOnAction(event);
    }

    public Button getBookACar() {
        return bookACar;
    }

    public Button getBookingBtn() {
        return bookingBtn;
    }

    public AnchorPane getRoot() {
        return root;
    }

    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Success View Application");

        AnchorPane root = createContent();
        scene = new Scene(root, 803, 599);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public AnchorPane createContent() {
        root.setPrefSize(803, 599);

        Pane topPane = new Pane();
        topPane.setPrefSize(803, 119);
        root.getChildren().add(topPane);

        Label searchLabel = new Label("Search by brand or model:");
        searchLabel.setLayoutX(641);
        searchLabel.setLayoutY(24);
        topPane.getChildren().add(searchLabel);

        searchTF.setLayoutX(641);
        searchTF.setLayoutY(41);
        searchTF.setPromptText("Toyota...");
        topPane.getChildren().add(searchTF);

        Label sortByLabel = new Label("Sort by:");
        sortByLabel.setLayoutX(50);
        sortByLabel.setLayoutY(45);
        root.getChildren().add(sortByLabel);

        sortByComboBox.setLayoutX(50);
        sortByComboBox.setLayoutY(70);
        sortByComboBox.setValue("Manufacturer");
        root.getChildren().add(sortByComboBox);

        Label sortOrderLabel = new Label("Sort order:");
        sortOrderLabel.setLayoutX(200);
        sortOrderLabel.setLayoutY(45);
        root.getChildren().add(sortOrderLabel);

        sortOrderComboBox.setLayoutX(200);
        sortOrderComboBox.setLayoutY(70);
        sortOrderComboBox.setValue("Ascending");
        root.getChildren().add(sortOrderComboBox);

        listView.setLayoutX(50);
        listView.setLayoutY(109);
        listView.setPrefSize(711, 424);
        root.getChildren().add(listView);

        AnchorPane.setTopAnchor(listView, null);
        AnchorPane.setLeftAnchor(firstPageButton, 100.0);
        firstPageButton.setLayoutY(540);
        root.getChildren().add(firstPageButton);

        AnchorPane.setTopAnchor(listView, null);
        AnchorPane.setLeftAnchor(previousPageButton, 220.0);
        previousPageButton.setLayoutY(540);
        root.getChildren().add(previousPageButton);

        AnchorPane.setTopAnchor(listView, null);
        AnchorPane.setRightAnchor(nextPageButton, 220.0);
        nextPageButton.setLayoutY(540);
        root.getChildren().add(nextPageButton);

        AnchorPane.setTopAnchor(listView, null);
        AnchorPane.setRightAnchor(lastPageButton, 100.0);
        lastPageButton.setLayoutY(540);
        root.getChildren().add(lastPageButton);

        bookingBtn.setLayoutX(368);
        bookingBtn.setLayoutY(540);
        root.getChildren().add(bookingBtn);

        bookACar.setLayoutX(364);
        bookACar.setLayoutY(575);
        root.getChildren().add(bookACar);

        showAllCarsCheckBox.setLayoutX(320);
        showAllCarsCheckBox.setLayoutY(70);
        root.getChildren().add(showAllCarsCheckBox);

        feedback.setLayoutX(600);
        feedback.setLayoutY(700);
        root.getChildren().add(feedback);
        return root;
    }

    public Button getFirstPageButton() {
        return firstPageButton;
    }

    public Button getLastPageButton() {
        return lastPageButton;
    }

    public CheckBox getShowAllCarsCheckBox() {
        return showAllCarsCheckBox;
    }

    public ListView<String> getListView() {
        return listView;
    }

    public TextField getSearchTF() {
        return searchTF;
    }

    public ComboBox<String> getSortByComboBox() {
        return sortByComboBox;
    }

    public ComboBox<String> getSortOrderComboBox() {
        return sortOrderComboBox;
    }

    public static int getItemsPerPage() {
        return ITEMS_PER_PAGE;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public Button getNextPageButton() {
        return nextPageButton;
    }

    public Button getPreviousPageButton() {
        return previousPageButton;
    }

    public void setListView(ListView<String> listView) {
        this.listView = listView;
    }

    public void setSearchTF(TextField searchTF) {
        this.searchTF = searchTF;
    }

    public void setSortByComboBox(ComboBox<String> sortByComboBox) {
        this.sortByComboBox = sortByComboBox;
    }

    public void setSortOrderComboBox(ComboBox<String> sortOrderComboBox) {
        this.sortOrderComboBox = sortOrderComboBox;
    }

    public void setShowAllCarsCheckBox(CheckBox showAllCarsCheckBox) {
        this.showAllCarsCheckBox = showAllCarsCheckBox;
    }

    public void setFirstPageButton(Button firstPageButton) {
        this.firstPageButton = firstPageButton;
    }

    public void setLastPageButton(Button lastPageButton) {
        this.lastPageButton = lastPageButton;
    }

    public void setNextPageButton(Button nextPageButton) {
        this.nextPageButton = nextPageButton;
    }

    public void setPreviousPageButton(Button previousPageButton) {
        this.previousPageButton = previousPageButton;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
    }

    public void setBookingBtn(Button bookingBtn) {
        this.bookingBtn = bookingBtn;
    }

    public void setBookACar(Button bookACar) {
        this.bookACar = bookACar;
    }

    public Button getFeedback() {
        return feedback;
    }

    public void setFeedback(Button feedback) {
        this.feedback = feedback;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}