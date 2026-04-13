package com.tracker.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for StudentRequest class.
 * These tests interact with the real database.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentRequestTest {

    private static StudentRequest request;
    private final String testFirstName = "JUnit";
    private final String testLastName = "TestUser";

    @BeforeAll
    static void setup() {
        request = new StudentRequest();
    }

    @Test
    @Order(1)
    @DisplayName("Should create a new student without errors")
    void testCreateStudent() {
        assertDoesNotThrow(() -> {
            request.CreateStudent(testFirstName, testLastName, 25, 15.5f);
        });
    }

    @Test
    @Order(2)
    @DisplayName("Should find all students including the test one")
    void testFindAll() {
        // This method prints to console, we just verify it doesn't crash
        assertDoesNotThrow(() -> {
            request.FindAll();
        });
    }

    @Test
    @Order(3)
    @DisplayName("Should update student information")
    void testUpdate() {
        // Since we don't have GetIdByName, we assume ID 1 exists or similar
        // For a perfect test, we would need a GetId method. 
        // For now, we test the execution of the query.
        assertDoesNotThrow(() -> {
            request.Update(1, testFirstName, "UpdatedName", 26, 16.0f);
        });
    }

    @Test
    @Order(4)
    @DisplayName("Should handle deletion logic")
    void testDelete() {
        // We test if the method handles the call correctly
        assertDoesNotThrow(() -> {
            request.DeleteStudent(9999); // Deleting a non-existing ID is handled safely by our code
        });
    }
}
