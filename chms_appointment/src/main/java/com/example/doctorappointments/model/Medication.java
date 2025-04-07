package com.example.doctorappointments.model;

import java.sql.Timestamp;

public class Medication {

    private int IDMedicament;
    private String NomMed;
    private String DescMed;
    private double Prix_Unit;
    private Timestamp DateExpiration;
    private String Type;
    private int Qty;

    // Constructor
    public Medication(int IDMedicament, String NomMed, String DescMed, double Prix_Unit, Timestamp DateExpiration, String Type, int Qty) {
        this.IDMedicament = IDMedicament;
        this.NomMed = NomMed;
        this.DescMed = DescMed;
        this.Prix_Unit = Prix_Unit;
        this.DateExpiration = DateExpiration;
        this.Type = Type;
        this.Qty = Qty;
    }

    // Getters and Setters
    public int getIDMedicament() {
        return IDMedicament;
    }

    public void setIDMedicament(int IDMedicament) {
        this.IDMedicament = IDMedicament;
    }

    public String getNomMed() {
        return NomMed;
    }

    public void setNomMed(String NomMed) {
        this.NomMed = NomMed;
    }

    public String getDescMed() {
        return DescMed;
    }

    public void setDescMed(String DescMed) {
        this.DescMed = DescMed;
    }

    public double getPrix_Unit() {
        return Prix_Unit;
    }

    public void setPrix_Unit(double Prix_Unit) {
        this.Prix_Unit = Prix_Unit;
    }

    public Timestamp getDateExpiration() {
        return DateExpiration;
    }

    public void setDateExpiration(Timestamp DateExpiration) {
        this.DateExpiration = DateExpiration;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int Qty) {
        this.Qty = Qty;
    }
}
