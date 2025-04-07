package com.pack.billingsystem.controllers;

import com.pack.billingsystem.models.OrdonnanceTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestController {

    public static List<OrdonnanceTest> getNonPayeTestsByPatient(int patientID) throws SQLException {
        List<OrdonnanceTest> testsNonPayes = new ArrayList<>();

        String query = """
                SELECT o.IDOrdonnanceTest AS IDTest, t.Name AS nom_test, tr.DateEffectue AS date_test, t.Price AS prix_test, tr.ResultValue AS resultat_patient
                FROM test t
                JOIN testResult tr ON t.IDTest = tr.IDTest
                JOIN ordonnancetest o ON tr.IDOrdonnanceTest = o.IDOrdonnanceTest
                JOIN patient p ON o.IDPatient = p.IDPatient
                WHERE p.IDPatient = ? AND o.Status = 0
                ORDER BY DateOrdonnanceTest DESC
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OrdonnanceTest ordonnanceTest = new OrdonnanceTest();
                    ordonnanceTest.setIdOrdonnanceTest(rs.getInt("IDTest"));
                    ordonnanceTest.setTestNom(rs.getString("nom_test"));
                    ordonnanceTest.setDate(rs.getDate("date_test"));
                    ordonnanceTest.setResultat(rs.getInt("resultat_patient"));
                    ordonnanceTest.setPrix(rs.getDouble("prix_test"));
                    testsNonPayes.add(ordonnanceTest);
                }
            }
        }
        return testsNonPayes;
    }

    public static void updateOrdonnanceTestStatus(int IDPatient) throws SQLException {
        List<OrdonnanceTest> tests = getNonPayeTestsByPatient(IDPatient);
        for (OrdonnanceTest test : tests) {
            int IDTest = test.getIdOrdonnanceTest();
            String updateOrdonnanceTestQuery = "UPDATE OrdonnanceTest SET status = 1 WHERE IDOrdonnanceTest = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(updateOrdonnanceTestQuery)) {
                pstmt.setInt(1, IDTest);
                pstmt.executeUpdate();
            }
        }

    }
}
