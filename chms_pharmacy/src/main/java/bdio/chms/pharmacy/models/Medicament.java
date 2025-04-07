package bdio.chms.pharmacy.models;

import java.util.Date;

public class Medicament {
    private int idMedicament;
    private String nomMed;
    private String descMed;
    private double prixUnit;
    private Date dateExpiration;
    private String type;
    private int qty;

    // Constructor used to display medications related to a prescription (only name needed)
    public Medicament(String nomMed) {
        this.nomMed = nomMed;
    }

    public Medicament(int idMedicament, String nomMed, String descMed, double prixUnit, Date dateExpiration, String type, int qty) {
        this.idMedicament = idMedicament;
        this.nomMed = nomMed;
        this.descMed = descMed;
        this.prixUnit = prixUnit;
        this.dateExpiration = dateExpiration;
        this.type = type;
        this.qty = qty;
    }


    public int getIdMedicament() {
        return idMedicament;
    }

    public String getNomMed() {
        return nomMed;
    }

    public String getDescMed() {
        return descMed;
    }

    public double getPrixUnit() {
        return prixUnit;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public String getType() {
        return type;
    }

    public int getQty() {
        return qty;
    }

    // Setters
    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }

    public void setDescMed(String descMed) {
        this.descMed = descMed;
    }

    public void setPrixUnit(double prixUnit) {
        this.prixUnit = prixUnit;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
