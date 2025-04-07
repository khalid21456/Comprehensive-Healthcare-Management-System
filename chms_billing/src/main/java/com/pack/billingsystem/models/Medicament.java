package com.pack.billingsystem.models;

public class Medicament {
    private int idMedicament;
    private String nom;
    private int quantite;
    private double prix;

    public Medicament(int idMedicament, String nom, int quantite, double prix) {
        this.idMedicament = idMedicament;
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
    }

    public Medicament() {
    }

    // Getters et Setters
    public int getIdMedicament() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

}
