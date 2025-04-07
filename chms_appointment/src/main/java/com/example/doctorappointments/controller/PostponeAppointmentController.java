package com.example.doctorappointments.controller;

import com.example.doctorappointments.service.AppointmentService;
import com.example.doctorappointments.service.PostponeAppointmentService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class PostponeAppointmentController {

    public interface PostponeCallback {
        void onPostponeSuccess();
    }
    private PostponeCallback callback;

    private int appointmentID;
    private Timestamp appointmentDate;
    private String doctorName;
    private String patientName;

    @FXML
    private Label title;

    @FXML
    private Label current_appointment_date;

    @FXML
    private Label doctor_label;

    @FXML
    private DatePicker new_date;

    @FXML
    private Label patient_label;

    @FXML
    private ComboBox<String> new_doctor;

    @FXML
    private ComboBox<String> new_time;

    @FXML
    private Button submit_button;

    @FXML
    private Button cancel_button;


    @FXML
    public void initialize() {
        populateDoctorComboBox();
        configureDatePicker();
        configureTimeComboBox();
    }



    public void setInterfaceDetails(int appointmentID, Timestamp appointmentDate, String doctorName, String patientName) {
        System.out.println("Appointment ID: " + appointmentID);
        this.appointmentID = appointmentID;
        this.appointmentDate = appointmentDate;
        this.doctorName = doctorName;
        this.patientName = patientName;

        title.setText("Postpone Appointment : " + appointmentID);
        current_appointment_date.setText("Current Schedule: " + appointmentDate);
        doctor_label.setText("Doctor: " + doctorName);
        patient_label.setText("Patient: " + patientName);
    }

    private void populateDoctorComboBox() {
        try {
            List<String> doctorNames = PostponeAppointmentService.getAllDoctorNames();
            doctorNames.sort(String::compareToIgnoreCase);
            new_doctor.setItems(FXCollections.observableArrayList(doctorNames));

            new_doctor.valueProperty().addListener((obs, oldDoctor, newDoctor) -> configureDatePicker());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCallback(PostponeCallback callback) {
        this.callback = callback;
    }


    private void configureDatePicker() {
        new_date.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                String selectedDoctor = new_doctor.getValue();
                List<LocalDate> availableDates = selectedDoctor != null
                        ? PostponeAppointmentService.getAvailableDates(selectedDoctor)
                        : Collections.emptyList();

                if (empty || !availableDates.contains(date)) {
                    setDisable(true);
                    setStyle("-fx-background-color:  #F2F2F2;");
                } else {
                    setDisable(false);
                    setStyle("");
                }
            }
        });
        new_doctor.valueProperty().addListener((obs, oldDoctor, newDoctor) -> new_date.setValue(null));
    }



    private void configureTimeComboBox() {
        new_date.valueProperty().addListener((obs, oldDate, newDate) -> updateAvailableHours());
        new_doctor.valueProperty().addListener((obs, oldDoctor, newDoctor) -> updateAvailableHours());
    }

    private void updateAvailableHours() {
        LocalDate selectedDate = new_date.getValue();
        String selectedDoctor = new_doctor.getValue();

        if (selectedDate == null || selectedDoctor == null || selectedDoctor.isEmpty()) {
            new_time.setItems(FXCollections.observableArrayList());
            return;
        }
        List<String> availableHours = PostponeAppointmentService.getAvailableHours(selectedDoctor, selectedDate);

        new_time.setItems(FXCollections.observableArrayList(availableHours));
    }



    @FXML
    private void onSubmit() {
        if (validateInputs()) {
            try {
                String selectedDoctor = new_doctor.getValue();
                LocalDate selectedDate = new_date.getValue();
                String selectedTime = new_time.getValue();

                int newDoctorId = PostponeAppointmentService.getDoctorId(selectedDoctor);
                String[] timeRange = selectedTime.split(" - ");
                LocalTime startTime = LocalTime.parse(timeRange[0]);
                LocalTime endTime = LocalTime.parse(timeRange[1]);

                boolean success = PostponeAppointmentService.updatePlanning(
                        appointmentID, selectedDate, startTime, endTime, newDoctorId);

                if (success) {
                    if (callback != null) {
                        callback.onPostponeSuccess(); // Notify AppointmentController
                    }
                    Stage currentStage = (Stage) submit_button.getScene().getWindow();
                    showConfirmationMessage("Appointment successfully postponed!",currentStage);
                } else {
                    showErrorMessage("Failed to postpone the appointment. Please try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorMessage("An error occurred while postponing the appointment.");
            }
        }
    }

    private boolean validateInputs() {
        if (new_doctor.getValue() == null || new_doctor.getValue().isEmpty()) {
            showErrorMessage("Please select a new doctor.");
            return false;
        }
        if (new_date.getValue() == null) {
            showErrorMessage("Please select a new date.");
            return false;
        }
        if (new_time.getValue() == null || new_time.getValue().isEmpty()) {
            showErrorMessage("Please select a new time.");
            return false;
        }
        return true;
    }
    private void showConfirmationMessage(String message, Stage currentStage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setOnCloseRequest(event -> {
            currentStage.close();
        });
        alert.showAndWait();
    }


    private void showErrorMessage(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onCancel(){
        Stage currentStage = (Stage) cancel_button.getScene().getWindow();
        currentStage.close();

    }
}
