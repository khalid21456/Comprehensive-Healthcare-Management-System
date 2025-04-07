package com.example.lab.controllers;

import com.example.lab.models.OrdonnanceTest;
import com.example.lab.services.OrdonnanceTestService;
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

public class LabTechnicianPageController {

    private OrdonnanceTestService ordonnanceTestService;
    private TestService testService;

    @FXML
    private TableView<OrdonnanceTest> pendingTable;
    @FXML
    private TableColumn<OrdonnanceTest, Integer> pendingIDColumn;
    @FXML
    private TableColumn<OrdonnanceTest, String> pendingDoctorColumn;
    @FXML
    private TableColumn<OrdonnanceTest, String> pendingPatientColumn;
    @FXML
    private TableColumn<OrdonnanceTest, String> pendingDateColumn;
    @FXML
    private TableColumn<OrdonnanceTest, String> pendingStatusColumn;
    @FXML
    private TableColumn<OrdonnanceTest, Void> pendingActionColumn;

    @FXML
    private TableView<OrdonnanceTest> completedTable;
    @FXML
    private TableColumn<OrdonnanceTest, Integer> completedIDColumn;
    @FXML
    private TableColumn<OrdonnanceTest, String> completedDoctorColumn;
    @FXML
    private TableColumn<OrdonnanceTest, String> completedPatientColumn;
    @FXML
    private TableColumn<OrdonnanceTest, String> completedDateColumn;
    @FXML
    private TableColumn<OrdonnanceTest, String> completedStatusColumn;
    @FXML
    private TableColumn<OrdonnanceTest, Void> completedActionColumn;

    public void initialize() {
        ordonnanceTestService = new OrdonnanceTestService();
        testService = new TestService();

        pendingIDColumn.setCellValueFactory(cellData -> cellData.getValue().idOrdonnanceTestProperty().asObject());
        pendingDoctorColumn.setCellValueFactory(cellData -> cellData.getValue().idDoctorProperty().asString());
        pendingPatientColumn.setCellValueFactory(cellData -> cellData.getValue().idPatientProperty().asString());
        pendingDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateOrdonnanceTestProperty());
        pendingStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        completedIDColumn.setCellValueFactory(cellData -> cellData.getValue().idOrdonnanceTestProperty().asObject());
        completedDoctorColumn.setCellValueFactory(cellData -> cellData.getValue().idDoctorProperty().asString());
        completedPatientColumn.setCellValueFactory(cellData -> cellData.getValue().idPatientProperty().asString());
        completedDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateOrdonnanceTestProperty());
        completedStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        pendingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        completedTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        setupActionsColumn(pendingActionColumn, "process");
        setupActionsColumn(completedActionColumn, "view");

        loadPendingTests();
        loadCompletedTests();
    }

    private void setupActionsColumn(TableColumn<OrdonnanceTest, Void> actionColumn, String buttonType) {
        actionColumn.setCellFactory(col -> new TableCell<OrdonnanceTest, Void>() {
            private final Button processButton = new Button("Process");
            private final Button viewButton = new Button("View");

            {
                if ("process".equals(buttonType)) {
                    processButton.getStyleClass().add("button-add");
                    processButton.setOnAction(event -> {
                        OrdonnanceTest selectedTest = getTableRow().getItem();
                        if (selectedTest != null) {
                            handleProcessTest(selectedTest);
                        }
                    });
                    HBox hbox = new HBox(10, processButton);
                    hbox.setAlignment(javafx.geometry.Pos.CENTER);
                    setGraphic(hbox);
                } else if ("view".equals(buttonType)) {
                    viewButton.getStyleClass().add("button-add");
                    viewButton.setOnAction(event -> {
                        OrdonnanceTest selectedTest = getTableRow().getItem();
                        if (selectedTest != null) {
                            handleViewTest(selectedTest);
                        }
                    });
                    HBox hbox = new HBox(10, viewButton);
                    hbox.setAlignment(javafx.geometry.Pos.CENTER);
                    setGraphic(hbox);
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }
            }
        });
    }

    private void loadPendingTests() {
        List<OrdonnanceTest> pendingTests = ordonnanceTestService.getOrdonnanceTestsByStatus("Pending");
        pendingTable.getItems().clear();
        pendingTable.getItems().addAll(pendingTests);
        setupActionsColumn(pendingActionColumn, "process");  // Reapply button setup
    }

    private void loadCompletedTests() {
        List<OrdonnanceTest> completedTests = ordonnanceTestService.getOrdonnanceTestsByStatus("Completed");
        completedTable.getItems().clear();
        completedTable.getItems().addAll(completedTests);
        setupActionsColumn(completedActionColumn, "view");  // Reapply button setup
    }

    @FXML
    private void handleProcessTest(OrdonnanceTest selectedTest) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab/views/ProcessTestForm.fxml"));
            Parent root = loader.load();
            ProcessTestFormController controller = loader.getController();
            controller.initData(selectedTest);
            Stage stage = new Stage();
            stage.setTitle("Process Test Results");
            stage.setScene(new Scene(root));

            // Refresh tables when the process window closes
            stage.setOnHidden(event -> {
                loadPendingTests();
                loadCompletedTests();
            });

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load Process Test form.");
        }
    }

    @FXML
    private void handleViewTest(OrdonnanceTest selectedTest) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab/views/ViewTestResultsForm.fxml"));
            Parent root = loader.load();
            ViewTestResultsFormController controller = loader.getController();
            controller.initData(selectedTest);
            Stage stage = new Stage();
            stage.setTitle("View Test Results");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load View Test form.");
        }
    }

    @FXML
    private void handleTestManagement(ActionEvent event) {
        loadPage("/com/example/lab/views/TestManagementPage.fxml", event);
    }

    private void loadPage(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1100, 700);
            scene.getStylesheets().add(getClass().getResource("/com/example/lab/styles/styles.css").toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load page.");
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
