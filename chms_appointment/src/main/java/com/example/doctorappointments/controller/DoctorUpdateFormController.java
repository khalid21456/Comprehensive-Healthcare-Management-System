package com.example.doctorappointments.controller;

import com.example.doctorappointments.model.Doctor;
import com.example.doctorappointments.model.Speciality;
import com.example.doctorappointments.service.DoctorService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class DoctorUpdateFormController implements Initializable {
    private int doctorID;

    @FXML
    private Label title;

    @FXML
    private ComboBox<String> combo_speciality;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField telField;

    @FXML
    private TextField adresseField;


    private Map<String, Integer> specialityMap = new HashMap<>();

    private DoctorsList doctorsListController; // Reference to DoctorsList controller


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSpecialities();
        //loadInitialData();
    }


    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
        loadInitialData();
    }

    private void loadInitialData() {
        ObservableList<Doctor> doctor = DoctorService.getDoctorDetails(doctorID);


        if (doctor.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No doctor found with ID: " + doctorID);
            ((Stage) title.getScene().getWindow()).close();
            return;
        }

        nomField.setText(doctor.getFirst().getNom());
        prenomField.setText(doctor.getFirst().getPrenom());
        telField.setText(doctor.getFirst().getTel());
        adresseField.setText(doctor.getFirst().getAdresse());

        String speciality = null;

        for (Map.Entry<String, Integer> entry : specialityMap.entrySet()) {
            if (entry.getValue().equals(doctor.getFirst().getIdSpeciality())) {
                speciality = entry.getKey();
                break;
            }
        }
        combo_speciality.setValue(speciality);
        title.setText("Update Doctor : " + doctorID);

    }

    private void loadSpecialities() {
        ObservableList<Speciality> specialities = DoctorService.getAllSpecialities();


        for (Speciality speciality : specialities) {
            combo_speciality.getItems().add(speciality.getIdSpeciality()+ " - " + speciality.getNomSpeciality());
            specialityMap.put(speciality.getIdSpeciality()+ " - " + speciality.getNomSpeciality(), speciality.getIdSpeciality());
        }

    }

    @FXML
    private void handleSubmitButtonAction() {
        String speciality = combo_speciality.getValue();
        if (speciality == null) {
            showAlert(Alert.AlertType.WARNING, "No speciality selected.");
            return;
        }

        Integer specialityId = specialityMap.get(speciality);
        if (specialityId == null) {
            showAlert(Alert.AlertType.WARNING, "Speciality ID not found.");
            return;
        }

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String tel = telField.getText();
        String adresse = adresseField.getText();

        if (nom.isEmpty() || prenom.isEmpty() || tel.isEmpty() || adresse.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "All fields must be filled.");
            return;
        }


        boolean done= DoctorService.updateDoctor(doctorID,specialityId, nom, prenom, tel, adresse);
        if (done) {
            showAlert(Alert.AlertType.INFORMATION, "Doctor updated successfully!");
            // Close the update form
            Stage stage = (Stage) title.getScene().getWindow();
            stage.close();
            // Refresh doctor data in DoctorsList
            if (doctorsListController != null) {
                doctorsListController.refreshDoctorData();
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Failed to update doctor information. Please try again.");
        }
    }


    public void setDoctorsListController(DoctorsList doctorsListController) {
        this.doctorsListController = doctorsListController;
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
