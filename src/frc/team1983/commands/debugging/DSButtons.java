package frc.team1983.commands.debugging;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team1983.subsystems.utilities.Motor;
import java.util.ArrayList;

public class DSButtons
{
    private boolean continuousPress = false;
    private int motorIndex = 0;
    // motor always starts out on 0
    //private Motor motors[] = new Motor[16];
    private ArrayList<Motor> motors;
    private DigitalInput motorUp;
    private DigitalInput motorDown;
    private AnalogInput manualSpeed;

    public DSButtons()
    {
        /*this.motorUp = motorUp;
        this.motorDown = motorDown;
        this.manualSpeed = manualSpeed; */

       /* motorUp = new DigitalInput(5);
        motorDown = new DigitalInput(4);
        manualSpeed = new AnalogInput(2); */

    }

    public void initialize(ArrayList<Motor> motorList, DigitalInput indexUp, DigitalInput indexDown, AnalogInput manSpeed)
    {
        motorUp = indexUp;
        motorDown = indexDown;
        manualSpeed = manSpeed;

        motors = new ArrayList<Motor>(motorList);

       // motors.get(motorIndex).setNeutralMode(NeutralMode.Coast);
        /* for(int i=0; i<16;i++)
        {
            motors.add(new Motor(i, NeutralMode.Coast, false));
            motors.get(i).setNeutralMode(NeutralMode.Coast);
            System.out.println("Initialized motor " + i);
        } */
    }

    public void execute()
    {
        double speed = (manualSpeed.getVoltage() / 5.0) - 0.5;
        if(manualSpeed != null)
        {
            // motors[motorIndex].set((manualSpeed.getVoltage()/2.5)-1);
            // motors[motorIndex].set(speed);
            motors.get(motorIndex).set(speed);
            // for now, maximum speed is set to half of full possible speed
        }
        if(!continuousPress)
        {
            if(motorUp.get())
            {
                System.out.println("Motor up is activated");
                // motors[motorIndex].set(0);
                motors.get(motorIndex).set(0);
                if(motorIndex == motors.size())
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
                System.out.println("Motor down is activated");
                //motors[motorIndex].set(0);
                motors.get(motorIndex).set(0);
                if(motorIndex == 0)
                {
                    motorIndex = (motors.size() - 1);
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
        //motors[motorIndex].set(0);
        motors.get(motorIndex).set(0);
        for(int i = 0; i < 16; i++)
        {
            //motors[i].set(0);
            motors.get(motorIndex).set(0);
        }
    }

    protected boolean isFinished()
    {
        return false;
    }

    public int getMotorIndex()
    {
        return motorIndex;
    }

    public double getSpeed()
    {
        return ((manualSpeed.getVoltage() / 5) - 0.5);
    }

}
