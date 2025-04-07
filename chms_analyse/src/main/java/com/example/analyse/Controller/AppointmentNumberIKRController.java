package com.example.analyse.Controller;


import com.example.analyse.Model.appointmentsNumberIKRModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Map;
public class AppointmentNumberIKRController {
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final appointmentsNumberIKRModel model = new  appointmentsNumberIKRModel();



    @FXML
    public void initialize() {
        // Configure the axes
        xAxis.setLabel("Months");
        yAxis.setLabel("Number of Appointments");

        // Add months to the x-axis
        xAxis.getCategories().addAll(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );

        // Load and display data on the chart
        loadChartData();
    }

    private void loadChartData() {
        // Fetch data from the model
        Map<Integer, Integer> data = model.getAppointmentsPerMonth();

        // Create a series for the data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Appointments");

        // Populate the series with data
        for (int i = 1; i <= 12; i++) {
            String monthName = xAxis.getCategories().get(i - 1);
            int count = data.getOrDefault(i, 0); // 0 if no data for the month
            XYChart.Data<String, Number> chartData = new XYChart.Data<>(monthName, count);
            series.getData().add(chartData);

            // Apply color to the bar
            chartData.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) { // Node is now added to the scene graph
                    newNode.setStyle("-fx-bar-fill: #81C784;"); // Replace with desired color
                }
            });
        }

        // Add the series to the chart
        barChart.getData().add(series);

        // Style the legend
        barChart.lookupAll(".chart-legend-item-symbol").forEach(node -> {
            node.setStyle("-fx-background-color: #81C784;"); // Match the bar color
        });
    }


}
