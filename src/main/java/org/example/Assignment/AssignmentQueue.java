package org.example.Assignment;

import org.example.Assignment.DI.AddSingleton;
import org.example.Assignment.Repo.ChunkReadQueue;
import org.example.Assignment.Repo.IChunkReadQueue;
import org.example.Assignment.Service.ILogService;
import org.example.Assignment.Service.LogService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class AssignmentQueue {
    public static void main(String[] args) throws InterruptedException {
        AddSingleton.init();
        ILogService logService = AddSingleton.getBean(ILogService.class);
        final String filePath = "C:\\Users\\PC\\OneDrive\\Máy tính\\log.txt";
        final String outputFolder = "C:\\Users\\PC\\OneDrive\\Máy tính";
        int chunkSize = 50000;
        final List<String> POISON_PILL = Collections.emptyList();
        Queue<List<String>> queue = new ArrayDeque<>(50);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Choose option:");
            System.out.println("1 - Find by level");
            System.out.println("2 - Find by from-to date");
            System.out.println("3 - Find by message");
            System.out.print("Input: ");
            int choice = 0;
            try{
                choice = Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Invalid input");
            }

            switch (choice) {
                case 1 -> {
                    try{
                        System.out.print("Log level [INFO, WARN, ERROR]: ");
                        String level = sc.nextLine().trim();

                        if (level.equalsIgnoreCase("INFO")) {
                            System.out.println("Log level [INFO, WARN, ERROR]");
                        } else if (level.equalsIgnoreCase("WARN")) {
                            System.out.println("Log level [WARN, ERROR]");
                        } else if (level.equalsIgnoreCase("ERROR")) {
                            System.out.println("Log level [ERROR]");
                        } else {
                            System.out.println("Invalid input!");
                        }

                        Thread producer = new Thread(
                                logService.createProducer(chunkSize, filePath, queue, POISON_PILL)
                        );
                        producer.start();

                        System.out.println("Inpur: 1 = Console, 2 = File");
                        int outputMode = Integer.parseInt(sc.nextLine());
                        long startTime = System.currentTimeMillis();
                        Thread consumer = new Thread(
                                logService.createConsumer(
                                        level,
                                        null,
                                        null, null, queue,
                                        POISON_PILL,outputMode,outputFolder
                                )
                        );
                        consumer.start();

                        try {
                            consumer.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        long endTime = System.currentTimeMillis();
                        long totalTimeMs = endTime - startTime;
                        double totalTimeSec = totalTimeMs / 1000.0;
                        System.out.println("Total program time: " + totalTimeSec + " s");
                    }catch (Exception e){
                        throw new RuntimeException(e);
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
                    long startTime = System.currentTimeMillis();

                    Thread producer = new Thread(
                            logService.createProducer(chunkSize, filePath, queue, POISON_PILL)
                    );
                    producer.start();


                    Thread consumer = new Thread(
                            logService.createConsumer(
                                    null,
                                    from,
                                    to, null, queue,
                                    POISON_PILL,outputMode,outputFolder
                            )
                    );
                    consumer.start();

                    try {
                        consumer.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    long endTime = System.currentTimeMillis();
                    long totalTimeMs = endTime - startTime;
                    double totalTimeSec = totalTimeMs / 1000.0;
                    System.out.println("Total program time: " + totalTimeSec + " s");
                }
                case 3 -> {
                    System.out.print("Log Message: ");
                    String message = sc.nextLine();
                    System.out.println("Inpur: 1 = Console, 2 = File");
                    int outputMode = Integer.parseInt(sc.nextLine());
                    long startTime = System.currentTimeMillis();

                    Thread producer = new Thread(
                            logService.createProducer(chunkSize, filePath, queue, POISON_PILL)
                    );
                    producer.start();


                    Thread consumer = new Thread(
                            logService.createConsumer(
                                    null,
                                    null,
                                    null, message, queue,
                                    POISON_PILL,outputMode,outputFolder
                            )
                    );
                    consumer.start();

                    try {
                        consumer.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    long endTime = System.currentTimeMillis();
                    long totalTimeMs = endTime - startTime;
                    double totalTimeSec = totalTimeMs / 1000.0;
                    System.out.println("Total program time: " + totalTimeSec + " s");
                }
                default -> {
                    System.out.println("Input 1 - 2 - 3");
                }
            }
            System.out.println("s");
        }
    }
}