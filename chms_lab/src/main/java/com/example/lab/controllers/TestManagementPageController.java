package com.example.lab.controllers;

import com.example.lab.models.Test;
import com.example.lab.services.TestService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TestManagementPageController {

    private TestService testService;

    @FXML
    private TableView<Test> testTable;
    @FXML
    private TableColumn<Test, String> testNameColumn;
    @FXML
    private TableColumn<Test, String> testUnitColumn;
    @FXML
    private TableColumn<Test, Double> testPriceColumn;
    @FXML
    private TableColumn<Test, Double> testMinValueColumn;
    @FXML
    private TableColumn<Test, Double> testMaxValueColumn;
    @FXML
    private TableColumn<Test, String> testDescriptionColumn;
    @FXML
    private TableColumn<Test, Void> actionsColumn;
    @FXML
    private Button addTestButton;

    public void initialize() {
        testService = new TestService();  // Ensure service is initialized here

        // Set the column resize policy to allow automatic resizing
        testTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Set up table columns with appropriate bindings for each column
        testNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        testUnitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        testPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        testMinValueColumn.setCellValueFactory(cellData -> cellData.getValue().minValueProperty().asObject());
        testMaxValueColumn.setCellValueFactory(cellData -> cellData.getValue().maxValueProperty().asObject());
        testDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        // Set up actions column with buttons for each row
        actionsColumn.setCellFactory(col -> new TableCell<Test, Void>() {
            private final Button deleteButton = new Button("Delete");
            private final Button modifyButton = new Button("Modify");

            {
                // Style and set actions for the delete button
                deleteButton.getStyleClass().add("button-delete");
                deleteButton.setOnAction(event -> {
                    Test selectedTest = getTableRow().getItem();
                    if (selectedTest != null) {
                        handleDeleteTest(selectedTest);
                    }
                });

                // Style and set actions for the modify button
                modifyButton.getStyleClass().add("button-modify");
                modifyButton.setOnAction(event -> {
                    Test selectedTest = getTableRow().getItem();
                    if (selectedTest != null) {
                        try {
                            handleModifyTest(selectedTest);  // Wrapped in try-catch
                        } catch (IOException e) {
                            e.printStackTrace();
                            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load Modify Test form.");
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Add both buttons in an HBox and center them
                    HBox hbox = new HBox(10, deleteButton, modifyButton);  // Add buttons next to each other
                    hbox.setAlignment(javafx.geometry.Pos.CENTER);  // Center the buttons
                    setGraphic(hbox);
                }
            }
        });

        // Load the tests from the database
        loadTests();
    }

    private void loadTests() {
        List<Test> tests = testService.getAllTests();  // Retrieve tests from service
        testTable.getItems().clear();
        testTable.getItems().addAll(tests);
    }

    @FXML
    private void handleDeleteTest(Test selectedTest) {
        if (selectedTest != null) {
            // Delete the test from the database
            testService.deleteTest(selectedTest);
            loadTests();  // Reload the tests after deletion
            showAlert(Alert.AlertType.INFORMATION, "Test Deleted", "Test has been deleted successfully.");
        }
    }

    @FXML
    private void handleOrdonnanceTests(ActionEvent event) {
        // Directly navigate to the LabTechnicianPageController
        loadPage("/com/example/lab/views/LabTechnicianPage.fxml", event);
    }

    @FXML
    private void handleAddTest() {
        try {
            // Open the "Add Test" form in a new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab/views/AddTestForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add New Test");
            stage.setScene(new Scene(root));
            stage.show();

            // Reload the table after adding the test
            stage.setOnHiding(e -> loadTests()); // Refresh table after the add form closes

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load Add Test form.");
        }
    }

    @FXML
    private void handleModifyTest(Test selectedTest) throws IOException {
        if (selectedTest != null) {
            // Open the "Modify Test" form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab/views/ModifyTestForm.fxml"));
            Parent root = loader.load();

            // Get the controller and set the selected test
            ModifyTestFormController controller = loader.getController();
            controller.setTest(selectedTest);

            Stage stage = new Stage();
            stage.setTitle("Modify Test");
            stage.setScene(new Scene(root));
            stage.show();

            // Reload the table after modifying the test
            stage.setOnHiding(e -> loadTests()); // Refresh table after the modify form closes
        }
    }

    private void loadPage(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Set up the scene
            Scene scene = new Scene(root, 1100, 700);
            scene.getStylesheets().add(getClass().getResource("/com/example/lab/styles/styles.css").toExternalForm());

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Page Load Error", "Unable to load page: " + fxmlPath);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
