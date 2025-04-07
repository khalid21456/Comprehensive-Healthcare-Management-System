package com.example.analyse.Controller;

import com.example.analyse.Model.TestTimeTraitementKDJModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class TraitementResultsKDJController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final TestTimeTraitementKDJModel testResultModel = new TestTimeTraitementKDJModel();

    @FXML
    public void initialize() {
        // Configuration de l'axe des X (affichage du contenu)


        // Configuration de l'axe des Y (temps en heures, de 0 à 48)
        yAxis.setLabel("Time (hours)");
        yAxis.setAutoRanging(false);  // Désactive le auto-ranging
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(48);
        yAxis.setTickUnit(5);  // Affiche des ticks toutes les 5 heures

        // Récupération de la moyenne depuis le modèle
        double averageTime = testResultModel.getAverageProcessingTime();

        // Création de la série de données

        XYChart.Series<String, Number> series = new XYChart.Series<>();


        // Ajout de données dynamiques à la série

        series.getData().add(new XYChart.Data<>("Average Time", averageTime));

        // Ajouter la série au graphique
        barChart.getData().add(series);
        barChart.setLegendVisible(false);

        // Appliquer un style aux barres une fois qu'elles sont rendues
        series.getData().forEach(data -> {
            data.getNode().setStyle("-fx-bar-fill: #5A9BD5;");  // Couleur bleue pour chaque barre
        });
    }
}
