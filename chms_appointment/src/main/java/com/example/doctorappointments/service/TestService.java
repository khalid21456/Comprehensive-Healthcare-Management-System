package com.example.doctorappointments.service;

import com.example.doctorappointments.model.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestService {

    public ObservableList<Test> getAllTests() {
        ObservableList<Test> tests = FXCollections.observableArrayList();

        String query = "SELECT * FROM test";

        try (Connection conn = DatabaseConnection.getConnection();) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Test test = new Test(
                        rs.getInt("IDTest"),
                        rs.getString("Name"),
                        rs.getDouble("MaxVal"),
                        rs.getDouble("MinVal"),
                        rs.getDouble("Price")
                );
                tests.add(test);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving medications: " + e.getMessage());
        }
        System.out.println(tests);


        return tests;
    }

}
