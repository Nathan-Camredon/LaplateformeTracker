package com.tracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.tracker.config.DatabaseConfig;

/**
 * Standalone utility class used to securely generate users manually.
 * Intended to be run purely from the terminal or IDE for setup purposes.
 */
public class CreateUser {
    /**
     * Console entry to inject a pre-defined administrator directly into the database.
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        String login = "flo";

        String hashedPassword = "9533f81e605d392348c66e74b3d39ed7fa2ba19e48710be14c5144b67e7c81d8";

        String sql = "INSERT INTO \"User\" (login, password) VALUES (?, ?) ON CONFLICT (login) DO UPDATE SET password = EXCLUDED.password";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();

            System.out.println("User 'flo' successfully created/updated in the database.");
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
