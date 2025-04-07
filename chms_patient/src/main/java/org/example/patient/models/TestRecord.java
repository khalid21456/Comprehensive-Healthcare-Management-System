package org.example.patient.models;

import java.time.LocalDate;

import javafx.scene.control.Button;

public class TestRecord {
	 private String testName;
	    private String testResults;
	    private LocalDate testDate;
	    private String status;
	    private String doctor;
	    private Button prescription;

	    public TestRecord(String testName, String testResults, LocalDate testDate, String status, String doctor, Button prescription) {
	        this.testName = testName;
	        this.testResults = testResults;
	        this.testDate = testDate;
	        this.status = status;
	        this.doctor = doctor;
	        this.prescription = prescription;
	    }

	    // Getters et setters
	    public String getTestName() {
	        return testName;
	    }

	    public void setTestName(String testName) {
	        this.testName = testName;
	    }

	    public String getTestResults() {
	        return testResults;
	    }

	    public void setTestResults(String testResults) {
	        this.testResults = testResults;
	    }

	    public LocalDate getTestDate() {
	        return testDate;
	    }

	    public void setTestDate(LocalDate testDate) {
	        this.testDate = testDate;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getDoctor() {
	        return doctor;
	    }

	    public void setDoctor(String doctor) {
	        this.doctor = doctor;
	    }

	    public Button getPrescription() {
	        return prescription;
	    }

	    public void setPrescription(Button prescription) {
	        this.prescription = prescription;
	    }

}
