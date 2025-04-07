package com.pack.billingsystem.controllers;

import com.pack.billingsystem.models.Medicament;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.List;

public class MedicationGridController {
    public static void updateMedicationsGrid(GridPane gridPane, int patientID) {
        try {
            // Effacer les anciens éléments du GridPane
            gridPane.getChildren().clear();

            // Récupérer la liste des rendez-vous non payés
            List<Medicament> unpaidmedicaments = MedicamentController.getMedicamentsByPatient(patientID);

            if (unpaidmedicaments.isEmpty()) {
                Label noMedicamentLabel = new Label("Aucun medicament non payé trouvé.");
                gridPane.add(noMedicamentLabel, 0, 0);
                return;
            }

            int row = 0;
            // Parcourir les rendez-vous et les afficher
            for (Medicament medicament : unpaidmedicaments) {
                Label medicationNameLabel = new Label("Medicament " );
                medicationNameLabel.getStyleClass().add("detail-label");

                Label medicationPriceLabel = new Label("Prix");
                medicationPriceLabel.getStyleClass().add("detail-label");

                gridPane.add(medicationNameLabel, 0, row);
                gridPane.add(medicationPriceLabel, 2, row);

                Label medicationNameValue = new Label( medicament.getNom());
                medicationNameValue.getStyleClass().add("detail-value");

                Label medicationPriceValue = new Label(String.format("%.2f DH", medicament.getPrix()));
                medicationPriceValue.getStyleClass().add("detail-value");

                gridPane.add(medicationNameValue, 1, row);
                gridPane.add(medicationPriceValue, 3, row);

                row++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Erreur lors de la récupération des données.");
            gridPane.add(errorLabel, 0, 0);
        }
    }
}
