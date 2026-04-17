package com.tracker.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.tracker.config.DatabaseConfig;

/**
 * Service orchestrating local user authentication mechanisms and security hashing.
 */
public class LoginCheck {

    /**
     * Verifies the administrator credentials against the encrypted database records.
     * @param login The inputted login name
     * @param password The inputted raw password
     * @return boolean True if the credentials are valid, false otherwise
     */
    public boolean checkLogin(String login, String password) {
        String hashedPassword = hashPassword(password);
        if (hashedPassword == null) return false;

        String query = "SELECT * FROM \"User\" WHERE login = ? AND password = ?";

        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, hashedPassword);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error during login check: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Produces a SHA-256 hash for a given plaintext string.
     * @param password The raw password to encode
     * @return The serialized hex-string representation of the hash, or null upon failure
     */
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
