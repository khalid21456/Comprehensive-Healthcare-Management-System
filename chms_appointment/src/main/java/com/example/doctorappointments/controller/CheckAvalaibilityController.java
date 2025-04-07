package com.example.doctorappointments.controller;

import com.example.doctorappointments.service.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.example.doctorappointments.service.DoctorDAO;
import com.example.doctorappointments.model.DoctorDTO;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckAvalaibilityController {

    @FXML
    private ComboBox<String> doctorComboBox; // Pour choisir le médecin

    @FXML
    private DatePicker datePicker; // Pour choisir la date

    @FXML
    private TextField timeTextField; // Pour entrer l'heure (exemple : "10:00")

    @FXML
    private Label resultLabel; // Pour afficher si le créneau est disponible ou non

    // Initialisation et remplissage de la ComboBox
    private Map<String, Integer> doctorMap = new HashMap<>(); // Associe les noms aux IDs

    public void initialize() {
        try {
            DoctorDAO doctorDAO = new DoctorDAO();
            List<DoctorDTO> doctors = doctorDAO.getAllDoctors();

            for (DoctorDTO doctor : doctors) {
                String displayName = "Dr " + doctor.getName() + " - " + doctor.getSpeciality();
                doctorComboBox.getItems().add(displayName);
                doctorMap.put(displayName, doctor.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Vérifier si le créneau est disponible
    @FXML
    private void checkAvailability() {
        String selectedDoctor = doctorComboBox.getSelectionModel().getSelectedItem();
        String selectedDate = (datePicker.getValue() != null) ? datePicker.getValue().toString() : null;
        String selectedTime = timeTextField.getText();

        // Vérifier si tous les champs sont remplis
        if (selectedDoctor == null || selectedDate == null || selectedTime.isEmpty()) {
            resultLabel.setText("Please fill in all the fields!");
            return;
        }

        // Récupérer l'ID du médecin à partir de la map
        int doctorId = doctorMap.get(selectedDoctor);

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Requête SQL mise à jour pour vérifier la disponibilité via l'ID du médecin
            String query = """
            SELECT Availability
            FROM planning
            WHERE IDDoctor = ? 
            AND Date = ? 
            AND Date_Start = ?
        """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, doctorId);      // Utiliser l'ID du médecin
            statement.setString(2, selectedDate);   // La date sélectionnée
            statement.setString(3, selectedTime);   // L'heure du créneau

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int availability = resultSet.getInt("Availability");
                if (availability == 1) {
                    showAlert("The time slot is available!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("The time slot is already taken.", Alert.AlertType.WARNING);
                }
            } else {
                showAlert("No data found for this time slot.", Alert.AlertType.ERROR);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("Error during verification.");
        }
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Verification result.");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
