package org.example.patient.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.patient.models.Insurance;
import org.example.patient.models.Patient;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class RegisterController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField cinField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private ComboBox<String> cityComboBox;
    @FXML private DatePicker birthdayPicker;
    @FXML private ComboBox<String> sexComboBox;
    @FXML private ComboBox<Insurance> insuranceComboBox;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField visiblePasswordField;
    @FXML private TextField visibleConfirmPasswordField;
    @FXML private Label messageLabel;

    private PatientDao patientDao;
    private List<String> cities = Arrays.asList(
        "Casablanca", "Rabat", "Fes", "Marrakesh", "Tangier", "Salé", "Meknes",
        "Agadir", "Oujda", "Kenitra", "Tetouan", "Temara", "Safi", "Mohammedia",
        "Khouribga", "Beni Mellal", "El Jadida", "Taza", "Nador", "Settat",
        "Larache", "Ksar El Kebir", "Khemisset", "Guelmim", "Berrechid", "Taourirt",
        "Berkane", "Sidi Slimane", "Errachidia", "Sidi Kacem", "Khenifra",
        "Essaouira", "Tiznit", "Taroudant", "Ouarzazate", "Al Hoceima"
    );

    public RegisterController() {
        this.patientDao = new PatientDaoImpl();
    }

    @FXML
    private void initialize() {
        try {
            // Initialize sex options
            sexComboBox.getItems().addAll("M", "F");
            
            // Initialize city options
            cityComboBox.getItems().addAll(cities);
            
            // Load insurance options from database
            List<Insurance> insurances = patientDao.getAllInsurances();
            insuranceComboBox.getItems().addAll(insurances);
            
            // Set cell factory for insurance combo box to display insurance names
            insuranceComboBox.setCellFactory(param -> new ListCell<Insurance>() {
                @Override
                protected void updateItem(Insurance item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNameInsurance() + " (" + item.getPercentage() + "%)");
                    }
                }
            });
            
            // Set button cell factory to display selected insurance
            insuranceComboBox.setButtonCell(new ListCell<Insurance>() {
                @Override
                protected void updateItem(Insurance item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNameInsurance() + " (" + item.getPercentage() + "%)");
                    }
                }
            });

            // Setup password visibility toggle
            visiblePasswordField.managedProperty().bind(visiblePasswordField.visibleProperty());
            visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
            
            visibleConfirmPasswordField.managedProperty().bind(visibleConfirmPasswordField.visibleProperty());
            visibleConfirmPasswordField.textProperty().bindBidirectional(confirmPasswordField.textProperty());
            
            // Set prompt text for date picker
            birthdayPicker.setPromptText("MM-DD-YYYY");
            
        } catch (Exception e) {
            messageLabel.setText("Error initializing form: " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void togglePasswordVisibility(ActionEvent event) {
        passwordField.setVisible(!passwordField.isVisible());
        visiblePasswordField.setVisible(!visiblePasswordField.isVisible());
        passwordField.setManaged(!passwordField.isManaged());
        visiblePasswordField.setManaged(!visiblePasswordField.isManaged());
    }

    @FXML
    private void toggleConfirmPasswordVisibility(ActionEvent event) {
        confirmPasswordField.setVisible(!confirmPasswordField.isVisible());
        visibleConfirmPasswordField.setVisible(!visibleConfirmPasswordField.isVisible());
        confirmPasswordField.setManaged(!confirmPasswordField.isManaged());
        visibleConfirmPasswordField.setManaged(!visibleConfirmPasswordField.isManaged());
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }

        try {
            Patient newPatient = createPatient();
            if (patientDao.insertPatient(newPatient)) {
                messageLabel.setText("Registration successful!");
                messageLabel.setStyle("-fx-text-fill: green;");
                
                // Navigate to login page after successful registration
                loadLoginPage();
            } else {
                messageLabel.setText("Registration failed. Please try again.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (SQLException e) {
            messageLabel.setText("Error during registration: " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || 
            cinField.getText().isEmpty() || phoneField.getText().isEmpty() || 
            addressField.getText().isEmpty() || cityComboBox.getValue() == null || 
            birthdayPicker.getValue() == null || sexComboBox.getValue() == null || 
            insuranceComboBox.getValue() == null || passwordField.getText().isEmpty()) {
            
            messageLabel.setText("Please fill in all fields");
            messageLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            messageLabel.setText("Passwords do not match");
            messageLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        return true;
    }

    private Patient createPatient() {
        Patient patient = new Patient();
        patient.setNom(lastNameField.getText());
        patient.setPrenom(firstNameField.getText());
        patient.setSexe(sexComboBox.getValue());
        patient.setBirthDate(birthdayPicker.getValue().atStartOfDay());
        patient.setAdresse(addressField.getText());
        patient.setTel(phoneField.getText());
        patient.setIdInsurance(insuranceComboBox.getValue().getIdInsurance());
        patient.setCin(cinField.getText());
        patient.setVille(cityComboBox.getValue());
        patient.setPassword(passwordField.getText());
        return patient;
    }

    @FXML
    private void handleSignIn(ActionEvent event) {
        loadLoginPage();
    }

    private void loadLoginPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/patient/views/login.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Profile");
            stage.show();
            
            // Close the registration window
            ((Stage) firstNameField.getScene().getWindow()).close();
        } catch (IOException e) {
            messageLabel.setText("Error loading update profile page");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
}
