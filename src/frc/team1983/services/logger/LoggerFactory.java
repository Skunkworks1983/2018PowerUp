package frc.team1983.services.logger;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class LoggerFactory
{

    public LoggerFactory() {    }

    public static Logger createNewLogger(Class clazz) {
        Logger newLogger = Logger.getLogger("clazz");
        ConsoleAppender consoleAppender = new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN));
        RollingFileAppender rollingFileAppender = new RollingFileAppender();

        rollingFileAppender.setName("Rolling appender");
        rollingFileAppender.setFile("2018PowerUp/log.txt");
        rollingFileAppender.setThreshold(Level.TRACE);
        rollingFileAppender.setAppend(true);
        rollingFileAppender.setLayout(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN));
        rollingFileAppender.setBufferSize(10);
        rollingFileAppender.setMaxFileSize("5KB");

        newLogger.addAppender(consoleAppender);
        newLogger.addAppender(rollingFileAppender);
        return newLogger;
    }
}
