package com.pack.billingsystem.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ordonnance {
    private int idOrdonnance;
    private Date dateCreation;
    private Doctor doctor;
    private String status;
    private List<MedicamentOrdonnance> medicaments;

    public Ordonnance(){}

    public Ordonnance(int idOrdonnance, Date dateCreation, String status) {
        this.idOrdonnance = idOrdonnance;
        this.dateCreation = dateCreation;
        this.status = status;
        this.medicaments = new ArrayList<>();
    }

    // Méthode pour ajouter un médicament à l'ordonnance
    public void addMedicament(MedicamentOrdonnance medicamentOrdonnance) {
        medicaments.add(medicamentOrdonnance);
    }

    // Méthode pour obtenir les détails de l'ordonnance
    public String getDetails() {
        StringBuilder details = new StringBuilder("Ordonnance ID: " + idOrdonnance + "\n");
        details.append("Date de création: ").append(dateCreation).append("\n");
        details.append("Statut: ").append(status).append("\n");
        details.append("Médicaments: \n");
        for (MedicamentOrdonnance mo : medicaments) {
            details.append(mo.toString()).append("\n");
        }
        return details.toString();
    }

    // Méthode pour calculer le prix total de l'ordonnance
    public double calculerPrixTotal() {
        double total = 0;
        for (MedicamentOrdonnance mo : medicaments) {
            total += mo.getQuantite() * mo.getMedicament().getPrix();
        }
        return total;
    }

    // Getters et Setters
    public int getIdOrdonnance() {
        return idOrdonnance;
    }

    public void setIdOrdonnance(int idOrdonnance) {
        this.idOrdonnance = idOrdonnance;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MedicamentOrdonnance> getMedicaments() {
        return medicaments;
    }

    public void setDoctor(int id) {

    }

    public void setMedicaments(List<MedicamentOrdonnance> medicaments) {
        this.medicaments = medicaments;
    }

    @Override
    public String toString() {
        return getDetails();
    }
}
