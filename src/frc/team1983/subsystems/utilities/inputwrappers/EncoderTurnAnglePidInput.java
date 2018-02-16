package frc.team1983.subsystems.utilities.inputwrappers;

import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;

public class EncoderTurnAnglePidInput extends PidInputWrapper
{
    public EncoderTurnAnglePidInput(Drivebase drivebase)
    {
        this.drivebase = drivebase;
    }

    private Drivebase drivebase;

    public double pidGet()
    {
        return (drivebase.getLeftEncoderValue() - drivebase.getRightEncoderValue()) * Constants.AutoValues.EFFECTIVE_REDUCTION_DRIVEBASE
                * Constants.AutoValues.WHEELBASE_DEGREES * Constants.AutoValues.WHEEL_CIRCUMFERENCE;
    }
}
