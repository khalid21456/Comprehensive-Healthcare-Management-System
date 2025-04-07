package com.example.analyse.Controller;

import com.example.analyse.Model.ProgressHMZModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
public class ProgressBarHMZController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label percentageLabel;

    @FXML
    public void initialize() {
        // Initialize ProgressModel
        ProgressHMZModel progressModel = new ProgressHMZModel();
        double progress = progressModel.getAppointmentsProgressThisYear();

        // Set the progress to the progress bar
        progressBar.setProgress(progress / 100.0);

        // Set the percentage text below the progress bar
        percentageLabel.setText(String.format("%.2f%%", progress));
    }
}
