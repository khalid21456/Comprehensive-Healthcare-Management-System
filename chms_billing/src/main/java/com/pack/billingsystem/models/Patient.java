package com.pack.billingsystem.models;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int idPatient;
    private String nom;
    private String prenom;
    private String telephone;
    private double pourcentageAssurance;
    private List<Appointment> appointments;
    private List<Bill> bills;

    public Patient() {
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    // Getters et Setters
    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public double getPourcentageAssurance() {
        return pourcentageAssurance;
    }

    public void setPourcentageAssurance(double pourcentageAssurance) {
        this.pourcentageAssurance = pourcentageAssurance;
    }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public List<Appointment> getAppointments() { return appointments; }
    public List<Bill> getBills() { return bills; }
}
