package com.pack.billingsystem.controllers;

import com.pack.billingsystem.models.OrdonnanceTest;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.List;

public class TestGridController {
    public static void updateTestGrid(GridPane gridPane, int patientID) {
        try {
            // Effacer les anciens éléments du GridPane
            gridPane.getChildren().clear();

            // Récupérer la liste des rendez-vous non payés
            List<OrdonnanceTest> unpaidtests = TestController.getNonPayeTestsByPatient(patientID);

            if (unpaidtests.isEmpty()) {
                Label noAppointmentLabel = new Label("Aucun test non payé trouvé.");
                gridPane.add(noAppointmentLabel, 0, 0);
                return;
            }

            int row = 0;
            // Parcourir les rendez-vous et les afficher
            for (OrdonnanceTest test : unpaidtests) {
                Label nameLabel = new Label("Nom");
                nameLabel.getStyleClass().add("detail-label");

                Label dateLabel = new Label("Date");
                dateLabel.getStyleClass().add("detail-label");

                Label prixLabel = new Label("Prix");
                prixLabel.getStyleClass().add("detail-label");

                Label resultLabel = new Label("Résultat");
                resultLabel.getStyleClass().add("detail-label");

                gridPane.add(nameLabel, 0, row);
                gridPane.add(dateLabel, 2, row);
                gridPane.add(prixLabel, 4, row);
                gridPane.add(resultLabel, 6, row);

                Label nameValue = new Label(test.getTestNom());
                nameValue.getStyleClass().add("detail-value");

                Label dateValue = new Label(test.getDate().toString());
                dateValue.getStyleClass().add("detail-value");

                Label prixValue = new Label(String.format("%.2f DH", test.getPrix()));
                prixValue.getStyleClass().add("detail-value");

                Label resultValue = new Label(String.format("%d",test.getResultat()));
                resultValue.getStyleClass().add("detail-value");

                gridPane.add(nameValue, 1, row);
                gridPane.add(dateValue, 3, row);
                gridPane.add(prixValue, 5, row);
                gridPane.add(resultValue, 7, row);

                row++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Erreur lors de la récupération des données.");
            gridPane.add(errorLabel, 0, 0);
        }
    }


}
