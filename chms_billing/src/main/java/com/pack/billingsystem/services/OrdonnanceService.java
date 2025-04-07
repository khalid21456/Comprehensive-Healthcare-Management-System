package com.pack.billingsystem.services;


import com.pack.billingsystem.controllers.DatabaseConnection;
import com.pack.billingsystem.models.Ordonnance;
import com.pack.billingsystem.models.Patient;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class OrdonnanceService {

    public static Ordonnance getOrdonnanceById(int idOrdonannce) throws SQLException{
        Ordonnance ordonnance = new Ordonnance();
        String query = "SELECT * FROM Ordonnance WHERE IDOrdonnance = "+idOrdonannce;
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        ordonnance.setIdOrdonnance(resultSet.getInt(1));
        ordonnance.setDoctor(resultSet.getInt(2));
        ordonnance.setDateCreation(resultSet.getDate(3));
        ordonnance.setStatus(resultSet.getString(6));
        connection.close();
        return ordonnance;
    }

}
