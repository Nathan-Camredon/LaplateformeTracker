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
                student.setEmail(resultSet.getString("email"));
                student.setAverage(0.0);

                studentsList.add(student);
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error while retrieving students: " + e.getMessage());
            e.printStackTrace();
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
     * Advanced Search with multiple optional criteria
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


    public boolean addStudent(Student s) {
        String query = "INSERT INTO \"Student\" (first_name, last_name, age, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, s.getFirstName());
            pstmt.setString(2, s.getLastName());
            pstmt.setInt(3, s.getAge());
            pstmt.setString(4, s.getEmail());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public void deleteStudent(int id) {}
    public void updateStudent(Student s) {}
    public void getStudent(int id) {}
}
