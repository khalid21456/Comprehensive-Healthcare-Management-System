package com.example.analyse.Model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
public class NumberPatientIKRModel {

    public Map<Integer, Integer> getPatientsPerYear() {
        Map<Integer, Integer> data = new HashMap<>();
        String query = "SELECT YEAR(AppointmentDate) AS annee, COUNT(DISTINCT IDPatient) AS total_patients " +
                "FROM appointment " +
                "WHERE AppointmentDate >= DATE_SUB(CURDATE(), INTERVAL 5 YEAR) " +
                "GROUP BY YEAR(AppointmentDate)";

        try (Connection conn = com.example.analyse.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (conn == null) {
                System.err.println("Database connection is null. Please check the connection.");
                return data;
            }

            while (rs.next()) {
                int year = rs.getInt("annee");
                int count = rs.getInt("total_patients");
                data.put(year, count);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }
}
