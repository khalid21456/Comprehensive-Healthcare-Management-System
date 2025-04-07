package org.example.patient.controllers;

import org.example.patient.models.Insurance;
import org.example.patient.models.Patient;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public interface PatientDao {
    List<Patient> getAllPatients() throws SQLException;
    Patient getPatientById(int id) throws SQLException;
    boolean updatePatient(Patient patient) throws SQLException;
    List<Insurance> getAllInsurances();
    boolean insertPatient(Patient patient) throws SQLException;
    Patient verifyCredentials(String cin, String password) throws SQLException;
}

