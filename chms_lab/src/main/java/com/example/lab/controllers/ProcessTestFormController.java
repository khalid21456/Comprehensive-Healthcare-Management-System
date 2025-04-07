package com.example.lab.controllers;

import com.example.lab.models.OrdonnanceTest;
import com.example.lab.models.Test;
import com.example.lab.models.TestResult;
import com.example.lab.services.TestService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ProcessTestFormController {

    private OrdonnanceTest selectedTest;
    private TestService testService;
    private List<TestResult> testResults = new ArrayList<>();

    @FXML
    private TableView<TestResult> testResultsTable;

    @FXML
    private TableColumn<TestResult, String> testNameColumn;

    @FXML
    private TableColumn<TestResult, String> resultInputColumn;

    @FXML
    private TableColumn<TestResult, String> resultDateColumn;

    @FXML
    private TableColumn<TestResult, String> minValueColumn;

    @FXML
    private TableColumn<TestResult, String> maxValueColumn;

    @FXML
    private Button submitButton;

    // Date format to validate against
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void initialize() {
        testService = new TestService();

        // Configure columns
        testNameColumn.setCellValueFactory(data -> data.getValue().testNameProperty());

        // Only configure result input and result date columns
        resultInputColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        resultInputColumn.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().getResultValue()))
        );
        resultInputColumn.setOnEditCommit(event -> {
            TestResult result = event.getRowValue();
            try {
                double parsedResult = Double.parseDouble(event.getNewValue());
                result.setResultValue(parsedResult);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid number for the test result.");
            }
        });

        resultDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        resultDateColumn.setCellValueFactory(data -> data.getValue().resultDateProperty());
        resultDateColumn.setOnEditCommit(event -> {
            TestResult result = event.getRowValue();
            String newDate = event.getNewValue();
            if (isValidDate(newDate)) {
                result.setResultDate(newDate);
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid Date Format", "Please enter the date in the format yyyy-MM-dd.");
            }
        });

        testResultsTable.setEditable(true);
    }

    // Method to check if the date is valid
    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DATE_FORMATTER);  // Tries to parse the string into a LocalDate
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void initData(OrdonnanceTest selectedTest) {
        this.selectedTest = selectedTest;
        loadTestResultsForm();
    }

    private void loadTestResultsForm() {
        List<Test> tests = testService.getTestsForOrdonnanceTest(selectedTest.getIdOrdonnanceTest());
        testResults.clear();

        for (Test test : tests) {
            TestResult testResult = new TestResult(
                    selectedTest.getIdOrdonnanceTest(),
                    test.getId(),
                    0.0,
                    LocalDate.now().toString() // Set current date as the default
            );
            testResult.setTestName(test.getName());
            testResult.setMinValue(test.getMinValue()); // Set minValue from Test model
            testResult.setMaxValue(test.getMaxValue()); // Set maxValue from Test model
            testResults.add(testResult);
        }

        // Debugging: print test results to ensure data is being loaded correctly
        testResults.forEach(result -> {
            System.out.println("Test: " + result.getTestName() + ", Result: " + result.getResultValue() + ", Date: " + result.getResultDate());
        });

        testResultsTable.getItems().setAll(testResults);
    }

    @FXML
    private void handleSubmitResults() {
        for (TestResult result : testResults) {
            if (result.getResultValue() == 0.0 || result.getResultDate().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing Data", "Please fill in all required fields.");
                return;
            }

            testService.insertOrUpdateTestResult(result);
        }

        // Update ordonnance status to "Completed"
        testService.updateOrdonnanceStatus(selectedTest.getIdOrdonnanceTest(), "Completed");

        showAlert(Alert.AlertType.INFORMATION, "Success", "Test results submitted successfully.");

        closeWindow();
        // Refresh table after submission
        loadTestResultsForm();
    }

    private void closeWindow() {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
