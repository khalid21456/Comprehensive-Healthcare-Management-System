package com.pack.billingsystem.controllers;

import com.pack.billingsystem.models.Appointment;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.List;

public class AppointmentGridController {
    public static void updateAppointmentGrid(GridPane gridPane, int patientID) {
        try {
            // Effacer les anciens éléments du GridPane
            gridPane.getChildren().clear();
            // Récupérer la liste des rendez-vous non payés
            List<Appointment> unpaidAppointments = AppointmentController.getAllAppointments(patientID);

            if (unpaidAppointments.isEmpty()) {
                Label noAppointmentLabel = new Label("Aucun rendez-vous non payé trouvé.");
                gridPane.add(noAppointmentLabel, 0, 0);
                return;
            }

            int row = 0;
            // Parcourir les rendez-vous et les afficher
            for (Appointment appointment : unpaidAppointments) {
                // Ajouter les en-têtes
                Label doctorLabelHeader = new Label("Docteur");
                doctorLabelHeader.getStyleClass().add("detail-label");

                Label serviceLabelHeader = new Label("Type de service");
                serviceLabelHeader.getStyleClass().add("detail-label");

                Label dateLabelHeader = new Label("Date");
                dateLabelHeader.getStyleClass().add("detail-label");

                Label priceLabelHeader = new Label("Prix");
                priceLabelHeader.getStyleClass().add("detail-label");

                gridPane.add(doctorLabelHeader, 0, row);
                gridPane.add(serviceLabelHeader, 2, row);
                gridPane.add(dateLabelHeader, 4, row);
                gridPane.add(priceLabelHeader, 6, row);

                Label doctorName = new Label(appointment.getDoctor().getNom()+" "+appointment.getDoctor().getPrenom()); // Service correspond au nom du docteur
                doctorName.getStyleClass().add("detail-value");

                Label serviceName = new Label(appointment.getService()); // Service correspond au nom du docteur
                serviceName.getStyleClass().add("detail-value");

                Label date = new Label(appointment.getAppointmentDate().toString());
                date.getStyleClass().add("detail-value");

                Label price = new Label(String.format("%.2f DH", appointment.getPrice()));
                price.getStyleClass().add("detail-value");

                gridPane.add(doctorName, 1, row);
                gridPane.add(serviceName, 3, row);
                gridPane.add(date, 5, row);
                gridPane.add(price, 7, row);

                row++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Erreur lors de la récupération des données.");
            gridPane.add(errorLabel, 0, 0);
        }
    }
}
