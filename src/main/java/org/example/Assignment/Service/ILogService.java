package org.example.Assignment.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;

public interface ILogService {
    Runnable createProducer(int chunkSize, String filePath, Queue<List<String>> queue, List<String> POISON_PILL);
    Runnable createConsumer(String level,
                            LocalDateTime from, LocalDateTime to, String message,
                            Queue<List<String>> queue, List<String> POISON_PILL,
                            int outputMode, String outputFolder);
    Runnable createWorker(List<String> chunkData, String level,
                          LocalDateTime from, LocalDateTime to, String message,
                          int outputMode, String outputFolder);
}
