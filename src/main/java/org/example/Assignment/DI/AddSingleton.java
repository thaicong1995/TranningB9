package org.example.Assignment.DI;

import org.example.Assignment.Repo.ChunkReadQueue;
import org.example.Assignment.Repo.IChunkReadQueue;
import org.example.Assignment.Service.ILogService;
import org.example.Assignment.Service.LogService;

import java.util.HashMap;
import java.util.Map;

public class AddSingleton {
//    private AddSingleton() {}
//    private static class Holder {
//        private static final ChunkReadQueue INSTANCE = new ChunkReadQueue();
//    }
//
//    public static ChunkReadQueue getInstance() {
//        return Holder.INSTANCE;
//    }

    private static final Map<Class<?>, Object> beans = new HashMap<>();

    public static <T> void registerBean(Class<T> clazz, T instance) {
        beans.put(clazz, instance);
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }

    public static void init() {
        ChunkReadQueue queue = new ChunkReadQueue();
        registerBean(ChunkReadQueue.class, queue);

        LogService logService = new LogService(queue);
        registerBean(LogService.class, logService);

        registerBean(ILogService.class, logService);
        registerBean(IChunkReadQueue.class, queue);

    }
}
