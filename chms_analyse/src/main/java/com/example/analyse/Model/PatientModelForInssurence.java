package com.example.analyse.Model;

import com.example.analyse.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientModelForInssurence {

    public double[] getPatientInsuranceData() {
        String query = """
        SELECT 
            COUNT(IDInsurance) / COUNT(IDPatient) * 100 AS percentageInsured,
            (SELECT COUNT(IDPatient) FROM patient WHERE IDInsurance IS NULL) / COUNT(IDPatient) * 100 AS percentageUninsured
        FROM patient;
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return new double[]{
                        resultSet.getDouble("percentageInsured"),
                        resultSet.getDouble("percentageUninsured")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return default values if no data is found or an exception occurs
        return new double[]{0.0, 0.0};
    }
}
