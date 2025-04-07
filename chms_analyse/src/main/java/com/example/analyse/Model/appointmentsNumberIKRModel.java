package com.example.analyse.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class appointmentsNumberIKRModel {

    private static  final Logger LOGGER = Logger.getLogger(appointmentsNumberIKRModel.class.getName());

    public Map<Integer, Integer> getAppointmentsPerMonth() {
        Map<Integer, Integer> data = new HashMap<>();
        String query = "SELECT MONTH(AppointmentDate) AS mois, COUNT(*) AS total_appointments " +
                "FROM appointment " +
                "WHERE YEAR(AppointmentDate) = YEAR(CURDATE()) " +
                "GROUP BY MONTH(AppointmentDate) " +
                "ORDER BY mois";

        try (Connection conn = com.example.analyse.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int month = rs.getInt("mois");
                int count = rs.getInt("total_appointments");
                data.put(month, count);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error: {0}", e.getMessage());
        }

        return data;
    }
}
