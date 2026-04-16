package com.tracker.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.tracker.config.DatabaseConfig;

public class LoginCheck {
    
    public boolean checkLogin(String login, String password) {
        String hashedPassword = hashPassword(password);
        if (hashedPassword == null) return false;

        // Use double quotes for "User" table in PostgreSQL
        String query = "SELECT * FROM \"User\" WHERE login = ? AND password = ?";

        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, hashedPassword);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a match is found
            }
        } catch (SQLException e) {
            System.err.println("Error during login check: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not found: " + e.getMessage());
            return null;
        }
    }
}
