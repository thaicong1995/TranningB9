package org.example.Assignment.Repo;

import org.example.Assignment.HandleException.CustomException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;

public interface IChunkReadQueue {
    Queue<List<String>> readFileChunks(int chunkSize, String filePath,  Queue<List<String>> queue) throws CustomException.IOException;
    List<String>  processChunk(List<String> chunkData, String level,
                               LocalDateTime from, LocalDateTime to, String message);
    void writeToFile(List<String> logsFilter, String outputPath);
    String CreateFile(String outputFolder, String fileName);
}
