package fun.clclcl.yummic.jsch;


import org.slf4j.Logger;

public class Slf4jLogger extends SshLogger {

    Logger logger;

    public Slf4jLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void log(int level, String message, Exception e) {
        switch (level) {
            case FATAL:
            case ERROR:
                logger.error(message, e);
                break;
            case INFO:
                logger.info(message, e);
                break;
            case WARN:
                logger.warn(message, e);
                break;
            case DEBUG:
            default:
                logger.debug(message, e);
                break;
        }
    }

    @Override
    public boolean isEnabled(int level) {
        return true;
    }

    @Override
    public void log(int level, String message) {
        this.log(level, message, null);
    }
}
