package com.example.doctorappointments.service;

import com.example.doctorappointments.model.Doctor;
import com.example.doctorappointments.model.Speciality;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorService {
    public static ObservableList<Doctor> getDoctorDetails(int id) {
        ObservableList<Doctor> doctors = FXCollections.observableArrayList();

        String query = "SELECT * FROM doctor WHERE IDDoctor = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) { // Check if ResultSet is empty
                System.out.println("No doctor found with ID: " + id);
                return doctors;
            }

            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getInt("IDDoctor"),
                        rs.getInt("IDSpeciality"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Tel"),
                        rs.getString("Adresse")
                );
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving doctors: " + e.getMessage());
        }

        return doctors;
    }

    public static boolean insertDoctor(int idSpeciality, String nom, String prenom, String tel, String adresse) {
        String sql = "INSERT INTO doctor (IDSpeciality, Nom, Prenom, Tel, Adresse) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idSpeciality);
            pstmt.setString(2, nom);
            pstmt.setString(3, prenom);
            pstmt.setString(4, tel);
            pstmt.setString(5, adresse);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting doctor: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateDoctor(int id, int idSpeciality, String nom, String prenom, String tel, String adresse) {
        String sql = "UPDATE doctor SET IDSpeciality = ?, Nom = ?, Prenom = ?, Tel = ?, Adresse = ? WHERE IDDoctor = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idSpeciality);
            pstmt.setString(2, nom);
            pstmt.setString(3, prenom);
            pstmt.setString(4, tel);
            pstmt.setString(5, adresse);
            pstmt.setInt(6, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating doctor: " + e.getMessage());
            return false;
        }
    }

    public static ObservableList<Speciality> getAllSpecialities() {
        ObservableList<Speciality> specialities = FXCollections.observableArrayList();

        String query = "SELECT * FROM speciality";

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Speciality speciality = new Speciality(
                        rs.getInt("IDSpeciality"),
                        rs.getString("NomSpeciality")
                );
                specialities.add(speciality);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving specialities: " + e.getMessage());
        }

        return specialities;
    }

    public ObservableList<Doctor> getAllDoctors() {
        ObservableList<Doctor> doctors = FXCollections.observableArrayList();
        String query = "SELECT doctor.IDDoctor, doctor.IDSpeciality, doctor.Nom, doctor.Prenom, doctor.Tel, doctor.Adresse, speciality.NomSpeciality " +
                "FROM doctor " +
                "JOIN speciality ON doctor.IDSpeciality = speciality.IDSpeciality";  // Modify this query to suit your database schema

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getInt("IDDoctor"),
                        rs.getInt("IDSpeciality"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Tel"),
                        rs.getString("Adresse")
                );
                doctor.setSpecialityName(rs.getString("NomSpeciality"));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving doctors: " + e.getMessage());
        }
        return doctors;
    }

    public static boolean deleteDoctor(int id) {
        String deleteAppointmentsSql = "DELETE FROM appointment WHERE IDDoctor = ?";
        String deleteDoctorSql = "DELETE FROM doctor WHERE IDDoctor = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement deleteAppointmentsStmt = conn.prepareStatement(deleteAppointmentsSql);
                 PreparedStatement deleteDoctorStmt = conn.prepareStatement(deleteDoctorSql)) {

                deleteAppointmentsStmt.setInt(1, id);
                deleteAppointmentsStmt.executeUpdate();

                deleteDoctorStmt.setInt(1, id);
                int affectedRows = deleteDoctorStmt.executeUpdate();

                conn.commit(); // Commit transaction
                return affectedRows > 0;

            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                System.err.println("Error deleting doctor: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting doctor: " + e.getMessage());
            return false;
        }
    }

}