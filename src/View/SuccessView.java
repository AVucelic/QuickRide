package View;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ConnectivityLayers.DLExeption;
import Models.Car;
import Models.Cars;

public class SuccessView extends Application {

    private ListView<String> listView = new ListView<>();
    private TextField searchTF = new TextField();
    private ComboBox<String> sortByComboBox = new ComboBox<>();
    private ComboBox<String> sortOrderComboBox = new ComboBox<>();
    private Cars carsModel;

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

    public SuccessView() {

    }

    @Override
    public void start(Stage primaryStage) {

        carsModel = new Cars(); // Instantiate the carsModel here

        primaryStage.setTitle("Success View Application");

        AnchorPane root = createContent();
        Scene scene = new Scene(root, 803, 599);
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

        return root;
    }

    public void populateListView(String searchQuery) {
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

}
