package bricks.trading.application.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggerUtilImpl implements ILoggerUtil {

    @Override
    public void info(String message, String fileMethod) {
        log.info(generateLog(message, fileMethod));
    }

    @Override
    public void warn(String message, String fileMethod) {
        log.warn(generateLog(message, fileMethod));
    }

    @Override
    public void error(String message, String fileMethod) {
        log.error(generateLog(message, fileMethod));
    }

    private String generateLog(String message, String fileMethod) {
        return (fileMethod+" - "+message);
    }
}
