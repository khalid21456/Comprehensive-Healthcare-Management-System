package com.example.doctorappointments.controller;

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

public class DoctorFormController implements Initializable {
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
    private DoctorsList doctorsListController; // Reference to DoctorsList controller


    private Map<String, Integer> specialityMap = new HashMap<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSpecialities();
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
        // Récupération des valeurs des champs
        String speciality = combo_speciality.getValue();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String tel = telField.getText();
        String adresse = adresseField.getText();

        // Vérification si tous les champs sont vides
        if ((speciality == null || speciality.isEmpty()) &&
                (nom == null || nom.isEmpty()) &&
                (prenom == null || prenom.isEmpty()) &&
                (tel == null || tel.isEmpty()) &&
                (adresse == null || adresse.isEmpty())) {
            showAlert(Alert.AlertType.WARNING, "All fields are empty. Please fill in the form.");
            return;
        }

        // Vérification si la spécialité est vide
        if (speciality == null || speciality.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No speciality selected.");
            return;
        }

        // Vérification si l'ID de la spécialité est introuvable
        Integer specialityId = specialityMap.get(speciality);
        if (specialityId == null) {
            showAlert(Alert.AlertType.WARNING, "Speciality ID not found.");
            return;
        }

        // Vérification des autres champs individuellement
        if (nom == null || nom.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Nom field is empty.");
            return;
        }

        if (prenom == null || prenom.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Prenom field is empty.");
            return;
        }

        if (adresse == null || adresse.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Adresse field is empty.");
            return;
        }

        if (tel == null || tel.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Tel field is empty.");
            return;
        }

        // Si tous les champs sont correctement remplis
        System.out.println("Speciality: " + speciality);
        System.out.println("Speciality ID: " + specialityId);
        System.out.println("Nom: " + nom);
        System.out.println("Prenom: " + prenom);
        System.out.println("Tel: " + tel);
        System.out.println("Adresse: " + adresse);

        boolean done = DoctorService.insertDoctor(specialityId, nom, prenom, tel, adresse);
        if (done) {
            showAlert(Alert.AlertType.INFORMATION, "Doctor added successfully!");
            // Fermer le formulaire
            Stage stage = (Stage) title.getScene().getWindow();
            stage.close();
            // Actualiser la liste des docteurs
            if (doctorsListController != null) {
                doctorsListController.refreshDoctorData();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error in inserting the new doctor");
        }
    }


    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setDoctorsListController(DoctorsList doctorsListController) {
        this.doctorsListController = doctorsListController;
    }
}