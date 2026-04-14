package com.tracker.service;

public class StatisticService {
    /**
     * Calculates the average of a list of grades.
     * 
     * @param grades List of grades (doubles)
     * @return The average of the grades, or 0.0 if the list is empty or null.
     */
    public double calculateAverage(java.util.List<Double> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0;
        for (Double grade : grades) {
            sum += grade;
        }
        
        return sum / grades.size();
    }
}
