package com.example.analyse.Model;

import com.example.analyse.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentPerServiceHMZModel {
    public static class ServiceAppointments {
        private final String serviceName;
        private final int numberOfAppointments;

        public ServiceAppointments(String serviceName, int numberOfAppointments) {
            this.serviceName = serviceName;
            this.numberOfAppointments = numberOfAppointments;
        }

        public String getServiceName() {
            return serviceName;
        }

        public int getNumberOfAppointments() {
            return numberOfAppointments;
        }
    }

    public List<ServiceAppointments> getAppointmentsPerService() {
        List<ServiceAppointments> results = new ArrayList<>();

        String query = """
            SELECT s.NameService, COUNT(a.IDAppointment) AS NumberOfAppointments 
            FROM appointment a 
            JOIN service s ON a.IDService = s.IDService 
            GROUP BY s.NameService 
            ORDER BY s.NameService;
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String serviceName = resultSet.getString("NameService");
                int numberOfAppointments = resultSet.getInt("NumberOfAppointments");
                results.add(new ServiceAppointments(serviceName, numberOfAppointments));
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        return results;
    }
}
