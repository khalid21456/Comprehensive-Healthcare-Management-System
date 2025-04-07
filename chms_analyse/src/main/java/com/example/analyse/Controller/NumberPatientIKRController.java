package com.example.analyse.Controller;

import com.example.analyse.Model.NumberPatientIKRModel;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Map;

public class NumberPatientIKRController {
    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final NumberPatientIKRModel model  = new NumberPatientIKRModel();

    @FXML
    public void initialize() {
        // Configure axes
        xAxis.setLabel("YEARS");
        yAxis.setLabel("PATIENTS");
        lineChart.setTitle("NUMBER OF PATIENTS PER YEAR");

        // Configure Y-axis scale
        yAxis.setTickUnit(50);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(300);

        // Load chart data
        loadChartData();
    }

    private void loadChartData() {
        // Fetch data from the model
        Map<Integer, Integer> data = model.getPatientsPerYear();

        // Create a data series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Patients");

        // Populate the series with data
        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
        }

        // Add the series to the chart
        lineChart.getData().add(series);

        // Apply style to the series
        if (series.getNode() != null) {
            series.getNode().setStyle("-fx-stroke: #4CAF50; -fx-stroke-width: 2px;");
        }
    }
}
