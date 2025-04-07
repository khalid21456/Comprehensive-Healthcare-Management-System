package com.example.analyse.Model;


import com.example.analyse.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
public class TestTimeTraitementKDJModel {
    public double getAverageProcessingTime() {
        double averageTime = 0.0;
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Requête SQL pour calculer la moyenne

            String query = "SELECT AVG(TIMESTAMPDIFF(HOUR, DateEffectue, ResultDate)) AS AverageProcessingTime FROM TestResult";

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                averageTime = resultSet.getDouble("AverageProcessingTime");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return averageTime;
    }
}
