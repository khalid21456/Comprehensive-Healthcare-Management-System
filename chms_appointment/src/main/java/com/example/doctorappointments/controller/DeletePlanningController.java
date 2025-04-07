package com.example.doctorappointments.controller;

import com.example.doctorappointments.model.DoctorDTO;
import com.example.doctorappointments.service.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.doctorappointments.service.DoctorDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;

public class DeletePlanningController {

    @FXML
    private ComboBox<String> doctorComboBox; // Pour choisir le médecin

    @FXML
    private DatePicker datePicker; // Pour choisir la date

    @FXML
    private ListView<String> slotsListView; // Liste des créneaux disponibles

    @FXML
    private Button deleteButton; // Bouton de suppression

    @FXML
    private Button loadSlotsButton; // Bouton pour charger les créneaux

    private ObservableList<String> availableSlots = FXCollections.observableArrayList();

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


    private boolean isValidTimeSlot(String timeSlot) {
        return timeSlot.matches("^\\d{2}:\\d{2}$") || timeSlot.matches("^\\d{2}:\\d{2}-\\d{2}:\\d{2}$");
    }

    @FXML
    private void editSelectedSlot() {
        String selectedSlot = slotsListView.getSelectionModel().getSelectedItem();

        if (selectedSlot == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a time slot to modify.");
            return;
        }

        // Open a dialog to input the new slot
        TextInputDialog dialog = new TextInputDialog(selectedSlot);
        dialog.setTitle("Modify the time slot.");
        dialog.setHeaderText("Modify the selected time slot.");
        dialog.setContentText("New time slot (HH:mm):"); // Adjusted to HH:mm

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty() || result.get().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No time slot has been entered.");
            return;
        }

        String newSlot = result.get();

        // Validate the new slot format (HH:mm)
        if (!isValidTimeSlot(newSlot)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid time slot in the HH:mm format.");
            return;
        }

        String selectedDoctor = doctorComboBox.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDoctor == null || selectedDate == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a doctor and a date.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Split the new slot into start time (since there's no end time in HH:mm format)
            String newStartTime = newSlot.trim();

            // Split the selected slot into start time (same as above)
            String oldStartTime = selectedSlot.trim();

            // Calculate the end time (one hour after the start time)
            String newEndTime = calculateEndTime(newStartTime);

            // Update the slot in the database
            String query = """
        UPDATE planning
        SET Date_Start = ?, Date_Fin = ?
        WHERE IDDoctor = ? AND Date = ? AND Date_Start = ?
        """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newStartTime);
            statement.setString(2, newEndTime); // Use the calculated end time
            statement.setInt(3, doctorMap.get(selectedDoctor)); // Get doctor ID from the map
            statement.setDate(4, java.sql.Date.valueOf(selectedDate.toString()));
            statement.setString(5, oldStartTime);

            System.out.println("Executing update query with parameters:");
            System.out.println("New Start Time: " + newStartTime);
            System.out.println("New End Time: " + newEndTime);
            System.out.println("Doctor ID: " + doctorMap.get(selectedDoctor));
            System.out.println("Selected Date: " + selectedDate.toString());
            System.out.println("Old Start Time: " + oldStartTime);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                // Update the slot in the ListView
                availableSlots.set(availableSlots.indexOf(selectedSlot), newSlot);
                showAlert(Alert.AlertType.INFORMATION, "Success", "The time slot has been successfully modified!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "The time slot modification failed. Please check the data.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while modifying the time slot.");
        }
    }

    /**
     * Calculate the end time for a given start time (1 hour after the start).
     * @param startTime Start time in HH:mm format.
     * @return Calculated end time in HH:mm format.
     */
    private String calculateEndTime(String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = start.plusHours(1); // Adding 1 hour to the start time
        return end.format(formatter);
    }


    // Charger les créneaux disponibles selon le médecin et la date
    @FXML
    private void loadAvailableSlots() {
        String selectedDoctor = doctorComboBox.getSelectionModel().getSelectedItem();
        String selectedDate = (datePicker.getValue() != null) ? datePicker.getValue().toString() : null;

        if (selectedDoctor == null || selectedDate == null) {
            slotsListView.setItems(FXCollections.observableArrayList("Please select a doctor and a date."));
            deleteButton.setDisable(true);
            return;
        }

        // Récupérer l'ID du médecin depuis le doctorMap
        Integer doctorId = doctorMap.get(selectedDoctor);

        if (doctorId == null) {
            // Si l'ID du médecin n'est pas trouvé, afficher un message d'erreur
            showAlert(Alert.AlertType.INFORMATION, "Error", "Doctor not found.");
            return;
        }

        availableSlots.clear(); // Vider la liste des créneaux précédents

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Requête SQL mise à jour pour utiliser l'ID du médecin
            String query = """
           SELECT p.Date_Start
           FROM planning p
           WHERE p.IDDoctor = ? AND p.Date = ? AND p.Availability = 1
        """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, doctorId); // Passer l'ID du médecin
            statement.setString(2, selectedDate); // Passer la date sélectionnée

            ResultSet resultSet = statement.executeQuery();

            // Ajouter les créneaux à la liste
            while (resultSet.next()) {
                String timeString = resultSet.getString("Date_Start");
                LocalTime time = LocalTime.parse(timeString); // Parse the time
                String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm")); // Format it to HH:mm
                availableSlots.add(formattedTime);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Si aucun créneau n'est disponible, afficher un message
        if (availableSlots.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No time slot available.", "There is no available time slot for this doctor and this date.");
        } else {
            slotsListView.setItems(availableSlots);
        }

        // Activer ou désactiver le bouton de suppression en fonction des créneaux disponibles
        deleteButton.setDisable(availableSlots.isEmpty());
    }


    // Supprimer le ou les créneaux sélectionnés
    @FXML
    private void deleteSelectedSlots() {
        ObservableList<String> selectedSlots = slotsListView.getSelectionModel().getSelectedItems();

        if (selectedSlots.isEmpty()) {
            return; // Si aucun créneau n'est sélectionné, ne rien faire
        }

        String selectedDoctor = doctorComboBox.getSelectionModel().getSelectedItem();
        String selectedDate = (datePicker.getValue() != null) ? datePicker.getValue().toString() : null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Supprimer les créneaux sélectionnés
            String query = """
                DELETE FROM planning
                WHERE IDDoctor = (SELECT IDDoctor FROM doctor WHERE Nom = ?) AND Date = ? AND Date_Start = ?
            """;

            PreparedStatement statement = connection.prepareStatement(query);

            for (String timeSlot : selectedSlots) {
                statement.setString(1, selectedDoctor);
                statement.setString(2, selectedDate);
                statement.setString(3, timeSlot);
                statement.executeUpdate();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Mettre à jour la liste après suppression
        availableSlots.removeAll(selectedSlots);
        deleteButton.setDisable(availableSlots.isEmpty()); // Désactiver le bouton si plus de créneaux

        showAlert(Alert.AlertType.INFORMATION, "Deletion successful.", "The selected time slots have been successfully deleted.");
    }

    // Méthode pour afficher une alerte
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);  // Use the passed alert type
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional, set if you want a header
        alert.setContentText(message);
        alert.showAndWait();
    }
}
