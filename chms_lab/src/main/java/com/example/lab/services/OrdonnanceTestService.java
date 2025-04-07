package com.example.lab.services;

import com.example.lab.database.DatabaseConnection;
import com.example.lab.models.OrdonnanceTest;
import com.example.lab.models.TestResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdonnanceTestService {

    // Method to get ordonnance tests by status
    public List<OrdonnanceTest> getOrdonnanceTestsByStatus(String status) {
        List<OrdonnanceTest> ordonnanceTests = new ArrayList<>();
        String query = "SELECT * FROM ordonnancetest WHERE Status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrdonnanceTest ordonnanceTest = new OrdonnanceTest(
                        rs.getInt("IDOrdonnanceTest"),
                        rs.getInt("IDDoctor"),
                        rs.getString("DateOrdonnanceTest"),
                        rs.getInt("IDPatient"),
                        rs.getString("Status")
                );

                // Fetch the test results for the ordonnance test
                TestService testService = new TestService();
                List<TestResult> testResults = testService.getTestResultsByOrdonnance(ordonnanceTest.getIdOrdonnanceTest());
                ordonnanceTest.setTestResults(testResults);  // Set test results

                ordonnanceTests.add(ordonnanceTest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordonnanceTests;
    }

    // Method to update the status of an ordonnance test
    public void updateOrdonnanceTestStatus(int idOrdonnanceTest, String status) {
        String query = "UPDATE ordonnancetest SET Status = ? WHERE IDOrdonnanceTest = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setInt(2, idOrdonnanceTest);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
