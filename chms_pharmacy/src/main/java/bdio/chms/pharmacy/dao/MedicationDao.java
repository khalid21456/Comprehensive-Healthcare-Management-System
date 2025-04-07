package bdio.chms.pharmacy.dao;

import bdio.chms.pharmacy.models.Medicament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MedicationDao {

    public Medicament getMedicationById(int id) throws SQLException {
        Medicament med = null;
        String query = "SELECT nomMed FROM medicament WHERE idmedicament = ?";
        try (PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    med = new Medicament(
                            resultSet.getString("NomMed")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return med;
    }

    public List<Medicament> getAllMedications() {
        List<Medicament> medicaments = new ArrayList<>();
        String sql = "SELECT * FROM medicament";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medicament medicament = new Medicament(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getDate("expiration_time"),
                        rs.getString("type"),
                        rs.getInt("quantite")
                );
                medicaments.add(medicament);
            }
        } catch (SQLException e) {

        }
        return medicaments;
    }
}
