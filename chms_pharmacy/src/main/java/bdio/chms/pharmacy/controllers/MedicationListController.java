package bdio.chms.pharmacy.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import bdio.chms.pharmacy.models.Medicament;
import bdio.chms.pharmacy.dao.MedicationService;

import java.util.Date;

public class MedicationListController {

    @FXML
    private TableView<Medicament> medicationTable;

    @FXML
    private TableColumn<Medicament, Integer> idColumn;
    @FXML
    private TableColumn<Medicament, String> nameColumn;
    @FXML
    private TableColumn<Medicament, String> descriptionColumn;
    @FXML
    private TableColumn<Medicament, Date> expirationTimeColumn;
    @FXML
    private TableColumn<Medicament, Double> priceColumn;
    @FXML
    private TableColumn<Medicament, String> typeColumn;
    @FXML
    private TableColumn<Medicament, Integer> quantityColumn;
    @FXML
    private TableColumn<Medicament, Void> actionColumn;


    @FXML
    public void initialize() {
        MedicationService medicationService = new MedicationService();
        medicationTable.getScene().getStylesheets().add(getClass().getResource("/bdio/chms/pharmacy/fxml/styles.css").toExternalForm());
        // Set the cell value factory for each column
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomMed()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        expirationTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateExpiration()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrixUnit()).asObject());
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescMed()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQty()));
        // Load medications into the table
        medicationTable.getItems().setAll(medicationService.getAllMedications());
    }
}
