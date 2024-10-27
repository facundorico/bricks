package bricks.trading.application.logging;

public interface ILoggerUtil {

    void info (String message, String fileMethod);

    void warn (String message, String fileMethod);

    void error (String message, String fileMethod);
}
