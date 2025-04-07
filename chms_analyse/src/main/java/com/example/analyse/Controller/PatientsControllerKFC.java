package com.example.analyse.Controller;

import com.example.analyse.Model.PatientsModelKFC;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
public class PatientsControllerKFC {
    @FXML
    private TableView<PatientsModelKFC.DoctorPatientData> tableView;
    @FXML
    private TableColumn<PatientsModelKFC.DoctorPatientData, String> doctorNameColumn;
    @FXML
    private TableColumn<PatientsModelKFC.DoctorPatientData, Double> avgPatientsPerDayColumn;

    private final PatientsModelKFC model = new PatientsModelKFC();

    @FXML
    public void initialize() {
        doctorNameColumn.setCellValueFactory(cellData -> cellData.getValue().doctorNameProperty());
        avgPatientsPerDayColumn.setCellValueFactory(cellData -> cellData.getValue().averagePatientsPerDayProperty().asObject());

        ObservableList<PatientsModelKFC.DoctorPatientData> doctorPatientDataList = model.getPatientsPerDoctor();
        tableView.setItems(doctorPatientDataList);
    }
}
