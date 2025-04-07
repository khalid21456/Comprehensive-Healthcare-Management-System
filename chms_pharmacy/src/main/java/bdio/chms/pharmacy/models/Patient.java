package bdio.chms.pharmacy.models;

import java.util.Date;

public class Patient {
    private int idPatient;
    private String nom;
    private String prenom;
    private String sexe;
    private Date birthDate;
    private String adresse;
    private String tel;
    private int idInsurance;
    private String cin;
    private String ville;

    //constructeur pour afficher les details du patient concerné par une ordonnance (pharmacy management)
    public Patient(int idPatient,String nom,String prenom,String sexe,Date birthDate) {
        this.idPatient=idPatient;
        this.nom=nom;
        this.prenom=prenom;
        this.sexe=sexe;
        this.birthDate=birthDate;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
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

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getIdInsurance() {
        return idInsurance;
    }

    public void setIdInsurance(int idInsurance) {
        this.idInsurance = idInsurance;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
