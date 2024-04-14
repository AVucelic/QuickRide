package View;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Timestamp;
import java.time.LocalDate;
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
    private CheckBox showAllCarsCheckBox; // Add CheckBox field
    private Button bookACar;

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
        bookingModel = new Bookings(getUserID());

        primaryStage.setTitle("QuickRide");

        AnchorPane root = createContent();
        scene = new Scene(root, 803, 599);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public AnchorPane createContent() {
        showAllCarsCheckBox = new CheckBox("Show all cars");

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
        bookingBtn.setLayoutX(368);
        bookingBtn.setLayoutY(540);
        root.getChildren().add(bookingBtn);
        bookACar = new Button("Book a car");
        bookACar.setOnAction(event -> bookACar());
        bookACar.setLayoutX(364);
        bookACar.setLayoutY(575);
        root.getChildren().add(bookACar);
        showAllCarsCheckBox.setOnAction(event -> populateListView(searchTF.getText()));
        showAllCarsCheckBox.setLayoutX(320);
        showAllCarsCheckBox.setLayoutY(70);
        root.getChildren().add(showAllCarsCheckBox);
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

            // Filter cars based on the search query and availability
            List<Car> filteredCars = carsData.stream()
                    .filter(car -> car.getManufacturer().toLowerCase().contains(searchQuery.toLowerCase())
                            || car.getModel().toLowerCase().contains(searchQuery.toLowerCase()))
                    .filter(car -> car.getStatus().equals("1")) // Show only available cars by default
                    .sorted(getComparator(sortByComboBox.getValue(), sortOrderComboBox.getValue()))
                    .collect(Collectors.toList());

            // If the checkbox is selected, include unavailable cars
            if (showAllCarsCheckBox.isSelected()) {
                List<Car> unavailableCars = carsData.stream()
                        .filter(car -> car.getManufacturer().toLowerCase().contains(searchQuery.toLowerCase())
                                || car.getModel().toLowerCase().contains(searchQuery.toLowerCase()))
                        .filter(car -> !car.getStatus().equals("1")) // Exclude available cars
                        .sorted(getComparator(sortByComboBox.getValue(), sortOrderComboBox.getValue()))
                        .collect(Collectors.toList());

                filteredCars.addAll(unavailableCars); // Add unavailable cars to the list
            }

            // Paginate the search results
            int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
            int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, filteredCars.size());

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

    public void bookACar() {
        Car selectedCar = getSelectedCar();
        if (getSelectedCar() != null) {
            Stage popupStage = new Stage();
            popupStage.setTitle("Book a car");

            // Create grid pane to organize UI elements
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            // Labels
            Label carIDLabel = new Label("Car ID:");
            Label bookingTimeLabel = new Label("Booking Time:");

            // Text fields
            TextField carIDTextField = new TextField(String.valueOf(selectedCar.getCarID()));
            carIDTextField.setEditable(false);

            DatePicker bookingDatePicker = new DatePicker(); // DatePicker for selecting booking date
            // Set the prompt text for clarity
            bookingDatePicker.setPromptText("Select Booking Date");

            // Button
            Button bookButton = new Button("Book");
            bookButton.setOnAction(event -> {
                // Implement booking logic here
                int carID = Integer.parseInt(carIDTextField.getText());
                LocalDate bookingDate = bookingDatePicker.getValue();
                if (bookingDate != null) {
                    // Convert LocalDate to Timestamp
                    Timestamp bookingTime = Timestamp.valueOf(bookingDate.atStartOfDay());
                    try {
                        bookingModel.bookACar(carID, getUserID());
                        selectedCar.setStatus("0");
                        // change the text on the list view after car gets booked so it's not available
                        // for booking anymore.
                    } catch (DLExeption e) {
                        System.out.println("Booking failed.");
                        e.printStackTrace();
                    }
                    popupStage.close();
                } else {
                    // Show an alert if no date is selected
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select a booking date.");
                    alert.showAndWait();
                }
            });

            // Add UI elements to the grid pane
            gridPane.add(carIDLabel, 0, 0);
            gridPane.add(carIDTextField, 1, 0);
            gridPane.add(bookingTimeLabel, 0, 1);
            gridPane.add(bookingDatePicker, 1, 1);
            gridPane.add(bookButton, 0, 2);

            // Create scene with the grid pane
            Scene scene = new Scene(gridPane, 300, 150);
            popupStage.setScene(scene);

            // Show the popup stage
            popupStage.show();
        } else {
            // Show an alert indicating that no car is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a car from the list before booking.");
            alert.showAndWait();
        }
    }

    private Car getSelectedCar() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String[] carData = selectedItem.split(","); // Assuming car.toString() returns a comma-separated string
            String manufacturer = carData[0].split(":")[1].trim();
            String model = carData[1].split(":")[1].trim();
            // Iterate over the cars data list to find the matching car
            try {
                for (Object obj : carsModel.getData()) {
                    if (obj instanceof Car) {
                        Car car = (Car) obj;
                        if (car.getManufacturer().equals(manufacturer) && car.getModel().equals(model)) {
                            if (car.isStatus().equals("True")) { // Check if the car is available
                                return car;

                            } else {
                                // Show an alert indicating that the car is not available
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Car Not Available");
                                alert.setHeaderText(null);
                                alert.setContentText("Sorry, this car is currently not available for booking.");
                                alert.showAndWait();
                                listView.getSelectionModel().clearSelection();
                                return null; // Return null to indicate that the car is not available
                            }
                        }
                    }
                }
            } catch (DLExeption e) {
                // Handle exception
                e.printStackTrace();
            }
        }
        return null;
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
