package com.example.analyse.Model;
import com.example.analyse.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
public class InsuranceClaimsKDJModel {
    public  Map<String, Integer> getInsuranceClaimsByYear() {
        Map<String, Integer> claimsByYear = new HashMap<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT YEAR(DateReclamation) AS Year, COUNT(*) AS Claims " +
                    "FROM ReclamationAssurance " +
                    "GROUP BY YEAR(DateReclamation)";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String year = resultSet.getString("Year");
                int claims = resultSet.getInt("Claims");
                claimsByYear.put(year, claims);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return claimsByYear;
    }
}
