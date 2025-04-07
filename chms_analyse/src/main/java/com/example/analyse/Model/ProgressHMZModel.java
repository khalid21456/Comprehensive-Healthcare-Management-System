package com.example.analyse.Model;

import com.example.analyse.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ProgressHMZModel {

    public double getAppointmentsProgressThisYear() {
        int currentYear = java.time.Year.now().getValue();
        int lastYear = currentYear - 1;

        // Get number of appointments for the current year
        int currentYearAppointments = getAppointmentsCountForYear(currentYear);

        // Get number of appointments for the last year
        int lastYearAppointments = getAppointmentsCountForYear(lastYear);

        // Calculate progress as a percentage of appointments this year vs. last year
        return calculateProgress(currentYearAppointments, lastYearAppointments);
    }

    private int getAppointmentsCountForYear(int year) {
        String query = """
            SELECT COUNT(IDAppointment) AS AppointmentCount 
            FROM appointment 
            WHERE YEAR(AppointmentDate) = ?
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, year);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("AppointmentCount");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        return 0; // Default value if no data found
    }

    private double calculateProgress(int currentYearAppointments, int lastYearAppointments) {
        if (lastYearAppointments == 0) {
            return 100; // If last year's appointments are 0, assume 100% progress
        }
        return ((double) currentYearAppointments / lastYearAppointments) * 100;
    }
}
