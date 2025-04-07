package com.pack.billingsystem.models;

import java.sql.Date;
import java.time.LocalDateTime;

public class Appointment {
    private int idAppointment;
    private Doctor doctor;
    private Patient patient;
    private Date appointmentDate;
    private double price;
    private boolean paye;
    private String status; // Ex: "Confirmed", "Canceled"
    private String service;

    public Appointment() {}

    public Appointment(int idAppointment, Doctor doctor, Patient patient, Date appointmentDate, double price, boolean paye, String status, String service) {
        this.idAppointment = idAppointment;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.price = price;
        this.paye = paye;
        this.status = status;
        this.service = service;
    }

    // Getters et Setters
    public int getIdAppointment() { return idAppointment; }
    public void setIdAppointment(int idAppointment) { this.idAppointment = idAppointment; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Date getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isPaye() { return paye; }
    public void setPaye(boolean paye) { this.paye = paye; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
}
