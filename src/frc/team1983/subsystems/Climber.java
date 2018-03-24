package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.sensors.DigitalInputWrapper;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.ServoWrapper;
import frc.team1983.subsystems.utilities.climberexceptions.HookNotEngagedException;
import frc.team1983.subsystems.utilities.climberexceptions.NoTensionException;

public class Climber extends Subsystem
{
    private ServoWrapper leftForkLock, rightForkLock, camLeft, camRight;
    private boolean hookEngaged, haveTension, dogGearEngaged;
    private Motor tensionMotor;
    private DigitalInputWrapper leftCamSwitch, rightCamSwitch;

    private Motor hook;

    //TODO look into alerts for limit switches
    //drop anytime
    //hook
    //then tension
    //then engage dog gear

    public Climber()
    {
        leftForkLock = new ServoWrapper(Constants.MotorMap.Climber.DROP_SERVO_LEFT);
        rightForkLock = new ServoWrapper(Constants.MotorMap.Climber.DROP_SERVO_RIGHT);

        camLeft = new ServoWrapper(Constants.MotorMap.Climber.CAM_SERVO_LEFT);
        camRight = new ServoWrapper(Constants.MotorMap.Climber.CAM_SERVO_RIGHT);

        hook = new Motor(Constants.MotorMap.Climber.HOOK_MOTOR, false);

        leftCamSwitch = new DigitalInputWrapper(Constants.SensorMap.Collector.LEFT_CAM_SWITCH,
                                                Constants.SensorMap.Collector.LEFT_CAM_REVERSED);
        rightCamSwitch = new DigitalInputWrapper(Constants.SensorMap.Collector.RIGHT_CAM_SWITCH,
                                                 Constants.SensorMap.Collector.RIGHT_CAM_REVERSED);

        tensionMotor = new Motor(Constants.MotorMap.Climber.TENSION_MOTOR, Constants.MotorMap.Climber.TENSION_MOTOR_REVERSED);

        hookEngaged = false;
        dogGearEngaged = false;
    }

    public void lockForks()
    {
        leftForkLock.set(0.65);
        rightForkLock.set(1);
    }

    public void unlockForks()
    {
        leftForkLock.set(0.90);
        rightForkLock.set(0.75);
    }

    public void hook()
    {
        //Determined experimentally
        hook.set(ControlMode.PercentOutput, 0.2);
        hookEngaged = true;
    }

    public void unhook()
    {
        hook.set(ControlMode.PercentOutput, -0.2);
    }

    public void stopHooking()
    {
        hook.set(ControlMode.PercentOutput, 0);
    }

    public void setTensionMotor(double speed)// throws HookNotEngagedException
    {/*
        if(!hookEngaged)
        {
            throw new HookNotEngagedException();
        }*/
        tensionMotor.set(ControlMode.PercentOutput, speed);
    }

    public void stopTensionMotor()
    {
        tensionMotor.set(ControlMode.PercentOutput, 0);
    }

    public double getTensionCurrent()
    {
        //free current 1.8 amps
        //stall current 41 amps
        return tensionMotor.getOutputCurrent();
    }

    public void confirmTensionCreation()
    {
        haveTension = true;
    }

    public boolean haveTension()
    {
        return haveTension;
    }

    public void engageDogGear() //throws HookNotEngagedException, NoTensionException
    {/*
        if(!hookEngaged)
        {
            throw new HookNotEngagedException();
        }
        if(!haveTension)
        {
            throw new NoTensionException();
        }*/
        camLeft.set(1.0);
        camRight.set(0.0);

        hook.set(ControlMode.PercentOutput, 0);
    }

    public void disengageDogGear()
    {
        camLeft.set(0.2);
        camRight.set(0.8);
    }

    public void initDefaultCommand()
    {

    }

    public boolean getCamSwitch(boolean left)
    {
        if(left)
        {
            return leftCamSwitch.get();
        }
        else
        {
            return rightCamSwitch.get();
        }
    }

    /*public double getHookPosition()
    {
        return hook0.getPosition();
    }*/
}
