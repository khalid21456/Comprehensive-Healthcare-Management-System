package bdio.chms.pharmacy.models;

import bdio.chms.pharmacy.dao.OrdonnanceDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Ordonnance {
    private int id;
    private ArrayList<Integer> medicamentIds;
    private Date dateCreation;
    private int patient;
    private int doctor;
    private String status;

    public Ordonnance(int id,int doctor, Date dateCreation, int patient, String status) {
        this.id = id;
        this.doctor = doctor;
        this.dateCreation = dateCreation;
        this.patient = patient;
        this.status = status;
        try {
            loadMedicaments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getDoctor() {
        return doctor;
    }

    public void setDoctor(int doctor) {
        this.doctor = doctor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Integer> getMedicamentIds() {
        return medicamentIds;
    }

    public void loadMedicaments() throws SQLException {
        OrdonnanceDao dao = new OrdonnanceDao();
        this.medicamentIds = dao.getMedicamentsByOrdonnanceId(this.id);
    }

    public void setMedicamentIds(ArrayList<Integer> medicamentIds) {
        this.medicamentIds = medicamentIds;
    }

    @Override
    public String toString() {
        return "Ordonnance{" +
                "id=" + id +
                ", medicamentIds=" + medicamentIds +
                ", dateCreation=" + dateCreation +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", status='" + status + '\'' +
                '}';
    }
}
