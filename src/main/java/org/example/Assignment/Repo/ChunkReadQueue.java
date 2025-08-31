package org.example.Assignment.Repo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ChunkReadQueue implements IChunkReadQueue {


    //region read
    @Override
    public Queue<List<String>> readFileChunks(int chunkSize, String filePath,  Queue<List<String>> queue) {
        if (chunkSize <= 0 || filePath == null || Files.notExists(Paths.get(filePath))) {
            return  null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            List<String> bf = new ArrayList<>();
            String line;
            // Đọc toàn bộ file
            while ((line = br.readLine()) != null) {
                bf.add(line);
                if (bf.size() == chunkSize) {
                    queue.add(new ArrayList<>(bf));
                    bf.clear();
                }
            }
            // Giới hạn số dòng cần lấy từ dưới lên
//            for (int i = lines.size() - 1; i >= 0; i--) {
//                bf.add(lines.get(i));
//                if (bf.size() == chunkSize) {
//                    queue.add(new ArrayList<>(bf));
//                    bf.clear();
//                }
//            }

            if (!bf.isEmpty()) {
                queue.add(new ArrayList<>(bf));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return queue;
    }
    //endregion read

    //region processChunk
    @Override
    public List<String>  processChunk(List<String> chunkData, String level,
                                      LocalDateTime from, LocalDateTime to, String message) {
        return filterLog(chunkData, level, from, to, message);
    }
    //endregion processChunk

    //region writeToFile
    @Override
    public void writeToFile(List<String> logsFilter, String outputPath){
        synchronized (this) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath, true))) {
                for (String line : logsFilter) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //endregion writeToFile

    //region createFile
    @Override
    public String CreateFile(String outputFolder, String fileName) {
        File folder = new File(outputFolder);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) {
                System.out.println("Cannot create directory: " + outputFolder);
                return null;
            } else {
                System.out.println("Directory created: " + outputFolder);
            }
        }

        File file = new File(folder, fileName);
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("File created: " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("File exists: " + file.getAbsolutePath());
        }
        return file.getAbsolutePath();
    }
    //endregion createFile

    //region filterLog
    private List<String> filterLog(List<String> chunkData,
                                   String level,
                                   LocalDateTime from,
                                   LocalDateTime to,
                                   String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        List<String> result = chunkData;

        if (message != null && !message.isEmpty()) {
            result = findByMessage(result, message);
        }

        if (from != null && to != null) {
            result = findByDate(result, from, to);
        }

        if (level != null && !level.isEmpty()) {
            result = findByLevel(result, level);
        }
        return result;
    }
    //endregion filterLog

    private List<String> findByMessage (List<String> chunkData, String message) {
         return  chunkData.stream()
                 .filter(line -> {
                     int dashIndex = line.indexOf('-');
                     if (dashIndex == -1) return false;
                     String msgPart = line.substring(dashIndex + 1).trim();
                     return msgPart.contains(message);
                 })
                 .toList();
    }

    private List<String> findByDate(List<String> chunkData, LocalDateTime from, LocalDateTime  to ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return chunkData.stream().filter(line -> {
            String timestampStr = line.substring(1, 24);
            LocalDateTime logTime = LocalDateTime.parse(timestampStr, formatter);
            return (logTime.isEqual(from) || logTime.isAfter(from))
                    && (logTime.isEqual(to)   || logTime.isBefore(to));
        }).toList();
    }

    private List<String> findByLevel(List<String> chunkData,String level) {
        switch (level.toUpperCase()) {
            case "INFO":
                return chunkData.stream()
                        .filter(line -> line.contains("[INFO]"))
                        .toList();
            case "ERROR":
                return chunkData.stream()
                        .filter(line -> line.contains("[ERROR]"))
                        .toList();
            case "WARN":
                return chunkData.stream()
                        .filter(line -> line.contains("[WARN]"))
                        .toList();
            default:
                System.out.println("Input must be [INFO,ERROR,WARN]");
        }

        System.out.println("Thread " + Thread.currentThread().getName() +
                " level " + level + ", count = " + chunkData.size());

        return chunkData;
    }
}
