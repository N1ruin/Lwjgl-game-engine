package exception;

public class EngineException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public EngineException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
}
