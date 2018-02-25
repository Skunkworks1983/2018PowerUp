package frc.team1983.services.logger;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.spi.LoggerContext;

public class LoggerFactory
{
    public LoggerFactory() {    }

    public static Logger createNewLogger(Class clazz) { return createNewLogger(clazz, Level.INFO); }

    public static Logger createNewLogger(Class clazz, Level level) {

        String name = clazz.getName();
        Logger newLogger;

        ConfigurationBuilder< BuiltConfiguration > builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        builder.setStatusLevel( level);
        builder.setConfigurationName("RollingBuilder");
// create a console appender
        AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE").addAttribute("target",
                                                                                                         ConsoleAppender.Target.SYSTEM_OUT);
        appenderBuilder.add(builder.newLayout("PatternLayout")
                                   .addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"));
        builder.add( appenderBuilder );


// create the new logger
        builder.add( builder.newLogger( name, level).add( builder.newAppenderRef( "Stdout" ) ).addAttribute( "additivity", false ) );


        builder.add( builder.newRootLogger( Level.INFO ).add( builder.newAppenderRef( "Stdout" ) ) );
        LoggerContext ctx = Configurator.initialize(builder.build());

        return (Logger) ctx.getLogger(name);
    }

}
