package com.example.doctorappointments.service;

import javafx.collections.ObservableList;
import java.sql.*;

public class OrdonnanceService {
    public static boolean insertOrdonnanceTest(int idDoctor, Timestamp dateTest, int idPatient, ObservableList<Integer> idTests) {
        String sqlOrdonnance = "INSERT INTO ordonnancetest (IDDoctor, DateOrdonnanceTest, IDPatient, Status) VALUES (?, ?, ?, ?)";
        String sqlOrdonnanceMedicament = "INSERT INTO testresult (IDOrdonnanceTest, IDTest) VALUES (?, ?)";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement pstmtOrdonnance = conn.prepareStatement(sqlOrdonnance, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement pstmtOrdonnanceMedicament = conn.prepareStatement(sqlOrdonnanceMedicament)) {

                // Prepare and execute Ordonnance insertion
                pstmtOrdonnance.setInt(1, idDoctor);
                pstmtOrdonnance.setTimestamp(2, dateTest);
                pstmtOrdonnance.setInt(3, idPatient);
                pstmtOrdonnance.setInt(4, 0);

                int affectedRows = pstmtOrdonnance.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating ordonnance failed, no rows affected.");
                }

                try (ResultSet generatedKeys = pstmtOrdonnance.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);

                        // Prepare batch insert for Medicaments
                        for (Integer idMedicament : idTests) {
                            pstmtOrdonnanceMedicament.setInt(1, generatedId);
                            pstmtOrdonnanceMedicament.setInt(2, idMedicament);
                            pstmtOrdonnanceMedicament.addBatch();
                        }

                        // Execute batch insert
                        pstmtOrdonnanceMedicament.executeBatch();
                    } else {
                        throw new SQLException("Creating ordonnance failed, no ID obtained.");
                    }
                }

                // Commit transaction
                conn.commit();
                return true;

            } catch (SQLException e) {
                // Rollback transaction in case of error
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error during rollback: " + ex.getMessage());
                }
                System.err.println("Database error: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            return false;
        } finally {
            // Ensure connection is closed
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset to default
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public static boolean insertOrdonnance(int idDoctor, Timestamp dateCreation, int idPharmacien, int idPatient, ObservableList<Integer> idMedicaments) {
        String sqlOrdonnance = "INSERT INTO Ordonnance (IDDoctor, DateCreation, IDPharmacien, IDPatient, Status) VALUES (?, ?, ?, ?, ?)";
        String sqlOrdonnanceMedicament = "INSERT INTO Ordonnance_Medicament (IDOrdonnance, IDMedicament) VALUES (?, ?)";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement pstmtOrdonnance = conn.prepareStatement(sqlOrdonnance, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement pstmtOrdonnanceMedicament = conn.prepareStatement(sqlOrdonnanceMedicament)) {

                // Prepare and execute Ordonnance insertion
                pstmtOrdonnance.setInt(1, idDoctor);
                pstmtOrdonnance.setTimestamp(2, dateCreation);
                pstmtOrdonnance.setInt(3, idPharmacien);
                pstmtOrdonnance.setInt(4, idPatient);
                pstmtOrdonnance.setInt(5, 0);

                int affectedRows = pstmtOrdonnance.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating ordonnance failed, no rows affected.");
                }

                try (ResultSet generatedKeys = pstmtOrdonnance.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);

                        // Prepare batch insert for Medicaments
                        for (Integer idMedicament : idMedicaments) {
                            pstmtOrdonnanceMedicament.setInt(1, generatedId);
                            pstmtOrdonnanceMedicament.setInt(2, idMedicament);
                            pstmtOrdonnanceMedicament.addBatch();
                        }

                        // Execute batch insert
                        pstmtOrdonnanceMedicament.executeBatch();
                    } else {
                        throw new SQLException("Creating ordonnance failed, no ID obtained.");
                    }
                }

                // Commit transaction
                conn.commit();
                return true;

            } catch (SQLException e) {
                // Rollback transaction in case of error
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error during rollback: " + ex.getMessage());
                }
                System.err.println("Database error: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            return false;
        } finally {
            // Ensure connection is closed
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset to default
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}