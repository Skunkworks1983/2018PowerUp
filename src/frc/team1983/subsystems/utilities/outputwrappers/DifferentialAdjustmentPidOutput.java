package frc.team1983.subsystems.utilities.outputwrappers;

public class DifferentialAdjustmentPidOutput extends PidOutputWrapper
{
    private DifferentialTurnAnglePidOutput differentialTurnAnglePidOutput;
    public DifferentialAdjustmentPidOutput(DifferentialTurnAnglePidOutput differentialTurnAnglePidOutput)
    {
        this.differentialTurnAnglePidOutput = differentialTurnAnglePidOutput;
    }

    public void writeHelper(double out)
    {
        differentialTurnAnglePidOutput.setAdjustmentSpeed(out);
    }

}
