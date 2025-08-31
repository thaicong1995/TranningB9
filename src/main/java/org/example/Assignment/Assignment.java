package org.example.Assignment;

import org.example.Assignment.Poll.ThreadPoolConfig;
import org.example.Assignment.Repo.ChunkRead;
import org.example.Assignment.Utils.StringUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class Assignment {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        final String filePath = "C:\\Users\\PC\\OneDrive\\Máy tính\\log.txt";
        final String outputFolder = "C:\\Users\\PC\\OneDrive\\Máy tính";
        int chunkSize = 250000;
        int threadCount = 100;

        String message = "";
        long startTime = System.currentTimeMillis();
        // Đọc file thành chunk
        ChunkRead reader = new ChunkRead(filePath, chunkSize);
        List<List<String>> chunks = reader.read();

        // Thread pool
        ThreadPoolConfig poolConfig = new ThreadPoolConfig(threadCount);
        ExecutorService pool = poolConfig.getPool();

        Scanner sc = new Scanner(System.in);
        System.out.println("Choose option:");
        System.out.println("1 - Find by level");
        System.out.println("2 - Find by from-to date");
        System.out.println("3 - Find by level + from-to");


        int choice = Integer.parseInt(sc.nextLine());
//        String outputFile = reader.CreateFile(outputFolder, "filtered_logs.txt");

        switch (choice) {
            case 1 -> {
                System.out.print("Log level [INFO, WARN, ERROR]: ");
                String level = sc.nextLine();

                System.out.println("Inpur: 1 = Console, 2 = File");
                int outputMode = Integer.parseInt(sc.nextLine());

                for (int i = 0; i < chunks.size(); i++) {
                    List<String> chunk = chunks.get(i);
                    pool.submit(() -> {
                        List<String> logs = null;
                        try {
                            logs = reader.processChunk(chunk, level, null, null);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                        if (logs != null && !logs.isEmpty()) {
                            if (outputMode == 1) {
                                logs.forEach(System.out::println);
                            } else if (outputMode == 2) {
                                String outputFile = reader.CreateFile(outputFolder, level);
                                reader.writeToFile(logs, outputFile);
                            }
                        }
                    });
                }
            }

            case 2 -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.print("Enter from date: ");
                String fromInput = sc.nextLine();
                LocalDateTime from = fromInput.isEmpty() ? null : LocalDateTime.parse(fromInput, formatter);

                System.out.print("Enter to date: ");
                String toInput = sc.nextLine();
                LocalDateTime to = toInput.isEmpty() ? null : LocalDateTime.parse(toInput, formatter);

                System.out.println("Inpur: 1 = Console, 2 = File");
                int outputMode = Integer.parseInt(sc.nextLine());

                for (int i = 0; i < chunks.size(); i++) {
                    List<String> chunk = chunks.get(i);
                    pool.submit(() -> {
                        List<String> logs = null;
                        try {
                            logs = reader.processChunk(chunk, null, from, to);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                        if (logs != null && !logs.isEmpty()) {
                            if (outputMode == 1) {
                                logs.forEach(System.out::println);
                            } else if (outputMode == 2) {
                                String outputFile = reader.CreateFile(outputFolder, StringUtils.sanitizeString(fromInput+"_"+toInput));
                                reader.writeToFile(logs, outputFile);
                            }
                        }
                    });
                }

            }

            //2025-08-26 15:43:45.275
            //2025-08-27 15:43:45.657
            case 3 -> {
                System.out.print("Log level [INFO, WARN, ERROR]: ");
                String level = sc.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.print("Enter from date: ");
                String fromInput = sc.nextLine();
                LocalDateTime from = fromInput.isEmpty() ? null : LocalDateTime.parse(fromInput, formatter);

                System.out.print("Enter to date: ");
                String toInput = sc.nextLine();
                LocalDateTime to = toInput.isEmpty() ? null : LocalDateTime.parse(toInput, formatter);

                for (int i = 0; i < chunks.size(); i++) {
                    int chunkIndex = i;
                    List<String> chunk = chunks.get(i);
                    pool.submit(() -> reader.processChunk(chunk, level, from, to));
                }

            }

            default -> System.out.println("Invalid choice");
        }

        poolConfig.shutdown();
        System.out.println("END");
        long endTime = System.currentTimeMillis();
        long totalTimeMs = endTime - startTime;
        double totalTimeSec = totalTimeMs / 1000.0;
        System.out.println("Total program time: " + totalTimeSec + " s");

//        String fileName = "C:\\Users\\PC\\OneDrive\\Máy tính\\log.txt";
//        int totalLines = 500_000;  // số log lines
//        String[] levels = {"INFO", "WARN", "ERROR", "DEBUG"};
//        String[] services = {"AuthService", "UserService", "PaymentService", "OrderService"};
//        Random random = new Random();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//
//        LocalDateTime startTime = LocalDateTime.now(); // thời điểm bắt đầu
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//            for (int i = 1; i <= totalLines; i++) {
//                // Cộng ngày mỗi 10 dòng
//                LocalDateTime timestamp = startTime.plusDays((i - 1) / 10);
//
//                String level = levels[random.nextInt(levels.length)];
//                String service = services[random.nextInt(services.length)];
//                String message = "This is log message number " + i;
//
//                String logLine = String.format("[%s] [%s] [%s]- %s",
//                        timestamp.format(formatter), level, service, message);
//
//                writer.write(logLine);
//                writer.newLine();
//            }
//            System.out.println("Generated " + totalLines + " log lines into " + fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
