package com.example.doctorappointments.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.doctorappointments.service.DoctorDAO;
import com.example.doctorappointments.model.Availability;
import com.example.doctorappointments.model.DoctorDTO;
import com.example.doctorappointments.service.PlanningDAO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import java.time.LocalDate;
import java.util.List;

public class WorkHourController {

    public Button cancelButton;
    public Button addButton;
    // Champs liés au fichier FXML
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField morningTextField; // Champ pour le créneau du matin
    @FXML
    private TextField afternoonTextField; // Champ pour le créneau de l'après-midi
    @FXML
    private ComboBox<String> doctorComboBox;

    @FXML
    private boolean isValidTimeSlot(String timeSlot) {
        return timeSlot.matches("^\\d{2}:\\d{2}-\\d{2}:\\d{2}$");
    }

    @FXML// La ComboBox dans ton interface

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

    // Bouton "Ajouter"

    @FXML
    private void onAddAvailabilityButtonClick() {
        LocalDate selectedDate = datePicker.getValue();
        String morningSlot = morningTextField.getText();
        String afternoonSlot = afternoonTextField.getText();
        String selectedDoctor = doctorComboBox.getValue();

        // Vérifier que la date et le médecin sont sélectionnés
        if (selectedDate == null || selectedDoctor == null) {
            showAlert("Error", "Please select a date and a doctor.", Alert.AlertType.WARNING);
            return;
        }

        // Vérifier si au moins l'un des créneaux (matin ou après-midi) est rempli
        if ((morningSlot == null || morningSlot.isEmpty()) && (afternoonSlot == null || afternoonSlot.isEmpty())) {
            showAlert("Error", "Please fill in at least one of the time slots (morning or afternoon).", Alert.AlertType.WARNING);
            return;
        }

        // Vérifier les formats des créneaux s'ils sont remplis
        if (morningSlot != null && !morningSlot.isEmpty() && !isValidTimeSlot(morningSlot)) {
            showAlert("Error", "The morning time slot must be in the HH:mm-HH:mm format.", Alert.AlertType.WARNING);
            return;
        }
        if (afternoonSlot != null && !afternoonSlot.isEmpty() && !isValidTimeSlot(afternoonSlot)) {
            showAlert("Error", "The afternoon time slot must be in the HH:mm-HH:mm format.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Récupérer l'ID du docteur à partir de la `ComboBox`
            int doctorId = doctorMap.get(selectedDoctor);
            PlanningDAO planningDAO = new PlanningDAO();

            // Traiter le créneau du matin si rempli
            if (morningSlot != null && !morningSlot.isEmpty()) {
                List<Availability> morningAvailabilities = splitTimeSlot(morningSlot, selectedDate, doctorId);
                for (Availability availability : morningAvailabilities) {
                    planningDAO.addPlanning(availability);
                }
            }

            // Traiter le créneau de l'après-midi si rempli
            if (afternoonSlot != null && !afternoonSlot.isEmpty()) {
                List<Availability> afternoonAvailabilities = splitTimeSlot(afternoonSlot, selectedDate, doctorId);
                for (Availability availability : afternoonAvailabilities) {
                    planningDAO.addPlanning(availability);
                }
            }

            showAlert("Success", "Availability successfully added for the selected doctor!", Alert.AlertType.INFORMATION);
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while adding the availability.", Alert.AlertType.ERROR);
        }
    }


    // Bouton "Annuler"
    @FXML
    private void onCancelButtonClick() {
        clearFields();
    }

    // Méthode utilitaire pour afficher des alertes
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Méthode pour réinitialiser les champs
    private void clearFields() {
        datePicker.setValue(null);
        morningTextField.clear();
        afternoonTextField.clear();
    }



    public static List<Availability> splitTimeSlot(String timeSlot, LocalDate date, int doctorId) {
        List<Availability> availabilities = new ArrayList<>();

        // Supposons que `timeSlot` est au format "09:00-11:00"
        String[] times = timeSlot.split("-");
        LocalTime startTime = LocalTime.parse(times[0]);
        LocalTime endTime = LocalTime.parse(times[1]);

        // Diviser en créneaux horaires d'une heure
        LocalTime currentTime = startTime;
        while (currentTime.isBefore(endTime)) {
            LocalTime nextTime = currentTime.plusHours(1);
            if (nextTime.isAfter(endTime)) {
                nextTime = endTime; // Ajuster si la plage n'est pas un multiple d'une heure
            }

            availabilities.add(new Availability(doctorId, date, currentTime, nextTime, true));
            currentTime = nextTime;
        }

        return availabilities;
    }
}
