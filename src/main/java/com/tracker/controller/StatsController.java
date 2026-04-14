package com.tracker.controller;

import com.tracker.service.StatisticService;
import java.util.List;

public class StatsController {
    
    private final StatisticService statisticService = new StatisticService();

    /**
     * Receives a list of grades and returns the average calculated by the service.
     * 
     * @param grades List of grades
     * @return The average of the grades
     */
    public double handleCalculateAverage(List<Double> grades) {
        return statisticService.calculateAverage(grades);
    }
}
