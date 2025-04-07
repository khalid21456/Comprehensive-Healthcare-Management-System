package com.example.analyse.Controller;

import com.example.analyse.Model.TestResultModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class Vis2TestResultController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        barChart.setTitle("Number of Tests Per Month (Past Year)");
        barChart.getXAxis().setLabel("Month");
        barChart.getYAxis().setLabel("Number of Tests");

        // Populate the chart
        populateBarChart();
    }

    private void populateBarChart() {
        TestResultModel model = new TestResultModel();
        List<TestResultModel.MonthlyTest> data = model.getMonthlyTests();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Tests");

        for (TestResultModel.MonthlyTest entry : data) {
            series.getData().add(new XYChart.Data<>(entry.getMonth(), entry.getNumberOfTests()));
        }

        barChart.getData().add(series);
    }
}
