package com.example.analyse.Model;

import com.example.analyse.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestResultModel {

    public static class MonthlyTest {
        private final String month;
        private final int numberOfTests;

        public MonthlyTest(String month, int numberOfTests) {
            this.month = month;
            this.numberOfTests = numberOfTests;
        }

        public String getMonth() {
            return month;
        }

        public int getNumberOfTests() {
            return numberOfTests;
        }
    }

    public List<MonthlyTest> getMonthlyTests() {
        List<MonthlyTest> results = new ArrayList<>();

        String query = """
            SELECT 
                MONTHNAME(t.DateEffectue) AS Month, 
                COUNT(*) AS NumberOfTests 
            FROM testresult t 
            GROUP BY MONTH(t.DateEffectue)
            ORDER BY MONTH(t.DateEffectue);
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String month = resultSet.getString("Month");
                int numberOfTests = resultSet.getInt("NumberOfTests");
                results.add(new MonthlyTest(month, numberOfTests));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
}
