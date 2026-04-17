package com.tracker.service;

/**
 * Service class handling numerical and statistical evaluations.
 */
public class StatisticService {

    /**
     * Computes the mathematical average of a provided list of floating-point numbers.
     * @param grades The list of numeric values
     * @return The average value as a double, or 0.0 if the list is null or empty
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
