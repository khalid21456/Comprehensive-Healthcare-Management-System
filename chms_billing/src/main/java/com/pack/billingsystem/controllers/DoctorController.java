package com.pack.billingsystem.controllers;

import com.pack.billingsystem.models.Doctor;

import java.sql.*;

public class DoctorController {
    public static Doctor getDoctorById(int doctorId) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        Doctor doctor = null;

        String query = "SELECT * FROM Doctor WHERE IDDoctor = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, doctorId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            doctor = new Doctor();
            doctor.setIdDoctor(rs.getInt("IDDoctor"));
            doctor.setPrenom(rs.getString("prenom"));
            doctor.setNom(rs.getString("nom"));
            doctor.setTelephone(rs.getString("tel"));
            // Ajoutez d'autres champs si nécessaire
        }

        rs.close();
        pstmt.close();
        conn.close();

        return doctor;
    }
}
