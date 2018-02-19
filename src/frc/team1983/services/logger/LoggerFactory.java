package frc.team1983.services.logger;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.spi.LoggerContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerFactory
{
    private static LayoutComponentBuilder layout;
    private static String name, initTime;
    private static ConfigurationBuilder<BuiltConfiguration> builder;
    private static AppenderComponentBuilder appenderBuilder;
    private static LoggerContext ctx;

    public static Logger createNewLogger(Class clazz)
    {
        return createNewLogger(clazz, Level.INFO);
    }

    private static Logger createNewLogger(Class clazz, Level level)
    {
        name = clazz.getSimpleName();

        builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        layout = builder.newLayout("PatternLayout")
                        .addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");

        builder.setStatusLevel(level);
        builder.setConfigurationName("RollingBuilder");
// create a console appender
        appenderBuilder = builder.newAppender("Stdout", "CONSOLE")
                                 .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        appenderBuilder.add(layout);
        builder.add(appenderBuilder);

// create a file appender
        if(initTime == null)
        {
            initTime = new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date());
        }
        appenderBuilder = builder.newAppender("File", "FILE")
                                 .addAttribute("fileName", "/home/lvuser/logs/RobotLog " + initTime);
        appenderBuilder.add(layout);
        builder.add(appenderBuilder);

// create the new logger
        builder.add(builder.newLogger(name, level)
                           .add(builder.newAppenderRef("Stdout")).addAttribute("additivity", false));
        builder.add(builder.newLogger(name, level).add(builder.newAppenderRef("File")));

        builder.add(builder.newRootLogger(Level.INFO).add(builder.newAppenderRef("Stdout")));
        ctx = Configurator.initialize(builder.build());

        return (Logger) ctx.getLogger(name);
    }

}
