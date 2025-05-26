package fr.mirage.core.proxy.queue;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import fr.mirage.api.queue.MatchQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class QueueRunnable implements Runnable {

    private final Set<MatchQueue> queues;

    private final ScheduledExecutorService executorService;
    private final Future<?> task;

    public QueueRunnable(Set<MatchQueue> queues, int maxThreads, boolean isVirtual) {
        this.queues = queues;
        this.executorService = isVirtual
                ? Executors.newScheduledThreadPool(maxThreads, Thread.ofVirtual().name("alva-virtual-queue-processor-%d", 0).factory())
                : Executors.newScheduledThreadPool(maxThreads, new ThreadFactoryBuilder().setNameFormat("alva-virtual-queue-processor-%d").build());
        this.task = executorService.scheduleAtFixedRate(this, 20, 1, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        if (task == null || task.isCancelled() || task.isDone()) return;

        try {

        } catch (Exception exception) {
            log.error("Error : {}", exception.getMessage());
        }
    }

    public void shutdown() {
        if (task == null) return;

        if (!task.isCancelled() || !task.isDone())
            task.cancel(false);

        if (executorService != null && !executorService.isShutdown())
            executorService.shutdown();
    }

}