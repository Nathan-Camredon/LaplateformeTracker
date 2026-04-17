package com.tracker.controller;

import com.tracker.service.StatisticService;
import java.util.List;

/**
 * Handles communication between UI views and statistical backend processing.
 */
public class StatsController {

    private final StatisticService statisticService = new StatisticService();

    /**
     * Processes a sequence of grades to compute the student's mathematical mean.
     * @param grades A generic list of floating point elements
     * @return The computed average as a double
     */
    public double handleCalculateAverage(List<Double> grades) {
        return statisticService.calculateAverage(grades);
    }
}
