package bdio.chms.pharmacy.dao;

import bdio.chms.pharmacy.models.Medicament;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicationService {

    private static final Logger LOGGER = Logger.getLogger(MedicationService.class.getName());
    public void addMedication(Medicament medicament) {
        String sql = "INSERT INTO medicament (NomMed, DescMed, Prix_Unit, DateExpiration, Type, Qty) VALUES (?, ?, ?, ?, ?, ?)";

        if (!isValidDate(medicament.getDateExpiration())) {
            throw new IllegalArgumentException("Invalid date format for expiration time. Expected format: yyyy-MM-dd");
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, medicament.getNomMed());
            stmt.setString(2, medicament.getDescMed());
            stmt.setDouble(3, medicament.getPrixUnit());
            stmt.setDate(4, new java.sql.Date(medicament.getDateExpiration().getTime()));
            stmt.setString(5, medicament.getType());
            stmt.setInt(6, medicament.getQty()); // Default quantity is 0

            stmt.executeUpdate();

            // Retrieve the auto-generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    medicament.setIdMedicament(generatedKeys.getInt(1)); // Set the ID in the object
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding medication", e);
        }
    }



    // Method to validate that the date is in the correct format: "yyyy-MM-dd"
    public boolean isValidDate(java.util.Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            // Converting the date to string format and parsing it
            String dateString = dateFormat.format(date);
            dateFormat.parse(dateString); // This checks the date format
            return true;
        } catch (ParseException e) {
            return false;
        }
    }




    public Medicament getMedicationById(int id) throws SQLException {
        Medicament med = null;
        String query = "SELECT IDMedicament, NomMed, DescMed, Prix_Unit, DateExpiration, Type, Qty FROM medicament WHERE IDMedicament = ?";

        try (PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    med = new Medicament(
                            resultSet.getInt("IDMedicament"),
                            resultSet.getString("NomMed"),
                            resultSet.getString("DescMed"),
                            resultSet.getDouble("Prix_Unit"),
                            resultSet.getDate("DateExpiration"),
                            resultSet.getString("Type"),
                            resultSet.getInt("Qty")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving medication by ID", e);
        }

        return med;
    }



    // Method to get all medications from the database
    public List<Medicament> getAllMedications() {
        List<Medicament> medicaments = new ArrayList<>();
        String sql = "SELECT IDMedicament, NomMed, DescMed, Prix_Unit, DateExpiration, Type, Qty FROM medicament";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medicament medicament = new Medicament(
                        rs.getInt("IDMedicament"),
                        rs.getString("NomMed"),
                        rs.getString("DescMed"),
                        rs.getDouble("Prix_Unit"),
                        rs.getDate("DateExpiration"),
                        rs.getString("Type"),
                        rs.getInt("Qty")
                );
                medicaments.add(medicament);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving medications", e);
        }
        return medicaments;
    }

    // Method to remove a medication by its ID from the database
    public void removeMedication(int id) {
        String sql = "DELETE FROM medicament WHERE IDMedicament = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error removing medication", e);
        }
    }

    // Method to update an existing medication in the database
    public void updateMedication(Medicament updatedMedicament) {
        String sql = "UPDATE medicament SET NomMed = ?, DescMed = ?, Prix_Unit = ?, DateExpiration = ?, Type = ?, Qty = ? WHERE IDMedicament = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, updatedMedicament.getNomMed());
            stmt.setString(2, updatedMedicament.getDescMed());
            stmt.setDouble(3, updatedMedicament.getPrixUnit());
            stmt.setDate(4, new java.sql.Date(updatedMedicament.getDateExpiration().getTime())); // Convert to SQL Date
            stmt.setString(5, updatedMedicament.getType());
            stmt.setInt(6, updatedMedicament.getQty()); // Update quantity
            stmt.setInt(7, updatedMedicament.getIdMedicament());

            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating medication", e);
        }
    }

    // Method to update the quantity of a medication in the database
    public void updateQuantite(int id, int quantiteAjout) {
        String sql = "UPDATE medicament SET Qty = Qty + ? WHERE IDMedicament = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantiteAjout);  // Add quantity to the existing quantity
            stmt.setInt(2, id);  // Use medication ID to find it in the database
            stmt.executeUpdate();  // Execute the update
            System.out.println("Quantité mise à jour avec succès dans la base de données!");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating medication quantity", e);
        }
    }
}
