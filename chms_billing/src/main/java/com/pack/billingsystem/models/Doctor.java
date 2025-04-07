package com.pack.billingsystem.models;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private int idDoctor;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private List<Appointment> appointments;

    public Doctor() {}

    public Doctor(int idDoctor, String nom, String prenom, String telephone, String adresse) {
        this.idDoctor = idDoctor;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.appointments = new ArrayList<>();
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    // Getters et Setters
    public int getIdDoctor() { return idDoctor; }
    public void setIdDoctor(int idDoctor) { this.idDoctor = idDoctor; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public List<Appointment> getAppointments() { return appointments; }
}

