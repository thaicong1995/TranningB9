package org.example.Assignment;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChunkRead {
    private final String filePath;
    private  final int chunkSize;

    public ChunkRead(String filePath, int chunkSize) {
        this.filePath = filePath;
        this.chunkSize = chunkSize;

    }

    public List<List<String>> read() throws FileNotFoundException {
        List<List<String>> chunks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            List<String> bf = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                bf.add(line);
                if (bf.size() == chunkSize) {
                    chunks.add(new ArrayList<>(bf));
                    bf.clear();
                }
            }

            if (!bf.isEmpty()) {
                chunks.add(new ArrayList<>(bf));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return chunks;
    }

    public List<String>  processChunk( List<String> chunkData, String level,
                                              LocalDateTime from, LocalDateTime to, String message)
            throws FileNotFoundException {
        if ( level != null && !level.isEmpty() ) {
            var logs = findByLevel(level,chunkData);
            System.out.println( level +"----" + logs);
            return logs;
        }
        else if (from.isBefore(to)) {
            var logs = findByDate(chunkData, from, to);
            System.out.println( level +"----" + logs);
            return logs;
        }
        else {
            return null;
        }
    }

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

    private List<String> findByDate(List<String> chunkData, LocalDateTime from, LocalDateTime  to ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return chunkData.stream().filter(line -> {
            String timestampStr = line.substring(1, 24);
            LocalDateTime logTime = LocalDateTime.parse(timestampStr, formatter);
            return (logTime.isEqual(from) || logTime.isAfter(from))
                    && (logTime.isEqual(to)   || logTime.isBefore(to));
        }).toList();
    }


    private List<String> findByLevel(String level, List<String> chunkData) {
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
                " xử lý level " + level + ", count=" + chunkData.size());

        return chunkData;

    }

}
