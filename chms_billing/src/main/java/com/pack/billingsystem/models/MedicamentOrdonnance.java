package com.pack.billingsystem.models;

public class MedicamentOrdonnance {
    private int idMedOrdonnance;
    private Medicament medicament;
    private int quantite;

    public MedicamentOrdonnance(int idMedOrdonnance, Medicament medicament, int quantite) {
        this.idMedOrdonnance = idMedOrdonnance;
        this.medicament = medicament;
        this.quantite = quantite;
    }

    // Getters et Setters
    public int getIdMedOrdonnance() {
        return idMedOrdonnance;
    }

    public void setIdMedOrdonnance(int idMedOrdonnance) {
        this.idMedOrdonnance = idMedOrdonnance;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "MedicamentOrdonnance{" +
                "idMedOrdonnance=" + idMedOrdonnance +
                ", medicament=" + medicament +
                ", quantite=" + quantite +
                '}';
    }
}

