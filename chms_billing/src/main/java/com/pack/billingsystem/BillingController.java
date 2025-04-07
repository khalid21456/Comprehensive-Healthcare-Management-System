package com.pack.billingsystem;

import com.pack.billingsystem.controllers.*;
import com.pack.billingsystem.models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class BillingController {
    @FXML
    private GridPane patientDetailsGrid, testLabDetailsGrid, medicationsDetailsGrid;
    @FXML
    private Button backButton;
    @FXML
    private Label patientNameLabel,totalPriceLabel,totalPriceInsuranceLabel,medicationPriceTextLabel,facturationPriceTextLabel,testPriceTextLabel;

    private boolean isPatientDetailsVisible = false;
    private boolean isTestLabDetailsVisible = false;
    private boolean isMedicationsDetailsVisible = false;
    private int patientID  ;

    public BillingController() {
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;

        try {
            if (this.patientID <= 0) {
                throw new IllegalArgumentException("ID Patient doit être supérieur à 0");
            }

            // Charger les détails du patient
            Patient patient = PatientController.getPatient(this.patientID);

            // Remplir les informations
            patientNameLabel.setText(patient.getNom() + " " + patient.getPrenom());
            facturationPriceTextLabel.setText(String.format("%.2f DH", PrixController.updatePriceAppointement(patientID)));
            testPriceTextLabel.setText(String.format("%.2f DH", PrixController.updatePriceTest(patientID)));
            medicationPriceTextLabel.setText(String.format("%.2f DH", PrixController.updatePriceMedicaments(patientID)));
            totalPriceInsuranceLabel.setText(String.format("%.2f DH", PrixController.updateTotalPriceInsurance(patientID, patient.getPourcentageAssurance())));
            totalPriceLabel.setText(String.format("%.2f DH", PrixController.updateTotalPrice(patientID)));

            // Rendre visibles les éléments nécessaires
            totalPriceInsuranceLabel.setVisible(true);
            totalPriceLabel.setVisible(true);

            // Instancier le contrôleur PDF
            pdfController = new PdfController();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }


    private PdfController pdfController;

    /*@FXML
    public void initialize() throws SQLException {
        System.out.println("ID Patient dans initialize : " + this.patientID);
        try {
            if (this.patientID <= 0) {
                throw new IllegalArgumentException("ID Patient doit être supérieur à 0");
            }
            Patient patient = PatientController.getPatient(this.patientID);
            // Populate patient details
            patientDetailsGrid.setVisible(false);
            testLabDetailsGrid.setVisible(false);
            medicationsDetailsGrid.setVisible(false);
            totalPriceLabel.setVisible(false);

            facturationPriceTextLabel.setText(String.format("%.2f DH", PrixController.updatePriceAppointement(patientID)));
            testPriceTextLabel.setText(String.format("%.2f DH",PrixController.updatePriceTest(patientID)));
            medicationPriceTextLabel.setText(String.format("%.2f DH", PrixController.updatePriceMedicaments(patientID)));

            patientNameLabel.setText(patient.getNom()+" "+patient.getPrenom());
            totalPriceInsuranceLabel.setText(String.format("%.2f DH", PrixController.updateTotalPriceInsurance(patientID,patient.getPourcentageAssurance())));
            totalPriceInsuranceLabel.setVisible(true);
            totalPriceLabel.setText(String.format("%.2f DH", PrixController.updateTotalPrice(patientID)));
            totalPriceLabel.setVisible(true);

            pdfController = new PdfController();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        }

    }*/

    @FXML
    private void handlePatientInfoClick(MouseEvent event) {
        toggleDetailsGrid(patientDetailsGrid);
    }

    @FXML
    private void handleTestLabClick(MouseEvent event) {
        toggleDetailsGrid(testLabDetailsGrid);
    }

    @FXML
    private void handleMedicationsClick(MouseEvent event) {
        toggleDetailsGrid(medicationsDetailsGrid);
    }

    @FXML
    private void handleGenerateBillClick() throws SQLException{
        // Ouvrir un sélecteur de fichier pour enregistrer le PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer la facture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                pdfController.generatePdf(file.getAbsolutePath(),patientID, patientNameLabel.getText(),totalPriceLabel.getText(), totalPriceInsuranceLabel.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //modifier les champs payees
        AppointmentController.updateAppointmentStatus(patientID);
        TestController.updateOrdonnanceTestStatus(patientID);
        MedicamentController.updateOrdonnanceStatus(patientID);

        totalPriceLabel.setText("0,00 DH");
        totalPriceInsuranceLabel.setText("0,00 DH");
        medicationPriceTextLabel.setText("0,00 DH");
        facturationPriceTextLabel.setText("0,00 DH");
        testPriceTextLabel.setText("0,00 DH");

        patientDetailsGrid.setVisible(false);
        medicationsDetailsGrid.setVisible(false);
        testLabDetailsGrid.setVisible(false);
    }

    public void switchToListBills(MouseEvent event) {
        try {
            // Charger le fichier FXML de la liste des factures
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BillsList.fxml"));
            Parent root = loader.load();

            // Changer la scène pour afficher la liste des factures
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page de la liste des factures.");
        }
    }

    private void toggleDetailsGrid(GridPane gridPane) {
        if (gridPane == patientDetailsGrid) {
            isPatientDetailsVisible = !isPatientDetailsVisible;
            gridPane.setVisible(isPatientDetailsVisible);
        } else if (gridPane == testLabDetailsGrid) {
            isTestLabDetailsVisible = !isTestLabDetailsVisible;
            gridPane.setVisible(isTestLabDetailsVisible);
        } else if (gridPane == medicationsDetailsGrid) {
            isMedicationsDetailsVisible = !isMedicationsDetailsVisible;
            gridPane.setVisible(isMedicationsDetailsVisible);
        }

        if (gridPane.isVisible()) {
            updateDetailsGrid(gridPane);
        } else {
            gridPane.getChildren().clear();
        }
    }

    private void updateDetailsGrid(GridPane gridPane) {
        gridPane.getChildren().clear();
        if (gridPane == patientDetailsGrid) {
            AppointmentGridController.updateAppointmentGrid(gridPane,patientID);
        } else if (gridPane == testLabDetailsGrid) {
            TestGridController.updateTestGrid(gridPane, patientID);
        } else if (gridPane == medicationsDetailsGrid) {
            MedicationGridController.updateMedicationsGrid(gridPane,patientID);
        }

    }
}
