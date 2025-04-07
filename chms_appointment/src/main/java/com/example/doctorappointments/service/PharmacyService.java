package com.example.doctorappointments.service;

import com.example.doctorappointments.model.Medication;
import com.example.doctorappointments.model.Pharmacy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PharmacyService {

    public ObservableList<Medication> getAllMedications() {
        ObservableList<Medication> medications = FXCollections.observableArrayList();

        String query = "SELECT * FROM medicament ";

        try (Connection conn = DatabaseConnection.getConnection();) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Medication medication = new Medication(
                            rs.getInt("IDMedicament"),
                            rs.getString("NomMed"),
                            rs.getString("DescMed"),
                            rs.getDouble("Prix_Unit"),
                            rs.getTimestamp("DateExpiration"),
                            rs.getString("Type"),
                            rs.getInt("Qty")
                    );
                    medications.add(medication);
                }
        } catch (SQLException e) {
            System.err.println("Error retrieving medications: " + e.getMessage());
        }


        return medications;
    }

    public ObservableList<Pharmacy> getAllPharmacies() {
        ObservableList<Pharmacy> pharmacies = FXCollections.observableArrayList();

        String query = "SELECT * FROM pharmacien ";

        try (Connection conn = DatabaseConnection.getConnection();) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Pharmacy pharmacy = new Pharmacy(
                        rs.getInt("IDPharmacien"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Tel"),
                        rs.getString("Adresse")
                );
                pharmacies.add(pharmacy);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving pharmacies: " + e.getMessage());
        }

        return pharmacies;
    }
}
