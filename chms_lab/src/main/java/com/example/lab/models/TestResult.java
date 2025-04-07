package com.example.lab.models;

import javafx.beans.property.*;

public class TestResult {

    private final IntegerProperty idOrdonnanceTest;
    private final IntegerProperty idTest;
    private final DoubleProperty resultValue;
    private final StringProperty resultDate;
    private final StringProperty dateEffectue; // Use StringProperty

    private final StringProperty testName; // Added testName property
    private final DoubleProperty minValue;  // Added minValue property
    private final DoubleProperty maxValue;  // Added maxValue property

    // Updated constructor
    public TestResult(int idOrdonnanceTest, int idTest, double resultValue, String resultDate, String dateEffectue) {
        this.idOrdonnanceTest = new SimpleIntegerProperty(idOrdonnanceTest);
        this.idTest = new SimpleIntegerProperty(idTest);
        this.resultValue = new SimpleDoubleProperty(resultValue);
        this.resultDate = new SimpleStringProperty(resultDate);
        this.dateEffectue = new SimpleStringProperty(dateEffectue); // Initialize dateEffectue
        this.testName = new SimpleStringProperty("");
        this.minValue = new SimpleDoubleProperty(0.0);
        this.maxValue = new SimpleDoubleProperty(0.0);
    }

    // Optional: Default constructor for backward compatibility
    public TestResult(int idOrdonnanceTest, int idTest, double resultValue, String resultDate) {
        this(idOrdonnanceTest, idTest, resultValue, resultDate, ""); // Default dateEffectue as an empty string
    }


    // Getters
    public int getIdOrdonnanceTest() {
        return idOrdonnanceTest.get();
    }

    public int getIdTest() {
        return idTest.get();
    }

    public double getResultValue() {
        return resultValue.get();
    }

    public String getResultDate() {
        return resultDate.get();
    }

    public String getTestName() {
        return testName.get();
    }

    public double getMinValue() {
        return minValue.get();
    }

    public double getMaxValue() {
        return maxValue.get();
    }

    public String getDateEffectue() {
        return dateEffectue.get();
    }

    public StringProperty resultDateProperty() {
        return resultDate;
    }

    public StringProperty testNameProperty() {
        return testName;
    }

    public StringProperty dateEffectueProperty() {
        return dateEffectue;
    }

    // Setters
    public void setResultValue(double resultValue) {
        this.resultValue.set(resultValue);
    }

    public void setResultDate(String resultDate) {
        this.resultDate.set(resultDate);
    }

    public void setTestName(String testName) {
        this.testName.set(testName);
    }

    public void setMinValue(double minValue) {
        this.minValue.set(minValue);
    }

    public void setMaxValue(double maxValue) {
        this.maxValue.set(maxValue);
    }

    public void setDateEffectue(String dateEffectue) {
    this.dateEffectue.set(dateEffectue);
}
}