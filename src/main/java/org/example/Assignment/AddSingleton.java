package org.example.Assignment;

import org.example.JavaCore.Creational_Pattern.Singleton;

public class AddSingleton {
    private AddSingleton() {}
    private static class Holder {
        private static final ChunkReadQueue INSTANCE = new ChunkReadQueue();
    }

    public static ChunkReadQueue getInstance() {
        return Holder.INSTANCE;
    }
}
