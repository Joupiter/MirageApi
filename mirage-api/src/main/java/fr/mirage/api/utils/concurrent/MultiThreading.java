package fr.mirage.api.utils.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.experimental.UtilityClass;

import java.util.concurrent.*;

@UtilityClass
public class MultiThreading {

    public final ExecutorService FIXED_POOL = Executors.newFixedThreadPool(10, new ThreadFactoryBuilder().setNameFormat("alva-fixed-thread-%d").build());
    public final ScheduledExecutorService SCHEDULED_POOL = Executors.newScheduledThreadPool(10, new ThreadFactoryBuilder().setNameFormat("alva-scheduled-thread-%s").build());

    public ScheduledFuture<?> schedule(Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
        return SCHEDULED_POOL.scheduleAtFixedRate(runnable, initialDelay, delay, unit);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long initialDelay, TimeUnit unit) {
        return SCHEDULED_POOL.schedule(runnable, initialDelay, unit);
    }

    public void execute(Runnable runnable) {
        FIXED_POOL.execute(runnable);
    }

    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, FIXED_POOL);
    }

}