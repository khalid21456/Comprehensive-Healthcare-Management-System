package com.example.lab.controllers;

import com.example.lab.models.Test;
import com.example.lab.services.TestService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModifyTestFormController {

    private TestService testService = new TestService();
    private Test selectedTest;

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

    public void setTest(Test selectedTest) {
        this.selectedTest = selectedTest;

        // Populate fields with current test data
        testNameField.setText(selectedTest.getName());
        testUnitField.setText(selectedTest.getUnit());
        testPriceField.setText(String.valueOf(selectedTest.getPrice()));
        testMinValueField.setText(String.valueOf(selectedTest.getMinValue()));
        testMaxValueField.setText(String.valueOf(selectedTest.getMaxValue()));
        testDescriptionField.setText(selectedTest.getDescription());
    }

    @FXML
    private void handleSaveChanges() {
        // Update the test object with new data
        selectedTest.setName(testNameField.getText());
        selectedTest.setUnit(testUnitField.getText());
        selectedTest.setPrice(Double.parseDouble(testPriceField.getText()));
        selectedTest.setMinValue(Double.parseDouble(testMinValueField.getText()));
        selectedTest.setMaxValue(Double.parseDouble(testMaxValueField.getText()));
        selectedTest.setDescription(testDescriptionField.getText());

        // Save changes to the database
        testService.updateTest(selectedTest);

        // Close the form
        closeWindow();
    }



    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) testNameField.getScene().getWindow();
        stage.close();
    }
}
