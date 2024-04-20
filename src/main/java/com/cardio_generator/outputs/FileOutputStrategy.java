package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Adding data to files
 * 
 * @author Viktoria Ivanova (i6340968)
 */

//changed to start with capital letter because of the name of the class(everywhere it's encountered)
public class FileOutputStrategy implements OutputStrategy {

    private String BaseDirectory;

    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    public FileOutputStrategy(String baseDirectory) {
      this.BaseDirectory = baseDirectory; //2 spaces indent and line break
    }

    @Override
    /**
     * Adding data to files 
     * @param patientId patientID
     * @param timeStamp timestamp of the data
     * @param label label for the data
     * @param data the data that is going to be added
     */
    public void output(int patientId, long timeStamp, String label, String data) { //timestamp changed to timeStamp
        try {
            // Create the directory
            //moved so that it is at +2 spaces indent
          Files.createDirectories(Paths.get(BaseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String FilePath = file_map.computeIfAbsent(label,
            k -> Paths.get(BaseDirectory, label + ".txt").toString()); //comma stays attached to the token that precedes it and the constructor is attached to the parenthesis and +4 spaces

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
          Files.newBufferedWriter(Paths.get(FilePath),
              StandardOpenOption.CREATE, StandardOpenOption.APPEND))) { //comma stays attached to the token and +4 spaces
              out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timeStamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + FilePath + ": " + e.getMessage());
        }
    }
}