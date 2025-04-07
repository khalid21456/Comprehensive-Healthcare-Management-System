package com.example.analyse.Controller;

import com.example.analyse.Model.AppointmentPerServiceHMZModel;
import com.example.analyse.Model.ProgressHMZModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class AppointmentProgressController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label percentageLabel;

    @FXML
    public void initialize() {
        setupBarChart();
        setupProgressBar();
    }

    private void setupBarChart() {
        AppointmentPerServiceHMZModel model = new AppointmentPerServiceHMZModel();
        var appointmentsData = model.getAppointmentsPerService();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Appointments by Service");

        for (var appointment : appointmentsData) {
            series.getData().add(new XYChart.Data<>(appointment.getServiceName(), appointment.getNumberOfAppointments()));
        }

        barChart.getData().add(series);
        xAxis.setLabel("Service");
        yAxis.setLabel("Number of Appointments");
    }

    private void setupProgressBar() {
        ProgressHMZModel progressModel = new ProgressHMZModel();
        double progress = progressModel.getAppointmentsProgressThisYear();

        progressBar.setProgress(progress / 100.0);
        percentageLabel.setText(String.format("%.2f%%", progress));
    }
}