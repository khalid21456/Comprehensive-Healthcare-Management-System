package org.example.patient.controllers;
import org.example.patient.models.Insurance;
import org.example.patient.models.Patient;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao {
	
	public PatientDaoImpl() {
		
	}

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patient";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Patient patient = new Patient(
                    resultSet.getInt("IDPatient"),
                    resultSet.getString("Nom"),
                    resultSet.getString("Prenom"),
                    resultSet.getString("Sexe"),
                    resultSet.getTimestamp("BirthDate").toLocalDateTime(),
                    resultSet.getString("Adresse"),
                    resultSet.getString("Tel"),
                    resultSet.getInt("IDInsurance"),
                    resultSet.getString("CIN"),
                    resultSet.getString("Ville"),
                    resultSet.getString("password")
                );
                patients.add(patient);
            }
        }

        return patients;
    }

    @Override
    public Patient getPatientById(int id) throws SQLException {
        String query = "SELECT * FROM patient WHERE IDPatient = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Patient(
                        resultSet.getInt("IDPatient"),
                        resultSet.getString("Nom"),
                        resultSet.getString("Prenom"),
                        resultSet.getString("Sexe"),
                        resultSet.getTimestamp("BirthDate").toLocalDateTime(),
                        resultSet.getString("Adresse"),
                        resultSet.getString("Tel"),
                        resultSet.getInt("IDInsurance"),
                        resultSet.getString("CIN"),
                        resultSet.getString("Ville"),
                        resultSet.getString("password")
                    );
                }
            }
        }

        return null; 
    }

   

    @Override
    public boolean updatePatient(Patient patient) throws SQLException {
        String query = "UPDATE patient SET Nom = ?, Prenom = ?, Sexe = ?, BirthDate = ?, Adresse = ?, Tel = ?, IDInsurance = ?, CIN = ?, Ville = ? WHERE IDPatient = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, patient.getNom());
            preparedStatement.setString(2, patient.getPrenom());
            preparedStatement.setString(3, patient.getSexe());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(patient.getBirthDate()));
            preparedStatement.setString(5, patient.getAdresse());
            preparedStatement.setString(6, patient.getTel());
            preparedStatement.setInt(7, patient.getIdInsurance());
            preparedStatement.setString(8, patient.getCin());
            preparedStatement.setString(9, patient.getVille());
            preparedStatement.setInt(10, patient.getIdPatient());

            return preparedStatement.executeUpdate() > 0;
        }
    }
    
    @Override
    public List<Insurance> getAllInsurances() {
        List<Insurance> insurances = new ArrayList<>();
        String query = "SELECT * FROM insurance";  // SQL query to fetch all insurance records

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int idInsurance = rs.getInt("IDInsurance");
                String nameInsurance = rs.getString("NameInsurance");
                double percentage = rs.getDouble("Pourcentage");

                insurances.add(new Insurance(idInsurance, nameInsurance, percentage));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return insurances;
    }
    
    @Override
    public boolean insertPatient(Patient patient) throws SQLException {
        String query = "INSERT INTO patient (Nom, Prenom, Sexe, BirthDate, Adresse, Tel, IDInsurance, CIN, Ville, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, patient.getNom());
            preparedStatement.setString(2, patient.getPrenom());
            preparedStatement.setString(3, patient.getSexe());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(patient.getBirthDate()));
            preparedStatement.setString(5, patient.getAdresse());
            preparedStatement.setString(6, patient.getTel());
            preparedStatement.setInt(7, patient.getIdInsurance());
            preparedStatement.setString(8, patient.getCin());
            preparedStatement.setString(9, patient.getVille());
            preparedStatement.setString(10, patient.getPassword());

            return preparedStatement.executeUpdate() > 0;
        }
    }
    
    @Override
    public Patient verifyCredentials(String cin, String password) throws SQLException {
        String sql = "SELECT * FROM patient WHERE CIN = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cin);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Patient(
                        rs.getInt("IDPatient"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Sexe"),
                        rs.getTimestamp("BirthDate").toLocalDateTime(),
                        rs.getString("Adresse"),
                        rs.getString("Tel"),
                        rs.getInt("IDInsurance"),
                        rs.getString("CIN"),
                        rs.getString("Ville"),
                        rs.getString("password")
                    );
                }
            }
        }
        return null;
    }
}