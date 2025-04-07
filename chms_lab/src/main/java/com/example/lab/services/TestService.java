package com.example.lab.services;

import com.example.lab.database.DatabaseConnection;
import com.example.lab.models.Test;
import com.example.lab.models.TestResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestService {

    // Existing methods for managing tests

    // Method to retrieve all tests
    public List<Test> getAllTests() {
        List<Test> tests = new ArrayList<>();
        String query = "SELECT * FROM test";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Test test = new Test(rs.getInt("IDTest"),
                        rs.getString("Name"),
                        rs.getDouble("MinVal"),
                        rs.getDouble("MaxVal"),
                        rs.getDouble("Price"),
                        rs.getString("Description"),
                        rs.getString("Unit"));
                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }

    // Method to delete a test by ID
    public void deleteTest(Test test) {
        String query = "DELETE FROM test WHERE IDTest = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, test.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a test by ID
    public void updateTest(Test test) {
        String query = "UPDATE test SET Name = ?, MinVal = ?, MaxVal = ?, Price = ?, Description = ?, Unit = ? WHERE IDTest = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, test.getName());
            stmt.setDouble(2, test.getMinValue());
            stmt.setDouble(3, test.getMaxValue());
            stmt.setDouble(4, test.getPrice());
            stmt.setString(5, test.getDescription());
            stmt.setString(6, test.getUnit());
            stmt.setInt(7, test.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a new test
    public void addTest(String name, double minValue, double maxValue, double price, String description, String unit) {
        String query = "INSERT INTO test (Name, MinVal, MaxVal, Price, Description, Unit) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setDouble(2, minValue);
            stmt.setDouble(3, maxValue);
            stmt.setDouble(4, price);
            stmt.setString(5, description);
            stmt.setString(6, unit);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Test> getTestsForOrdonnanceTest(int ordonnanceTestId) {
        List<Test> tests = new ArrayList<>();
        String query = "SELECT t.*, tr.ResultValue, tr.ResultDate, tr.DateEffectue FROM test t " +
                "JOIN testresult tr ON t.IDTest = tr.IDTest " +
                "WHERE tr.IDOrdonnanceTest = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ordonnanceTestId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Test test = new Test(rs.getInt("IDTest"),
                        rs.getString("Name"),
                        rs.getDouble("MinVal"),
                        rs.getDouble("MaxVal"),
                        rs.getDouble("Price"),
                        rs.getString("Description"),
                        rs.getString("Unit"));

                // Fixing result retrieval
                TestResult testResult = new TestResult(
                        ordonnanceTestId,
                        rs.getInt("IDTest"),
                        rs.getDouble("ResultValue"),
                        rs.getString("ResultDate"),
                        rs.getString("DateEffectue")
                );

                test.setTestResult(testResult.getResultValue());
                test.setResultDate(testResult.getResultDate());

                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }






    public void insertOrUpdateTestResult(TestResult testResult) {
        String query = "INSERT INTO testresult (IDOrdonnanceTest, IDTest, ResultValue, ResultDate, DateEffectue) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE ResultValue = ?, ResultDate = ?, DateEffectue = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, testResult.getIdOrdonnanceTest());
            stmt.setInt(2, testResult.getIdTest());
            stmt.setDouble(3, testResult.getResultValue());
            stmt.setString(4, testResult.getResultDate());

            // Handle DateEffectue validation
            if (testResult.getDateEffectue() == null || testResult.getDateEffectue().isEmpty()) {
                stmt.setNull(5, Types.DATE);
                stmt.setNull(8, Types.DATE); // For update
            } else {
                stmt.setString(5, testResult.getDateEffectue());
                stmt.setString(8, testResult.getDateEffectue()); // For update
            }

            // Update values
            stmt.setDouble(6, testResult.getResultValue());
            stmt.setString(7, testResult.getResultDate());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public List<TestResult> getTestResultsByOrdonnance(int idOrdonnanceTest) {
        List<TestResult> testResults = new ArrayList<>();
        String query = "SELECT tr.*, t.Name, t.MinVal, t.MaxVal " +
                "FROM testresult tr " +
                "JOIN test t ON tr.IDTest = t.IDTest " +
                "WHERE tr.IDOrdonnanceTest = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idOrdonnanceTest);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TestResult testResult = new TestResult(
                        rs.getInt("IDOrdonnanceTest"),
                        rs.getInt("IDTest"),
                        rs.getDouble("ResultValue"),
                        rs.getString("ResultDate"),
                        rs.getString("DateEffectue")
                );

                // Set additional fields
                testResult.setTestName(rs.getString("Name"));
                testResult.setMinValue(rs.getDouble("MinVal"));
                testResult.setMaxValue(rs.getDouble("MaxVal"));

                testResults.add(testResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testResults;
    }


    // Method to update the ordonnance status to "Completed"
    public void updateOrdonnanceStatus(int ordonnanceId, String status) {
        String query = "UPDATE ordonnancetest SET Status = ? WHERE IDOrdonnanceTest = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status); // Set the new status
            stmt.setInt(2, ordonnanceId); // Set the ordonnance ID

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a test result by ID
    public void deleteTestResult(TestResult testResult) {
        String query = "DELETE FROM testresult WHERE IDOrdonnanceTest = ? AND IDTest = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, testResult.getIdOrdonnanceTest());
            stmt.setInt(2, testResult.getIdTest());
            stmt.executeUpdate();
        } catch (SQLException e)            {
            e.printStackTrace();
        }
    }
}
