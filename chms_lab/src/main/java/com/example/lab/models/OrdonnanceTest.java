package com.example.lab.models;

import javafx.beans.property.*;
import java.util.List;

public class OrdonnanceTest {

    private final IntegerProperty idOrdonnanceTest;
    private final IntegerProperty idDoctor;
    private final StringProperty dateOrdonnanceTest;
    private final IntegerProperty idPatient;
    private final StringProperty status;
    private List<TestResult> testResults; // List of results for each test

    // Constructor
    public OrdonnanceTest(int idOrdonnanceTest, int idDoctor, String dateOrdonnanceTest,
                          int idPatient, String status) {
        this.idOrdonnanceTest = new SimpleIntegerProperty(idOrdonnanceTest);
        this.idDoctor = new SimpleIntegerProperty(idDoctor);
        this.dateOrdonnanceTest = new SimpleStringProperty(dateOrdonnanceTest);
        this.idPatient = new SimpleIntegerProperty(idPatient);
        this.status = new SimpleStringProperty(status);
    }

    // Getters and Setters
    public int getIdOrdonnanceTest() {
        return idOrdonnanceTest.get();
    }

    public int getIdDoctor() {
        return idDoctor.get();
    }

    public String getordonnanceTestDate() {
        return dateOrdonnanceTest.get();
    }

    public int getIdPatient() {
        return idPatient.get();
    }

    public String getStatus() {
        return status.get();
    }

    public List<TestResult> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<TestResult> testResults) {
        this.testResults = testResults;
    }

    // Property accessors (for JavaFX bindings)
    public IntegerProperty idOrdonnanceTestProperty() {
        return idOrdonnanceTest;
    }

    public IntegerProperty idDoctorProperty() {
        return idDoctor;
    }

    public StringProperty dateOrdonnanceTestProperty() {
        return dateOrdonnanceTest;
    }

    public IntegerProperty idPatientProperty() {
        return idPatient;
    }

    public StringProperty statusProperty() {
        return status;
    }

    // Setters
    public void setIdOrdonnanceTest(int idOrdonnanceTest) {
        this.idOrdonnanceTest.set(idOrdonnanceTest);
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor.set(idDoctor);
    }

    public void setordonnanceTestDate(String ordonnanceTestDate) {
        this.dateOrdonnanceTest.set(ordonnanceTestDate);
    }

    public void setIdPatient(int idPatient) {
        this.idPatient.set(idPatient);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
