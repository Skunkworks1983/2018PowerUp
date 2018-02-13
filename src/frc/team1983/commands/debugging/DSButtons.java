package frc.team1983.commands.debugging;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team1983.subsystems.utilities.Motor;
import java.util.ArrayList;

public class DSButtons
{
    private boolean continuousPress = false;
    // this is used below, stopping the buttons form being held and rapidly indexing through the motors
    private int motorIndex = 0;
    // motor always starts out on motorIndex 0
    private ArrayList<Motor> motors;
    // instead of using an array, like originally, now we're using an array list called motors, containing Motor
    private DigitalInput motorUp;
    // this is a button plugged into an DIO port on the roboRIO
    private DigitalInput motorDown;
    // this is a button plugged into an DIO port on the roboRIO
    private AnalogInput manualSpeed;
    // this is a potentiometer plugged into an Analog port on the roboRIO

    public DSButtons()
    {
        // originally, this constructor contained the setting up of the motors and the buttons
        // for unit testing, and other purposes, the constructors have been moved to both initalize and robot
        // all the required code for this class to function is still available, in different places and formats
    }

    public void initialize(ArrayList<Motor> motorList, DigitalInput indexUp, DigitalInput indexDown, AnalogInput manSpeed)
            // it's required we pass in the array list, digital inputs, and analog input in order for this class to work
            // anywhere initalize is called, these methods have to be passed in
    {
        motorUp = indexUp;
        // motorup here is set equal to indexup, which is used when calling initialize
        motorDown = indexDown;
        // motordown here is set equal to indexdown, which is used when calling initialize
        manualSpeed = manSpeed;
        // manualspeed here is set equal to manspeed, which is used when calling initalize

        motors = new ArrayList<Motor>(motorList);
        // the array list of motors here is set to
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
                if(motorIndex == motors.size() - 1)
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
