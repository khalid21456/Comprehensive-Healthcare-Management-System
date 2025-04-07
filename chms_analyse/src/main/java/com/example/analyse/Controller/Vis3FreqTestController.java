package com.example.analyse.Controller;
import com.example.analyse.Model.TestFreqModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import java.util.List;

public class Vis3FreqTestController {

    @FXML
    private BarChart<Number, String> barChart;

    private TestFreqModel model;

    public  Vis3FreqTestController() {
        this.model = new TestFreqModel();
    }
    @FXML
    public void initialize() {
        // Configure the BarChart
        barChart.setTitle("Top 5 Most Frequent Tests");
        barChart.getXAxis().setLabel("Frequency");
        barChart.getYAxis().setLabel("Test Services");

        // Fetch data from the model and populate the chart
        fetchAndPopulateData();
    }

    private void fetchAndPopulateData() {
        List<TestFreqModel.TestData> data = model.getTop5FrequentTests();
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        series.setName("Frequency");

        for (TestFreqModel.TestData testData : data) {
            XYChart.Data<Number, String> chartData = new XYChart.Data<>(testData.getFrequency(), testData.getTestName());
            series.getData().add(chartData);

            // Apply color to the bar
            chartData.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) { // Node is now added to the scene
                    newValue.setStyle("-fx-bar-fill: #81C784;");
                }
            });
        }

        // Add the series to the bar chart
        barChart.getData().add(series);
    }
}
