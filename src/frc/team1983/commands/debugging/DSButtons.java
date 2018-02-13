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
    // instead of using an array, like originally, now we're using an array list called motors
    private DigitalInput motorUp;
    // this is a button plugged into a DIO port on the roboRIO, according to this code its is port 5
    private DigitalInput motorDown;
    // this is a button plugged into a DIO port on the roboRIO, according to this code its port 4
    private AnalogInput manualSpeed;
    // this is a potentiometer plugged into a Analog port on the roboRIO, according to this code its port 2

    public DSButtons()
    {
        // for unit testing, and other purposes, the constructors have been moved to both initalize and robot
    }

    public void initialize(ArrayList<Motor> motorList, DigitalInput indexUp, DigitalInput indexDown, AnalogInput manSpeed)
           // anywhere initalize is called, these methods have to be passed in
    {
        motorUp = indexUp;
        // motorup here is set equal to indexup, which is used when calling initialize
        motorDown = indexDown;
        // motordown here is set equal to indexdown, which is used when calling initialize
        manualSpeed = manSpeed;
        // manualspeed here is set equal to manspeed, which is used when calling initalize

        motors = new ArrayList<Motor>(motorList);
        // the array list of motors here is set to recieve the data from the motorlist array list
    }

    public void execute()
    {
        double speed = (manualSpeed.getVoltage() / 5.0) - 0.5;
        // the math takes the voltage recieved, divides it by 5, then subtracts 0.5
        // by implementing this, our maximum speed is half of what the actual maximum can be
        // to get to true maximum speed, the following line would give us 1 and -1
        // motors[motorIndex].set((manualSpeed.getVoltage()/2.5)-1);
        if(manualSpeed != null)
        {
            motors.get(motorIndex).set(speed);
            // this would go to the array list, get the motor currently indexed, and then set the speed of that motor
        }
        if(!continuousPress)
        // this will only run if continuous press is false, meaning the buttons are not held down
        {
            if(motorUp.get())
            // this runs if the motorup button is pressed
            {
                motors.get(motorIndex).set(0);
                // this would set the speed of any motors currently running to zero, as a precaution
                if(motorIndex == motors.size() - 1)
                // if its at the maximum motor index possible to reset to motor index 0 with the press
               // it just make sure the motors index in a loop so that we're not forced to only go one way and then back
                {
                    motorIndex = 0;
                }
                else
                    // otherwise, if any other motor is indexed, then with each press it just indexes one up
                {
                    motorIndex += 1;
                }
                System.out.println("New motor index is " + motorIndex + " up");
                // allows us to view in the console when the motors are indexing, and to which motor
                continuousPress = true;
                // setting continuous press to true here allows the code to recognize its not free to index again
                // TODO: check to see if this is actually correct
            }
            else if(motorDown.get())
            // the else if allows only one commmand to run at once, even if both buttons are pressed at the same time
            {
                motors.get(motorIndex).set(0);
                // this would set the speed of any motors currently running to zero, as a precaution
                if(motorIndex == 0)
                // if its at the minimum motor index possible to reset to the highest motor index with a press
                // it's just make sure the motors index in a loop so that we're not forced to only go one way and then back
                {
                    motorIndex = (motors.size() - 1);
                }
                else
                    // otherwise, it will index one motor down with each press
                {
                    motorIndex -= 1;
                }
                System.out.println("New motor index is " + motorIndex);
                // allows us to view in the console when the motors are indexing, and to which motor
                continuousPress = true;
                // setting continuous press to true here allows the code to recognize its not free to index again
                // TODO: check to see if this is actually correct
            }
        }
        else
            // if continuous press is registered as true
        {
            if(!((motorUp.get()) || motorDown.get()))
            // either motorup or motordown are not pressed
                // thomas brought truth tables into this, to figure out whether it would be an and statment or an or statement
            {
                System.out.println("Button released");
                // this registers in the console, allowing us to view when the button itself is released
                // can alert to problems if it is not showing up/registering
                continuousPress = false;
                // continuous press is set to false if neither of the buttons are pressed
            }
        }
    }

    public void end()
    {
        motors.get(motorIndex).set(0);
        // this would set the motor currently indexed to the speed of zero
        for(int i = 0; i < 16; i++)
        // this would cycle through all 16 motors
        {
            motors.get(motorIndex).set(0);
            // sets any and all motors to the speed of zero
        }
    }

    protected boolean isFinished()
            // is finished should never be called in this class
    {
        return false;
    }

    public int getMotorIndex()
            // used for unit testing purposes, not actually called in class
    {
        return motorIndex;
    }

    public double getSpeed()
            // used for unit testing purposes, not actually called in class
    {
        return ((manualSpeed.getVoltage() / 5) - 0.5);
    }

}
