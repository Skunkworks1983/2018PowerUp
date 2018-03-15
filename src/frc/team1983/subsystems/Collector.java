package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.sensors.DigitalInputWrapper;
import frc.team1983.subsystems.utilities.Motor;
import org.apache.logging.log4j.core.Logger;

//Subsystem that will acquire and expel the Power Cubes.
public class Collector extends Subsystem
{
    private Motor left, right;
    private Motor rotate;
    private double setpoint;
    private DigitalInputWrapper leftSwitch, rightSwitch;
    //The collector subsystem
    private Logger logger;

    public Collector()
    {
        left = new Motor(Constants.MotorMap.Collector.LEFT, Constants.MotorMap.Collector.LEFT_REVERSED);
        right = new Motor(Constants.MotorMap.Collector.RIGHT, Constants.MotorMap.Collector.RIGHT_REVERSED);
        rotate = new Motor(Constants.MotorMap.Collector.ROTATE, Constants.MotorMap.Collector.ROTATE_REVERSED, true);

        left.setNeutralMode(NeutralMode.Brake);
        right.setNeutralMode(NeutralMode.Brake);
        rotate.setNeutralMode(NeutralMode.Brake);

        leftSwitch = new DigitalInputWrapper(Constants.MotorMap.Collector.LEFT_SWITCH, Constants.MotorMap.Collector.LEFT_SWITCH_REVERSED);
        rightSwitch = new DigitalInputWrapper(Constants.MotorMap.Collector.RIGHT_SWITCH, Constants.MotorMap.Collector.RIGHT_SWITCH_REVERSED);

        rotate.config_kP(0, Constants.PidConstants.CollectorRotate.P, 0);
        rotate.config_kI(0, Constants.PidConstants.CollectorRotate.I, 0);
        rotate.config_kD(0, Constants.PidConstants.CollectorRotate.D, 0);
        rotate.config_kF(0, Constants.PidConstants.CollectorRotate.F, 0);

        rotate.configClosedloopRamp(0.25, 0);
        rotate.setSensorPhase(true);
        rotate.configPeakOutputForward(0.75, 0);

        rotate.selectProfileSlot(0, 0);
        logger = LoggerFactory.createNewLogger(Collector.class);
    }

    public void initDefaultCommand()
    {
    }

    public void setLeft(ControlMode mode, double value)
    {
        left.set(mode, value);
    }

    public void setRight(ControlMode mode, double value)
    {
        right.set(mode, value);
    }

    public void setRotate(ControlMode mode, double value)
    {
        rotate.set(mode, value);
    }

    public boolean isLeftSwitchDown()
    {
        return leftSwitch.get();
    }

    public boolean isRightSwitchDown()
    {
        return rightSwitch.get();
    }

    public double getPosition()
    {
        return rotate.getSelectedSensorPosition(0);
    }

    @Override
    public void periodic()
    {
        //logger.trace("Collector pos: {}", getPosition());
        //logger.trace("Collector error: {}", rotate.getClosedLoopError(0));
    }
}
