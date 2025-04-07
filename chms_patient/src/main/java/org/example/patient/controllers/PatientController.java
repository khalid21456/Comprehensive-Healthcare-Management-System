package org.example.patient.controllers;

import org.example.patient.models.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.sql.SQLException;

public class PatientController {
    private final PatientDao patientDao;
    private Patient currentPatient;

    @FXML
    private Label welcomeLabel;

    public PatientController() {
        this.patientDao = new PatientDaoImpl();
    }

    public void setPatient(Patient patient) {
        this.currentPatient = patient;
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + patient.getPrenom() + " " + patient.getNom() + "!");
        }
    }

    public boolean addPatient(Patient patient) throws SQLException {
        // Validation des données
        if (!validatePatient(patient)) {
            return false;
        }
        return patientDao.insertPatient(patient);
    }

    private boolean validatePatient(Patient patient) {
        if (patient == null) return false;
        if (patient.getNom() == null || patient.getNom().trim().isEmpty()) return false;
        if (patient.getPrenom() == null || patient.getPrenom().trim().isEmpty()) return false;
        if (patient.getCin() == null || patient.getCin().trim().isEmpty()) return false;
        if (patient.getTel() == null || patient.getTel().trim().isEmpty()) return false;
        return true;
    }
}
