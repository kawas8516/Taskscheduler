package com.taskscheduler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/taskscheduler";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "2407";

    static {
        try {
            Class.forName("org.postgresql.Driver"); // Load driver once
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("PostgreSQL JDBC Driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
