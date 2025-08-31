package org.example.Assignment.Service;

import org.example.Assignment.Repo.IChunkReadQueue;
import org.example.Assignment.Utils.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class LogService implements ILogService {

    private final IChunkReadQueue queueInstance;

    public LogService(IChunkReadQueue queueInstance) {
        this.queueInstance = queueInstance;
    }

    // Tạo producer task
    @Override
    public Runnable createProducer(int chunkSize, String filePath, Queue<List<String>> queue, List<String> POISON_PILL) {
        return () -> {
            try {
                System.out.println(Thread.currentThread().getName()+ "thread producer" );

                queueInstance.readFileChunks(chunkSize, filePath, queue);
                synchronized (queue) {
                    queue.add(POISON_PILL);
                    queue.notifyAll();
                }
                int index = 0;
                for (var item : queue) {
                    System.out.println("index=" + index + " value=" + Thread.currentThread().getName() );
                    index++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    // Tạo consumer task
    @Override
    public Runnable createConsumer(String level,
                                   LocalDateTime from, LocalDateTime to, String message,
                                   Queue<List<String>> queue, List<String> POISON_PILL,
                                   int outputMode, String outputFolder) {
        return () -> {
            try {
                System.out.println(Thread.currentThread().getName()+ "thread consumer");
                List<Thread> workers = new ArrayList<>();
                while (true) {
                    System.out.println(Thread.currentThread().getName()+ "thread consumer while");
                    List<String> chunk;
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            queue.wait();
                        }
                        chunk = queue.poll();
                    }

                    if (chunk == POISON_PILL) {
                        break;
                    }
                    if (chunk != null) {
                        Runnable worker = createWorker(chunk, level, from, to, message, outputMode, outputFolder);
                        Thread workerThread = new Thread(worker);
                        workers.add(workerThread);
                        workerThread.start();
                    }
                    for (Thread t : workers) {
                        t.join();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    // Tạo worker task
    @Override
    public Runnable createWorker(List<String> chunkData, String level,
                                 LocalDateTime from, LocalDateTime to, String message,
                                 int outputMode, String outputFolder) {
        return () -> {
            try {
                System.out.println(Thread.currentThread().getName()+ "thread worker" );

//                if (chunkData.size() > 2000) {
//                    Thread.sleep(3000);
//                } else if (chunkData.size() > 1000) {
//                    Thread.sleep(1500);
//                } else {
//                    Thread.sleep(500);
//                }

                var rs = queueInstance.processChunk(chunkData, level, from, to, message);
                System.out.println(Thread.currentThread().getName());
                System.out.println(rs.size());

                if (outputMode == 2) {
                    String fileName;
                    if (message != null && !message.isBlank()) {
                        fileName = message;
                    } else if (from != null && to != null) {
                        fileName = StringUtils.sanitizeString(from+"-"+to);
                    } else {
                        fileName = level;
                    }

                    String outputFile = queueInstance.CreateFile(outputFolder, fileName);
                    queueInstance.writeToFile(rs, outputFile);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
