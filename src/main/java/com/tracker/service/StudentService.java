package com.tracker.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.tracker.config.DatabaseConfig;
import com.tracker.model.Student;









public class StudentService {

    public List<Student> getAllStudents(String sortBy, boolean ascending){
        List<Student> studentsList = new ArrayList<>();
        try {

            // Database connection
            Connection connection = DatabaseConfig.getConnection();

            // Sort direction
            String direction = ascending ? "ASC" : "DESC";




            // Switch for sort column
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
                    colSql = "grade";
                    break;
                default:
                    colSql = "id";
                    break;
            }



            // SQL request
            String request = "SELECT * FROM \"Student\" ORDER BY " + colSql + " " + direction;

            // Request execution
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(request);


            // Student data extraction
            while(resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setAge(resultSet.getInt("age"));
                student.setAverage(resultSet.getDouble("grade"));

                studentsList.add(student);
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error while retrieving students: " + e.getMessage());
        }
        return studentsList;
        
    }






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
                s.setAverage(rs.getDouble("grade"));
                studentsList.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche : " + e.getMessage());
        }
        return studentsList;
    }

    public void addStudent() {}
    public void deleteStudent() {}
    public void updateStudent() {}
    public void getStudent() {}
}
