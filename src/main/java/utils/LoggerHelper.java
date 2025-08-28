package utils;


import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerHelper {
    private static final Logger Log = org.apache.logging.log4j.LogManager.getLogger(LoggerHelper.class);

    public static Logger getLogger(Class<?> cls) {
        return LogManager.getLogger(cls);
    }

    public static void logToAllure(String message) {
        Allure.step(message);
    }

    public static void info(String message) {
        Log.info(message);
    }
}
