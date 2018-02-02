package frc.team1983.commands.debugging;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.subsystems.utilities.Motor;

public class DSTestFunctionality extends Command
{
    private int motorIndex = 0;
    private Motor motors[] = new Motor [1];
    // there is only one space in the motor array, since we are only running one motor at a time
    // on 2017, motor 6 is the collector belt
    private DigitalInput motorSelector = new DigitalInput(0);

    public DSTestFunctionality()
    {
        int port = 6;
        // this sets the motor to motor port already stated automatically
        {
            motors[motorIndex] = new Motor (port, NeutralMode.Brake,true);
            // the motor indexed here is given the constraints required
        }
    }
    protected void initialize()
    {
      //nothing
    }
    protected void execute()
    {
        // this sets the speed of the selected motor to half speed automatically
        motors[motorIndex].set(0.5);
        if (motorSelector.get())
        {
            System.out.println("motorSelector returns true");
        }
        else
        {
            System.out.println("motorSelector returns false");
        }
    }
    protected void end()
    {
        // if this were called within the test, then the motor would be turned off
        // since this is currently not called, in order to shut off the motor you need to disable it
        motors[motorIndex].set(0);
    }
    protected void isFinished()
    {
        motors[motorIndex].set(0);
    }
}
