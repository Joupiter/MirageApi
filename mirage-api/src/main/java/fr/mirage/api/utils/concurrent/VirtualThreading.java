package fr.mirage.api.utils.concurrent;

import lombok.experimental.UtilityClass;

import java.util.concurrent.*;

@UtilityClass
public class VirtualThreading {

    public final ExecutorService FIXED_POOL = Executors.newFixedThreadPool(20, Thread.ofVirtual().name("alva-virtual-thread-", 0).factory());
    public final ScheduledExecutorService SCHEDULED_POOL = Executors.newScheduledThreadPool(20, Thread.ofVirtual().name("alva-virtual-thread-", 0).factory());

    public ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        return SCHEDULED_POOL.schedule(runnable, delay, timeUnit);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long initialDelay, long period, TimeUnit timeUnit) {
        return SCHEDULED_POOL.scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);
    }

    public void execute(Runnable runnable) {
        FIXED_POOL.execute(runnable);
    }

    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, FIXED_POOL);
    }

    public Future<?> submit(Runnable runnable) {
        return FIXED_POOL.submit(runnable);
    }

    public <T> CompletableFuture<T> submit(T type) {
        return CompletableFuture.supplyAsync(() -> type, FIXED_POOL);
    }

}