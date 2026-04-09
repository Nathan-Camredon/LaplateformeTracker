package com.tracker.service;

import com.tracker.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ExportServiceTest {

    private ExportService exportService;

    @BeforeEach
    void setUp() {
        exportService = new ExportService();
    }

    @Test
    void testExportStudentsToCSV_EmptyList() {
        List<Student> students = new ArrayList<>();
        String result = exportService.exportStudentsToCSV(students);
        
        String expectedHeader = "id,firstName,lastName,age,average\n";
        assertEquals(expectedHeader, result, "Exporting an empty list should only return the header.");
    }

    @Test
    void testExportStudentsToCSV_NullList() {
        String result = exportService.exportStudentsToCSV(null);
        
        String expectedHeader = "id,firstName,lastName,age,average\n";
        assertEquals(expectedHeader, result, "Exporting a null list should only return the header.");
    }

    @Test
    void testExportStudentsToCSV_NormalData() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Alice", "Wonderland", 20, 16.5));
        students.add(new Student(2, "Bob", "Builder", 22, 14.0));

        String result = exportService.exportStudentsToCSV(students);

        assertTrue(result.contains("1,Alice,Wonderland,20,16.5"));
        assertTrue(result.contains("2,Bob,Builder,22,14.0"));
    }

    @Test
    void testExportStudentsToCSV_SpecialCharacters() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "John", "Doe, Jr.", 20, 15.0));
        students.add(new Student(2, "Jane", "Quote\"Name", 21, 17.5));

        String result = exportService.exportStudentsToCSV(students);

        // Check for double quote escaping for commas
        assertTrue(result.contains("1,John,\"Doe, Jr.\",20,15.0"), "The comma should be surrounded by quotes.");
        
        // Check for double quote escaping (should be doubled)
        assertTrue(result.contains("2,Jane,\"Quote\"\"Name\",21,17.5"), "The quote should be doubled.");
    }
}
