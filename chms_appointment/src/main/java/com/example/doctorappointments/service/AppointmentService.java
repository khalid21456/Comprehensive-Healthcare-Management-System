package com.example.doctorappointments.service;
import com.example.doctorappointments.model.Appointment;
import com.example.doctorappointments.model.AppointmentDetails;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {
    public static AppointmentDetails getAppointmentById(int appointmentId) {
        AppointmentDetails appointment = null;

        String query = "SELECT appointment.*, doctor.IDDoctor as doctorID, doctor.IDSpeciality as doctorSpeciality, doctor.Nom as doctorNom, doctor.Prenom as doctorPrenom, doctor.Tel as doctorTel, doctor.Adresse as doctorAdresse, patient.IDPatient as patientID, patient.Nom as patientNom, patient.Prenom as patientPrenom, patient.Sexe as patientSexe, patient.BirthDate as patientBirthDate, patient.Adresse as patientAdresse, patient.Tel as patientTel, patient.IDInsurance as patientInsurance, patient.CIN as patientCIN, patient.Ville as patientVille FROM appointment, `doctor`, `patient` WHERE appointment.IDDoctor = doctor.IDDoctor and appointment.IDPatient=patient.IDPatient and IDAppointment = ?";



        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, appointmentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                appointment = new AppointmentDetails(
                        rs.getInt("IDAppointment"),
                        rs.getInt("doctorID"),
                        rs.getInt("patientID"),
                        rs.getTimestamp("AppointmentDate"),
                        rs.getString("doctorNom") + " " + rs.getString("doctorPrenom"),
                        rs.getString("patientNom") + " " + rs.getString("patientPrenom")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving appointment: " + e.getMessage());
        }

        return appointment;
    }



    public static List<AppointmentDetails> getAllAppointments() {
        List<AppointmentDetails> appointments = new ArrayList<>();
        String query = "SELECT a.IDAppointment, a.IDDoctor, a.IDPatient, a.AppointmentDate, a.Status, a.Paye,s.NameService AS ServiceName, "
                + "p.Nom AS PatientNom, p.Prenom AS PatientPrenom, "
                + "d.Nom AS DoctorNom, d.Prenom AS DoctorPrenom "
                + "FROM appointment a "
                + "LEFT JOIN service s ON a.IDService = s.IDService "
                + "JOIN patient p ON a.IDPatient = p.IDPatient "
                + "JOIN doctor d ON a.IDDoctor = d.IDDoctor "
                + "ORDER BY a.IDAppointment DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer IDAppointment = resultSet.getInt("IDAppointment");
                Integer IDDoctor = resultSet.getInt("IDDoctor");
                Integer IDPatient = resultSet.getInt("IDPatient");
                Timestamp AppointmentDate = resultSet.getTimestamp("AppointmentDate");
                String Status = resultSet.getString("Status");
                Integer Paye = resultSet.getInt("Paye");
                String ServiceName = resultSet.getString("ServiceName");
                String PatientFullName = resultSet.getString("PatientPrenom") + " " + resultSet.getString("PatientNom");
                String DoctorFullName = resultSet.getString("DoctorPrenom") + " " + resultSet.getString("DoctorNom");

                AppointmentDetails appointmentDetails = new AppointmentDetails(IDAppointment, IDDoctor, IDPatient, AppointmentDate,
                        DoctorFullName, PatientFullName, ServiceName, Status, Paye);
                appointments.add(appointmentDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }






    public static void cancelAppointment(int appointmentID) {
        String query = "UPDATE appointment SET status = ? WHERE IDAppointment = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "canceled");
            preparedStatement.setInt(2, appointmentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void updateDoctorAvailability(int doctorId, LocalDate date, String dateStart, boolean isAvailable) {
        String query = "UPDATE planning SET availability = ? WHERE IDdoctor = ? AND Date = ? AND Date_Start = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, isAvailable ? 1 : 0);
            preparedStatement.setInt(2, doctorId);
            preparedStatement.setDate(3, Date.valueOf(date));
            preparedStatement.setString(4, dateStart);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







    public static boolean updateAppointment(int appointmentID, int newDoctorID, LocalDate newDate, LocalTime newTime) {
        String query = """
        UPDATE appointment 
        SET IDDoctor = ?, 
            AppointmentDate = ?
        WHERE IDAppointment = ?
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Combine date and time into a single `Timestamp`
            LocalDateTime appointmentDateTime = LocalDateTime.of(newDate, newTime);
            Timestamp timestamp = Timestamp.valueOf(appointmentDateTime);

            // Set parameters for the prepared statement
            statement.setInt(1, newDoctorID);
            statement.setTimestamp(2, timestamp);
            statement.setInt(3, appointmentID);

            // Execute the update and return true if successful
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



        public static Appointment getAppointmentDetails(int appointmentId) {
            Appointment appointment = null;

            String query = "SELECT appointment.IDAppointment, appointment.IDDoctor, appointment.IDPatient, "
                    + "appointment.AppointmentDate, appointment.Price, appointment.Paye, appointment.Status, "
                    + "appointment.IDService "
                    + "FROM appointment "
                    + "WHERE appointment.IDAppointment = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, appointmentId);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    appointment = new Appointment(
                            rs.getInt("IDAppointment"),
                            rs.getInt("IDDoctor"),
                            rs.getInt("IDPatient"),
                            rs.getTimestamp("AppointmentDate"),
                            rs.getDouble("Price"),
                            rs.getInt("Paye"),
                            rs.getString("Status"),
                            rs.getInt("IDService")
                    );
                }

            } catch (SQLException e) {
                System.err.println("Error retrieving appointment details: " + e.getMessage());
            }

            return appointment;
        }




    public static boolean deleteAppointment(int appointmentId) {
        String query = "DELETE FROM appointment WHERE IDAppointment = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, appointmentId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting appointment: " + e.getMessage());
            return false;
        }
    }



        public static List<Appointment> getAppointmentsByDoctor(int doctorId) {
            List<Appointment> appointments = new ArrayList<>();
            String query = "SELECT a.IDAppointment, a.IDDoctor, a.IDPatient, a.AppointmentDate, a.Price, a.Paye, a.Status, a.IDService, "
                    + "p.Nom AS PatientNom, p.Prenom AS PatientPrenom, "
                    + "d.Nom AS DoctorNom, d.Prenom AS DoctorPrenom "
                    + "FROM appointment a "
                    + "JOIN patient p ON a.IDPatient = p.IDPatient "
                    + "JOIN doctor d ON a.IDDoctor = d.IDDoctor "
                    + "WHERE a.IDDoctor = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, doctorId);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Integer IDAppointment = resultSet.getInt("IDAppointment");
                    Integer IDDoctor = resultSet.getInt("IDDoctor");
                    Integer IDPatient = resultSet.getInt("IDPatient");
                    Timestamp AppointmentDate = resultSet.getTimestamp("AppointmentDate");
                    Double Price = resultSet.getDouble("Price");
                    Integer Paye = resultSet.getInt("Paye");
                    String Status = resultSet.getString("Status");
                    Integer IDService = resultSet.getInt("IDService");
                    Appointment appointment = new Appointment(
                            IDAppointment, IDDoctor, IDPatient, AppointmentDate, Price, Paye, Status, IDService
                    );
                    appointments.add(appointment);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return appointments;
        }



}