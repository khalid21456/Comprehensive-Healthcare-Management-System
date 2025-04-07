package com.pack.billingsystem.controllers;

import com.pack.billingsystem.models.Medicament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentController {

    public static List<Medicament> getMedicamentsByPatient(int patientID) throws SQLException {
        List<Medicament> medicaments = new ArrayList<>();

        String query = """
                SELECT orm.IDOrdonnance AS IDMedicament, m.NomMed AS nom_medicament, m.Prix_Unit AS prix_medicament
                FROM medicament m
                JOIN ordonnance_medicament om ON m.IDMedicament = om.IDMedicament
                JOIN ordonnance orm ON om.IDOrdonnance = orm.IDOrdonnance
                WHERE orm.IDPatient = ? AND orm.Status = 'Delivred'
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Medicament medicament = new Medicament();
                    medicament.setIdMedicament(rs.getInt("IDMedicament"));
                    medicament.setNom(rs.getString("nom_medicament"));
                    medicament.setPrix(rs.getDouble("prix_medicament"));
                    medicaments.add(medicament);
                }
            }
        }
        return medicaments;
    }

    public static void updateOrdonnanceStatus(int IDPatient) throws SQLException {
        List<Medicament> medicaments = getMedicamentsByPatient(IDPatient);
        for (Medicament medicament : medicaments) {
            int idMedicament = medicament.getIdMedicament();
            String updateOrdonnanceQuery = "UPDATE ordonnance SET status = 'Completed' WHERE IDOrdonnance = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(updateOrdonnanceQuery)) {
                pstmt.setInt(1, idMedicament);
                pstmt.executeUpdate();
            }
        }
    }
}
