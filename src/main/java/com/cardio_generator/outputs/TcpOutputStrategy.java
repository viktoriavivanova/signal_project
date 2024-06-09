package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * The TcpOutputStrategy class implements the OutputStrategy interface to send patient data
 * over a TCP connection. This class starts a TCP server on the specified port and sends
 * data to the connected client.
 */
 public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

    /**
     * Constructs a TcpOutputStrategy and starts a TCP server on the specified port.
     *
     * <p>The server will wait for a client to connect in a separate thread, so it does
     * not block the main thread. Once a client is connected, data can be sent to the client.</p>
     *
     * @param port the port number on which the TCP server will listen
     */
    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the specified patient data over the TCP connection.
     *
     * <p>If a client is connected, this method formats the data as a comma-separated
     * string and sends it to the client.</p>
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the timestamp when the data was generated
     * @param label a label describing the type of data (e.g., "ECG", "Blood Pressure")
     * @param data the actual data to be outputted
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
