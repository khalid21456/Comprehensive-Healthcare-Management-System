package com.example.analyse.Model;
import com.example.analyse.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
public class GenderAgeKDJModel {
    public Map<String, Map<String, Integer>> getAgeGroupsBySex() {
        Map<String, Map<String, Integer>> ageGroups = new HashMap<>();
        ageGroups.put("Male", new HashMap<>());
        ageGroups.put("Female", new HashMap<>());

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT Sexe, FLOOR(DATEDIFF(CURDATE(), BirthDate) / 365) AS Age FROM Patient";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String sex = resultSet.getString("Sexe");
                int age = resultSet.getInt("Age");

                String ageGroup = (age / 10) * 10 + "-" + ((age / 10) * 10 + 9);

                if (sex.equalsIgnoreCase("Male")) {
                    ageGroups.get("Male").put(ageGroup, ageGroups.get("Male").getOrDefault(ageGroup, 0) + 1);
                } else if (sex.equalsIgnoreCase("Female")) {
                    ageGroups.get("Female").put(ageGroup, ageGroups.get("Female").getOrDefault(ageGroup, 0) + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ageGroups;
    }
}
