package frc.team1983.services.smellylog;

public class Logger {
    private String className;

    private Level level;

    Logger(String className, Level level) //Package-private so that only LoggerFactory can make a logger
    {
        this.className = className;
        this.level = level;
    }

    public void trace(String message)
    {
        if(this.level.PRIORITY >= Level.TRACE.PRIORITY)
        {
            log(Level.TRACE, message);
        }
    }

    public void debug(String message)
    {
        if(this.level.PRIORITY >= Level.DEBUG.PRIORITY)
        {
            log(Level.DEBUG, message);
        }
    }

    public void info(String message)
    {
        if(this.level.PRIORITY >= Level.INFO.PRIORITY)
        {
            log(Level.INFO, message);
        }
    }

    public void warn(String message)
    {
        if(this.level.PRIORITY >= Level.WARN.PRIORITY)
        {
            log(Level.WARN, message);
        }
    }

    public void error(String message)
    {
        if(this.level.PRIORITY >= Level.ERROR.PRIORITY)
        {
            log(Level.ERROR, message);
        }
    }

    void log(Level level, String message) //Package-private so only loggers can log
    {
        System.out.println(className + " " + level.name() + ": " + message);
    }
}
