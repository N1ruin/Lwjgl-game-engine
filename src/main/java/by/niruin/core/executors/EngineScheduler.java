package by.niruin.core.executors;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Log4j2
public class EngineScheduler {
    private final ScheduledExecutorService executor;

    public EngineScheduler() {
        int threads = Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
        this.executor = Executors.newScheduledThreadPool(Math.min(threads, 2));
        log.info("Scheduler initialized with {} threads", threads);
    }

    public ScheduledFuture<?> schedule(Runnable task, long delay, TimeUnit unit) {
        return executor.schedule(task, delay, unit);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelay,
                                                  long period, TimeUnit unit) {
        return executor.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long initialDelay,
                                                     long delay, TimeUnit unit) {
        return executor.scheduleWithFixedDelay(task, initialDelay, delay, unit);
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }

    public void destroy() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
