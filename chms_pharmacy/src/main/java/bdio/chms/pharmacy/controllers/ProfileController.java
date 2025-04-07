package bdio.chms.pharmacy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import bdio.chms.pharmacy.dao.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private VBox sidebar;

    // File path constants for FXML files
    private static final String DASHBOARD_VIEW = "/bdio/chms/pharmacy/fxml/Dashboard.fxml";
    private static final String PROFILE_VIEW = "/bdio/chms/pharmacy/fxml/Profile.fxml";
    private static final String MEDICATION_VIEW = "/bdio/chms/pharmacy/fxml/Medicament.fxml";
    private static final String ORDONNANCE_VIEW = "/bdio/chms/pharmacy/fxml/Ordonnance.fxml";
    private static final String LOGIN_VIEW = "/bdio/chms/pharmacy/fxml/Login.fxml";
    private static final String FOURNISSEUR_VIEW = "/bdio/chms/pharmacy/fxml/Fournisseur.fxml";

    // Initialize the profile with data from the database
    public void initialize() {
        int defaultPharmacistID = 1; // Example: Replace with dynamic ID fetching logic if needed
        loadPharmacistInfo(defaultPharmacistID);
    }

    // Fetch and populate pharmacist information
    private void loadPharmacistInfo(int pharmacistID) {
        String query = "SELECT Prenom, Nom, email, Tel FROM pharmacien WHERE IDPharmacien = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, pharmacistID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Populate fields with fetched data
                    firstNameField.setText(resultSet.getString("Prenom"));
                    lastNameField.setText(resultSet.getString("Nom"));
                    emailField.setText(resultSet.getString("email"));
                    phoneField.setText(resultSet.getString("Tel"));
                } else {
                    System.err.println("No pharmacist found with ID: " + pharmacistID);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading pharmacist data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Generic method for handling view transitions
    private void loadView(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Replace the current scene with the new one
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading view: " + fxmlFile);
            e.printStackTrace();
        }
    }

    // Handlers for sidebar navigation
    @FXML
    private void goToDashboard(ActionEvent event) {
        loadView(DASHBOARD_VIEW, event);
    }

    @FXML
    private void goToProfile(ActionEvent event) {
        loadView(PROFILE_VIEW, event);
    }

    @FXML
    private void showViewMedication(ActionEvent event) {
        loadView(MEDICATION_VIEW, event);
    }

    @FXML
    private void goToOrdonnance(ActionEvent event) {
        loadView(ORDONNANCE_VIEW, event);
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        loadView(LOGIN_VIEW, event);
    }

    @FXML
    private void goToFournisseur(ActionEvent event) {
        loadView(FOURNISSEUR_VIEW, event);
    }

    // Handle the update button action
    @FXML
    private void handleUpdateButtonAction(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        // Check if any fields are empty
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Update Error", "Please fill in all fields.");
            return;
        }

        // Update the profile in the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE pharmacien SET Nom = ?, Prenom = ?, Tel = ?, email = ? WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, email);  // Update based on email (assuming it's unique)

            // Execute the update query
            int rowsUpdated = statement.executeUpdate();

            // Show success or error message
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Your profile has been updated.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Update Failed", "Failed to update profile. Please try again.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while connecting to the database:\n" + e.getMessage());
        }
    }

    // Method to show alerts to the user
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
