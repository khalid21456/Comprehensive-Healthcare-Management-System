package com.example.analyse.Model;

import com.example.analyse.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class FidelityModelKFC {
    public double getFidelityPercentage() {
        double percentage = 0.0;

        String query = """
                SELECT (COUNT(DISTINCT a.IDPatient) * 100.0 / 
                        (SELECT COUNT(DISTINCT IDPatient) FROM appointment)) AS PourcentageFidèles 
                FROM appointment a 
                WHERE a.AppointmentDate >= CURDATE() - INTERVAL 6 MONTH 
                AND a.IDPatient IN ( 
                    SELECT IDPatient 
                    FROM appointment 
                    WHERE AppointmentDate >= CURDATE() - INTERVAL 6 MONTH 
                    GROUP BY IDPatient 
                    HAVING COUNT(IDAppointment) >= 3
                )
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                percentage = resultSet.getDouble("PourcentageFidèles");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return percentage;
    }
}
