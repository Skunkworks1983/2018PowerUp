package frc.team1983.services;

/*
    logger: just prints to the console. might want to have more logic for debugging
    motion profiling and more complicated behavior. for now, just print
*/
public class Logger
{
    public Logger() {

    }

    public void logDebug(String message) {
        System.out.println("DEBUG:" + message);
    }

    public void logWarning(String message) {
        System.out.println("WARN:" + message);
    }

    public void logFatal(String message) {
        System.out.println("FATAL: " + message);
    }
}
