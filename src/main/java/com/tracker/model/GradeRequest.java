package com.tracker.model;

import com.tracker.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeRequest {

    /**
     * Récupère la liste des notes pour un étudiant donné.
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
     * Ajoute une nouvelle note.
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
}
