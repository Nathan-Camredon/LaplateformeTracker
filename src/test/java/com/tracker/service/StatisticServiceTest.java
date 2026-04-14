package com.tracker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatisticServiceTest {

    private StatisticService statisticService;

    @BeforeEach
    void setUp() {
        statisticService = new StatisticService();
    }

    @Test
    void testCalculateAverage_NormalData() {
        List<Double> grades = Arrays.asList(10.0, 15.0, 20.0);
        double result = statisticService.calculateAverage(grades);
        assertEquals(15.0, result, "The average of 10, 15 and 20 should be 15.");
    }

    @Test
    void testCalculateAverage_EmptyList() {
        List<Double> grades = new ArrayList<>();
        double result = statisticService.calculateAverage(grades);
        assertEquals(0.0, result, "The average of an empty list should be 0.0.");
    }

    @Test
    void testCalculateAverage_NullList() {
        double result = statisticService.calculateAverage(null);
        assertEquals(0.0, result, "The average of a null list should be 0.0.");
    }

    @Test
    void testCalculateAverage_SingleValue() {
        List<Double> grades = Arrays.asList(18.5);
        double result = statisticService.calculateAverage(grades);
        assertEquals(18.5, result, "The average of a single value should be that value.");
    }
}
