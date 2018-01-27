package frc.team1983.commands.debugging;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.team1983.subsystems.utilities.Motor;

public class DSTestFunctionality
{
    private int motorIndex = 0;
    private Motor motors[] = new Motor [1];

    public DSTestFunctionality()
    {
        int port = 6;
        {
            motors[motorIndex] = new Motor (port, NeutralMode.Brake,true);
        }
    }
    protected void initialize()
    {
      //nothing
    }
    public void execute()
    {
        motors[motorIndex].set(0.5);
    }
    public void end()
    {
        motors[motorIndex].set(0);
    }
}
