package com.example.lab.controllers;

import com.example.lab.models.OrdonnanceTest;
import com.example.lab.models.TestResult;
import com.example.lab.services.TestService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class ViewTestResultsFormController {

    private OrdonnanceTest selectedTest;
    private TestService testService;

    @FXML
    private TableView<TestResult> testResultsDetailTable;

    @FXML
    private TableColumn<TestResult, String> testNameColumn;

    @FXML
    private TableColumn<TestResult, Double> testValueColumn;

    @FXML
    private TableColumn<TestResult, Double> minValueColumn;

    @FXML
    private TableColumn<TestResult, Double> maxValueColumn;

    @FXML
    private TableColumn<TestResult, String> dateTestColumn;

    @FXML
    private TableColumn<TestResult, String> dateEffectueColumn;


    public void initialize() {
        testService = new TestService();

        // Configure columns
        testNameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTestName())
        );

        testValueColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getResultValue()).asObject()
        );

        minValueColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getMinValue()).asObject()
        );

        maxValueColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getMaxValue()).asObject()
        );

        dateTestColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getResultDate())
        );

        dateEffectueColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDateEffectue())
        );


        testResultsDetailTable.setEditable(false); // Non-editable table
    }

    public void initData(OrdonnanceTest selectedTest) {
        this.selectedTest = selectedTest;

        // Load data
        List<TestResult> results = testService.getTestResultsByOrdonnance(selectedTest.getIdOrdonnanceTest());

        // Debugging
        results.forEach(result -> {
            System.out.println("Test Name: " + result.getTestName());
            System.out.println("Test Value: " + result.getResultValue());
            System.out.println("Min Value: " + result.getMinValue());
            System.out.println("Max Value: " + result.getMaxValue());
            System.out.println("Date: " + result.getResultDate());
            System.out.println("Date Effectue: " + result.getDateEffectue());
        });

        // Populate table
        testResultsDetailTable.getItems().setAll(results);
    }

    @FXML
    private void handleClose() {
        testResultsDetailTable.getScene().getWindow().hide(); // Close the current stage
    }
}
