package com.example.doctorappointments.model;

public class Doctor {
    private int idDoctor;
    private int idSpeciality;
    private String nom;
    private String prenom;
    private String tel;
    private String adresse;
    private String specialityName;  // Add a field for the specialty name

    public Doctor(int idDoctor, int idSpeciality, String nom, String prenom, String tel, String adresse) {
        this.idDoctor = idDoctor;
        this.idSpeciality = idSpeciality;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.adresse = adresse;
    }

    // Getters and Setters
    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public int getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(int idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "idDoctor=" + idDoctor +
                ", idSpeciality=" + idSpeciality +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }
}
