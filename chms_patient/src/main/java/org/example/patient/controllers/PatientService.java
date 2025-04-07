package org.example.patient.controllers;

import org.example.patient.models.Insurance;
import org.example.patient.models.Patient;
import org.example.patient.controllers.PatientDaoImpl; // Ensure this matches the actual class name.
import java.sql.SQLException;
import java.util.List;

public class PatientService {
    private final PatientDao patientDao;

    public PatientService() {
        this.patientDao = new PatientDaoImpl();
    }
    
    public List<Patient> getAllPatients() throws SQLException {
        return patientDao.getAllPatients();
    }

    public Patient getPatientById(int id) throws SQLException {
        return patientDao.getPatientById(id);
    }

    public boolean updatePatient(Patient patient) throws SQLException {
        return patientDao.updatePatient(patient);
    }
    
    public  List<Insurance> getAllInsurances() {
    	return patientDao.getAllInsurances();
    }
    
}