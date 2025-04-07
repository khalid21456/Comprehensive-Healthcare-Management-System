package bdio.chms.pharmacy.dao;

import bdio.chms.pharmacy.models.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDao {
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public Patient getPatientById(int id) throws SQLException {
        Patient patient = null;
        String query = "SELECT * FROM patient WHERE idpatient = ?";
        try {
            preparedStatement = DatabaseConnection.getConnection().prepareStatement(query);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            patient = new Patient(
                    resultSet.getInt("idpatient"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getString("sexe"),
                    resultSet.getDate("birthDate")
            );
        }

        return patient;
    }
}
