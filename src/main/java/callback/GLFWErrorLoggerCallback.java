package callback;

import lombok.extern.log4j.Log4j2;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

@Log4j2
public class GLFWErrorLoggerCallback extends GLFWErrorCallback {

    public void init() {
        this.set();
    }

    @Override
    public void invoke(int error, long description) {
        var errorMessage = getDescription(description);

        switch (error) {
            case GLFW_NOT_INITIALIZED -> log.error("GLFW not initialized: {}", errorMessage);
            case GLFW_INVALID_ENUM -> log.error("GLFW invalid enum: {}", errorMessage);
            case GLFW_INVALID_VALUE -> log.error("GLFW invalid value: {}", errorMessage);
            case GLFW_API_UNAVAILABLE -> log.error("GLFW API unavailable: {}", errorMessage);
            case GLFW_VERSION_UNAVAILABLE -> log.error("GLFW version unavailable: {}", errorMessage);
            case GLFW_PLATFORM_ERROR -> log.error("GLFW platform error: {}", errorMessage);
            case GLFW_FORMAT_UNAVAILABLE -> log.error("GLFW format unavailable: {}", errorMessage);
            case GLFW_NO_WINDOW_CONTEXT -> log.error("GLFW no window context: {}", errorMessage);
            default -> log.error("GLFW error {}: {}", error, errorMessage);
        }
    }

    public void destroy() {
        this.free();
    }
}
