package frc.team1983.commands.debugging;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.services.OI;
import frc.team1983.subsystems.utilities.Motor;

public class RunOneMotor extends Command
{
    private boolean continuousPress = false;
    private int motorIndex = 0;
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

    protected void execute()
    {
        //motors[i].set(???){
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
                continuousPress = true;
            }
            // each press goes to the motor above, 1 at a time
            else if(oi.isPressed(1, 1))
            {
               //slects motor 0 if it was set to motor 15
                System.out.println("motor up");
                if(motorIndex == 15)
                {
                    motorIndex = 0;
                }
                else
                {
                    motorIndex += 1;
                }
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
        //TODO: Make sure .set command is correct
    }
// there isn't a point where isfinished is assigned or called, so it is always false
    protected boolean isFinished()
    {
        return false;
    }
// motor power is set to 0 as a precaution during end
    protected void End()
    {
        motors[motorIndex].set(0);
    }
// interrupted calls end, sets motor power to 0
    protected void Interrupted()
    {
        End();
    }
}
