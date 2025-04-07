package com.pack.billingsystem.controllers;

import com.pack.billingsystem.models.Bill;
import com.pack.billingsystem.models.BillRowData;
import com.pack.billingsystem.models.PatientBillDTO;
import com.pack.billingsystem.services.BillService;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SearchController {

    BillService billService = new BillService();

    public List<PatientBillDTO> onClickSearch(ActionEvent event, TextField inputSearch) throws SQLException{
        String pattern = inputSearch.getText();
        List<Bill> billsByPattern = billService.getAllBillsByPattern(pattern);
        List<PatientBillDTO> patientBillDTOS = billService.getUnpayedBills(billsByPattern);
        return patientBillDTOS;
    }


}
