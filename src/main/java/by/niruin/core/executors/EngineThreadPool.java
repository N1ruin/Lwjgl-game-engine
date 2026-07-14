package by.niruin.core.executors;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Log4j2
public class EngineThreadPool {
    private final ExecutorService executor;

    public EngineThreadPool() {
        int threads = Math.max(1, Runtime.getRuntime().availableProcessors() - 2);
        this.executor = Executors.newScheduledThreadPool(threads);
        log.info("Scheduler initialized with {} threads", threads);
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
