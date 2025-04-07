package com.example.doctorappointments.service;

import com.example.doctorappointments.model.Availability;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class PlanningDAO {


    public void addPlanning(Availability availability) throws Exception {
        String query = "INSERT INTO Planning (Date, Date_Start, Date_Fin, Availability,IDDoctor) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {


            statement.setDate(1, java.sql.Date.valueOf(availability.getDate()));
            statement.setTime(2, java.sql.Time.valueOf(availability.getStartTime()));
            statement.setTime(3, java.sql.Time.valueOf(availability.getEndTime()));
            statement.setBoolean(4, availability.isAvailable());
            statement.setInt(5, availability.getIDDoc());
            statement.executeUpdate();
        }
        System.out.println("Disponibilité ajoutée avec succès.");
    }
}
