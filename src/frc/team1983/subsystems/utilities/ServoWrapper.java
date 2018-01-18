package frc.team1983.subsystems.utilities;
import edu.wpi.first.wpilibj.Servo;

//Wrapper class around the WpiLib Servo, to allow for extendability in the future.
public class ServoWrapper extends Servo
{
    public ServoWrapper(int port)
    {
        super(port);
    }
}