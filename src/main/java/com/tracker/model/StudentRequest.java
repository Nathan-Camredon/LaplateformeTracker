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
    public boolean CreateStudent(String firstName, String lastName, int age, String email) {
        String sql = "INSERT INTO \"Student\" (first_name, last_name, age, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setString(4, email);
            
            int affectedRows = pstmt.executeUpdate();
            System.out.println("✅ Student added successfully: " + firstName + " " + lastName);
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Database error details:");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a student by their ID.
     */
    public boolean DeleteStudent(int id) {
        String sql = "DELETE FROM \"Student\" WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("✅ Student with ID " + id + " deleted.");
                return true;
            } else {
                System.out.println("⚠️ No student found with ID " + id);
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Deletion error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing student's information.
     */
    public boolean Update(int id, String firstName, String lastName, int age, String email) {
        String sql = "UPDATE \"Student\" SET first_name = ?, last_name = ?, age = ?, email = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setString(4, email);
            pstmt.setInt(5, id);
            
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Student updated successfully!");
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Update error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves and displays all students in the console.
     * Note: average is fetched from the grades table (simplified AVG join).
     */
    public void FindAll() {
        String sql = "SELECT s.*, (SELECT AVG(value) FROM \"Grade\" WHERE studentid = s.id) as avg_grade FROM \"Student\" s";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- Student List ---");
            while (rs.next()) {
                System.out.println(String.format("ID: %d | %s %s | Age: %d | Email: %s | Average: %.2f",
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getFloat("avg_grade")
                ));
            }
            System.out.println("---------------------\n");
            
        } catch (SQLException e) {
            System.err.println("❌ Retrieval error: " + e.getMessage());
        }
    }

    /**
     * Retrieves all students from the database for the JavaFX UI.
     */
    public java.util.List<Student> getAllStudents() {
        java.util.List<Student> students = new java.util.ArrayList<>();
        String sql = "SELECT s.*, (SELECT AVG(value) FROM \"Grade\" WHERE studentid = s.id) as avg_grade FROM \"Student\" s";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setFirstName(rs.getString("first_name"));
                s.setLastName(rs.getString("last_name"));
                s.setAge(rs.getInt("age"));
                s.setEmail(rs.getString("email"));
                s.setAverage(rs.getFloat("avg_grade"));
                students.add(s);
            }
        } catch (SQLException e) {
            System.err.println("❌ Retrieval error: " + e.getMessage());
        }
        return students;
    }
}
