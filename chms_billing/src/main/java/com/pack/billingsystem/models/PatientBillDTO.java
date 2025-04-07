package com.pack.billingsystem.models;

import java.util.Date;

@SuppressWarnings("unused")
public class PatientBillDTO {

    private int billID;
    private int patientID;
    private String patientFirstName;
    private String patientLastName;
    private Date date;
    private String tel;

    public PatientBillDTO(String patientLastName, String patientFirstName, int patientID,Date date,int billID,String tel) {
        this.patientLastName = patientLastName;
        this.patientFirstName = patientFirstName;
        this.patientID = patientID;
        this.date = date;
        this.billID = billID;
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel){
        this.tel = tel;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    @Override
    public String toString() {
        return "PatientBillDTO{" +
                "patientID=" + patientID +
                ", patientFirstName='" + patientFirstName + '\'' +
                ", patientLastName='" + patientLastName + '\'' +
                ", date=" + date +
                '}';
    }
}
