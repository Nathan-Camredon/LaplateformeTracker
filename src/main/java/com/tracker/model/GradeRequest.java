package com.tracker.model;

import com.tracker.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations related to the Grade entity.
 */
public class GradeRequest {

    /**
     * Retrieves all grades associated with a specific student by their ID.
     * The ID of the target student
     * Return A List of Grade objects corresponding to the student
     */
    public List<Grade> getGradesByStudentId(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM \"Grade\" WHERE studentid = ? ORDER BY id ASC";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Grade g = new Grade();
                    g.setId(rs.getInt("id"));
                    g.setValue(rs.getInt("value"));
                    g.setSubject(rs.getString("subject"));
                    g.setStudentid(rs.getInt("studentid"));
                    grades.add(g);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    /**
     * Inserts a new grade into the database.
     * @param value The grade value (typically 0-20)
     * @param subject The subject or course name corresponding to the grade
     * @param studentId The ID of the student receiving this grade
     * Return boolean True if insertion was successful, false otherwise
     */
    public boolean addGrade(int value, String subject, int studentId) {
        String sql = "INSERT INTO \"Grade\" (value, subject, studentid) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, value);
            pstmt.setString(2, subject);
            pstmt.setInt(3, studentId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a specific grade from the database.
     * The unique ID of the grade to be removed
     * True if deletion was successful, false otherwise
     */
    public boolean deleteGrade(int gradeId) {
        String sql = "DELETE FROM \"Grade\" WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gradeId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
