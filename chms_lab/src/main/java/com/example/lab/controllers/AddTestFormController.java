package com.example.lab.controllers;

import com.example.lab.services.TestService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddTestFormController {

    private TestService testService = new TestService();

    @FXML
    private TextField testNameField;
    @FXML
    private TextField testUnitField;
    @FXML
    private TextField testPriceField;
    @FXML
    private TextField testMinValueField;
    @FXML
    private TextField testMaxValueField;
    @FXML
    private TextArea testDescriptionField;

    @FXML
    private void handleAddTest() {
        try {
            // Collect data from the fields
            String name = testNameField.getText();
            String unit = testUnitField.getText();
            double price = parseDouble(testPriceField.getText(), "Price");
            double minValue = parseDouble(testMinValueField.getText(), "Minimum Value");
            double maxValue = parseDouble(testMaxValueField.getText(), "Maximum Value");
            String description = testDescriptionField.getText();

            // Create a new Test object and save it
            testService.addTest(name, minValue, maxValue, price, description, unit);

            // Close the form
            closeWindow();
        } catch (NumberFormatException e) {
            // Handle invalid numeric input
            showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
        }
    }

    private double parseDouble(String value, String fieldName) throws NumberFormatException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please enter a valid number for " + fieldName);
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) testNameField.getScene().getWindow();
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
