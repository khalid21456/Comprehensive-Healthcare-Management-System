package org.example.patient.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3307/chms";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Your MySQL root password

    static {
        try {
            // Load the JDBC driver once when the class is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL JDBC Driver not found!");
            System.err.println("Please ensure you have the MySQL Connector/J in your classpath");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        // Create a new connection each time
        System.out.println("Attempting to connect to database at " + URL);
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Database connection successful!");
        
        // Ensure table and columns exist
        createTableIfNotExists(connection);
        
        return connection;
    }

    private static void createTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS patient (" +
                    "idPatient INT PRIMARY KEY AUTO_INCREMENT," +
                    "Nom VARCHAR(50)," +
                    "Prenom VARCHAR(50)," +
                    "Sexe VARCHAR(10)," +
                    "BirthDate DATETIME," +
                    "Adresse VARCHAR(255)," +
                    "Tel VARCHAR(20)," +
                    "IDInsurance INT," +
                    "CIN VARCHAR(20) UNIQUE," +
                    "Ville VARCHAR(50)" +
                    ")";
        
        try (var stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Patient table is ready");
            addPasswordColumnIfNotExists(conn);
        }
    }

    private static void addPasswordColumnIfNotExists(Connection conn) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_NAME = 'patient' AND COLUMN_NAME = 'password'";
        try (var checkStmt = conn.createStatement(); var rs = checkStmt.executeQuery(checkSql)) {
            if (rs.next() && rs.getInt(1) == 0) {
                String sql = "ALTER TABLE patient ADD COLUMN password VARCHAR(255)";
                try (var stmt = conn.createStatement()) {
                    stmt.execute(sql);
                    System.out.println("Password column added successfully");
                }
            } else {
                System.out.println("Password column already exists");
            }
        } catch (SQLException e) {
            System.err.println("Error checking or adding password column: " + e.getMessage());
            throw e;
        }
    }
}

