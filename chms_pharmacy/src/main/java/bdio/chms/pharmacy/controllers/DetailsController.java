package bdio.chms.pharmacy.controllers;

import bdio.chms.pharmacy.dao.DoctorDao;
import bdio.chms.pharmacy.dao.MedicationService;
import bdio.chms.pharmacy.dao.OrdonnanceDao;
import bdio.chms.pharmacy.dao.PatientDao;
import bdio.chms.pharmacy.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetailsController {
    @FXML
    private Text patientFirstName;
    @FXML
    private Text patientLastName;
    @FXML
    private Text patientSexLabel;
    @FXML
    private Text patientAgeLabel;
    @FXML
    private Label doctorNameLabel;
    @FXML
    private Label doctorSpecialtyLabel;
    @FXML
    private Label doctorPhoneLabel;
    @FXML
    private Label doctorAddressLabel;
    @FXML
    private ListView<String> medicamentsListView;
    @FXML
    private Button validateButton;

    private Ordonnance ordonnance;
    private DoctorDao doctorDao;
    private OrdonnanceDao ordonnanceDao ;
    private PatientDao patientDao;
    private MedicationService medicationDao;

    public DetailsController() {
        // Initialisation des DAO
        this.doctorDao = new DoctorDao();
        this.ordonnanceDao = new OrdonnanceDao();
        this.patientDao = new PatientDao();
        this.medicationDao = new MedicationService();
    }
    public void setOrdonnance(Ordonnance ordonnance) throws SQLException {
        if (ordonnance.getStatus().equals("Active")) {
            validateButton.setStyle(
                    "-fx-background-color:#5A9BD5FF;-fx-border-radius: 15px;"+
                            "-fx-pref-height: 10px;-fx-text-alignment: center;" +
                            "-fx-pref-width: 100px;-fx-text-fill:white;"+
                            "-fx-font-weight: bold;"
            );
            validateButton.setVisible(true);
        }
        this.ordonnance = ordonnance;
        displayDetails();
    }

    private void displayDetails() throws SQLException {
        Patient patient = null;
        patient = patientDao.getPatientById(ordonnance.getPatient());
        patientFirstName.setText(patient.getPrenom());
        patientLastName.setText(patient.getNom());
        patientSexLabel.setText(patient.getSexe());
        patientAgeLabel.setText(patient.getBirthDate().toString());

        Doctor doctor = null;
        try {
            doctor = doctorDao.getDoctorById(ordonnance.getDoctor());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        doctorNameLabel.setText(doctor.getNom()+" "+doctor.getPrenom());
        Speciality specialty = null;
        try {
            specialty = doctorDao.getSpecialtyByDoctorId(doctor.getIdDoctor());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        doctorSpecialtyLabel.setText(specialty.toString());
        doctorPhoneLabel.setText(doctor.getTel());
        doctorAddressLabel.setText(doctor.getAdresse());

        List<String> medications = new ArrayList<>();
        for (Integer medicationId : ordonnance.getMedicamentIds()) {
            Medicament medication = null;
            try {
                medication = medicationDao.getMedicationById(medicationId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            medications.add(medication.getNomMed()); // Get the actual String value
        }


        ObservableList<String> medicationList = FXCollections.observableArrayList(medications);
        medicamentsListView.setItems(medicationList);
    }


    @FXML
    private void validateOrdonnance() {
        ordonnance.setStatus("Delivred");
        try {
            ordonnanceDao.updateOrdonnance(ordonnance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        validateButton.setVisible(false);
    }
}
