package com.example.analyse.Model;

import com.example.analyse.DatabaseConnection;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class PatientsModelKFC {
    public ObservableList<DoctorPatientData> getPatientsPerDoctor() {
        String query = """
                WITH DailyCounts AS ( 
                    SELECT 
                        d.Nom AS DoctorName, 
                        DATE(a.AppointmentDate) AS AppointmentDay, 
                        COUNT(a.IDPatient) AS PatientsPerDay
                    FROM 
                        Appointment a
                    JOIN 
                        Doctor d 
                    ON 
                        a.IDDoctor = d.IDDoctor
                    GROUP BY 
                        d.IDDoctor, DATE(a.AppointmentDate)
                ),
                ModeCalculation AS (
                    SELECT 
                        DoctorName, 
                        PatientsPerDay, 
                        COUNT(PatientsPerDay) AS Frequency
                    FROM 
                        DailyCounts
                    GROUP BY 
                        DoctorName, PatientsPerDay
                ),
                RankedModes AS (
                    SELECT 
                        DoctorName, 
                        PatientsPerDay AS ModePatientsPerDay, 
                        Frequency,
                        ROW_NUMBER() OVER (PARTITION BY DoctorName ORDER BY Frequency DESC, PatientsPerDay DESC) AS RowNum
                    FROM 
                        ModeCalculation
                )
                SELECT 
                    DoctorName, 
                    ModePatientsPerDay
                FROM 
                    RankedModes
                WHERE 
                    RowNum = 1
                ORDER BY 
                    DoctorName;
        """;

        ObservableList<DoctorPatientData> doctorPatientDataList = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String doctorName = resultSet.getString("DoctorName");
                int modePatientsPerDay = resultSet.getInt("ModePatientsPerDay");

                doctorPatientDataList.add(new DoctorPatientData(doctorName, modePatientsPerDay));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorPatientDataList;
    }

    public static class DoctorPatientData {
        private final SimpleStringProperty doctorName;
        private final SimpleDoubleProperty averagePatientsPerDay;

        public DoctorPatientData(String doctorName, int modePatientsPerDay) {
            this.doctorName = new SimpleStringProperty(doctorName);
            this.averagePatientsPerDay = new SimpleDoubleProperty(modePatientsPerDay);
        }

        public String getDoctorName() {
            return doctorName.get();
        }

        public SimpleStringProperty doctorNameProperty() {
            return doctorName;
        }

        public double getAveragePatientsPerDay() {
            return averagePatientsPerDay.get();
        }

        public SimpleDoubleProperty averagePatientsPerDayProperty() {
            return averagePatientsPerDay;
        }
    }
}
