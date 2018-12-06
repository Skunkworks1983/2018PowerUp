package frc.team1983.services.smellylog;

public enum Level {
    TRACE(1),
    DEBUG(2),
    INFO(3),
    WARN(4),
    ERROR(5)
    ;

    public final int PRIORITY; //Higher number is higher priority

    Level(int priority) {
        PRIORITY = priority;
    }
}
