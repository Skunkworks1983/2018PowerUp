package org.usfirst.frc.team1983.robot.subsystems.utilities;
import edu.wpi.first.wpilibj.Servo;
//allows us to use servos, but also implement new methods
public class ServoWrapper extends Servo
{
    public ServoWrapper(int port) {
        super(port);
    }
}