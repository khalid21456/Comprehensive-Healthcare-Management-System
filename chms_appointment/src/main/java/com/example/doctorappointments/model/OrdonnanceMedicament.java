package com.example.doctorappointments.model;
import java.sql.Timestamp;

public class OrdonnanceMedicament {
    private Integer IDDoctor;
    private Timestamp DateCreation;
    private Integer IDPharmacien;
    private Integer IDPatient;
    private String Status;

    public OrdonnanceMedicament(Integer IDDoctor, Timestamp DateCreation, Integer IDPharmacien, Integer IDPatient, String Status) {
        this.IDDoctor = IDDoctor;
        this.DateCreation = DateCreation;
        this.IDPharmacien = IDPharmacien;
        this.IDPatient = IDPatient;
        this.Status = Status;
    }

    public Integer getIDDoctor() {
        return IDDoctor;
    }

    public void setIDDoctor(Integer IDDoctor) {
        this.IDDoctor = IDDoctor;
    }

    public Timestamp getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(Timestamp DateCreation) {
        this.DateCreation = DateCreation;
    }

    public Integer getIDPharmacien() {
        return IDPharmacien;
    }

    public void setIDPharmacien(Integer IDPharmacien) {
        this.IDPharmacien = IDPharmacien;
    }

    public Integer getIDPatient() {
        return IDPatient;
    }

    public void setIDPatient(Integer IDPatient) {
        this.IDPatient = IDPatient;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}