package com.example.doctorappointments.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PostponeAppointmentService {



    public static List<String> getAllDoctorNames() {
        List<String> doctorNames = new ArrayList<>();
        String query = "SELECT Nom, Prenom FROM doctor";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("Prenom");
                String lastName = resultSet.getString("Nom");
                String fullName = lastName + " " + firstName;
                doctorNames.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorNames;
    }




    public static List<LocalDate> getAvailableDates(String doctorName) {
        List<LocalDate> availableDates = new ArrayList<>();

        String[] doctorNameParts = doctorName.split(" ");
        if (doctorNameParts.length < 2) {
            return availableDates;
        }

        String lastName = doctorNameParts[0];
        String firstName = doctorNameParts[1];

        String query = """
        SELECT Date
        FROM planning
        WHERE availability = 1 AND IDDoctor = (
            SELECT IDDoctor FROM doctor WHERE Nom = ? AND Prenom = ?
        )
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, lastName);
            statement.setString(2, firstName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                availableDates.add(resultSet.getDate("Date").toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableDates;
    }





    public static List<String> getAvailableHours(String doctorName, LocalDate selectedDate) {
        List<String> availableHours = new ArrayList<>();
        String[] doctorNameParts = doctorName.split(" ");
        if (doctorNameParts.length < 2) {
            return availableHours;
        }

        String lastName = doctorNameParts[0];
        String firstName = doctorNameParts[1];

        String query = """
        SELECT Date_Start, Date_Fin
        FROM planning
        WHERE availability = 1
          AND IDDoctor = (
              SELECT IDDoctor FROM doctor WHERE Nom = ? AND Prenom = ?
          )
          AND Date = ?
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setDate(3, Date.valueOf(selectedDate));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Time startTime = resultSet.getTime("Date_Start");
                Time endTime = resultSet.getTime("Date_Fin");
                if (startTime != null && endTime != null) {
                    LocalTime start = startTime.toLocalTime();
                    LocalTime end = endTime.toLocalTime();
                    availableHours.add(start + " - " + end);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableHours;
    }





    public static int getDoctorId(String doctorName) {
        String[] doctorNameParts = doctorName.split(" ");
        if (doctorNameParts.length < 2) {
            throw new IllegalArgumentException("Invalid doctor name format.");
        }

        String lastName = doctorNameParts[0];
        String firstName = doctorNameParts[1];

        String query = "SELECT IDDoctor FROM doctor WHERE Nom = ? AND Prenom = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("IDDoctor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Doctor not found in the database.");
    }




    public static boolean updatePlanning(int appointmentID, LocalDate newDate, LocalTime startTime, LocalTime endTime, int newDoctorId) {
        String updatePlanningQuery = """
        UPDATE planning
        SET availability = 0
        WHERE IDDoctor = ?
          AND Date = ?
          AND Date_Start = ?
          AND Date_Fin = ?
    """;

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement planningStmt = connection.prepareStatement(updatePlanningQuery)) {
                planningStmt.setInt(1, newDoctorId);
                planningStmt.setDate(2, Date.valueOf(newDate));
                planningStmt.setTime(3, Time.valueOf(startTime));
                planningStmt.setTime(4, Time.valueOf(endTime));
                planningStmt.executeUpdate();
            }

            boolean isAppointmentUpdated = AppointmentService.updateAppointment(appointmentID, newDoctorId, newDate, startTime);

            if (!isAppointmentUpdated) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
