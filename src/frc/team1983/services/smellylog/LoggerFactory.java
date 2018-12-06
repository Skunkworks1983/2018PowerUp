package frc.team1983.services.smellylog;

import java.util.HashMap;

public class LoggerFactory {
    private static LoggerFactory ourInstance = new LoggerFactory();

    public static LoggerFactory getInstance() {
        return ourInstance;
    }

    private HashMap<String, Level> exceptionsList;

    private Level globalLevel = Level.INFO;

    private LoggerFactory() {
        exceptionsList = new HashMap<>();
    }

    public Logger newLogger(String className)
    {

        if(exceptionsList.containsKey(className))
        {
            return new Logger(className, exceptionsList.get(className));
        }

        return new Logger(className, globalLevel);
    }

    public void setGlobalLevel(Level newGlobalPrintLevel) {
        this.globalLevel = newGlobalPrintLevel;
    }

    public void addPrintException(String className, Level newLevel)
    {
        exceptionsList.put(className, newLevel);
    }
}
