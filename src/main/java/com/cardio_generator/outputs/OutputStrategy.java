package com.cardio_generator.outputs;

/**
 * The OutputStrategy interface provides a contract for outputting patient data.
 * Implementations of this interface should define how to handle and output the data
 * generated for patients, including formatting and delivery mechanisms.
 */

 public interface OutputStrategy {
    /**
     * Outputs the specified patient data.
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the timestamp when the data was generated
     * @param label a label describing the type of data (e.g., "ECG", "Blood Pressure")
     * @param data the actual data to be outputted
     */
    void output(int patientId, long timestamp, String label, String data);
}
