package org.example.JavaCore.Thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TaskService {
    private final ExecutorService service = Executors.newFixedThreadPool(3);

    public void executor(List<TaskTest> list) {
        List<Future<?>> futures = new ArrayList<>();
        for (TaskTest task : list) {
            Future<?> f = service.submit(task);
            futures.add(f);
        }

        boolean allDone;
        while (true) {
            allDone = true;
            for (Future<?> f : futures) {
                if (!f.isDone()) {
                    allDone = false;
                    break;
                }
            }

            //  Kiểm tra deadlock trong khi chờ
            checkDeadlock();

            if (allDone) {
                break;
            }

            try {
                // tránh busy-wait ăn CPU 100%
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println(">>> executor finished!");
    }

    public void checkDeadlock() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] ids = bean.findDeadlockedThreads();
        if (ids != null) {
            ThreadInfo[] infos = bean.getThreadInfo(ids, true, true);
            System.err.println(" Deadlock detected!");
            for (ThreadInfo info : infos) {
                System.err.println(info.toString());
            }
        }
    }
}
