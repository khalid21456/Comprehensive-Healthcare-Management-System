package com.pack.billingsystem.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Bill {
    private int idBill;
    private Patient patient;
    private Appointment appointment;
    private Ordonnance ordonnance;
    private OrdonnanceTest ordonnanceTest;
    private double total;
    private Date dateBill;

    public Bill(){}

    public Bill(int idBill, Patient patient, Appointment appointment, Ordonnance ordonnance, OrdonnanceTest ordonnanceTest, double total, Date dateBill) {
        this.idBill = idBill;
        this.patient = patient;
        this.appointment = appointment;
        this.ordonnance = ordonnance;
        this.ordonnanceTest = ordonnanceTest;
        this.total = total;
        this.dateBill = dateBill;
    }

    // Getters et Setters
    public int getIdBill() { return idBill; }
    public void setIdBill(int idBill) { this.idBill = idBill; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public Ordonnance getOrdonnance() { return ordonnance; }
    public void setOrdonnance(Ordonnance ordonnance) { this.ordonnance = ordonnance; }

    public OrdonnanceTest getOrdonnanceTest() { return ordonnanceTest; }
    public void setOrdonnanceTest(OrdonnanceTest ordonnanceTest) { this.ordonnanceTest = ordonnanceTest; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Date getDateBill() { return dateBill; }
    public void setDateBill(Date dateBill) { this.dateBill = dateBill; }
}

