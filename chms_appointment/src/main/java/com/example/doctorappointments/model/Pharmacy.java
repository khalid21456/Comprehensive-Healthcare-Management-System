package com.example.doctorappointments.model;

public class Pharmacy {
    private int IDPharmacien;
    private String Nom;
    private String Prenom;
    private String Tel;
    private String Adresse;

    // Constructor
    public Pharmacy(int IDPharmacien, String Nom, String Prenom, String Tel, String Adresse) {
        this.IDPharmacien = IDPharmacien;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.Tel = Tel;
        this.Adresse = Adresse;
    }

    // Getters and Setters
    public int getIDPharmacien() {
        return IDPharmacien;
    }

    public void setIDPharmacien(int IDPharmacien) {
        this.IDPharmacien = IDPharmacien;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }
}