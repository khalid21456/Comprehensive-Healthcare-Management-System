package com.pack.billingsystem.models;

import java.util.Date;

public class BillRowData {

    private int idBill;
    private int idPatient;
    private String prenom;
    private String nom;
    private Date date;
    private String tele;

    public BillRowData(int idBill, String prenom, String nom, Date date, String tele,int idPatient) {
        this.idBill = idBill;
        this.prenom = prenom;
        this.nom = nom;
        this.date = date;
        this.tele = tele;
        this.idPatient = idPatient;
    }


    public int getBillId() {
        return idBill;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public Date getDate() {
        return date;
    }
}
