package frc.team1983.subsystems.utilities.inputwrappers;

import frc.team1983.subsystems.Drivebase;

public class DifferentialTurnAnglePidInput extends PidInputWrapper
{
    private double leftEncoderStart;
    private double rightEncoderStart;
    private Drivebase drivebase;

    public DifferentialTurnAnglePidInput(Drivebase drivebase)
    {
        this.drivebase = drivebase;
        leftEncoderStart = drivebase.getLeftDist();
        rightEncoderStart = drivebase.getRightDist();
    }

    public double pidGet()
    {
        return (((drivebase.getLeftDist() - leftEncoderStart) -
                (drivebase.getRightDist() - rightEncoderStart)));

    }


}
