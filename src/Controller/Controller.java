package Controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import ConnectivityLayers.DLException;
import ConnectivityLayers.MySQLDatabase;
import Models.Booking;
import Models.Bookings;
import Models.Car;
import Models.Cars;
import Models.Feedbacks;
import Models.Insurance;
import Models.Insurances;
import Models.Payment;
import Models.Payments;
import Models.User;
import Models.Users;
import View.View;
import View.AdminView;
import View.SuccessView; // Import the SuccessView class
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller implements EventHandler<ActionEvent> {
    private View view;
    private Cars carsModel;
    private Bookings bookingsModel;

    private Users userModel;
    private Stage primaryStage; // We need access to the primary stage for view switching
    private User currentUser;
    private SuccessView successView = new SuccessView();
    private AdminView adminView;
    private MySQLDatabase database = new MySQLDatabase("root", "ritcroatia");
    private Insurances insuranceModel;
    private Payments paymentModel;
    private Feedbacks feedbackModel;

    public Bookings getBookingsModel() {
        return bookingsModel;
    }

    public SuccessView getSuccessView() {
        return successView;
    }

    public Controller(View view, Cars carsModel, Bookings bookingModel, Users userModel, Insurances insuranceModel,
            Payments paymentModel, Feedbacks feedbackModel,
            Stage primaryStage)
            throws DLException {
        this.view = view;
        this.carsModel = carsModel;
        this.bookingsModel = bookingModel;
        this.userModel = userModel;
        this.primaryStage = primaryStage;
        this.insuranceModel = insuranceModel;
        this.paymentModel = paymentModel;
        this.feedbackModel = feedbackModel;
        HandleLogin login = new HandleLogin(this, view, userModel);
        this.view.handleLogin(login);
        // this.view.handleRegister(register);
        HandleRegister register = new HandleRegister(this, view, userModel);
        this.view.handleRegister(register);
        // ArrayList<Object> cars = carsModel.getData();
    }

    @Override
    public void handle(ActionEvent event) {
        // Handle other events if needed
    }

    // Method to switch to the success view
    public void switchToSuccessView(User user) {
        currentUser = user;
        successView.start(primaryStage);
        // private HandleFeedback feedbackHandler = new HandleFeedback(successView,
        // null)
        HandleFeedback feedbackHandler = new HandleFeedback(successView, feedbackModel, currentUser);
        this.successView.HandleFeedback(feedbackHandler);
        this.successView.getSortByComboBox().setValue("Manufacturer");
        this.successView.getSortOrderComboBox().setValue("Ascending");
        this.successView.getFirstPageButton().setOnAction(e -> this.goToFirstPage());
        this.successView.getLastPageButton().setOnAction(e -> this.goToLastPage());
        this.successView.getPreviousPageButton().setOnAction(e -> this.goToPreviousPage());
        this.successView.getNextPageButton().setOnAction(e -> this.goToNextPage());
        this.successView.getSortOrderComboBox()
                .setOnAction(e -> this.populateListView(this.successView.getSearchTF().getText()));
        this.successView.getSortByComboBox()
                .setOnAction(e -> this.populateListView(this.successView.getSearchTF().getText()));
        this.successView.getShowAllCarsCheckBox()
                .setOnAction(e -> this.populateListView(this.successView.getSearchTF().getText()));
        this.successView.getSearchTF().textProperty().addListener((observable, oldValue, newValue) -> {
            populateListView(newValue);
        });
        this.successView.getBookingBtn().setOnAction(event -> this.switchToBookingsScene());
        this.successView.getBookACar().setOnAction(e -> this.bookACar());
        Platform.runLater(() -> {
            this.populateListView(this.successView.getSearchTF().getText());
        });
    }

    public void switchToAdminView(User user) {
        currentUser = user;
        adminView.start(primaryStage);
        this.adminView.getCarIDCol().setCellValueFactory(new PropertyValueFactory<>("carID"));
        this.adminView.getManufacturerCol().setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        this.adminView.getModelCol().setCellValueFactory(new PropertyValueFactory<>("model"));
        this.adminView.getPowerCol().setCellValueFactory(new PropertyValueFactory<>("power"));
        this.adminView.getYearCol().setCellValueFactory(new PropertyValueFactory<>("year_of_Production"));
        this.adminView.getMileageCol().setCellValueFactory(new PropertyValueFactory<>("mileage"));
        this.adminView.getStatusCol().setCellValueFactory(new PropertyValueFactory<>("status"));
        this.adminView.getPriceCol().setCellValueFactory(new PropertyValueFactory<>("price"));
        this.adminView.getAddButton().setOnAction(event -> this.addCar());
        this.adminView.getDeleteButton().setOnAction(event -> this.deleteCar());
        this.adminView.getModifyButton().setOnAction(event -> this.modifyCar());
        Platform.runLater(() -> {
            this.refreshTable();
        });
    }

    private static final int ITEMS_PER_PAGE = 7;
    private int currentPage = 1;

    public void populateListView(String searchQuery) {

        this.successView.getListView().getItems().clear();

        try {
            ArrayList<Object> carsDataObjects = carsModel.getData();
            ObservableList<String> items = FXCollections.observableArrayList();

            ArrayList<Car> carsData = new ArrayList<>();
            for (Object obj : carsDataObjects) {
                if (obj instanceof Car) {
                    carsData.add((Car) obj);
                }
            }

            List<Car> filteredCars = carsData.stream()
                    .filter(car -> car.getManufacturer().toLowerCase().contains(searchQuery.toLowerCase())
                            || car.getModel().toLowerCase().contains(searchQuery.toLowerCase()))
                    .filter(car -> car.getStatus().equals("1")) // This filters only available cars
                    .sorted(getComparator(this.successView.getSortByComboBox().getValue(),
                            this.successView.getSortOrderComboBox().getValue()))
                    .collect(Collectors.toList());

            if (this.successView.getShowAllCarsCheckBox().isSelected()) {
                List<Car> unavailableCars = carsData.stream()
                        .filter(car -> car.getManufacturer().toLowerCase().contains(searchQuery.toLowerCase())
                                || car.getModel().toLowerCase().contains(searchQuery.toLowerCase()))
                        .filter(car -> !car.getStatus().equals("1"))
                        .sorted(getComparator(this.successView.getSortByComboBox().getValue(),
                                this.successView.getSortOrderComboBox().getValue()))
                        .collect(Collectors.toList());

                filteredCars.addAll(unavailableCars);
            }

            // Paginate the search results
            int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
            int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, filteredCars.size());

            for (int i = startIndex; i < endIndex; i++) {
                Car car = filteredCars.get(i);
                items.add(car.toString());
            }

            this.successView.getListView().setItems(items);
        } catch (DLException e) {
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
            case "Price":
                comparator = Comparator.comparing(Car::getPrice);
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
        populateListView(this.successView.getSearchTF().getText());
    }

    private void goToPreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            populateListView(this.successView.getSearchTF().getText());
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
                populateListView(this.successView.getSearchTF().getText());
            }
        } catch (DLException e) {
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
            populateListView(this.successView.getSearchTF().getText());
        } catch (DLException e) {
            e.printStackTrace();
        }
    }

    ListView<String> bookingsListView;

    private void loadPastBookings() {
        ArrayList<Object> bookings;
        ObservableList<String> items = FXCollections.observableArrayList();
        try {
            bookings = bookingsModel.getAllBookings();
            ArrayList<Object> cars = carsModel.getData();
            LocalDate date = LocalDate.now();

            int currentUserId = currentUser.getUserID();
            for (Object object : bookings) {
                if (object instanceof Booking) {
                    Booking booking = (Booking) object;
                    if (booking.getUserID() == currentUserId) {
                        LocalDate dateToCompare = booking.getTimeBooked().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        for (Object carObj : cars) {
                            Car car = (Car) carObj;
                            if (car.getCarID() == booking.getCarID()) {
                                if (dateToCompare.isBefore(date)) {

                                    items.add(booking.toString());
                                }
                            }
                            bookingsListView.setItems(items);
                        }
                    }
                }
            }
        } catch (DLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load bookings data.");
            alert.showAndWait();
        }
    }

    // Method to load current bookings
    private void loadCurrentBookings() {
        ArrayList<Object> bookings;
        ObservableList<String> items = FXCollections.observableArrayList();
        try {
            bookings = bookingsModel.getData();
            ArrayList<Object> cars = carsModel.getData();
            LocalDate date = LocalDate.now();
            for (Object object : bookings) {
                Booking booking = (Booking) object;
                LocalDate dateToCompare = booking.getTimeBooked().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate();
                for (Object carObj : cars) {
                    Car car = (Car) carObj;
                    if (car.getCarID() == booking.getCarID()) {
                        if (dateToCompare.isBefore(date)) {
                            carsModel.modifyCar(car.getCarID(), car.getMileage(), "1");
                            bookingsModel.remove(booking.getBookingID());
                        } else {
                            items.add(booking.toString());
                        }
                    }
                }
            }
            bookingsListView.setItems(items);
        } catch (DLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load bookings data.");
            alert.showAndWait();
        }
    }

    public void switchToBookingsScene() {
        VBox vbox = new VBox(10);
        bookingsListView = new ListView<>();
        Button backButton = new Button("Back");
        Button removeBooking = new Button("Cancel booking");
        Button modifyBooking = new Button("Modify booking");
        Button checkPaymentAndInsurance = new Button("Check Insurance and Payment method");
        backButton.setOnAction(event -> {
            // Go back to the main user view scene
            primaryStage.setScene(this.successView.getScene());
        });
        CheckBox showPastCheckBox = new CheckBox("Show past bookings");
        showPastCheckBox.setSelected(false); // Ensure checkbox is initially unchecked

        // Define methods to load past and current bookings
        Runnable loadPastBookings = () -> {
            loadPastBookings();
        };

        Runnable loadCurrentBookings = () -> {
            loadCurrentBookings();
        };

        checkPaymentAndInsurance.setOnAction(event -> {
            // Get the selected booking
            String selectedBooking = bookingsListView.getSelectionModel().getSelectedItem();
            if (selectedBooking != null) {
                int bookingID = extractBookingID(selectedBooking); // Implement a method to extract booking ID from the
                                                                   // string
                try {
                    // Get the carID associated with the selected booking
                    Car selectedCar = getCarByBookingID(bookingID); // Implement this method in your Bookings class

                    Insurance insurance = insuranceModel.getInsuranceForCar(selectedCar.getCarID()); // Implement this
                                                                                                     // method in your
                                                                                                     // Insurances

                    Payment payment = paymentModel.getPaymentForBooking(currentUser.getUserID(),
                            selectedCar.getCarID()); // Implement this method in your

                    // Show the insurance and payment information in a popup window
                    showInsuranceAndPaymentInfo(insurance, payment);
                } catch (DLException e) {
                    // Handle exception
                    e.printStackTrace();
                }
            } else {
                // No booking is selected, display a message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please select a booking to view insurance and payment information.");
                alert.showAndWait();
            }
        });

        // Event handler for the checkbox
        showPastCheckBox.setOnAction(event -> {
            ArrayList<Object> bookings;
            ObservableList<String> items = FXCollections.observableArrayList();
            if (showPastCheckBox.isSelected()) {
                loadPastBookings.run();
            } else {
                loadCurrentBookings.run();
            }
        });

        removeBooking.setOnAction(event -> removeBooking());
        modifyBooking.setOnAction(event -> modifyBooking());
        vbox.getChildren().addAll(bookingsListView, backButton, removeBooking, modifyBooking, checkPaymentAndInsurance,
                showPastCheckBox);

        // Initially load current bookings
        loadCurrentBookings.run();

        Scene bookingsScene = new Scene(vbox, 400, 300);
        primaryStage.setScene(bookingsScene);
    }

    private void showInsuranceAndPaymentInfo(Insurance insurance, Payment payment) {
        // Create a new Stage for the popup window
        Stage popupStage = new Stage();
        popupStage.setTitle("Insurance and Payment Information");

        // Create labels to display insurance and payment details
        Label insuranceLabel = new Label("Insurance Information:");
        Label paymentLabel = new Label("Payment Information:");

        VBox vbox = new VBox(10);
        vbox.getChildren().add(insuranceLabel);

        if (insurance != null) {
            // If insurance is available, display its details
            Label insuranceModelLabel = new Label("Insurance Model: " + insurance.getInsuranceModel());
            Label carIDLabel = new Label("Car ID: " + insurance.getCarID());
            vbox.getChildren().addAll(insuranceModelLabel, carIDLabel);
        } else {
            // Display a message if no insurance information is found
            Label noInsuranceLabel = new Label("No insurance information found for this booking.");
            vbox.getChildren().add(noInsuranceLabel);
        }

        if (payment != null) {
            // If payment is available, display its details
            paymentLabel.setVisible(true);
            Label amountLabel = new Label("Amount: $" + payment.getAmount());
            Label methodLabel = new Label("Payment Method: " + payment.getMethod());
            Label cardTypeLabel = new Label("Card Type: " + payment.getCardType());
            Label timestampLabel = new Label("Timestamp: " + payment.getTimestamp());
            vbox.getChildren().addAll(paymentLabel, amountLabel, methodLabel, cardTypeLabel, timestampLabel);
        } else {
            // Display a message if no payment information is found
            paymentLabel.setVisible(false);
            Label noPaymentLabel = new Label("No payment information found for this booking.");
            vbox.getChildren().add(noPaymentLabel);
        }

        // Create a Scene with the VBox
        Scene scene = new Scene(vbox, 400, 200);

        // Set the Scene to the Stage and show the Stage
        popupStage.setScene(scene);
        popupStage.show();
    }

    public void bookACar() {
        Car selectedCar = getSelectedCar();
        if (selectedCar != null) {
            Stage bookingStage = new Stage();
            bookingStage.setTitle("Book a Car");

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
                LocalDate bookingDate = bookingDatePicker.getValue();
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                Timestamp bookingTimestamp = Timestamp.valueOf(bookingDate.atStartOfDay()); // Convert LocalDate
                bookingTimestamp.setHours(currentTime.getHours());
                bookingTimestamp.setMinutes(currentTime.getMinutes());
                bookingTimestamp.setSeconds(currentTime.getSeconds());
                try {
                    database.connect();
                    database.startTrans();
                    // to Timestamp
                    Booking booking = new Booking(currentUser.getUserID(), selectedCar.getCarID(),
                            bookingTimestamp);
                    bookingsModel.setData(booking);
                    // Commit the transaction after successful booking
                    // No transaction commit here; it will be committed after payment processing
                    bookingStage.close(); // Close the booking popup stage

                    // Display payment popup after booking
                    displayPaymentPopup(selectedCar, database); // Pass database connection for the same transaction

                } catch (DLException e) {
                    System.out.println("Booking failed.");
                    e.printStackTrace();
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
            bookingStage.setScene(scene);

            // Show the booking popup stage
            bookingStage.show();
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
        String selectedItem = this.successView.getListView().getSelectionModel().getSelectedItem();
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
                                this.successView.getListView().getSelectionModel().clearSelection();
                                return null; // Return null to indicate that the car is not available
                            }
                        }
                    }
                }
            } catch (DLException e) {
                // Handle exception
                e.printStackTrace();
            }
        }
        return null;
    }

    public void refreshTable() {
        try {
            ArrayList<Object> carsDataObjects = carsModel.getData();
            ArrayList<Car> carsData = new ArrayList<>();

            // Convert ArrayList<Object> to ArrayList<Car>
            for (Object obj : carsDataObjects) {
                if (obj instanceof Car) {
                    carsData.add((Car) obj);
                }
            }

            ObservableList<Car> carsList = FXCollections.observableArrayList(carsData);
            this.adminView.getTableView().setItems(carsList);
        } catch (DLException e) {
            e.printStackTrace();
        }
    }

    public void addCar() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Add New Car");

        // Create labels and text fields for each attribute of the Car class
        Label manufacturerLabel = new Label("Manufacturer:");
        TextField manufacturerField = new TextField();

        Label modelLabel = new Label("Model:");
        TextField modelField = new TextField();

        Label powerLabel = new Label("Power:");
        TextField powerField = new TextField();

        Label yearLabel = new Label("Year of Production:");
        TextField yearField = new TextField();

        Label mileageLabel = new Label("Mileage:");
        TextField mileageField = new TextField();

        Label statusLabel = new Label("Status:");
        TextField statusField = new TextField();

        Label priceLabel = new Label("Price per day:");
        TextField priceField = new TextField();

        // Create a button to confirm adding the new car
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            // Retrieve input values from text fields
            String manufacturer = manufacturerField.getText();
            String model = modelField.getText();
            int power = Integer.parseInt(powerField.getText());
            int year = Integer.parseInt(yearField.getText());
            int mileage = Integer.parseInt(mileageField.getText());
            String status = statusField.getText();
            double price = Double.parseDouble(priceField.getText());

            // Create a new Car object with the input values
            Car newCar = new Car(0, manufacturer, model, power, year, mileage, status, price);

            try {
                // Add the new car to the database
                carsModel.setData(newCar);
                // Refresh the table to display the updated data
                refreshTable();
                // Close the pop-up window
                popupStage.close();
            } catch (DLException e) {
                e.printStackTrace();
            }
        });

        // Create a layout for the pop-up window
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, manufacturerLabel, manufacturerField);
        gridPane.addRow(1, modelLabel, modelField);
        gridPane.addRow(2, powerLabel, powerField);
        gridPane.addRow(3, yearLabel, yearField);
        gridPane.addRow(4, mileageLabel, mileageField);
        gridPane.addRow(5, statusLabel, statusField);
        gridPane.addRow(6, priceLabel, priceField);
        gridPane.addRow(7, addButton);

        // Create the scene and set it to the pop-up window
        Scene scene = new Scene(gridPane, 300, 250);
        popupStage.setScene(scene);
        // Show the pop-up window
        popupStage.show();
    }

    public void deleteCar() {
        Car selectedCar = this.adminView.getTableView().getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            try {
                carsModel.remove(selectedCar.getCarID());
                refreshTable();
            } catch (DLException e) {
                e.printStackTrace();
            }
        } else {
            // Show an error message or alert dialog
        }
    }

    public void modifyCar() {
        Car selectedCar = this.adminView.getTableView().getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            // Create a pop-up window for modifying the car details
            Stage popupStage = new Stage();
            popupStage.setTitle("Modify Car Details");

            // Create labels and text fields for each attribute of the Car class
            Label manufacturerLabel = new Label("Manufacturer:");
            TextField manufacturerField = new TextField(selectedCar.getManufacturer());
            manufacturerField.setEditable(false);

            Label modelLabel = new Label("Model:");
            TextField modelField = new TextField(selectedCar.getModel());
            modelField.setEditable(false);
            Label powerLabel = new Label("Power:");
            TextField powerField = new TextField(String.valueOf(selectedCar.getPower()));
            powerField.setEditable(false);
            Label yearLabel = new Label("Year of Production:");
            TextField yearField = new TextField(String.valueOf(selectedCar.getYear_of_Production()));
            yearField.setEditable(false);
            Label mileageLabel = new Label("Mileage:");
            TextField mileageField = new TextField(String.valueOf(selectedCar.getMileage()));
            mileageField.setEditable(true);
            Label statusLabel = new Label("Status:");
            TextField statusField = new TextField(selectedCar.getStatus());
            statusField.setEditable(true);
            Label priceLabel = new Label("Price per day: ");
            TextField priceField = new TextField(String.valueOf(selectedCar.getPrice()));
            priceField.setEditable(false);
            // Create a button to confirm the modifications
            Button modifyButton = new Button("Modify");
            modifyButton.setOnAction(event -> {
                // Retrieve updated values from text fields
                String manufacturer = manufacturerField.getText();
                String model = modelField.getText();
                int power = Integer.parseInt(powerField.getText());
                int year = Integer.parseInt(yearField.getText());
                int mileage = Integer.parseInt(mileageField.getText());
                String status = statusField.getText();
                double price = Double.parseDouble(priceField.getText());

                // Update the selected car object with the new values
                selectedCar.setManufacturer(manufacturer);
                selectedCar.setModel(model);
                selectedCar.setPower(power);
                selectedCar.setYear_of_Production(year);
                selectedCar.setMileage(mileage);
                selectedCar.setStatus(status);
                selectedCar.setPrice(price);

                try {
                    // Update the car details in the database
                    carsModel.modifyCar(selectedCar.getCarID(), selectedCar.getMileage(),
                            selectedCar.getStatus());
                    // Refresh the table to display the updated data
                    refreshTable();
                    // Close the pop-up window
                    popupStage.close();
                } catch (DLException e) {
                    e.printStackTrace();
                }
            });

            // Create a layout for the pop-up window
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.addRow(0, manufacturerLabel, manufacturerField);
            gridPane.addRow(1, modelLabel, modelField);
            gridPane.addRow(2, powerLabel, powerField);
            gridPane.addRow(3, yearLabel, yearField);
            gridPane.addRow(4, mileageLabel, mileageField);
            gridPane.addRow(5, statusLabel, statusField);
            gridPane.addRow(6, priceLabel, priceField);
            gridPane.addRow(7, modifyButton);

            // Create the scene and set it to the pop-up window
            Scene scene = new Scene(gridPane, 300, 250);
            popupStage.setScene(scene);
            // Show the pop-up window
            popupStage.show();
        } else {
            // Show an error message or alert dialog if no car is selected
        }
    }

    public void removeBooking() {
        String selectedBookingString = this.bookingsListView.getSelectionModel().getSelectedItem();
        if (selectedBookingString != null) {
            try {
                int bookingID = extractBookingID(selectedBookingString);
                bookingsModel.remove(bookingID);

                Car car = getCarByBookingID(bookingID);
                if (car != null) {
                    car.setStatus("1");
                    carsModel.modifyCar(car.getCarID(), car.getMileage(), car.getStatus());
                }

                populateListView(this.successView.getSearchTF().getText());
                switchToBookingsScene();
            } catch (DLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No Booking Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a booking to remove.");
            alert.showAndWait();
        }
    }

    public void modifyBooking() {
        String selectedBookingString = this.bookingsListView.getSelectionModel().getSelectedItem();
        if (selectedBookingString != null) {
            int bookingID = extractBookingID(selectedBookingString);
            Car car = getCarByBookingID(bookingID);
            Stage popupStage = new Stage();
            popupStage.setTitle("Modify Booking");

            Label label = new Label("Pick a new booking end date: ");
            DatePicker datePicker = new DatePicker();

            VBox vbox = new VBox(label, datePicker);

            Scene scene = new Scene(vbox, 300, 200);

            popupStage.setScene(scene);

            popupStage.show();

            datePicker.setOnAction(event -> {
                LocalDate selectedDate = datePicker.getValue();
                Timestamp timestamp = Timestamp.valueOf(selectedDate.atStartOfDay());
                try {
                    bookingsModel.modify(bookingID, car.getCarID(), timestamp);

                    switchToBookingsScene();

                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("Booking Modification");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Booking successfully modified.");
                    successAlert.showAndWait();
                } catch (DLException e) {
                    e.printStackTrace();

                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Booking Modification Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Failed to modify the booking. Please try again.");
                    errorAlert.showAndWait();
                }
                popupStage.close();
            });

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No Booking Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a booking to modify.");
            alert.showAndWait();
        }
    }

    private int extractBookingID(String bookingString) {
        String[] parts = bookingString.split(":");
        String[] bookID = parts[1].split(",");
        return Integer.parseInt(bookID[0].trim());
    }

    private Car getCarByBookingID(int bookingID) {
        try {
            Booking booking = bookingsModel.getBookingByID(bookingID);
            if (booking != null) {
                int carID = booking.getCarID();
                return carsModel.getCarByID(carID);
            }
        } catch (DLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.matches("^\\d{16}$");
    }

    // private void processCardPayment(Car selectedCar, String cardNumber, String
    // cardType, MySQLDatabase database) {
    // // bookingsModel.bookACar(selectedCar.getCarID(), currentUser.getUserID());
    // // selectedCar.setStatus("0");
    // this.populateListView(this.successView.getSearchTF().getText());
    // }

    // private void processCashPayment(Car selectedCar, MySQLDatabase database) {
    // // bookingsModel.bookACar(selectedCar.getCarID(), currentUser.getUserID());
    // // selectedCar.setStatus("0");
    // this.populateListView(this.successView.getSearchTF().getText());
    // }

    private void processInsuranceSelection(Car selectedCar, String insuranceType, MySQLDatabase database) {
        Insurance insurance = new Insurance(selectedCar.getCarID(), insuranceType);

        try {
            insuranceModel.setData(insurance);
        } catch (DLException e) {
            try {
                database.rollbackTrans();
            } catch (DLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void displayPaymentPopup(Car selectedCar, MySQLDatabase database) {
        Stage paymentStage = new Stage();
        paymentStage.initModality(Modality.APPLICATION_MODAL);
        paymentStage.setTitle("Payment");

        Label paymentMethodLabel = new Label("Select Payment Method:");
        RadioButton cashRadioButton = new RadioButton("Cash");
        RadioButton cardRadioButton = new RadioButton("Card");
        ToggleGroup toggleGroup = new ToggleGroup();
        cashRadioButton.setToggleGroup(toggleGroup);
        cardRadioButton.setToggleGroup(toggleGroup);
        cardRadioButton.setSelected(true);

        Label cardDetailsLabel = new Label("Card Details:");
        TextField cardDetailsField = new TextField();
        cardDetailsField.setPromptText("Enter 16-digit card number");

        Label cardTypeLabel = new Label("Card Type:");
        ComboBox<String> cardTypeComboBox = new ComboBox<>(
                FXCollections.observableArrayList("Visa", "Mastercard", "American Express"));

        Label insuranceLabel = new Label("Insurance type:");
        ComboBox<String> insurance = new ComboBox<>(FXCollections.observableArrayList("Basic", "Premium", "Standard"));

        cashRadioButton.setOnAction(e -> {
            cardDetailsField.setEditable(false);
            cardTypeComboBox.setDisable(true);
            insurance.setDisable(false);
        });
        cardRadioButton.setOnAction(e -> {
            cardDetailsField.setEditable(true);
            cardTypeComboBox.setDisable(false);
            insurance.setDisable(false);
        });
        Button payButton = new Button("Pay");
        double amount = selectedCar.getPrice();

        payButton.setOnAction(e -> {
            try {
                database.startTrans();
            } catch (DLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Define a formatter for the desired date and time format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            // Format the current date and time using the formatter
            String formattedDateTime = currentDateTime.format(formatter);

            // Parse the formatted date and time string into a LocalDateTime
            LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTime, formatter);

            // Convert LocalDateTime to Timestamp
            Timestamp timestamp = Timestamp.valueOf(parsedDateTime);
            String paymentMethod;
            String cardDetails = "";
            if (toggleGroup.getSelectedToggle() == cashRadioButton) {
                paymentMethod = "Cash";
                cardDetails = "0";
                String insuranceType = insurance.getValue();

                Payment payment = new Payment(currentUser.getUserID(), selectedCar.getCarID(), amount,
                        paymentMethod, cardDetails, "Not available", timestamp);
                try {
                    paymentModel.setData(payment);
                } catch (DLException e1) {
                    e1.printStackTrace();
                }
                processInsuranceSelection(selectedCar, insuranceType, database);
                // processCashPayment(selectedCar, database);

            } else if (toggleGroup.getSelectedToggle() == cardRadioButton) {
                paymentMethod = "Card";
                String cardNumber = cardDetailsField.getText();
                String cardType = cardTypeComboBox.getValue();
                String insuranceType = insurance.getValue();
                cardDetails = cardDetailsField.getText();
                if (isValidCardNumber(cardNumber)) {
                    Payment payment = new Payment(currentUser.getUserID(), selectedCar.getCarID(), amount,
                            paymentMethod, cardDetails, cardType, timestamp);
                    try {
                        paymentModel.setData(payment);
                    } catch (DLException e1) {
                        e1.printStackTrace();
                    }
                    processInsuranceSelection(selectedCar, insuranceType, database);
                    // processCardPayment(selectedCar, cardNumber, cardType, database);
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Invalid Card Number");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Please enter a valid 16-digit card number.");
                    errorAlert.showAndWait();
                }
            }
            try {
                database.endTrans();
            } catch (DLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            paymentStage.close();
            populateListView(successView.getSearchTF().getText());
        });

        GridPane paymentLayout = new GridPane();
        paymentLayout.setHgap(10);
        paymentLayout.setVgap(10);
        paymentLayout.addRow(0, paymentMethodLabel);
        paymentLayout.addRow(1, cashRadioButton);
        paymentLayout.addRow(2, cardRadioButton);
        paymentLayout.addRow(3, cardDetailsLabel, cardDetailsField);
        paymentLayout.addRow(4, cardTypeLabel, cardTypeComboBox);
        paymentLayout.addRow(5, insuranceLabel, insurance);
        paymentLayout.addRow(6, payButton);

        Scene paymentScene = new Scene(paymentLayout, 300, 200);
        paymentStage.setScene(paymentScene);

        paymentStage.showAndWait();
    }

    public ListView<String> getBookingsListView() {
        return bookingsListView;
    }

    public void setBookingsListView(ListView<String> bookingsListView) {
        this.bookingsListView = bookingsListView;
    }

}
