package bdio.chms.pharmacy.dao;


import bdio.chms.pharmacy.models.Ordonnance;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdonnanceDao {

    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private PreparedStatement preparedStatement2;
    private Ordonnance ordonnance;
    private List<Ordonnance> ordonnances ;

    public OrdonnanceDao() {
        this.ordonnances = new ArrayList();
    }

    public List<Ordonnance> getAll() {
        try {
            statement = DatabaseConnection.getConnection().createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        try {
            resultSet = statement.executeQuery("select * from ordonnance;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                ordonnance=new Ordonnance(
                        resultSet.getInt("IDOrdonnance"),
                        resultSet.getInt("IDDoctor"),
                        resultSet.getDate("DateCreation"),
                        resultSet.getInt("IDPatient"),
                        resultSet.getString("Status")
                );
                ordonnances.add(ordonnance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordonnances;
    }

    public ArrayList<Integer> getMedicamentsByOrdonnanceId(int ordonnanceId) throws SQLException {
        ArrayList<Integer> medicaments = new ArrayList<>();
        Integer id;
        String query = "SELECT m.IDMedicament FROM medicament m , ordonnance_medicament om where m.IDMedicament = om.IDMedicament and om.IDOrdonnance = ?";

        try {
            preparedStatement = DatabaseConnection.getConnection().prepareStatement(query);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        preparedStatement.setInt(1, ordonnanceId);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            id=resultSet.getInt("idmedicament");
            medicaments.add(id);
        }

        return medicaments;
    }

    public void updateOrdonnance(Ordonnance ordonnance) throws SQLException {
        System.out.println(ordonnance);
        String query1 = "UPDATE ordonnance SET status = ? WHERE IDOrdonnance = ?";
        String query2 = "UPDATE medicament SET qty = qty - ? WHERE IDMedicament = ?";

        try {
            preparedStatement = DatabaseConnection.getConnection().prepareStatement(query1);
            preparedStatement2 = DatabaseConnection.getConnection().prepareStatement(query2);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        preparedStatement.setString(1, ordonnance.getStatus());
        preparedStatement.setInt(2, ordonnance.getId());

        int rowsUpdated = preparedStatement.executeUpdate();
        for (Integer medicament : ordonnance.getMedicamentIds()) {
            preparedStatement2.setInt(1, 1);
            preparedStatement2.setInt(2, medicament);
            int medicamentUpdated = preparedStatement2.executeUpdate();

            if (medicamentUpdated > 0) {
                System.out.println("Quantité mise à jour pour le médicament : " + medicament);
            } else {
                System.out.println("Aucun médicament mis à jour pour l'ID : " + medicament);
            }
        }

        if (rowsUpdated > 0) {
            System.out.println("L'ordonnance a été mise à jour avec succès.");
            showSuccessAlert();
        } else {
            System.out.println("Aucune ordonnance n'a été mise à jour.");
            showErrorAlert();
        }

    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Ordonnance validée");
        alert.setContentText("L'ordonnance a été validée avec succès.");
        alert.showAndWait();
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Problème de validation");
        alert.setContentText("Une erreur est survenue lors de la validation de l'ordonnance.");
        alert.showAndWait();
    }

}
