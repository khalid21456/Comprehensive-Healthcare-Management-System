package com.example.analyse.Controller;

import com.example.analyse.Model.GenderAgeKDJModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;
public class GenderAgeChartKDJController {
    @FXML
    private StackedBarChart<String, Number> genderAgePyramidChart;

    @FXML
    private HBox customLegendBox;  // A container for the custom legend

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private ComboBox<String> ageGroupComboBox;

    @FXML
    private ComboBox<String> chartTypeComboBox;

    private GenderAgeKDJModel genderAgeModel;

    @FXML
    public void initialize() {
        // Initialiser le modèle
        genderAgeModel = new GenderAgeKDJModel();

        // Récupérer les groupes d'âge par sexe
        Map<String, Map<String, Integer>> ageGroups = genderAgeModel.getAgeGroupsBySex();
        genderAgePyramidChart.setLegendVisible(false);  // Disable the default legend

        // Création des séries pour hommes et femmes
        XYChart.Series<String, Number> maleSeries = new XYChart.Series<>();
        maleSeries.setName("Male"); // Titre de la série Male
        XYChart.Series<String, Number> femaleSeries = new XYChart.Series<>();
        femaleSeries.setName("Female"); // Titre de la série Female

        // Ajouter les données aux séries
        ageGroups.get("Male").forEach((ageGroup, count) ->
                maleSeries.getData().add(new XYChart.Data<>(ageGroup, count)));
        ageGroups.get("Female").forEach((ageGroup, count) ->
                femaleSeries.getData().add(new XYChart.Data<>(ageGroup, count)));

        // Ajouter les séries au graphique
        genderAgePyramidChart.getData().addAll(maleSeries, femaleSeries);

        // Appliquer les couleurs et les tooltips après l'initialisation du graphique
        Platform.runLater(() -> {
            // Appliquer un style aux nœuds de la série 'Male' après leur création
            for (XYChart.Data<String, Number> data : maleSeries.getData()) {
                data.getNode().setStyle("-fx-bar-fill: #424088;");  // Bleu foncé

                // Créer un Tooltip pour les données 'Male'
                Tooltip tooltip = new Tooltip("Male: " + data.getYValue() + " in age group " + data.getXValue());
                Tooltip.install(data.getNode(), tooltip);  // Installer le Tooltip sur la barre
            }

            // Appliquer un style aux nœuds de la série 'Female' après leur création
            for (XYChart.Data<String, Number> data : femaleSeries.getData()) {
                data.getNode().setStyle("-fx-bar-fill: #5A9BD5;");  // Bleu clair

                // Créer un Tooltip pour les données 'Female'
                Tooltip tooltip = new Tooltip("Female: " + data.getYValue() + " in age group " + data.getXValue());
                Tooltip.install(data.getNode(), tooltip);  // Installer le Tooltip sur la barre
            }

            // Create custom legend for Male and Female
            createCustomLegend();
        });
    }

    private void createCustomLegend() {
        // Clear any previous legend entries
        customLegendBox.getChildren().clear();

        // Create custom legend items for Male and Female
        // For Male
        Rectangle maleColor = new Rectangle(20, 20, Color.web("#424088"));  // Blue for Male
        Label maleLabel = new Label("Male");
        HBox maleLegendItem = new HBox(5, maleColor, maleLabel);  // Space between the color box and label
        maleLegendItem.setStyle("-fx-font-size: 14px;");

        // For Female
        Rectangle femaleColor = new Rectangle(20, 20, Color.web("#5A9BD5"));  // Light Blue for Female
        Label femaleLabel = new Label("Female");
        HBox femaleLegendItem = new HBox(5, femaleColor, femaleLabel);
        femaleLegendItem.setStyle("-fx-font-size: 14px;");

        // Add these items to the custom legend box
        customLegendBox.getChildren().addAll(maleLegendItem, femaleLegendItem);
    }
}
