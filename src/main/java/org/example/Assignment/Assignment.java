package org.example.Assignment;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class Assignment {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        final String filePath = "C:\\Users\\PC\\OneDrive\\Máy tính\\log.txt";
        final String outputFolder = "C:\\Users\\PC\\OneDrive\\Máy tính";
        final String fileName = "error.txt";
        int chunkSize = 250000;
        int threadCount = 100;

        String message = "";

        // Đọc file thành chunk
        ChunkRead reader = new ChunkRead(filePath, chunkSize);
        List<List<String>> chunks = reader.read();

        // Thread pool
        ThreadPoolConfig poolConfig = new ThreadPoolConfig(threadCount);
        ExecutorService pool = poolConfig.getPool();

        Scanner sc = new Scanner(System.in);
        System.out.println("Choose option:");
        System.out.println("1 - Filter by level");
        System.out.println("2 - Filter by from-to date");
        System.out.println("3 - Filter by level + from-to");


        int choice = Integer.parseInt(sc.nextLine());
        String outputFile = reader.CreateFile(outputFolder, "filtered_logs.txt");

        switch (choice) {
            case 1 -> { // filter theo level
                System.out.print("Enter log level [INFO, WARN, ERROR]: ");
                String level = sc.nextLine();

                for (int i = 0; i < chunks.size(); i++) {
                    int chunkIndex = i;
                    List<String> chunk = chunks.get(i);
                    pool.submit(() -> reader.processChunk(chunk, level, null, null, outputFile));
                }
            }

            case 2 -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.print("Enter from datetime or empty: ");
                String fromInput = sc.nextLine();
                LocalDateTime from = fromInput.isEmpty() ? null : LocalDateTime.parse(fromInput, formatter);

                System.out.print("Enter to datetime or empty: ");
                String toInput = sc.nextLine();
                LocalDateTime to = toInput.isEmpty() ? null : LocalDateTime.parse(toInput, formatter);

                for (int i = 0; i < chunks.size(); i++) {
                    int chunkIndex = i;
                    List<String> chunk = chunks.get(i);
                    pool.submit(() -> reader.processChunk(chunk, null, from, to, outputFile));
                }
            }

            //2025-08-26 15:43:45.275
            //2025-08-27 15:43:45.657
            case 3 -> { // filter cả level và time
                System.out.print("Enter log level [INFO, WARN, ERROR]: ");
                String level = sc.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.print("Enter from datetime or empty: ");
                String fromInput = sc.nextLine();
                LocalDateTime from = fromInput.isEmpty() ? null : LocalDateTime.parse(fromInput, formatter);

                System.out.print("Enter to datetime or empty: ");
                String toInput = sc.nextLine();
                LocalDateTime to = toInput.isEmpty() ? null : LocalDateTime.parse(toInput, formatter);

                for (int i = 0; i < chunks.size(); i++) {
                    int chunkIndex = i;
                    List<String> chunk = chunks.get(i);
                    pool.submit(() -> reader.processChunk(chunk, level, from, to, outputFile));
                }

            }

            default -> System.out.println("Invalid choice");
        }

        poolConfig.shutdown();
        System.out.println("All chunks processed.");


//        // Process chunk song song
//        Scanner sc = new Scanner(System.in);
//        var input = sc.nextLine();
//
//        var output = reader.CreateFile(outputFolder,  input);
//
//        for (int i = 0; i < chunks.size(); i++) {
//            List<String> chunk = chunks.get(i);
//            pool.submit(() -> {
//                try {
//                    reader.processChunkByLevel(chunk, input, from, to, message);
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        }
//
//        poolConfig.shutdown();
//        System.out.println("All chunks processed.");

    }
}
