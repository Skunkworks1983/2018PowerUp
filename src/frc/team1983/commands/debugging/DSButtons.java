package frc.team1983.commands.debugging;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team1983.subsystems.utilities.Motor;

public class DSButtons
{
    private boolean continuousPress = false;
    private int motorIndex = 0;
    private Motor motors[] = new Motor[16];
    private DigitalInput motorUp;
    private DigitalInput motorDown;
    private AnalogInput manualSpeed;

    public DSButtons()
    {
        this.motorUp = motorUp;
        this.motorDown = motorDown;
        this.manualSpeed = manualSpeed;

        motorUp = new DigitalInput(5);
        motorDown = new DigitalInput(4);
        manualSpeed = new AnalogInput(2);

        for(int i = 0; i<16; i++)
        {
            motors[i] = new Motor(i, NeutralMode.Coast, false);
        }
    }
    protected void initialize()
    {
        System.out.println("command initialized");
    }
    protected void execute()
    {
        if (manualSpeed != null)
        {
            motors[motorIndex].set((manualSpeed.getVoltage()/2.5)-1);
        }
        if(!continuousPress)
        {
        if(motorUp != null)
        {
            if(motorIndex == 15)
            {
                motorIndex = 0;
                System.out.println("The motor running is number" + motors[motorIndex]);
            }
            else
            {
                motorIndex += 1;
                System.out.println("The motor running is number" + motors[motorIndex]);
            }
            continuousPress = true;
        }
        if(motorDown != null)
        {
            if(motorIndex == 0)
            {
                motorIndex = 15;
                System.out.println("The motor running is number" + motors[motorIndex]);
            }
            else
            {
                motorIndex -= 0;
                System.out.println("The motor running is number" + motors[motorIndex]);
            }
            continuousPress = true;
        }
    }
}
    protected void end()
    {
        motors[motorIndex].set(0);
    }
    protected boolean isFinished()
    {
        return false;
    }
}
