package com.tracker.model;

import com.tracker.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations related to the Student entity.
 * Provides methods for standard CRUD (Create, Read, Update, Delete) functionality.
 */
public class StudentRequest {

    /**
     * Creates a new student record in the database.
     * @param firstName The student's first name
     * @param lastName The student's last name
     * @param age The student's age
     * @param email The student's email
     * @return boolean True if creation was successful, false otherwise
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
     * Deletes a student from the database.
     * @param id The ID of the student to delete
     * @return boolean True if deletion was successful, false otherwise
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
     * Updates an existing student's underlying information.
     * @param id The ID of the student to update
     * @param firstName The updated first name
     * @param lastName The updated last name
     * @param age The updated age
     * @param email The updated email string
     * @return boolean True if the student was successfully updated
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
     * Retrieves all students from the database alongside their computed average grades.
     * @return A List of Student objects sorted by their ID in ascending order.
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
