package bdio.chms.pharmacy.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import bdio.chms.pharmacy.dao.DatabaseConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    public Button loginButton;
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField carePoint; // Hospital field

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        String hospital = carePoint.getText();

        if (email.isEmpty() || password.isEmpty() || hospital.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Main Error", "Please fill in all fields.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM pharmacien WHERE email = ? AND password = ? AND hospital = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, hospital);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Credentials are correct, navigate to Dashboard.fxml
                loadDashboard(event);
            } else {
                showAlert(Alert.AlertType.ERROR, "Main Failed", "Invalid credentials. Please try again.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while connecting to the database:\n" + e.getMessage());
        }
    }

    private void loadDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bdio/chms/pharmacy/fxml/Dashboard.fxml"));
            Scene dashboardScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(dashboardScene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the dashboard:\n" + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }




    @FXML
    private TextField passwordTextField; // This is the TextField for visible password

    @FXML
    private ImageView eyeIcon; // The eye icon to toggle visibility

    private boolean isPasswordVisible = false;

    @FXML
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide the plain text password field, show PasswordField
            passwordTextField.setVisible(false);
            passwordField.setVisible(true);
            isPasswordVisible = false;
        } else {
            // Show the plain text password field, hide PasswordField
            passwordField.setVisible(false);
            passwordTextField.setVisible(true);
            passwordTextField.setText(passwordField.getText());
            isPasswordVisible = true;
        }
    }

}
