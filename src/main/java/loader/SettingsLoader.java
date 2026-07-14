package loader;

import by.niruin.core.Window.WindowSettings;
import lombok.extern.log4j.Log4j2;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
public class SettingsLoader {
    private static final String SETTINGS_FILE_NAME = "/settings/window_settings.json";
    private static final int DEFAULT_WINDOW_WIDTH = 800;
    private static final int DEFAULT_WINDOW_HEIGHT = 600;
    private static final int DEFAULT_TARGET_FPS = 60;
    private static final boolean DEFAULT_VSYNC_ENABLED = true;
    private static final boolean DEFAULT_FULLSCREEN_ENABLED = true;
    private static final String DEFAULT_WINDOW_TITLE = "My game";

    private final ObjectMapper objectMapper;

    public SettingsLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public WindowSettings loadWindowSettings() {
        log.info("Window settings loading started");

        try (InputStream inputStream = getClass().getResourceAsStream(SETTINGS_FILE_NAME)) {
            log.info("Window settings loading ended");

            return objectMapper.readValue(inputStream, WindowSettings.class);
        } catch (IOException e) {
            log.warn("Window settings file loading error. File {} not exist. Standard settings applied",
                    SETTINGS_FILE_NAME);

            log.info("Window settings loading with default settings ended");
            return new WindowSettings(
                    DEFAULT_WINDOW_TITLE,
                    DEFAULT_WINDOW_WIDTH,
                    DEFAULT_WINDOW_HEIGHT,
                    DEFAULT_VSYNC_ENABLED,
                    DEFAULT_TARGET_FPS,
                    DEFAULT_FULLSCREEN_ENABLED);
        }
    }
}
