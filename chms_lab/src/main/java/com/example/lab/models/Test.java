package com.example.lab.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Test {
    private final int id;
    private final StringProperty name;
    private final DoubleProperty minValue;
    private final DoubleProperty maxValue;
    private final DoubleProperty price;
    private final StringProperty description;
    private final StringProperty unit;

    // New fields for TestResult and ResultDate
    private final DoubleProperty testResult;  // For storing the test result
    private final StringProperty resultDate;  // For storing the result date

    public Test(int id, String name, double minValue, double maxValue, double price, String description, String unit) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.minValue = new SimpleDoubleProperty(minValue);
        this.maxValue = new SimpleDoubleProperty(maxValue);
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty(description);
        this.unit = new SimpleStringProperty(unit);

        // Initialize the new fields
        this.testResult = new SimpleDoubleProperty(0.0);  // Default value of 0.0
        this.resultDate = new SimpleStringProperty("");  // Default value as empty string
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public double getMinValue() {
        return minValue.get();
    }

    public void setMinValue(double minValue) {
        this.minValue.set(minValue);
    }

    public DoubleProperty minValueProperty() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue.get();
    }

    public void setMaxValue(double maxValue) {
        this.maxValue.set(maxValue);
    }

    public DoubleProperty maxValueProperty() {
        return maxValue;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getUnit() {
        return unit.get();
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public void setTestResult(double testResult) {
        this.testResult.set(testResult);
    }



    public void setResultDate(String resultDate) {
        this.resultDate.set(resultDate);
    }


}
