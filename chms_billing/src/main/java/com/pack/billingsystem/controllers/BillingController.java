package com.pack.billingsystem.controllers;

import com.pack.billingsystem.models.*;
import javafx.fxml.FXML;
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
    private Label patientNameLabel,totalPriceLabel,totalPriceInsuranceLabel,medicationPriceTextLabel,facturationPriceTextLabel,testPriceTextLabel;

    private boolean isPatientDetailsVisible = false;
    private boolean isTestLabDetailsVisible = false;
    private boolean isMedicationsDetailsVisible = false;
    private int patientID = 2;

    private PdfController pdfController;

    @FXML
    public void initialize() throws SQLException {
        // Populate patient details
        patientDetailsGrid.setVisible(false);
        testLabDetailsGrid.setVisible(false);
        medicationsDetailsGrid.setVisible(false);
        totalPriceLabel.setVisible(false);

        facturationPriceTextLabel.setText(String.format("%.2f DH", PrixController.updatePriceAppointement(patientID)));
        testPriceTextLabel.setText(String.format("%.2f DH",PrixController.updatePriceTest(patientID)));
        medicationPriceTextLabel.setText(String.format("%.2f DH", PrixController.updatePriceMedicaments(patientID)));

        Patient patient = PatientController.getPatientById(patientID);
        patientNameLabel.setText(patient.getNom()+" "+patient.getPrenom());
        totalPriceInsuranceLabel.setText(String.format("%.2f DH", PrixController.updateTotalPriceInsurance(patientID,patient.getPourcentageAssurance())));
        totalPriceInsuranceLabel.setVisible(true);
        totalPriceLabel.setText(String.format("%.2f DH", PrixController.updateTotalPrice(patientID)));
        totalPriceLabel.setVisible(true);

        pdfController = new PdfController();
    }

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
