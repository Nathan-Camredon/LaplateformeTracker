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
                    colSql = "last_name";
                    break;
                case "first_name":
                    colSql = "first_name";
                    break;
                case "last_name":
                    colSql = "last_name";
                    break;
                case "age":
                    colSql = "age";
                    break;
                case "grade":
                    colSql = "grade";
                    break;
                default:
                    colSql = "last_name";
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



    public void addStudent(){
        
    }
    public void deleteStudent(){
        
    }
    public void updateStudent(){
        
    }
    public void getStudent(){
    }
}
