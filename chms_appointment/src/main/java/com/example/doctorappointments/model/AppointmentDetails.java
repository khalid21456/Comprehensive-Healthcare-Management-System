package com.example.doctorappointments.model;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class AppointmentDetails {
    private Integer IDAppointment;
    private Integer IDDoctor;
    private Integer IDPatient;
    private Timestamp AppointmentDate;
    private String DoctorFullName;
    private String PatientFullName;
    private String serviceName;
    private String Status;
    private Integer Paye;


    public String getDoctorFullName() {
        return DoctorFullName;
    }

    public String getPatientFullName() {
        return PatientFullName;
    }

    public AppointmentDetails(Integer IDAppointment, Integer IDDoctor, Integer IDPatient, Timestamp appointmentDate, String doctorFullName, String patientFullName) {
        this.IDAppointment = IDAppointment;
        this.IDDoctor = IDDoctor;
        this.IDPatient = IDPatient;
        this.AppointmentDate = appointmentDate;
        this.DoctorFullName = doctorFullName;
        this.PatientFullName = patientFullName;
    }


    public AppointmentDetails(Integer IDAppointment, Integer IDDoctor, Integer IDPatient, Timestamp appointmentDate,
                              String doctorFullName, String patientFullName, String serviceName,String status, Integer paye) {
        this.IDAppointment = IDAppointment;
        this.IDDoctor = IDDoctor;
        this.IDPatient = IDPatient;
        this.AppointmentDate = appointmentDate;
        this.DoctorFullName = doctorFullName;
        this.PatientFullName = patientFullName;
        this.serviceName = serviceName;
        this.Status = status;
        this.Paye = paye;
    }
    public Integer getIDAppointment() {
        return IDAppointment;
    }

    public void setIDAppointment(Integer IDAppointment) {
        this.IDAppointment = IDAppointment;
    }

    public Integer getIDDoctor() {
        return IDDoctor;
    }

    public void setIDDoctor(Integer IDDoctor) {
        this.IDDoctor = IDDoctor;
    }

    public Integer getIDPatient() {
        return IDPatient;
    }

    public void setIDPatient(Integer IDPatient) {
        this.IDPatient = IDPatient;
    }

    public Timestamp getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(Timestamp appointmentDate) {
        this.AppointmentDate = appointmentDate;
    }

    public String getServiceName() {  return serviceName;}

    public void setServiceName(String serviceName) { this.serviceName = serviceName;}

    public String getStatus() { return Status; }

    public void setStatus(String status) { this.Status = status;}

    public Integer getPaye() { return Paye; }

    public void setPaye(Integer paye) { this.Paye = paye;}

    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(AppointmentDate);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "IDAppointment=" + IDAppointment +
                ", IDDoctor=" + IDDoctor +
                ", IDPatient=" + IDPatient +
                ", AppointmentDate=" + AppointmentDate +
                ", DoctorFullName='" + DoctorFullName + '\'' +
                ", PatientFullName='" + PatientFullName + '\'' +
                '}';
    }

    public String getFormattedTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(AppointmentDate);
    }

}
