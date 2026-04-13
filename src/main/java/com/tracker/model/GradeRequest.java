package com.tracker.model;

import com.tracker.config.DatabaseConfig;
import java.sql.*;

public class GradeRequest {
    /**
     * Adds a new grade to a student
     */
    public void AddGrade(String subject, int value, int studentId) {
        String sql = "INSERT INTO \"Grade\" (value, subject, studentid) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, value);
            pstmt.setString(2, subject);
            pstmt.setInt(3, studentId);

            pstmt.executeUpdate();
            System.out.println("Grade added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DelGrade(int id) {
        String sql = "DELETE FROM \"Grade\" WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Grade deleted with id: " + id);
            } else {
                System.out.println("⚠️ No grade found with id: " + id);
            }

        } catch (SQLException e) {
            System.err.println("❌ Deletion error: " + e.getMessage());
        }
    }

    public void Update(int id, String subject, int value, int studentId) {
        String sql = "UPDATE \"Grade\" SET subject = ?, value = ?, studentid = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, subject);
            pstmt.setInt(2, value);
            pstmt.setInt(3, studentId);
            pstmt.setInt(4, id);
            
            pstmt.executeUpdate();
            System.out.println("✅ Grade updated successfully!");
            
         } catch (SQLException e) {
             System.err.println("❌ Update error: " + e.getMessage());
         }
    }

    public void FindAll() {
        String sql = "SELECT * FROM \"Grade\"";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- Grade List ---");
            while (rs.next()) {
                System.out.println(String.format("ID: %d | Subject: %s | Value: %d | Student ID: %d",
                        rs.getInt("id"),
                        rs.getString("subject"),
                        rs.getInt("value"),
                        rs.getInt("studentid")
                ));
            }
            System.out.println("------------------\n");
            
        } catch (SQLException e) {
            System.err.println("❌ Retrieval error: " + e.getMessage());
        }
    }
}
