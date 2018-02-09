package frc.team1983.commands.debugging;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import edu.wpi.first.wpilibj.Joystick;
import frc.team1983.subsystems.utilities.Motor;

/* public class DSJoystick {
    private boolean continuousPress = false;
    private int motorIndex = 0;
    private Motor motors[] = new Motor[16];
    private Joystick joy;

    public DSJoystick()
    {
        joy = new Joystick(0);
        for(int i = 0; i<16; i++)
        {
            motors[i] = new Motor(i, NeutralMode.Coast, false);
        }
    }

    public void initialize()
    {
        for(int i = 0; i < 16; i++)
        {
            motors[i].setNeutralMode(NeutralMode.Coast);
            System.out.println("Initialized motor" + i);
        }
    }

    public void execute()
    {
        motors[motorIndex].set(joy);
    }
} */
