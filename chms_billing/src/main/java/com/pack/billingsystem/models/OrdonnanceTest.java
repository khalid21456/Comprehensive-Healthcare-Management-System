package com.pack.billingsystem.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdonnanceTest {
    private int idOrdonnanceTest;
    private String testNom;
    private Date date;
    private int resultat;
    private double prix;

    public OrdonnanceTest() {}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Getters et Setters
    public int getIdOrdonnanceTest() { return idOrdonnanceTest; }
    public void setIdOrdonnanceTest(int idOrdonnanceTest) { this.idOrdonnanceTest = idOrdonnanceTest; }

    public String getTestNom() {
        return testNom;
    }

    public void setTestNom(String testNom) {
        this.testNom = testNom;
    }

    public int getResultat() {
        return resultat;
    }

    public void setResultat(int resultat) {
        this.resultat = resultat;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}

