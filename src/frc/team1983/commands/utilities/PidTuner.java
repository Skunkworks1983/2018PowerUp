package frc.team1983.commands.utilities;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class PidTuner extends Command
{
    String elevatorComment, elevatorP, elevatorI, elevatorD, elevatorF;


    public PidTuner() throws IOException
    {
        InputStream inputStream = new BufferedInputStream(new FileReader("PidValues.txt"));

        String l;
        while((l = inputStream.readLine()) != null)
        {

        }

        SmartDashboard.putNumber("ElevatorP", 0);
        SmartDashboard.putNumber("ElevatorI", 0);
        SmartDashboard.putNumber("ElevatorD", 0);
        SmartDashboard.putNumber("ElevatorF", 0);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {

    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {

    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
    }
}
