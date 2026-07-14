package by.niruin.core;

import exception.ExceptionCode;
import exception.EngineException;
import loader.SettingsLoader;
import callback.GLFWErrorLoggerCallback;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

@Log4j2
public class Window {
    private final GLFWErrorLoggerCallback loggerCallback;
    @Getter
    private final WindowSettings windowSettings;
    @Getter
    private long windowDescriptor;

    public Window(SettingsLoader settingsLoader) {
        this.windowSettings = settingsLoader.loadWindowSettings();
        loggerCallback = new GLFWErrorLoggerCallback();
    }

    public void init() {
        loggerCallback.init();

        if (!glfwInit()) {
            log.error("GLFW initializing error");
            throw new EngineException(ExceptionCode.GLFW_ERROR, "GLFW initializing error");
        }
        log.error("GLFW initialized successfully");

        createWindow(windowSettings);
        centeredWindow();

        glfwMakeContextCurrent(windowDescriptor);

        if (windowSettings.isVSyncEnabled) {
            glfwSwapInterval(1);
        }

        glfwShowWindow(windowDescriptor);
    }

    public void destroy() {
        loggerCallback.destroy();

        glfwFreeCallbacks(windowDescriptor);
        glfwDestroyWindow(windowDescriptor);

        glfwTerminate();
    }

    private void createWindow(WindowSettings settings) {
        log.info("Window initialization started");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        if (windowSettings.isFullscreen) {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        }

        windowDescriptor = glfwCreateWindow(
                settings.width,
                settings.height,
                settings.title,
                NULL,
                NULL);

        if (windowDescriptor == NULL) {
            log.fatal("Window creation error");
            throw new EngineException(ExceptionCode.GLFW_ERROR, "Window creation error");
        }

        log.info("Window creation successfully");
    }

    private void centeredWindow() {
        if (windowSettings.isFullscreen()) {
            log.debug("Window is fullscreen, skipping centering");
            return;
        }

        var primaryMonitorDescriptor = glfwGetPrimaryMonitor();
        GLFWVidMode videoMode = glfwGetVideoMode(primaryMonitorDescriptor);

        if (videoMode == null) {
            log.warn("Could not get video mode, skipping centering");
            return;
        }

        int screenWidth = videoMode.width();
        int screenHeight = videoMode.height();

        int[] windowWidth = new int[1];
        int[] windowHeight = new int[1];
        glfwGetWindowSize(windowDescriptor, windowWidth, windowHeight);

        int posX = (screenWidth - windowWidth[0]) / 2;
        int posY = (screenHeight - windowHeight[0]) / 2;

        glfwSetWindowPos(windowDescriptor, posX, posY);

        log.debug("Window centered: screen={}x{}, window={}x{}, position=({},{})",
                screenWidth, screenHeight, windowWidth[0], windowHeight[0], posX, posY);
    }

    public record WindowSettings(String title,
                                 int width,
                                 int height,
                                 boolean isVSyncEnabled,
                                 int targetFps,
                                 boolean isFullscreen) {


    }
}
