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
        assertEquals(expectedHeader, result, "L'export d'une liste vide ne doit retourner que l'en-tête.");
    }

    @Test
    void testExportStudentsToCSV_NullList() {
        String result = exportService.exportStudentsToCSV(null);
        
        String expectedHeader = "id,firstName,lastName,age,average\n";
        assertEquals(expectedHeader, result, "L'export d'une liste null ne doit retourner que l'en-tête.");
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

        // Vérifie l'échappement par guillemets pour la virgule
        assertTrue(result.contains("1,John,\"Doe, Jr.\",20,15.0"), "La virgule doit être entourée de guillemets.");
        
        // Vérifie l'échappement des guillemets (doit être doublé)
        assertTrue(result.contains("2,Jane,\"Quote\"\"Name\",21,17.5"), "Le guillemet doit être doublé.");
    }
}
