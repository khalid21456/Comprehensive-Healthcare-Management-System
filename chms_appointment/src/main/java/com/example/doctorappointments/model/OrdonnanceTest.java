package com.example.doctorappointments.model;

import java.sql.Timestamp;

public class OrdonnanceTest {
    private int IDOrdonnanceTest;
    private int IDDoctor;
    private Timestamp DateOrdonnanceTest;
    private int IDPatient;
    private String Status;

    // Getters and Setters
    public int getIDOrdonnanceTest() {
        return IDOrdonnanceTest;
    }

    public void setIDOrdonnanceTest(int IDOrdonnanceTest) {
        this.IDOrdonnanceTest = IDOrdonnanceTest;
    }

    public int getIDDoctor() {
        return IDDoctor;
    }

    public void setIDDoctor(int IDDoctor) {
        this.IDDoctor = IDDoctor;
    }

    public Timestamp getDateOrdonnanceTest() {
        return DateOrdonnanceTest;
    }

    public void setDateOrdonnanceTest(Timestamp DateOrdonnanceTest) {
        this.DateOrdonnanceTest = DateOrdonnanceTest;
    }

    public int getIDPatient() {
        return IDPatient;
    }

    public void setIDPatient(int IDPatient) {
        this.IDPatient = IDPatient;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}
