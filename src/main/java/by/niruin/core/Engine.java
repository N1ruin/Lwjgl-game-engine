package by.niruin.core;

import by.niruin.core.executors.EngineScheduler;
import by.niruin.core.executors.EngineThreadPool;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

@Log4j2
public class Engine {
    private final Window window;
    private final EngineScheduler engineScheduler;
    private final EngineThreadPool engineThreadPool;

    public Engine(Window window, EngineScheduler engineScheduler, EngineThreadPool engineThreadPool) {
        this.window = window;
        this.engineScheduler = engineScheduler;
        this.engineThreadPool = engineThreadPool;
    }

    public void init() {
        window.init();
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void run() {
        while (!glfwWindowShouldClose(window.getWindowDescriptor())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(window.getWindowDescriptor());
            glfwPollEvents();
        }

        window.destroy();
    }

    public void destroy() {
        engineScheduler.destroy();
        engineThreadPool.destroy();
        window.destroy();
    }
}
