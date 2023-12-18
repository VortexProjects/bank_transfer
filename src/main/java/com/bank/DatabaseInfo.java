package com.bank;
import java.sql.*;

public class DatabaseInfo {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/bank_transfer_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    public static boolean loginStatus=false;

    // Establish a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    // Create a statement for executing SQL queries
    public static Statement createStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    // Close the resources (statement and connection)
    public static void closeResources(Statement statement, Connection connection) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

