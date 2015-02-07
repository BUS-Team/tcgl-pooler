package br.com.extractor;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExtractorModule {
    private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    private Extractor extractor;

    public ExtractorModule() {
        extractor = new PostgresExtractor();
        scheduler.scheduleAtFixedRate(new PostgresExtractor(), 0, 15, TimeUnit.SECONDS);
    }
}
