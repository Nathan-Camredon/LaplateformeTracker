package com.tracker.model;

import com.tracker.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRequest {

    /**
     * Ajoute un étudiant dans la base de données.
     */
    public boolean CreateStudent(String firstName, String lastName, int age, String email) {
        String sql = "INSERT INTO \"Student\" (first_name, last_name, age, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setString(4, email);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Supprime un étudiant via son ID.
     */
    public boolean DeleteStudent(int id) {
        String sql = "DELETE FROM \"Student\" WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Met à jour les informations d'un étudiant.
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
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère la liste de tous les étudiants pour l'interface JavaFX.
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.*, (SELECT AVG(value) FROM \"Grade\" WHERE studentid = s.id) as avg_grade FROM \"Student\" s ORDER BY id ASC";

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
            e.printStackTrace();
        }
        return students;
    }
}
