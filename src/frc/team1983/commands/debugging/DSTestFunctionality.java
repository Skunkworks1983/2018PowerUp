package frc.team1983.commands.debugging;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.subsystems.utilities.Motor;

public class DSTestFunctionality
{
    private int motorIndex = 0;
    private Motor motors[] = new Motor [1];
    // there is only one space in the motor array, since we are only running one motor at a time
    // on 2017, motor 6 is the collector belt
    private DigitalInput motorSelector;
    private AnalogInput manualSpeed;
    private DigitalInput motorSelectorDown;

    public DSTestFunctionality()
    {
        int port = 6;
       /* motorSelector = new DigitalInput(5);
        manualSpeed = new AnalogInput(2);
        motorSelectorDown = new DigitalInput(4);
        // this sets the motor to motor port already stated automatically
        this.motorSelector = motorSelector;
        this.manualSpeed = manualSpeed;
        this.motorSelectorDown = motorSelectorDown;
        for(int i=0; i<16; i++) */
        {
            motors[motorIndex] = new Motor (port, NeutralMode.Coast,true);
            // the motor indexed here is given the constraints required
           // motorSelector = new DigitalInput(5);
           manualSpeed = new AnalogInput(2);
        }
    }
    public void initialize()
    {
      //nothing
        //motors[motorIndex].set(0.25);
        for(int i=0; i<16;)
        {
            motors[i].setNeutralMode(NeutralMode.Coast);
        }
    }
    public void execute()
    {
        // this sets the speed of the selected motor to half speed automatically
       // motors[motorIndex].set(0.5);
       /* if (motorSelector != null)
        {
            if (motorSelector.get()) {
                motors[motorIndex].set(0);
            } else {
                motors[motorIndex].set(0.5);
            }
        } */
        if (manualSpeed != null)
        {
            //motors[motorIndex].set((manualSpeed.getVoltage()/2.5)-1);
            motors[motorIndex].set((manualSpeed.getVoltage()/5)-0.5);
            // this would set the maximum speed to half speed
        }
    }
    public void end()
    {
        // if this were called within the test, then the motor would be turned off
        // since this is currently not called, in order to shut off the motor you need to disable it
        motors[motorIndex].set(0);
    }
    protected boolean isFinished()
    {
        return false;
    }
}
