package com.pack.billingsystem.services;


import com.pack.billingsystem.controllers.AppointmentController;
import com.pack.billingsystem.controllers.DatabaseConnection;
import com.pack.billingsystem.controllers.PatientController;
import com.pack.billingsystem.models.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unused")

public class BillService {

    public List<Bill> getAllBills() throws SQLException {
        String query = """ 
                        SELECT Bill.IDPatient,Nom,Prenom,Tel,DateBill,IDBill,IDOrdonnance,IDOrdonnanceTest,IDAppointment 
                        FROM Patient
                        LEFT JOIN Bill 
                        On Patient.IDPatient = Bill.IDPatient""";
        List<Bill> listBills = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        while(set.next()) {
            Bill tempBill = new Bill();
            Patient tempPatient = PatientController.getPatient(set.getInt("IDPatient"));
            Appointment tempAppointment = AppointmentController.getAppointement(set.getInt("IDAppointment"));
            Ordonnance tempOrdonnance = OrdonnanceService.getOrdonnanceById(set.getInt("IDOrdonnance"));
            tempBill.setPatient(tempPatient);
            tempBill.setAppointment(tempAppointment);
            tempBill.setOrdonnance(tempOrdonnance);
            tempBill.setIdBill(set.getInt("IDBill"));
            tempBill.setDateBill(set.getDate("DateBill"));
            listBills.add(tempBill);
        }
        return listBills;
    }

    public List<Bill> getAllBillsByPattern(String pattern) throws SQLException {
        String query = "SELECT Bill.IDPatient,Nom,Prenom,Tel,DateBill,IDBill,IDOrdonnance,IDOrdonnanceTest,IDAppointment FROM Patient INNER JOIN Bill On Patient.IDPatient = Bill.IDPatient AND Nom LIKE '"+pattern+"%'";
        List<Bill> listBills = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        while(set.next()) {
            Bill tempBill = new Bill();
            Patient tempPatient = PatientController.getPatient(set.getInt("IDPatient"));
            Appointment tempAppointment = AppointmentController.getAppointement(set.getInt("IDAppointment"));
            Ordonnance tempOrdonnance = OrdonnanceService.getOrdonnanceById(set.getInt("IDOrdonnance"));
            tempBill.setPatient(tempPatient);
            tempBill.setAppointment(tempAppointment);
            tempBill.setOrdonnance(tempOrdonnance);
            tempBill.setIdBill(set.getInt("IDBill"));
            tempBill.setDateBill(set.getDate("DateBill"));
            listBills.add(tempBill);
        }
        connection.close();
        return listBills;
    }

    public List<PatientBillDTO> getUnpayedBills(List<Bill> bills) {
        List<PatientBillDTO> listBills = new ArrayList<>();
        Iterator<Bill> iterable = bills.iterator();
        while(iterable.hasNext()) {
            Bill iteratorBill = iterable.next();
            if(!isPayed(iteratorBill)) {
                listBills.add(prepareDTO(iteratorBill));
            }
        }
        return listBills;
    }

    private PatientBillDTO prepareDTO(Bill bill) {
        return new PatientBillDTO(
                bill.getPatient().getNom(),
                bill.getPatient().getPrenom(),
                bill.getPatient().getIdPatient(),
                bill.getDateBill(),
                bill.getIdBill(),
                bill.getPatient().getTelephone()
        );
    }

    private boolean isPayed(Bill bill) {
        if(!bill.getAppointment().isPaye())
            return false;
        if(bill.getOrdonnance().getStatus().equals("Delivred"))
            return false;
        return true;
    }

}