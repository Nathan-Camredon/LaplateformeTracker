package com.tracker.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.tracker.config.DatabaseConfig;
import com.tracker.model.Student;

/**
 * Service class responsible for advanced retrieval and searching of Student entities.
 * Abstracts the direct database queries and structures them for the UI.
 */
public class StudentService {

    /**
     * Retrieves all students, optionally sorting them by a specified column.
     * @param sortBy The database column to sort by (e.g., "id", "first_name", "moyenne")
     * @param ascending True for ascending order, false for descending
     * @return A List of sorted Student objects
     */
    public List<Student> getAllStudents(String sortBy, boolean ascending){
        List<Student> studentsList = new ArrayList<>();
        try {

            Connection connection = DatabaseConfig.getConnection();

            String direction = ascending ? "ASC" : "DESC";

            String colSql;
            switch (sortBy.toLowerCase()) {
                case "id":
                    colSql = "id";
                    break;
                case "first_name":
                case "prenom":
                    colSql = "first_name";
                    break;
                case "last_name":
                case "nom":
                    colSql = "last_name";
                    break;
                case "age":
                    colSql = "age";
                    break;
                case "grade":
                case "moyenne":
                    colSql = "avg_grade";
                    break;
                default:
                    colSql = "id";
                    break;
            }

            String request = "SELECT s.*, (SELECT AVG(value) FROM \"Grade\" WHERE studentid = s.id) as avg_grade " + "FROM \"Student\" s ORDER BY " + colSql + " " + direction;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(request);

            while(resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setAge(resultSet.getInt("age"));
                student.setEmail(resultSet.getString("email"));
                student.setAverage(resultSet.getDouble("avg_grade"));

                studentsList.add(student);
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error while retrieving students: " + e.getMessage());
            e.printStackTrace();
        }
        return studentsList;

    }

    /**
     * Performs a generic fuzzy search matching ID, first name, or last name.
     * @param search The generalized search string input
     * @return A List of Student objects matching the criteria
     */
    public List<Student> searchStudents(String search) {
        List<Student> studentsList = new ArrayList<>();

        String pattern = "%" + search + "%";

        String query = "SELECT * FROM \"Student\" WHERE " +
                    "first_name LIKE ? OR " +
                    "last_name LIKE ? OR " +
                    "CAST(id AS TEXT) LIKE ?";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            pstmt.setString(3, pattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setFirstName(rs.getString("first_name"));
                s.setLastName(rs.getString("last_name"));
                s.setAge(rs.getInt("age"));
                s.setEmail(rs.getString("email"));
                s.setAverage(0.0);
                studentsList.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche : " + e.getMessage());
            e.printStackTrace();
        }
        return studentsList;
    }

    /**
     * Performs an advanced search based on distinct provided fields.
     * @param idStr The student ID (string format to allow partial matching)
     * @param firstName Partial or exact first name
     * @param lastName Partial or exact last name
     * @return A List of Student objects strictly matching the provided distinct parameters
     */
    public List<Student> advancedSearch(String idStr, String firstName, String lastName) {
        List<Student> studentsList = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM \"Student\" WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (idStr != null && !idStr.isEmpty()) {
            query.append("AND CAST(id AS TEXT) LIKE ? ");
            params.add("%" + idStr + "%");
        }
        if (firstName != null && !firstName.isEmpty()) {
            query.append("AND first_name ILIKE ? ");
            params.add("%" + firstName + "%");
        }
        if (lastName != null && !lastName.isEmpty()) {
            query.append("AND last_name ILIKE ? ");
            params.add("%" + lastName + "%");
        }

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setId(rs.getInt("id"));
                    s.setFirstName(rs.getString("first_name"));
                    s.setLastName(rs.getString("last_name"));
                    s.setAge(rs.getInt("age"));
                    s.setEmail(rs.getString("email"));
                    s.setAverage(0.0);
                    studentsList.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("Advanced search error: " + e.getMessage());
            e.printStackTrace();
        }
        return studentsList;
    }
}