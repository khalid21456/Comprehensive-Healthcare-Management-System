package com.example.analyse.Model;
import com.example.analyse.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestFreqModel {

    public List<TestData> getTop5FrequentTests() {
        List<TestData> topTests = new ArrayList<>();
        String query = """
            SELECT 
                COUNT(*) AS number_of_tests, 
                t.Name AS test_name
            FROM 
                testresult r
            JOIN 
                test t 
            ON 
                t.IDTest = r.IDTest
            GROUP BY 
                r.IDTest, t.Name
            ORDER BY 
                COUNT(*) DESC
            LIMIT 5;
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int frequency = resultSet.getInt("number_of_tests");
                String testName = resultSet.getString("test_name");
                topTests.add(new TestData(testName, frequency));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topTests;
    }

    public static class TestData {
        private String testName;
        private int frequency;

        public TestData(String testName, int frequency) {
            this.testName = testName;
            this.frequency = frequency;
        }

        public String getTestName() {
            return testName;
        }

        public int getFrequency() {
            return frequency;
        }
    }
}
