package by.niruin.core;

import by.niruin.core.executors.EngineScheduler;
import by.niruin.core.executors.EngineThreadPool;
import loader.SettingsLoader;
import tools.jackson.databind.ObjectMapper;

public class Main {
    static void main(String[] args) {
        var objectMapper = new ObjectMapper();
        var settingsLoader = new SettingsLoader(objectMapper);
        var window = new Window(settingsLoader);
        var scheduler = new EngineScheduler();
        var threadPool = new EngineThreadPool();

        var engine = new Engine(window, scheduler, threadPool);

        engine.init();

        engine.run();

        engine.destroy();
    }
}
