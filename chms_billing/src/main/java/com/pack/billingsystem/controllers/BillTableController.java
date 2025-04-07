package com.pack.billingsystem.controllers;

import com.pack.billingsystem.MainController;
import com.pack.billingsystem.models.Bill;
import com.pack.billingsystem.models.BillRowData;
import com.pack.billingsystem.models.PatientBillDTO;
import com.pack.billingsystem.services.BillService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class BillTableController implements Initializable {

    @FXML
    public Button searchBtn;

    @FXML
    public TextField inputSearch;

    @FXML
    public TableView<BillRowData> table;

    @FXML
    public TableColumn<BillRowData, Integer> idBill;

    @FXML
    public TableColumn<BillRowData, String> nom;

    @FXML
    public TableColumn<BillRowData, String> prenom;

    @FXML
    public TableColumn<BillRowData, String> tele;

    @FXML
    public TableColumn<BillRowData, Date> date;

    public ObservableList<BillRowData> list = FXCollections.observableArrayList();

    private BillService billService = new BillService();

    private MainController mainController = new MainController();

    private SearchController searchController = new SearchController();

    public void setRowData() throws SQLException {
        List<Bill> bills = billService.getAllBills();
        List<PatientBillDTO> unpayedBills = billService.getUnpayedBills(bills);
        for(PatientBillDTO unpayedBill:unpayedBills) {
            BillRowData row = new BillRowData(
                    unpayedBill.getBillID(),
                    unpayedBill.getPatientFirstName(),
                    unpayedBill.getPatientLastName(),
                    unpayedBill.getDate(),
                    unpayedBill.getTel(),
                    unpayedBill.getPatientID()
            );

            list.add(row);
        }
        table.setItems(list);
    }

    public void setRowData(List<PatientBillDTO> patientBillDTOS) throws SQLException {
        List<Bill> bills = billService.getAllBills();
        list.clear();
        for(PatientBillDTO unpayedBill:patientBillDTOS) {
            BillRowData row = new BillRowData(
                    unpayedBill.getBillID(),
                    unpayedBill.getPatientFirstName(),
                    unpayedBill.getPatientLastName(),
                    unpayedBill.getDate(),
                    unpayedBill.getTel(),
                    unpayedBill.getPatientID()
            );

            list.add(row);
        }
        table.setItems(list);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idBill.setCellValueFactory(new PropertyValueFactory<BillRowData,Integer>("idBill"));
        nom.setCellValueFactory(new PropertyValueFactory<BillRowData,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<BillRowData,String>("prenom"));
        date.setCellValueFactory(new PropertyValueFactory<BillRowData,Date>("date"));
        tele.setCellValueFactory(new PropertyValueFactory<BillRowData,String>("tele"));
        try {
            setRowData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                BillRowData selectedBill = table.getSelectionModel().getSelectedItem();
                if (selectedBill != null)  {
                    try {
                        int patientID = selectedBill.getBillId();
                        if (patientID>0)
                            mainController.switchToBill(event,selectedBill.getIdPatient());
                        else System.out.println("Erreur : ID patient Invalide.");
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void onClickSearch(ActionEvent event) throws SQLException{
        List<PatientBillDTO> rows =  searchController.onClickSearch(event,inputSearch);
        setRowData(rows);
    }



    public void refresh(ActionEvent event) {
        try {
            List<Bill> bills = billService.getAllBills();
            List<PatientBillDTO> rows = billService.getUnpayedBills(bills);
            setRowData(rows);
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }
}