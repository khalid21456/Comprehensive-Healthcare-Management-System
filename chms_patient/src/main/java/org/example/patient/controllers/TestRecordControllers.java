package org.example.patient.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.example.patient.application.Main;
import org.example.patient.controllers.DatabaseConnection;
import org.example.patient.models.TestRecord;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestRecordControllers {
	@FXML
	private Label noVisitLabel;

	@FXML
	private ImageView logoImageView;

	@FXML
	private Label titleLabel;

	@FXML
	private Label descriptionLabel;

    @FXML
    private TableView<TestRecord> tableView;

    @FXML
    private TableColumn<TestRecord, String> testNameColumn;

    @FXML
    private TableColumn<TestRecord, String> testResultsColumn;

    @FXML
    private TableColumn<TestRecord, LocalDate> testDateColumn;

    @FXML
    private TableColumn<TestRecord, String> statusColumn;

    @FXML
    private TableColumn<TestRecord, String> doctorColumn;

    @FXML
    private TableColumn<TestRecord, Button> prescriptionColumn;

    private ObservableList<TestRecord> dataList = FXCollections.observableArrayList();
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
    public void initialize() {
    	profileButton.setOnMouseClicked(event -> handleProfileButtonAction());
        folderButton.setOnMouseClicked(event -> handleFolderButtonAction());
        calendarButton.setOnMouseClicked(event -> handleCalendarButtonAction());
        menuButton.setOnMouseClicked(event -> handleMenuButtonAction());
        logoutButton.setOnMouseClicked(event -> handleLogoutButtonAction());
        

         titleLabel.getStyleClass().add("title-label"); // Ajoutez une classe CSS

    	tableView.getStylesheets().add(getClass().getResource("/org/example/patient/styles/application.css").toExternalForm());


        // Configurer les colonnes du TableView
        testNameColumn.setCellValueFactory(new PropertyValueFactory<>("testName"));
        testResultsColumn.setCellValueFactory(new PropertyValueFactory<>("testResults"));
        testDateColumn.setCellValueFactory(new PropertyValueFactory<>("testDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        prescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("prescription"));
        prescriptionColumn.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(Button button, boolean empty) {
                super.updateItem(button, empty);
                if (empty || button == null) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
        descriptionLabel.prefWidthProperty().bind(tableView.widthProperty().subtract(20)); // Réduction de 20px pour la marge
        descriptionLabel.setWrapText(true);
        adjustTableWidthOnWindowResize();
        int patientId = LoginController.patientid;
        loadDataFromDatabase(patientId);
        tableView.setItems(dataList);
        adjustColumnWidths();
        adjustTableHeight();
    }
    
    private void adjustTableHeight() {
        tableView.setFixedCellSize(25); // Hauteur d'une cellule
        int maxRows = 7;
        tableView.prefHeightProperty().bind(
            Bindings.min(
                tableView.fixedCellSizeProperty().multiply(Bindings.size(tableView.getItems()).add(1.01)),
                tableView.fixedCellSizeProperty().multiply(maxRows + 1.01)
            )
        );
        tableView.minHeightProperty().bind(tableView.prefHeightProperty());
        tableView.maxHeightProperty().bind(tableView.prefHeightProperty());
        descriptionLabel.maxWidthProperty().bind(
                tableView.widthProperty().subtract(20) // Réduction de 20px pour une marge
            );

            descriptionLabel.setWrapText(true); // Activer le wrapping pour gérer les textes longs
        
    }



    private void adjustColumnWidths() {
        // Appliquer une politique de redimensionnement uniforme
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Ajuster la largeur relative de chaque colonne
        testNameColumn.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% de la largeur totale
        testResultsColumn.setMaxWidth(1f * Integer.MAX_VALUE * 15); // 15%
        testDateColumn.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20%
        statusColumn.setMaxWidth(1f * Integer.MAX_VALUE * 15); // 15%
        doctorColumn.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20%
        prescriptionColumn.setMaxWidth(1f * Integer.MAX_VALUE * 10); // 10%
    }

    private void adjustTableWidthOnWindowResize() {
        // Récupération de la scène principale
        tableView.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    double windowWidth = newWidth.doubleValue();
                    double margin = 81 * 2; // 81px à gauche et à droite
                    tableView.setPrefWidth(windowWidth - margin);
                });
            }
        });
    }
    private void loadDataFromDatabase(int patientId) {
        String query = """
            SELECT 
                Test.Name AS TestName,
                TestResult.ResultValue AS TestResults,
                TestResult.ResultDate AS TestDate,
                ordonnancetest.Status,
                CONCAT(Doctor.nom, ' ', Doctor.prenom) AS DoctorName
            FROM 
                TestResult
            JOIN 
                Test ON TestResult.IDTest = Test.IDTest
            JOIN 
                Doctor ON TestResult.IDDoctor = Doctor.IDdoctor
            JOIN 
                Patient ON TestResult.IDPatient = Patient.IDPatient
			join
				ordonnancetest on ordonnancetest.idpatient=Patient.IDPatient
            WHERE 
                Patient.IDPatient = ?;
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId); // Remplacez par l'ID du patient que vous souhaitez afficher
            ResultSet rs = pstmt.executeQuery();

            // Effacer les données actuelles avant d'ajouter de nouvelles données
            dataList.clear();

            // Parcourir les résultats et les ajouter à la liste
            while (rs.next()) {
                String testName = rs.getString("TestName");
                String testResults = rs.getString("TestResults");
                LocalDate testDate = rs.getDate("TestDate").toLocalDate();
                String status = rs.getString("Status");
                String doctorName = rs.getString("DoctorName");
                Button prescriptionButton = new Button();
                
                

                Image icon = new Image(getClass().getResource("/views/Download02.png").toExternalForm());
                if (icon.isError()) {
                    System.out.println("Error loading image.");
                } else {
               	 ImageView imageView = new ImageView(icon);
                 imageView.setFitWidth(20);
                 imageView.setFitHeight(20);
                 prescriptionButton.setGraphic(imageView);
                }
                prescriptionButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                dataList.add(new TestRecord(testName, testResults, testDate, status, doctorName,prescriptionButton));
            }
            if (dataList.isEmpty()) {
                noVisitLabel.setText("Vous n'avez pas encore effectué de visite!");
                tableView.setVisible(false); // Cache le tableau
                descriptionLabel.setVisible(false);
            } else {
                noVisitLabel.setText(""); // Efface le message
                tableView.setVisible(true); // Affiche le tableau
                descriptionLabel.setVisible(true);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des données : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
