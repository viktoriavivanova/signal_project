package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The PatientDataGenerator interface provides a contract for generating patient data.
 * Implementations of this interface should generate patient data based on a given patient ID
 * and use the specified OutputStrategy to handle the generated data.
 */
public interface PatientDataGenerator {
    /**
     * Generates patient data for the specified patient ID and uses the provided
     * OutputStrategy to handle the generated data.
     *
     * @param patientId the unique identifier of the patient
     * @param outputStrategy the strategy to handle the output of the generated data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
