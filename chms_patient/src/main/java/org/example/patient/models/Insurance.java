package org.example.patient.models;

public class Insurance {
    private int idInsurance;
    private String nameInsurance;
    private double percentage;

    public Insurance(int idInsurance, String nameInsurance, double percentage) {
        this.idInsurance = idInsurance;
        this.nameInsurance = nameInsurance;
        this.percentage = percentage;
    }

    public int getIdInsurance() {
        return idInsurance;
    }

    public void setIdInsurance(int idInsurance) {
        this.idInsurance = idInsurance;
    }

    public String getNameInsurance() {
        return nameInsurance;
    }

    public void setNameInsurance(String nameInsurance) {
        this.nameInsurance = nameInsurance;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Insurance{idInsurance=" + idInsurance + ", nameInsurance='" + nameInsurance + "', percentage=" + percentage + '}';
    }
}
