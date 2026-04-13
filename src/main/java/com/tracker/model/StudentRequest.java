package com.tracker.model;

import com.tracker.config.DatabaseConfig;
import java.sql.*;

/**
 * Class responsible for handling CRUD operations for Student entities.
 */
public class StudentRequest {

    /**
     * Adds a new student to the database.
     */
    public void CreateStudent(String firstName, String lastName, int age, float average) {
        String sql = "INSERT INTO \"Student\" (first_name, last_name, age, average) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setFloat(4, average);
            
            pstmt.executeUpdate();
            System.out.println("✅ Student added successfully: " + firstName + " " + lastName);
            
        } catch (SQLException e) {
            System.err.println("❌ Database error details:");
            e.printStackTrace();
        }
    }

    /**
     * Deletes a student by their ID.
     */
    public void DeleteStudent(int id) {
        String sql = "DELETE FROM \"Student\" WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("✅ Student with ID " + id + " deleted.");
            } else {
                System.out.println("⚠️ No student found with ID " + id);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Deletion error: " + e.getMessage());
        }
    }

    /**
     * Updates an existing student's information.
     */
    public void Update(int id, String firstName, String lastName, int age, float average) {
        String sql = "UPDATE \"Student\" SET first_name = ?, last_name = ?, age = ?, average = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setFloat(4, average);
            pstmt.setInt(5, id);
            
            pstmt.executeUpdate();
            System.out.println("✅ Student updated successfully!");
            
        } catch (SQLException e) {
            System.err.println("❌ Update error: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays all students in the console.
     */
    public void FindAll() {
        String sql = "SELECT * FROM \"Student\"";
        
        try (Connection conn = DatabaseConfig.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- Student List ---");
            while (rs.next()) {
                System.out.println(String.format("ID: %d | %s %s | Age: %d | Average: %.2f",
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getFloat("average")
                ));
            }
            System.out.println("---------------------\n");
            
        } catch (SQLException e) {
            System.err.println("❌ Retrieval error: " + e.getMessage());
        }
    }
}
