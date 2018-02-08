package frc.team1983.commands.debugging;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.services.OI;
import frc.team1983.subsystems.utilities.Motor;

public class RunOneMotor //extends Command
    // commenting out command is needed in order for the test to run without it complaining
{
    private boolean continuousPress = false;
    private int motorIndex = 6;
    private OI oi;

    private Motor motors[] = new Motor[16];

    public RunOneMotor(OI oi)
    {
        this.oi = oi;
        for(int i = 0; i < 16; i++)
        {
            motors[i] = new Motor(i, NeutralMode.Coast, false);

        }
    }

    protected void initialize()
    {
        System.out.println("command running");
// leave blank, most happens within constructor
    }

    public void execute()
    // normally, this would be a protected void
    // when extends command is commented out, execute becomes public
    // one of the main reasons it needs to become public is because it will be called in Robot
    {
        //motors[motorIndex].set(0.5);
        // it is necessary to comment everything below this out, otherwise it will whine
        // commenting everything under this out also gets rid of the OI necessity]
        // this sets the speed of the motor indexed to half speed

       if(!continuousPress)
        {
            //each press goes to the motor lower, 1 at a time
            if(oi.isPressed(0, 1)) //triggers are always button 0 (according to Thomas)
            {
               //selects motor 15 if it was set to motor 0
                System.out.println("motor down");
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
            // each press goes to the motor above, 1 at a time
            else if(oi.isPressed(1, 1))
            {
               //selects motor 0 if it was set to motor 15
                System.out.println("motor up");
                if(motorIndex == 15)
                {
                    motorIndex = 0;
                }
                else
                {
                    motorIndex += 1;
                }
                System.out.println("New motor index is " + motorIndex);
                continuousPress = true;
            }
        }
        // returns the false if either is not pressed, stops motors from rapidly indexing while trigger is held down
        else
        {
            if(!(oi.isPressed(0,0)||(oi.isPressed(1,0))))
            {
                continuousPress = false;
            }
        }
        // prints out the position of the motor, according to the senor attatched/connected
        if(oi.isDown(2, 3))
        {
            System.out.println(motors[motorIndex].getSelectedSensorPosition(0));
        }
        // gets the y value of the left joystick
        double joy = oi.getAxis(0, 1);
        //Robot.getInstance().getOI().getLeftStickY();
        // joystick does not move motor if there is it is very slightly off from 0
        if(joy > -0.05 && joy < 0.05)
        {
            joy = 0;
        }

        //find motor in array, then set power output(from joystick input)
        //motors.get() = setOutput (joy);
        // sets the output of the motor currently selected in accordance to the joystick
        motors[motorIndex].set(joy);

    }
// there isn't a point where isfinished is assigned or called, so it is always false
    protected boolean isFinished()
    {
        return false;
    }
// motor power is set to 0 as a precaution during end
    public void End()
    // when the class extends command, then these would be protected so there wouldn't be confusion
    // for the reasons of running this in test, execute and end need to be public
    {
        motors[motorIndex].set(0);
    }
// interrupted calls end, sets motor power to 0
    protected void Interrupted()
    {
        End();
    }
}
