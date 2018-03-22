package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Robot;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.motion.MotionProfile;
import org.apache.logging.log4j.core.Logger;

//The elevator subsystem
public class Elevator extends Subsystem
{

    private Motor right1, right2;
    private Motor left1, left2;
    private Constants.ElevatorSetpoints setpoint;

    private Logger logger;

    public Elevator()
    {
        logger = LoggerFactory.createNewLogger(Elevator.class);

        left1 = new Motor(Constants.MotorMap.Elevator.LEFT1, Constants.MotorMap.Elevator.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.MotorMap.Elevator.LEFT2, Constants.MotorMap.Elevator.LEFT2_REVERSED);

        right1 = new Motor(Constants.MotorMap.Elevator.RIGHT1, Constants.MotorMap.Elevator.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.MotorMap.Elevator.RIGHT2, Constants.MotorMap.Elevator.RIGHT2_REVERSED);

        right1.configPIDF(0, Constants.PidConstants.ElevatorControlPid.Slot0.P, Constants.PidConstants.ElevatorControlPid.Slot0.I, Constants.PidConstants.ElevatorControlPid.Slot0.D, Constants.PidConstants.ElevatorControlPid.Slot0.F);
        right1.config_IntegralZone(0, Constants.PidConstants.ElevatorControlPid.Slot0.I_ZONE, 0);
        right1.configAllowableClosedloopError(0, Constants.PidConstants.ElevatorControlPid.Slot0.ALLOWABLE_CLOSED_LOOP_ERROR, 1);

        right1.configPIDF(1, Constants.PidConstants.ElevatorControlPid.Slot1.P, Constants.PidConstants.ElevatorControlPid.Slot1.I, Constants.PidConstants.ElevatorControlPid.Slot1.D, Constants.PidConstants.ElevatorControlPid.Slot1.F);
        right1.config_IntegralZone(1, Constants.PidConstants.ElevatorControlPid.Slot1.I_ZONE, 0);
        right1.configAllowableClosedloopError(1, Constants.PidConstants.ElevatorControlPid.Slot1.ALLOWABLE_CLOSED_LOOP_ERROR, 1);

        right1.configPeakOutputForward(1, 0);
        right1.configPeakOutputReverse(-1, 0);

        right1.configClosedloopRamp(.5, 0);

        right2.follow(right1);
        left1.follow(right1);
        left2.follow(right1);

        right1.configClosedloopRamp(.5, 0);

        right1.setSensorPhase(true);

        right1.setSelectedSensorPosition(0, 0, 100);
    }

    public void initDefaultCommand()
    {

    }

    public static double getFeet(double ticks)
    {
        return 0;
    }

    public static double getTicks(double feet)
    {
        return 0;
    }

    public void set(ControlMode mode, double value)
    {
        right1.set(mode, value);
    }

    public double getError()
    {
        return right1.getClosedLoopError(0);
    }

    public double getEncoderValue()
    {
        return right1.getSelectedSensorPosition(0);
    }

    public void setProfile(MotionProfile profile)
    {
        right1.setProfile(profile);
    }

    public void runProfile()
    {
        right1.runProfile();
    }

    public void stopProfile()
    {
        right1.stopProfile();
    }

    public boolean isProfileFinished()
    {
        return left1.isProfileFinished();
    }

    public Constants.ElevatorSetpoints getSetpoint()
    {
        return setpoint;
    }

    public void switchSlots(boolean goingUp)
    {
        if(goingUp == true)
        {
            right1.selectProfileSlot(0, 0);
            logger.info("slot 0");
        }
        else
        {
            right1.selectProfileSlot(1, 0);
            logger.info("slot 1");
        }
    }

    public void setWantedState(Constants.ElevatorSetpoints setpoint)
    {
        this.setpoint = setpoint;
    }

    public void setSensorPhase(boolean bool)
    {
        right1.setSensorPhase(bool);
    }

    @Override
    public void periodic()
    {
        //logger.debug("Error1: {}\tSetpoint: {}", right1.getClosedLoopError(0), right1.getClosedLoopTarget(0));

        System.out.println("periodic");
        System.out.println("Setpoint: " + getSetpoint() + " collector: " + Robot.getInstance().isCollectorUp());
        if(!(getSetpoint() != Constants.ElevatorSetpoints.BOTTOM && setpoint != Constants.ElevatorSetpoints.TRAVEL
            && Robot.getInstance().isCollectorUp()))
        {
            System.out.println("going");
            setSensorPhase(true);
            set(ControlMode.Position, getSetpoint().getEncoderTicks());
        }
        else
        {
            System.out.println("not going");
            //logger.debug("Elevator setpoint was high and collector was up, going to travel");
            setSensorPhase(true);
            set(ControlMode.Position, Constants.ElevatorSetpoints.TRAVEL.getEncoderTicks());
        }
    }

    public double getCurrentDraw()
    {
        return (right1.getOutputCurrent() + right2.getOutputCurrent())/2;
    }
}
