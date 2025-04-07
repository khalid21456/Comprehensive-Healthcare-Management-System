package com.example.doctorappointments.service;

import com.example.doctorappointments.model.DoctorDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {


    public List<DoctorDTO> getAllDoctors() throws Exception {
        List<DoctorDTO> doctors = new ArrayList<>();
        String query = "SELECT d.IDDoctor, d.Nom, s.NomSpeciality AS Speciality " +
                "FROM doctor d " +
                "JOIN Speciality s ON d.IDSpeciality = s.IDSpeciality";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                doctors.add(new DoctorDTO(
                        resultSet.getInt("IDDoctor"),
                        resultSet.getString("Nom"),
                        resultSet.getString("Speciality")
                ));
            }
        }
        return doctors;
    }
    public List<DoctorDTO> getAvailableDoctorsByDateTime(String date) {
        List<DoctorDTO> doctors = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String query = "SELECT d.IDDoctor, d.Nom, d.Specialty " +
                    "FROM doctor d " +
                    "JOIN Planning a ON d.IDDOCTOR = a.IDDOCTOR " +
                    "WHERE a.Date_Start = ? AND a.Availability = TRUE";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, date);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DoctorDTO doctor = new DoctorDTO();
                doctor.setId(resultSet.getInt("id"));
                doctor.setName(resultSet.getString("name"));
                doctor.setSpecialty(resultSet.getString("specialty"));
                doctors.add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doctors;
    }
}
