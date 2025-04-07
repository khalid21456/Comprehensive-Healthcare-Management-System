package org.example.patient.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.patient.models.Patient;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class LoginController {
	public static int patientid=0;
    @FXML private TextField cinField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
    
    private PatientDao patientDao;

    public LoginController() {
        this.patientDao = new PatientDaoImpl();
    }

    @FXML
    private void initialize() {
        // Clear any previous messages
        messageLabel.setText("");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String cin = cinField.getText();
        String password = passwordField.getText();

        if (cin.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both CIN and password");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            Patient patient = patientDao.verifyCredentials(cin, password);
            if (patient != null) {
            	patientid=patient.getIdPatient();
            	loadDashboardPage();

                
                
            } else {
                messageLabel.setText("Invalid CIN or password");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (SQLException e) {
            messageLabel.setText("Error during login: " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
    
    
    
    private void loadDashboardPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/patient/views/dashboard.fxml"));
            Parent root = loader.load();
            
            switchcontroller controller = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
            
            ((Stage) cinField.getScene().getWindow()).close();
        } catch (IOException e) {
            messageLabel.setText("Error loading dashboard page");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }


   

    @FXML
    private void handleCreateAccount(ActionEvent event) {
        try {
            URL fxmlUrl = getClass().getResource("/org/example/patient/views/register.fxml");
            if (fxmlUrl == null) {
                throw new IllegalStateException("FXML file 'register.fxml' not found in '/org/example/patient/views/'");
            }
            Parent root = FXMLLoader.load(fxmlUrl);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Create Account");
            stage.show();
            
            // Close the login window
            ((Stage) cinField.getScene().getWindow()).close();
        } catch (IOException e) {
            messageLabel.setText("Error loading registration form");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
}
