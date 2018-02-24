package frc.team1983.services.logger;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.spi.LoggerContext;

public class LoggerFactory
{
    public LoggerFactory() {    }

    public static Logger createNewLogger(Class clazz) { return createNewLogger(clazz, Level.INFO); }

    private static Logger createNewLogger(Class clazz, Level level) {

        String name = clazz.getName();
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        builder.setStatusLevel(level);
        builder.setConfigurationName("RollingBuilder");
// create a console appender
        AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE").addAttribute("target",
                                                                                                           ConsoleAppender.Target.SYSTEM_OUT);
        LayoutComponentBuilder layout = builder.newLayout("PatternLayout")
                                         .addAttribute("pattern", "%d [%t] %-5level %C{1}: %msg%n%throwable");
        appenderBuilder.add(layout);
        builder.add( appenderBuilder );


// create a rolling file appender
        ComponentBuilder triggeringPolicy = builder.newComponent("Policies").addComponent(builder.newComponent("OnStartupTriggeringPolicy"));
        appenderBuilder = builder.newAppender("rolling", "RollingFile")
                                 .addAttribute("fileName", "/home/lvuser//logs/log.txt")
                                 .addAttribute("filePattern", "/home/lvuser/logs/%d{MM-dd-yyyy}-%i-log.txt")
                                 .add(layout)
                                 .addComponent(triggeringPolicy);

        builder.add(appenderBuilder);

// create the new logger
        builder.add( builder.newLogger( name, level).add( builder.newAppenderRef( "Stdout" ) ).addAttribute( "additivity", false )
                            .add( builder.newAppenderRef( "rolling") ).addAttribute( "additivity", false ) );

        builder.add( builder.newRootLogger( Level.INFO )
                            .add( builder.newAppenderRef( "rolling" ) ).add(builder.newAppenderRef("Stdout")) );

        LoggerContext ctx = Configurator.initialize(builder.build());

        return (Logger) ctx.getLogger(name);
    }

}
