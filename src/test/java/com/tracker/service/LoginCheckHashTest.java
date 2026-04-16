package com.tracker.service;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;



public class LoginCheckHashTest {


@Test
public void testHashPassword() {
        LoginCheck loginCheck = new LoginCheck();

        String password = "password";
        String expectedHash = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
        String actualHash = loginCheck.hashPassword(password);
        assertEquals(expectedHash, actualHash);
    }
}