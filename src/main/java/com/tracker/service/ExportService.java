package com.tracker.service;

import com.tracker.model.Student;
import java.util.List;

/**
 * Internal service responsible for data exportation capabilities.
 */
public class ExportService {

    /**
     * Converts a given list of Student objects into a CSV formatted string.
     * @param students The List of students to export
     * @return A String containing standard CSV layout with headers
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

    /**
     * Helper method to escape potential problematic characters within CSV values.
     * @param data The raw string parameter
     * @return An escaped, CSV-compliant string
     */
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
