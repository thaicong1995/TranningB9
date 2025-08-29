package org.example.Assignment;

import org.example.Assignment.HandleException.CustomException;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class AssignmentQueue {
    public static void main(String[] args) throws InterruptedException {
        IChunkReadQueue queueInstance = AddSingleton.getInstance();
        final String filePath = "C:\\Users\\PC\\OneDrive\\Máy tính\\log.txt";
        final String outputFolder = "C:\\Users\\PC\\OneDrive\\Máy tính";
        int chunkSize = 50000;
        final List<String> POISON_PILL = Collections.emptyList();

//        Thread producer = new Thread(() ->{
//            try {
//                queueInstance.readFileChunks(chunkSize, filePath);
//                ChunkReadQueue.queue.add(POISON_PILL);
//                System.out.println(Thread.currentThread().getName());
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        producer.start();
//        producer.join();

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
                       String level = sc.nextLine();

                       if (!level.contains("INFO".toLowerCase())) {
                           System.out.println("Log level [INFO, WARN, ERROR]");
                           break;
                       }else if (level.contains("WARN".toLowerCase())) {
                           System.out.println("Log level [WARN, ERROR]");
                           break;
                       }else if (level.contains("ERROR".toLowerCase())) {
                           System.out.println("Log level [ERROR]");
                           break;
                       }

                       Thread producer = new Thread(() ->{
                           try {
                               queueInstance.readFileChunks(chunkSize, filePath);
                               ChunkReadQueue.queue.add(POISON_PILL);
                               System.out.println(Thread.currentThread().getName());
                           } catch ( CustomException.IOException  e) {
                               System.out.println("Error reading file");
                           }
                       });
                       producer.start();

                       System.out.println("Inpur: 1 = Console, 2 = File");
                       int outputMode = Integer.parseInt(sc.nextLine());
                       long startTime = System.currentTimeMillis();

                       Thread consumer = new Thread(() ->{
                           System.out.println(Thread.currentThread().getName());
                           while(true){
                               List<String> chunk =  ChunkReadQueue.queue.poll();
//                if(ChunkReadQueue.queue.isEmpty()){
//                    try{Thread.sleep(1000);}catch(InterruptedException e){}
//                }
                               if(chunk == POISON_PILL) break;
                               if(chunk != null){
                                   if (outputMode == 1) {
                                       Thread worker = new Thread(() ->{
                                           try {
                                               var rs = queueInstance.processChunk(chunk, level,
                                                       null, null, null);
                                               System.out.println(Thread.currentThread().getName());
                                               System.out.println(rs);
                                           } catch (Exception e) {
                                               throw new RuntimeException(e);
                                           }
                                       });
                                       worker.start();
                                       try {
                                           worker.join();
                                       } catch (InterruptedException e) {
                                           throw new RuntimeException(e);
                                       }
                                   } else if (outputMode == 2) {
                                       Thread worker = new Thread(() -> {
                                           try {
                                               var rs = queueInstance.processChunk(chunk, level,
                                                       null, null, null);
                                               System.out.println(Thread.currentThread().getName());
                                               System.out.println(rs);
                                               String outputFile = queueInstance.CreateFile(outputFolder, level);
                                               queueInstance.writeToFile(rs, outputFile);
                                           } catch (Exception e) {
                                               throw new RuntimeException(e);
                                           }
                                       });
                                       worker.start();
                                       try {
                                           worker.join();
                                       } catch (InterruptedException e) {
                                           throw new RuntimeException(e);
                                       }
                                   }
                               }
                           }
                       });

                       consumer.start();
                       consumer.join();
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

                   Thread producer = new Thread(() ->{
                       try {
                           queueInstance.readFileChunks(chunkSize, filePath);
                           ChunkReadQueue.queue.add(POISON_PILL);
                           System.out.println(Thread.currentThread().getName());
                       } catch (Exception e) {
                           throw new RuntimeException(e);
                       }
                   });
                   producer.start();

                   Thread consumer = new Thread(() ->{
                       System.out.println(Thread.currentThread().getName());
                       while(true){
                           List<String> chunk =  ChunkReadQueue.queue.poll();
//                if(ChunkReadQueue.queue.isEmpty()){
//                    try{Thread.sleep(1000);}catch(InterruptedException e){}
//                }
                           if(chunk == POISON_PILL) break;
                           if(chunk != null){
                               if (outputMode == 1) {
                                   Thread worker = new Thread(() ->{
                                       try {
                                           var rs = queueInstance.processChunk(chunk, null,
                                                   from, to, null);
                                           System.out.println(Thread.currentThread().getName());
                                           System.out.println(rs);

                                       } catch (Exception e) {
                                           throw new RuntimeException(e);
                                       }

                                   });
                                   worker.start();
                                   try {
                                       worker.join();
                                   } catch (InterruptedException e) {
                                       throw new RuntimeException(e);
                                   }
                               } else if (outputMode == 2) {
                                   Thread worker = new Thread(() -> {
                                       try {
                                           var rs = queueInstance.processChunk(chunk, null,
                                                   from, to, null);
                                           System.out.println(Thread.currentThread().getName());
                                           System.out.println(rs);
                                           String outputFile = queueInstance.CreateFile(outputFolder, StringUtils.sanitizeString(fromInput+"_"+toInput));
                                           queueInstance.writeToFile(rs, outputFile);
                                       } catch (Exception e) {
                                           throw new RuntimeException(e);
                                       }
                                   });
                                   worker.start();
                                   try {
                                       worker.join();
                                   } catch (InterruptedException e) {
                                       throw new RuntimeException(e);
                                   }
                               }
                           }

                       }
                   });

                   consumer.start();
                   consumer.join();
                   long endTime = System.currentTimeMillis();
                   long totalTimeMs = endTime - startTime;
                   double totalTimeSec = totalTimeMs / 1000.0;
                   System.out.println("Total program time: " + totalTimeSec + " s");
               }
               case 3 -> {
                   System.out.print("Log Message: ");
                   String message = sc.nextLine();
                   System.out.println("Inpur: 1 = Console, 2 = File");
                   long startTime = System.currentTimeMillis();

                   Thread producer = new Thread(() ->{
                       try {
                           queueInstance.readFileChunks(chunkSize, filePath);
                           ChunkReadQueue.queue.add(POISON_PILL);
                           System.out.println(Thread.currentThread().getName());
                       } catch (Exception e) {
                           throw new RuntimeException(e);
                       }
                   });
                   producer.start();

                   Thread consumer = new Thread(() ->{
                       int outputMode = Integer.parseInt(sc.nextLine());
                       System.out.println(Thread.currentThread().getName());
                       while(true){
                           List<String> chunk =  ChunkReadQueue.queue.poll();
//                if(ChunkReadQueue.queue.isEmpty()){
//                    try{Thread.sleep(1000);}catch(InterruptedException e){}
//                }
                           if(chunk == POISON_PILL) break;
                           if(chunk != null){
                               if (outputMode == 1) {
                                   Thread worker = new Thread(() ->{
                                       try {
                                           var rs = queueInstance.processChunk(chunk, null,
                                                   null, null, message);
                                           System.out.println(Thread.currentThread().getName());
                                           System.out.println(rs);

                                       } catch (Exception e) {
                                           throw new RuntimeException(e);
                                       }
                                   });
                                   worker.start();
                                   try {
                                       worker.join();
                                   } catch (InterruptedException e) {
                                       throw new RuntimeException(e);
                                   }
                               } else if (outputMode == 2) {
                                   Thread worker = new Thread(() ->{
                                       try {
                                           var rs = queueInstance.processChunk(chunk, null,
                                                   null, null, message);
                                           System.out.println(Thread.currentThread().getName());
                                           System.out.println(rs);
                                           String outputFile = queueInstance.CreateFile(outputFolder, message);
                                           queueInstance.writeToFile(rs,outputFile);
                                       } catch (Exception e) {
                                           throw new RuntimeException(e);
                                       }
                                   });
                                   worker.start();
                                   try {
                                       worker.join();
                                   } catch (InterruptedException e) {
                                       throw new RuntimeException(e);
                                   }
                               }
                           }
                       }
                   });

                   consumer.start();
                   consumer.join();
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
