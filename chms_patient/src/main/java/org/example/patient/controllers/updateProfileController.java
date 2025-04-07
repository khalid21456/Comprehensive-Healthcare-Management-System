package org.example.patient.controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.patient.models.Insurance;
import org.example.patient.models.Patient;

public class updateProfileController {
	Patient p;

    @FXML
    private TextField prenomInput;

    @FXML
    private TextField nomInput;

    @FXML
    private TextField cinInput;

    @FXML
    private TextField telephoneInput;

    @FXML
    private TextField adresseInput;

    @FXML
    private ComboBox<String> sexeCombo;

    @FXML
    private ComboBox<String> villeCombo;

    @FXML
    private ComboBox<String> assuranceCombo;

    @FXML
    private DatePicker dateNaissancePicker;


    @FXML
    private Button saveUpdateButton;
    
    List<Insurance> insurances;
    
    @FXML
    private Button profileButton, folderButton, calendarButton, menuButton, logoutButton;
    
    @FXML
    private StackPane rootPane; 

    private Stage stage;
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        // Validate login logic here
        boolean loginSuccessful = true; // Replace with actual validation

        if (loginSuccessful) {
            switchToScene("/org/example/patient/views/dashboard.fxml");
        } else {
            // Show error message
            System.out.println("Login failed");
        }
    }
   
    @FXML
    private void handleHomeButtonAction() {
        switchToScene("dashboard.fxml");
    }
   
    @FXML
    private void handleProfileButtonAction() {
        switchToScene("updateProfile.fxml");
    }

    @FXML
    private void handleMenuButtonAction () {

        switchToScene("dashboard.fxml");
    }
    @FXML
    private void handleFolderButtonAction() {
        switchToScene("Scene.fxml");
    }

    
    @FXML
    private void handleCalendarButtonAction() {
//    	switchToScene("Calendar.fxml");
    }

    
    @FXML
    private void handleLogoutButtonAction() {
        switchToScene("login.fxml");
    }

   
    private void switchToScene(String fxmlFile) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/patient/views/" + fxmlFile));
            Parent root = loader.load();
            if (stage == null) {
                stage = (Stage) rootPane.getScene().getWindow();
            }
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() throws SQLException {
    	profileButton.setOnMouseClicked(event -> handleProfileButtonAction());
        folderButton.setOnMouseClicked(event -> handleFolderButtonAction());
        calendarButton.setOnMouseClicked(event -> handleCalendarButtonAction());
        menuButton.setOnMouseClicked(event -> handleHomeButtonAction());
        logoutButton.setOnMouseClicked(event -> handleLogoutButtonAction());
    	 List<String> cities = Arrays.asList(
                 "Casablanca", "Rabat", "Fes", "Marrakesh", "Tangier", "Salé", "Meknes", 
                 "Agadir", "Oujda", "Kenitra", "Tetouan", "Temara", "Safi", "Mohammedia", 
                 "Khouribga", "Beni Mellal", "El Jadida", "Taza", "Nador", "Settat", 
                 "Larache", "Ksar El Kebir", "Khemisset", "Guelmim", "Inezgane", "Azrou", 
                 "Errachidia", "Berkane", "Sidi Kacem", "Sidi Slimane", "Tiznit", "Taroudant", 
                 "Chefchaouen", "Al Hoceima", "Ouarzazate", "Essaouira"
             );
         List<String> ins=new ArrayList<String>();

    	getPatientById();
        prenomInput.setText(p.getPrenom());
        nomInput.setText(p.getNom());
        cinInput.setText(p.getCin());
        adresseInput.setText(p.getAdresse());
        telephoneInput.setText(p.getTel());
       
        villeCombo.getItems().addAll(cities);
        
        villeCombo.setValue(p.getVille());
                
        for(int i=0;i<insurances.size();i++) {
        	ins.add(insurances.get(i).getNameInsurance());
        }
        assuranceCombo.getItems().setAll(ins);
        for (Insurance insurance : insurances) {
            if (insurance.getIdInsurance() == p.getIdInsurance()) {
                assuranceCombo.setValue(insurance.getNameInsurance());
            }
        }
        
        List<String> sexeList=new ArrayList<String>();
        sexeList.add("Femme");
        sexeList.add("Homme");
        sexeCombo.getItems().addAll(sexeList);        
        
        sexeCombo.setValue(p.getSexe());
      /*  String dateOnly = p.getBirthDate().substring(0, 10);

        LocalDate date = LocalDate.parse(dateOnly);
        dateNaissancePicker.setValue(date);
        */
        LocalDateTime birthDateTime = p.getBirthDate();

     // Extract the LocalDate part
     LocalDate date = birthDateTime.toLocalDate();

     // Set the value of the DatePicker
     dateNaissancePicker.setValue(date);
    }
    
    
    private void getPatientById() throws SQLException {
    	 PatientService ps=new PatientService();
    	 p=ps.getPatientById(LoginController.patientid);
    	 insurances=ps.getAllInsurances();
    }

    @FXML
    private void handleSaveUpdateButton() throws SQLException {
        // Collect data from the fields
        String prenom = prenomInput.getText();
        String nom = nomInput.getText();
        String cin = cinInput.getText();
        String tel = telephoneInput.getText();
        String adresse = adresseInput.getText();
        String sexe = sexeCombo.getValue();
        String ville = villeCombo.getValue();
        String assurance = assuranceCombo.getValue();
        int idInsurance=0;
        
        for (Insurance insurance : insurances) {
            if (insurance.getNameInsurance().equalsIgnoreCase(assurance)) {
                idInsurance=insurance.getIdInsurance();
                break;
        }
        }
        
        
        LocalDate birthDate = dateNaissancePicker.getValue();
        LocalDateTime birthDateTime = birthDate.atStartOfDay(); // Adds time as 00:00:00

     LocalTime specificTime = LocalTime.of(12, 30); // Example: 12:30 PM
     LocalDateTime birthDateTimeWithTime = birthDate.atTime(specificTime);
     
        PatientService ps=new PatientService();
       
        Patient pUpdated=new Patient(LoginController.patientid,nom,prenom,sexe,birthDateTimeWithTime,adresse,tel,idInsurance,cin,ville);
        

        
        if (!isValidName(prenom)) {
            showAlert("Invalid First Name", "Please enter a valid first name.");
            return;
        }
        if (!isValidName(nom)) {
            showAlert("Invalid Last Name", "Please enter a valid last name.");
            return;
        }
        if (!isValidCIN(cin)) {
            showAlert("Invalid CIN", "Please enter a valid CIN.");
            return;
        }
        if (!isValidPhoneNumber(tel)) {
            showAlert("Invalid Phone Number", "Please enter a valid phone number.");
            return;
        }
        if (!isValidAddress(adresse)) {
            showAlert("Invalid Address", "Please enter a valid address.");
            return;
        }
        if (!isValidBirthDate(birthDate)) {
            showAlert("Invalid Birth Date", "Please select a valid birth date.");
            return;
        }
        
        
        ps.updatePatient(pUpdated);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile Updated");
        alert.setHeaderText(null); // You can set a header if needed
        alert.setContentText("Your profile has been updated successfully.");
        Image successImage = new Image("file:../../images/CheckCircle.png");
        if (successImage.isError()) {
            System.out.println("Error loading image.");
        } else {
            ImageView successIcon = new ImageView(successImage);
            
            successIcon.setFitHeight(50); 
            successIcon.setFitWidth(50);  
            
            alert.setGraphic(successIcon);
        }
        alert.showAndWait();
   
    }
    	
    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z]+");
    }

    private boolean isValidCIN(String cin) {
        return cin != null && cin.matches("[A-Za-z]{2}\\d{5}");
    }

    private boolean isValidBirthDate(LocalDate birthDate) {
    	return birthDate==null;
    	 }
    private boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("^(06|07)\\d{8}$");
    }
    
    private boolean isValidAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }
   
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


 

}