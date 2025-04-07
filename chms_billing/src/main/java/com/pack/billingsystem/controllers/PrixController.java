package com.pack.billingsystem.controllers;

import com.pack.billingsystem.models.Appointment;
import com.pack.billingsystem.models.Medicament;
import com.pack.billingsystem.models.OrdonnanceTest;

import java.sql.SQLException;
import java.util.List;

public class PrixController {
    public static double updatePriceAppointement(int patientID){
        double price = 0.0;
        try {
            List<Appointment> unpaidAppointments = AppointmentController.getAllAppointments(patientID);
            for (Appointment appointment :unpaidAppointments){
                price += appointment.getPrice();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static double updatePriceMedicaments(int patientID){
        double price = 0.0;
        try {
            List<Medicament> unpaidmedicaments = MedicamentController.getMedicamentsByPatient(patientID);
            for (Medicament medicament :unpaidmedicaments){
                price += medicament.getPrix();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static double updatePriceTest(int patientID){
        double price = 0.0;
        try {
            List<OrdonnanceTest> unpaidTests = TestController.getNonPayeTestsByPatient(patientID);
            for (OrdonnanceTest test :unpaidTests){
                price += test.getPrix();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static double updateTotalPrice(int patientID) {
        double totalPrice = 0.0;
        totalPrice = updatePriceMedicaments(patientID) + updatePriceTest(patientID) + updatePriceAppointement(patientID);
        return totalPrice;
    }

    public static double updateTotalPriceInsurance(int patientID,double pourcentage) {
        double reduction = updateTotalPrice(patientID) * (pourcentage/100.0);
        double finalPrice = updateTotalPrice(patientID) - reduction;
        return finalPrice;
    }
}
