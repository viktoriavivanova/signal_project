package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

public class AlertGenerator implements PatientDataGenerator {

    public static final Random RANDOM_GENERATOR = new Random(); //constant so changed to all uppercase letters (everywhere it's encountered)
    //blank line between consecutive members

    //non-constant field so lowerCamelCase (everywhere it's encounteres)
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
     * Constructs  AlertGenerator 
     * 
     * @param patientCount number of patients for which to generate alerts
     */

    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates and outputs alert data for a patient
     * 
     * @param patientId ID of patient for which to generate data
     * @param outputStrategy output strategy for outputting the generated data
     */

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double Lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-Lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println(
                "An error occurred while generating alert data for patient " + patientId); //breaking while keeping concatenation
            e.printStackTrace();
        }
    }
}
