package com.pack.billingsystem.controllers;

import com.pack.billingsystem.models.Appointment;
import com.pack.billingsystem.models.Doctor;
import com.pack.billingsystem.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentController {

    public static List<Appointment> getAllAppointments(int patientID) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();

        String query = "SELECT * FROM Appointment JOIN Service ON Appointment.IDService = Service.IDService  WHERE IDPatient = ? AND paye = false ORDER BY appointmentDate DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setIdAppointment(rs.getInt("IDAppointment"));
                    int IDDoctor = rs.getInt("IDDoctor");
                    Doctor doctor = new DoctorController().getDoctorById(IDDoctor); // Utilisation de DoctorController
                    appointment.setDoctor(doctor);
                    appointment.setAppointmentDate(rs.getDate("appointmentDate"));
                    appointment.setPrice(rs.getDouble("price"));
                    appointment.setPaye(rs.getBoolean("paye"));
                    appointment.setService(rs.getString("NameService"));
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }

    public static void updateAppointmentStatus(int IDPatient) throws SQLException {
        List<Appointment> appointments = AppointmentController.getAllAppointments(IDPatient);
        for (Appointment appointment : appointments) {
            int IDAppointment = appointment.getIdAppointment();
            String updateAppointmentQuery = "UPDATE appointment SET paye = 1 WHERE IDAppointment = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(updateAppointmentQuery)) {
                pstmt.setInt(1, IDAppointment);
                pstmt.executeUpdate();
            }
        }
    }

    public static Appointment getAppointement(int id) throws SQLException{
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM APPOINTMENT JOIN Service ON Appointment.IDService = Service.IDService WHERE IDAppointment = "+id);
        Appointment appointment = new Appointment();
        set.next();
        appointment.setPaye(set.getBoolean("Paye"));
        appointment.setAppointmentDate(set.getDate("AppointmentDate"));
        appointment.setPrice(set.getDouble("Price"));
        appointment.setService(set.getString("NameService"));
        appointment.setStatus(set.getString("Status"));
        return appointment;
    }


}

