package frc.team1983.commands.debugging;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team1983.subsystems.utilities.Motor;
import java.util.ArrayList;

public class RunOneMotor
{
    //this is used below, stopping the buttons from being held and rapidly indexing through the motors
    private boolean continuousPress = false;
    private int motorIndex = 0;
    private ArrayList<Motor> motors;
    //port is 5
    private DigitalInput motorUp;
    //port is 4
    private DigitalInput motorDown;
    //port is 2
    private AnalogInput manualSpeed;

    //the ports for the digital inputs and analog input have to be changed in robot if they're different from this code

    public RunOneMotor()
    {
        // for unit testing, and other purposes, the constructors have been moved to both initalize and robot
    }

    public void initialize(ArrayList<Motor> motorList, DigitalInput indexUp, DigitalInput indexDown, AnalogInput manSpeed)
    {
        motorUp = indexUp;
        motorDown = indexDown;
        manualSpeed = manSpeed;

        // the array list of motors here is set to recieve the data from the motorlist array list
        motors = new ArrayList<Motor>(motorList);
    }

    public void execute()
    {
        // our maximum speed is half of what the actual maximum can be
        double speed = (manualSpeed.getVoltage() / 5.0) - 0.5;
        if(manualSpeed != null)
        {
            motors.get(motorIndex).set(speed);
        }
        // buttons both have to be released in order for this whole loop to run
        if(!continuousPress)
        {
            if(motorUp.get())
            {
                // this would set the speed of any motors currently running to zero, as a precaution
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
                //setting continuous press to true here allows the code to recognize its not free to index again
                continuousPress = true;
            }
            // the else if allows only one commmand to run, even if both buttons are pressed at the same time
            else if(motorDown.get())
            {
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
            // either motorup or motordown are not pressed
            if(!((motorUp.get()) || motorDown.get()))
            {
                System.out.println("Button released");
                continuousPress = false;
            }
        }
    }

    public void end()
    {
        motors.get(motorIndex).set(0);
        for(int i = 0; i < 16; i++)
        {
            motors.get(motorIndex).set(0);
        }
    }

    // is finished should never be called in this class
    protected boolean isFinished()
    {
        return false;
    }

    // used for unit testing purposes, not actually called in class
    public int getMotorIndex()
    {
        return motorIndex;
    }

    // used for unit testing purposes, not actually called in class
    public double getSpeed()
    {
        return ((manualSpeed.getVoltage() / 5) - 0.5);
    }

}
