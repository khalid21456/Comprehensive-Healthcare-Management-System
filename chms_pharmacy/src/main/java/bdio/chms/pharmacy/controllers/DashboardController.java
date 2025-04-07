package bdio.chms.pharmacy.controllers;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import bdio.chms.pharmacy.dao.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.Node;





public class DashboardController {


    @FXML
    private Node sidebar;

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Method to load views dynamically into the dashboard content area

    private void loadView(String fxmlFile, ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            root = loader.load();

            // Get the current stage and set the new scene
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            // Manage sidebar visibility based on the view being loaded
            manageSidebarVisibility(fxmlFile);

            // Set the scene to the stage and show it
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading view: " + fxmlFile);
            e.printStackTrace();
        }
    }


    private void manageSidebarVisibility(String fxmlFile) {
        if (sidebar != null) {
            boolean isSidebarVisible = fxmlFile.equals("/bdio/chms/pharmacy/fxml/Dashboard.fxml") ||
                    fxmlFile.equals("/bdio/chms/pharmacy/fxml/Medicament.fxml");
            sidebar.setVisible(isSidebarVisible);
        }
    }



    @FXML
    private void goToProfile(ActionEvent event) {
        loadView("/bdio/chms/pharmacy/fxml/Profile.fxml", event);
    }

    @FXML
    private void goToDashboard(ActionEvent event) {
        loadView("/bdio/chms/pharmacy/fxml/Dashboard.fxml", event);
    }

    @FXML
    private void showViewMedication(ActionEvent event) {
        loadView("/bdio/chms/pharmacy/fxml/Medicament.fxml", event);
    }

    @FXML
    private void goToOrdonnance(ActionEvent event) {
        loadView("/bdio/chms/pharmacy/fxml/Ordonnance.fxml", event);
    }

    @FXML
    private void goToFournisseur(ActionEvent event) {
        loadView("/bdio/chms/pharmacy/fxml/Fournisseur.fxml", event);
    }

    @FXML
    private Label welcomeLabel;

    private String pharmacistFullName = "";
    @FXML
    public void initialize() {
        // Set a default message
        welcomeLabel.setText("Hello, Guest");
        // Load the pharmacist info right away if you have the ID
        loadPharmacistInfo(1); // Replace 1 with your actual pharmacist ID
    }

    public void loadPharmacistInfo(int pharmacistID) {
        String query = "SELECT Prenom, Nom, email, Tel FROM pharmacien WHERE IDPharmacien = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, pharmacistID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("Prenom");
                    String lastName = resultSet.getString("Nom");
                    pharmacistFullName = firstName + " " + lastName;

                    // Update welcome message on JavaFX thread
                    javafx.application.Platform.runLater(() -> {
                        welcomeLabel.setText("Hello, " + pharmacistFullName);
                    });

                } else {
                    System.err.println("No pharmacist found with ID: " + pharmacistID);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading pharmacist data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}