package frc.team1983.subsystems.utilities;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.Robot;
import frc.team1983.services.PidValuesWatcher;
import frc.team1983.settings.Constants;

/*Class that wraps the PIDController class provided by WpiLib. Not used for any extra functionality yet, but should be
  the default implementation in our codebase.
*/
public class PidControllerWrapper extends PIDController
{
    private double p, i, d, f;
    private PIDSource source;
    private PIDOutput output;
    private String name;
    double[] pidValues;
    PidValuesWatcher watcher;

    public PidControllerWrapper(double p, double i, double d, double f, PIDSource source, PIDOutput output,
                                String name, PidValuesWatcher watcher)
    {
        super(p, i, d, f, source, output);
        this.name = name;
        this.watcher = watcher;
    }

    public PidControllerWrapper(double p, double i, double d, PIDSource source, PIDOutput output,
                                String name, PidValuesWatcher watcher)
    {
        this(p, i, d, Constants.PidNames.PLACEHOLDER_VALUE, source, output, name, watcher);
    }

    private void ConstantsUpdater()
    {
        double[] pidValues = watcher.getPidValues(name);
        setP(pidValues[0]);
        setI(pidValues[1]);
        setD(pidValues[2]);
        setF(pidValues[3]);
    }
}
