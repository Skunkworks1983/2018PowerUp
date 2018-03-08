package frc.team1983.services.automanager;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.Robot;
import frc.team1983.commands.autonomous.PlaceCubeInExchangeZone;
import frc.team1983.commands.autonomous.PlaceCubeInScale;
import frc.team1983.commands.autonomous.PlaceCubeInSwitch;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;

import java.lang.reflect.InvocationTargetException;

public class AutoFactory
{
    public static CommandGroup createAuto(AutoEnum autoEnum)
    {
        Logger logger = LoggerFactory.createNewLogger(AutoFactory.class, Level.WARN);

        Class<? extends CommandGroup> clazz = autoEnum.getClazz();
        try
        {
             return clazz.getConstructor(Drivebase.class, StatefulDashboard.class)
                 .newInstance(Robot.getInstance().getDrivebase(), Robot.getInstance().getDashboard());
        }
        catch(NoSuchMethodException | NullPointerException | IllegalAccessException |
                InstantiationException | InvocationTargetException | ClassCastException e)
        {
            logger.warn("Error when creating auto", e);
            return new CommandGroup();
        }
    }

    public enum AutoEnum
    {
        EXCHANGE_ZONE("Exchange Zone", PlaceCubeInExchangeZone.class),
        SCALE("Scale", PlaceCubeInScale.class),
        SWITCH("Switch", PlaceCubeInSwitch.class),
        AUTO_SMELLY("AutoEnum SmellyDrive", null);

        private String simpleName;
        private Class<? extends CommandGroup> clazz;

        AutoEnum(String simpleName, Class<? extends CommandGroup> clazz)
        {
            this.simpleName = simpleName;
            this.clazz = clazz;
        }

        public String getSimpleName()
        {
            return simpleName;
        }

        public Class<? extends CommandGroup> getClazz()
        {
            return clazz;
        }
    }
}
