package View;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ConnectivityLayers.DLExeption;
import Models.Booking;
import Models.Bookings;
import Models.Car;
import Models.Cars;
import Models.User;

public class UserView extends Application {

    private ListView<String> listView = new ListView<>();
    private TextField searchTF = new TextField();
    private ComboBox<String> sortByComboBox = new ComboBox<>();
    private ComboBox<String> sortOrderComboBox = new ComboBox<>();
    private Cars carsModel;
    private Bookings bookingModel;
    private Stage primaryStage;
    private Button bookingBtn;
    private int userID;
    private UserView successView;
    private Scene scene;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    private static final int ITEMS_PER_PAGE = 7;
    private int currentPage = 1;

    public UserView() {

    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        carsModel = new Cars(); // Instantiate the carsModel here
        System.out.println(getUserID());
        bookingModel = new Bookings(getUserID());

        primaryStage.setTitle("QuickRide");

        AnchorPane root = createContent();
        scene = new Scene(root, 803, 599);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public AnchorPane createContent() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(803, 599);

        Pane topPane = new Pane();
        topPane.setPrefSize(803, 119);
        root.getChildren().add(topPane);

        Label searchLabel = new Label("Search by brand or model:");
        searchLabel.setLayoutX(610);
        searchLabel.setLayoutY(45);
        topPane.getChildren().add(searchLabel);

        searchTF.setLayoutX(610);
        searchTF.setLayoutY(70);
        searchTF.setPromptText("Toyota...");
        topPane.getChildren().add(searchTF);

        Label sortByLabel = new Label("Sort by:");
        sortByLabel.setLayoutX(50);
        sortByLabel.setLayoutY(45);
        root.getChildren().add(sortByLabel);

        sortByComboBox.setLayoutX(50);
        sortByComboBox.setLayoutY(70);
        sortByComboBox.getItems().addAll("Manufacturer", "Model", "Power", "Year of Production", "Mileage", "Status");
        sortByComboBox.setValue("Manufacturer");
        root.getChildren().add(sortByComboBox);

        Label sortOrderLabel = new Label("Sort order:");
        sortOrderLabel.setLayoutX(200);
        sortOrderLabel.setLayoutY(45);
        root.getChildren().add(sortOrderLabel);

        sortOrderComboBox.setLayoutX(200);
        sortOrderComboBox.setLayoutY(70);
        sortOrderComboBox.getItems().addAll("Ascending", "Descending");
        sortOrderComboBox.setValue("Ascending");
        root.getChildren().add(sortOrderComboBox);

        listView.setLayoutX(50);
        listView.setLayoutY(109);
        listView.setPrefSize(711, 424);
        root.getChildren().add(listView);

        // Add listener to search text field
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            populateListView(newValue); // Populate list view with filtered data
        });

        // Add listener to sort by combo box
        sortByComboBox.setOnAction(event -> {
            populateListView(searchTF.getText()); // Populate list view with sorted data
        });

        // Add listener to sort order combo box
        sortOrderComboBox.setOnAction(event -> {
            populateListView(searchTF.getText()); // Populate list view with sorted data
        });

        populateListView(searchTF.getText()); // Populate list view with all data
        // initially

        Button firstPageButton = new Button("First Page");
        firstPageButton.setOnAction(event -> goToFirstPage());
        AnchorPane.setTopAnchor(listView, null);
        AnchorPane.setLeftAnchor(firstPageButton, 100.0);
        firstPageButton.setLayoutY(540);
        root.getChildren().add(firstPageButton);

        Button previousPageButton = new Button("Previous Page");
        previousPageButton.setOnAction(event -> goToPreviousPage());
        AnchorPane.setTopAnchor(listView, null);
        AnchorPane.setLeftAnchor(previousPageButton, 220.0);
        previousPageButton.setLayoutY(540);
        root.getChildren().add(previousPageButton);

        Button nextPageButton = new Button("Next Page");
        nextPageButton.setOnAction(event -> goToNextPage());
        AnchorPane.setTopAnchor(listView, null);
        AnchorPane.setRightAnchor(nextPageButton, 220.0);
        nextPageButton.setLayoutY(540);
        root.getChildren().add(nextPageButton);

        Button lastPageButton = new Button("Last Page");
        lastPageButton.setOnAction(event -> goToLastPage());
        AnchorPane.setTopAnchor(listView, null);
        AnchorPane.setRightAnchor(lastPageButton, 100.0);
        lastPageButton.setLayoutY(540);
        root.getChildren().add(lastPageButton);

        bookingBtn = new Button("Bookings");
        bookingBtn.setOnAction(event -> switchToBookingsScene());
        bookingBtn.setLayoutX(401.5);
        bookingBtn.setLayoutY(560);
        root.getChildren().add(bookingBtn);

        return root;
    }

    public void populateListView(String searchQuery) {
        successView = new UserView();
        try {
            ArrayList<Object> carsDataObjects = carsModel.getData();
            ObservableList<String> items = FXCollections.observableArrayList();

            // Convert ArrayList<Object> to ArrayList<Car>
            ArrayList<Car> carsData = new ArrayList<>();
            for (Object obj : carsDataObjects) {
                if (obj instanceof Car) {
                    carsData.add((Car) obj);
                }
            }

            // Filter and sort cars based on the search query
            List<Car> filteredCars = carsData.stream()
                    .filter(car -> car.getManufacturer().toLowerCase().contains(searchQuery.toLowerCase())
                            || car.getModel().toLowerCase().contains(searchQuery.toLowerCase()))
                    .sorted(getComparator(sortByComboBox.getValue(), sortOrderComboBox.getValue()))
                    .collect(Collectors.toList());

            // Paginate the search results
            int startIndex = (currentPage - 1) * ITEMS_PER_PAGE; // Start index for the current page
            int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, filteredCars.size()); // End index for the current page

            for (int i = startIndex; i < endIndex; i++) {
                Car car = filteredCars.get(i);
                items.add(car.toString());
            }

            listView.setItems(items);
        } catch (DLExeption e) {
            e.printStackTrace();
        }
    }

    private Comparator<Car> getComparator(String sortBy, String sortOrder) {
        Comparator<Car> comparator;
        switch (sortBy) {
            case "Manufacturer":
                comparator = Comparator.comparing(Car::getManufacturer);
                break;
            case "Model":
                comparator = Comparator.comparing(Car::getModel);
                break;
            case "Power":
                comparator = Comparator.comparingInt(Car::getPower);
                break;
            case "Year of Production":
                comparator = Comparator.comparing(Car::getYear_of_Production);
                break;
            case "Mileage":
                comparator = Comparator.comparingInt(Car::getMileage);
                break;
            case "Status":
                comparator = Comparator.comparing(Car::getStatus);
                break;
            default:
                comparator = Comparator.comparing(Car::getManufacturer); // Default to
                break;
        }

        if (sortOrder.equals("Descending")) {
            comparator = comparator.reversed(); // Reverse the comparator if descending
        }

        return comparator;
    }

    private void goToFirstPage() {
        currentPage = 1;
        populateListView(searchTF.getText());
    }

    private void goToPreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            populateListView(searchTF.getText());
        }
    }

    private void goToNextPage() {
        try {
            ArrayList<Object> carsDataObjects = carsModel.getData();
            ArrayList<Car> carsData = new ArrayList<>();

            // Perform type cast from ArrayList<Object> to ArrayList<Car>
            for (Object obj : carsDataObjects) {
                if (obj instanceof Car) {
                    carsData.add((Car) obj);
                }
            }

            int maxPage = (int) Math.ceil((double) carsData.size() / ITEMS_PER_PAGE);

            if (currentPage < maxPage) {
                currentPage++;
                populateListView(searchTF.getText());
            }
        } catch (DLExeption e) {
            e.printStackTrace();
        }
    }

    private void goToLastPage() {
        try {
            ArrayList<Object> carsDataObjects = carsModel.getData();
            ArrayList<Car> carsData = new ArrayList<>();

            // Perform type cast from ArrayList<Object> to ArrayList<Car>
            for (Object obj : carsDataObjects) {
                if (obj instanceof Car) {
                    carsData.add((Car) obj);
                }
            }

            int maxPage = (int) Math.ceil((double) carsData.size() / ITEMS_PER_PAGE);
            currentPage = maxPage;
            populateListView(searchTF.getText());
        } catch (DLExeption e) {
            e.printStackTrace();
        }
    }

    public void switchToBookingsScene() {
        VBox vbox = new VBox(10);
        ListView<String> bookingsListView = new ListView<>();
        Button backButton = new Button("Back");

        backButton.setOnAction(event -> {
            // Go back to the main user view scene
            primaryStage.setScene(scene);
        });

        vbox.getChildren().addAll(bookingsListView, backButton);

        try {
            // Get bookings data and populate the bookingsListView
            ArrayList<Object> bookingsDataObjects = bookingModel.getData(); // Assuming bookingsModel is defined
            ObservableList<String> items = FXCollections.observableArrayList();
            System.out.println(bookingsDataObjects);
            for (Object obj : bookingsDataObjects) {
                if (obj instanceof Booking) {
                    Booking booking = (Booking) obj;
                    items.add(booking.toString());
                }
            }

            bookingsListView.setItems(items);
        } catch (DLExeption e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load bookings data.");
            alert.showAndWait();
        }

        Scene bookingsScene = new Scene(vbox, 400, 300);
        primaryStage.setScene(bookingsScene);
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

    public Cars getCarsModel() {
        return carsModel;
    }

    public void setCarsModel(Cars carsModel) {
        this.carsModel = carsModel;
    }

    public Bookings getBookingModel() {
        return bookingModel;
    }

    public void setBookingModel(Bookings bookingModel) {
        this.bookingModel = bookingModel;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Button getBookingBtn() {
        return bookingBtn;
    }

    public void setBookingBtn(Button bookingBtn) {
        this.bookingBtn = bookingBtn;
    }

    public UserView getSuccessView() {
        return successView;
    }

    public void setSuccessView(UserView successView) {
        this.successView = successView;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

}
