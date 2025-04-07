package com.example.doctorappointments.controller;

import com.example.doctorappointments.service.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorAvailabilityController {


    @FXML
    private ComboBox<String> doctorComboBox;

    @FXML
    private ListView<String> availabilityListView;

    @FXML
    public void initialize() {
        populateDoctorComboBox();
    }

    private void populateDoctorComboBox() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT Nom FROM doctor";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                doctorComboBox.getItems().add(resultSet.getString("Nom"));
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDoctorSelection() {
        String selectedDoctor = doctorComboBox.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            displayDoctorAvailability(selectedDoctor);
        }
    }

    private void displayDoctorAvailability(String doctorName) {
        availabilityListView.getItems().clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT Date, Date_Start, Date_Fin FROM planning WHERE IDDoctor = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, doctorName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String date = resultSet.getString("Date");
                String morningSlot = resultSet.getString("Date_Start");
                String afternoonSlot = resultSet.getString("Date_Fin");

                String availability = String.format("Date: %s | Morning: %s | Afternoon: %s",
                        date, morningSlot, afternoonSlot);
                availabilityListView.getItems().add(availability);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
