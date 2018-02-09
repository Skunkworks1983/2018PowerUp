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
        /*this.motorUp = motorUp;
        this.motorDown = motorDown;
        this.manualSpeed = manualSpeed; */

        motorUp = new DigitalInput(5);
        motorDown = new DigitalInput(4);
        manualSpeed = new AnalogInput(2);

        for(int i = 0; i<16; i++)
        {
            motors[i] = new Motor(i, NeutralMode.Coast, false);
        }
    }
    public void initialize()
    {
        for(int i=0; i<16;i++)
        {
            motors[i].setNeutralMode(NeutralMode.Coast);
            System.out.println("Initialized motor " + i);
        }
    }
    public void execute()
    {
        double speed = (manualSpeed.getVoltage()/5.0)-0.5;
        if (manualSpeed != null)
        {
           // motors[motorIndex].set((manualSpeed.getVoltage()/2.5)-1);
            motors[motorIndex].set(speed);
            // for now, maximum speed is set to half of full possible speed
        }
        if(!continuousPress)
        {
            if(motorUp.get())
            {
                motors[motorIndex].set(0);
                if(motorIndex == 15)
                {
                    motorIndex = 0;
                }
                else
                {
                    motorIndex += 1;
                }
                System.out.println("New motor index is " + motorIndex + " up");
                continuousPress = true;
            }
            else if(motorDown.get())
            {
                motors[motorIndex].set(0);
                if(motorIndex == 0)
                {
                    motorIndex = 15;
                }
                else
                {
                    motorIndex -= 1;
                }
                System.out.println("New motor index is " + motorIndex);
                continuousPress = true;
            }
        }
        else
        {
            if(!((motorUp.get()) || motorDown.get()))
            {
                System.out.println("Button released");
                continuousPress = false;
            }
        }
}
    public void end()
    {
        motors[motorIndex].set(0);
        for(int i=0; i<16;i++)
        {
            motors[i].set(0);
        }
    }
    protected boolean isFinished()
    {
        return false;
    }
}
