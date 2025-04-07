package com.example.lab.controllers;

import com.example.lab.services.TestService;
import com.example.lab.models.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TestController {

    @FXML private TableView<Test> testTable;
    @FXML private TableColumn<Test, String> testNameColumn;
    @FXML private TableColumn<Test, Double> testMinValueColumn;
    @FXML private TableColumn<Test, Double> testMaxValueColumn;
    @FXML private TableColumn<Test, Double> testPriceColumn;
    @FXML private TableColumn<Test, String> testDescriptionColumn;
    @FXML private TableColumn<Test, String> testUnitColumn;

    private ObservableList<Test> testList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize the table columns
        testNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        testMinValueColumn.setCellValueFactory(cellData -> cellData.getValue().minValueProperty().asObject());
        testMaxValueColumn.setCellValueFactory(cellData -> cellData.getValue().maxValueProperty().asObject());
        testPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        testDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        testUnitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());

        // Load tests from the database
        loadTests();
    }

    private void loadTests() {
        // Call service to fetch the tests from the database
        TestService testService = new TestService();
        testList.setAll(testService.getAllTests());
        testTable.setItems(testList);
    }

    @FXML
    private void handleModifyTest() {
        // Logic to modify the selected test
        Test selectedTest = testTable.getSelectionModel().getSelectedItem();
        if (selectedTest != null) {
            // Open modify test form or handle modification
            System.out.println("Modify test: " + selectedTest.getName());
        }
    }

    @FXML
    private void handleDeleteTest() {
        // Logic to delete the selected test
        Test selectedTest = testTable.getSelectionModel().getSelectedItem();
        if (selectedTest != null) {
            // Call service to delete the test
            TestService testService = new TestService();
            testTable.getItems().remove(selectedTest);
            testList.remove(selectedTest); // Remove from table view
        }
    }

    @FXML
    private void handleAddTest() {
        // Logic to show the add test form
        System.out.println("Open form to add a new test");
        // Open the form using a new stage or set visible
    }
}
