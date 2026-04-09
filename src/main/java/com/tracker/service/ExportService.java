package com.tracker.service;

import com.tracker.model.Student;
import java.util.List;

public class ExportService {

    /**
     * Convert the student list into csv file
     */
    public String exportStudentsToCSV(List<Student> students) {
        StringBuilder csv = new StringBuilder();

        csv.append("id,firstName,lastName,age,average\n");

        if (students != null) {
            for (Student student : students) {
                csv.append(student.getId()).append(",")
                        .append(escapeCSV(student.getFirstName())).append(",")
                        .append(escapeCSV(student.getLastName())).append(",")
                        .append(student.getAge()).append(",")
                        .append(student.getAverage()).append("\n");
            }
        }

        return csv.toString();
    }

    private String escapeCSV(String data) {
        if (data == null) {
            return "";
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
