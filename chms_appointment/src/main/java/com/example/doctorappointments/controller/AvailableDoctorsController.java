package com.example.doctorappointments.controller;

import com.example.doctorappointments.service.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.sql.*;
import java.time.LocalDate;

public class AvailableDoctorsController {

    @FXML
    private Button searchButton;  // Bouton de recherche

    @FXML
    private ListView<String> availabilityListView;  // Liste des médecins disponibles

    @FXML
    private DatePicker datePicker;  // Sélecteur de date

    //@FXML
    //private TextField specialtyTextField;  // Champ pour la spécialité (facultatif)

    @FXML
    private ComboBox<String> specialtyComboBox; // Champ pour la spécialité (facultatif)

    @FXML
    public void initialize() {
        specialtyComboBox();
    }

    private void specialtyComboBox() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT DISTINCT NomSpeciality FROM Speciality";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            ObservableList<String> specialties = FXCollections.observableArrayList();
            while (resultSet.next()) {
                specialties.add(resultSet.getString("NomSpeciality"));
            }

            specialtyComboBox.setItems(specialties);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error while loading the specialties.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void handleSearchButtonClick() {
        LocalDate selectedDate = datePicker.getValue();
        String specialty = specialtyComboBox.getValue();  // Récupérer la spécialité

        if (selectedDate == null) {
            // Afficher un message d'alerte si la date n'est pas sélectionnée
            showAlert("Please select a date.", Alert.AlertType.WARNING);
            return;
        }

        // Appeler la méthode pour afficher les médecins disponibles à la date et spécialité sélectionnées
        displayDoctorsAvailableOnDate(selectedDate, specialty);
    }


    private void displayDoctorsAvailableOnDate(LocalDate selectedDate, String specialty) {
        availabilityListView.getItems().clear();  // Effacer les anciens résultats
        boolean resultsFound = false;  // Pour vérifier s'il y a des résultats

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Construire la requête SQL
            String query = "SELECT d.Nom, s.NomSpeciality, p.Date_Start FROM doctor d " +
                    "JOIN Planning p ON d.IDDoctor = p.IDDoctor " +
                    "JOIN Speciality s ON d.IDSpeciality = s.IDSpeciality " +
                    "WHERE p.Date = ?";

            // Ajouter la condition de spécialité si elle est fournie
            if (specialty != null && !specialty.isEmpty()) {
                query += " AND s.NomSpeciality LIKE ?";
            }

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(selectedDate));  // Paramètre pour la date

            // Ajouter le paramètre spécialité si nécessaire
            if (specialty != null && !specialty.isEmpty()) {
                statement.setString(2, "%" + specialty + "%");  // Recherche partielle
            }

            ResultSet resultSet = statement.executeQuery();

            // Traiter les résultats
            while (resultSet.next()) {
                resultsFound = true;  // Un résultat a été trouvé
                String doctorName = resultSet.getString("Nom");
                String doctorSpecialty = resultSet.getString("NomSpeciality");
                String availableTime = resultSet.getString("Date_Start");

                // Formater et ajouter chaque médecin à la liste
                String availability = String.format("Dr. %s | Spécialité: %s | Heure: %s",
                        doctorName, doctorSpecialty, availableTime);
                availabilityListView.getItems().add(availability);
            }

            // Vérifier si aucun résultat n'a été trouvé
            if (!resultsFound) {
                showAlert("No doctor available for this date and specialty.", Alert.AlertType.INFORMATION);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("An error occurred while retrieving the data.", Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(null);  // Si tu veux afficher un titre, tu peux modifier ceci
        alert.setContentText(message);
        alert.showAndWait();
    }

}
