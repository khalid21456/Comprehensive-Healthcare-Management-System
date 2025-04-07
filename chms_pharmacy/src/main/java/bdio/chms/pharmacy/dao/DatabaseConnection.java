package bdio.chms.pharmacy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3307/chms";
    private static final String USER = "root";  // Replace with your MySQL username
    private static final String PASSWORD = "";  // Replace with your MySQL password

    // Load the JDBC driver (Optional: can be omitted if using JDBC 4.0 or later)
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get the database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
