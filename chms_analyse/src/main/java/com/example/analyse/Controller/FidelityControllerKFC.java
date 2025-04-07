package com.example.analyse.Controller;

import com.example.analyse.Model.FidelityModelKFC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
public class FidelityControllerKFC {

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label percentageLabel;

    private final FidelityModelKFC fidelityModel = new FidelityModelKFC();

    @FXML
    public void initialize() {
        // Fetch the fidelity percentage
        double percentage = fidelityModel.getFidelityPercentage();

        // Update progress bar and label
        progressBar.setProgress(percentage / 100);
        percentageLabel.setText(String.format("Pourcentage des patients fidèles : %.2f%%", percentage));
    }
}
