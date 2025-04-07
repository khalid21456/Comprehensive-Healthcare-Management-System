package bdio.chms.pharmacy.dao;

import bdio.chms.pharmacy.models.Doctor;
import bdio.chms.pharmacy.models.Speciality;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDao {
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;


    public Doctor getDoctorById(int id) throws SQLException {
        try {
            preparedStatement =DatabaseConnection.getConnection().prepareStatement("SELECT * FROM doctor WHERE iddoctor = ?");

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        Doctor doctor = null;
        if (resultSet.next()) {
            doctor = new Doctor(
                    resultSet.getInt("IDDoctor"),
                    resultSet.getInt("IDSpeciality"),
                    resultSet.getString("Nom"),
                    resultSet.getString("Prenom"),
                    resultSet.getString("Tel"),
                    resultSet.getString("Adresse")
            );
        }
        return doctor;
    }

    public Speciality getSpecialtyByDoctorId(int doctorId) throws SQLException {
        Speciality specialite = null;
        String query = "SELECT * FROM speciality WHERE idspeciality = ?";

        try {
            preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT nomspeciality FROM doctor d,speciality s WHERE s.idspeciality =d.idspeciality and iddoctor=?");

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        preparedStatement.setInt(1, doctorId);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            specialite = new Speciality(
                    resultSet.getString("nomspeciality"));
        }

        return specialite;
    }
}
