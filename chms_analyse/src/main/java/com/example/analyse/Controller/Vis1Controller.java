
package com.example.analyse.Controller;
import com.example.analyse.Model.PatientModelForInssurence;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
public class Vis1Controller {

    @FXML
    private PieChart pieChart;
    private PatientModelForInssurence model;

    public Vis1Controller() {
        this.model = new PatientModelForInssurence();
    }

    public void initialize() {
        try {
            double[] data = model.getPatientInsuranceData();

            // Create chart slices
            PieChart.Data insuredSlice = new PieChart.Data("Patients Assurés", data[0]);
            PieChart.Data uninsuredSlice = new PieChart.Data("Patients Non Assurés", data[1]);

            pieChart.getData().addAll(insuredSlice, uninsuredSlice);

            // Add percentage labels
            for (PieChart.Data slice : pieChart.getData()) {
                slice.nameProperty().set(slice.getName() + " (" + String.format("%.1f%%", slice.getPieValue()) + ")");
            }

            // Set custom colors
            insuredSlice.getNode().setStyle("-fx-pie-color: #81C784;"); // Green pastel
            uninsuredSlice.getNode().setStyle("-fx-pie-color: #5A9BD5;"); // Light blue

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
