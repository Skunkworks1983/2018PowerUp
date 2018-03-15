package frc.team1983.subsystems.sensors;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import frc.team1983.settings.Constants.SensorMap.GyroConstants;

//Class designed for the NavX-MXP that we use on the robot. We only ever need to get the Y axis ("yaw" of the robot),
//so that's the only exposed method.
public class Gyro extends AHRS
{
    private boolean isDead;
    private boolean isBigGyro; //true is big navx, false is lil navx

    public Gyro(SPI.Port port)
    {
        super(port);
        isDead = false;
        isBigGyro = true;
    }

    public Gyro(I2C.Port port)
    {
        super(port);
        isDead = false;
        isBigGyro = false;
    }

    //Iterates to ensure that the gyro is initializing correctly, otherwise let other commands know that the gyro doesn't work.
    public void checkGyroStatus()
    {
        if(isBigGyro)
        {
            int counter = 0;
            while(!isConnected())
            {
                counter++;
                if(counter > GyroConstants.IS_CONNECTED_TIMEOUT)
                {
                    isDead = true;
                    break;
                }
            }

            counter = 0;
            while(!isCalibrating())
            {
                counter++;
                if(counter > GyroConstants.IS_CALIBRATING_TIMEOUT) //TODO: figure out if this works
                {
                    isDead = true;
                    break;
                }
            }

        }
        else
        {
            float initialYaw = getYaw();
            isDead = true;
            for(int counter = 0; counter < 10; counter++)
            {
                try
                {
                    Thread.sleep(1);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                if(getYaw() != initialYaw)
                {
                    isDead = false;
                    break;
                }
            }
        }
        if(getRawGyroY() > 5000 || getRawGyroY() < -5000)
        {
            isDead = true;
        }
        System.out.println("Is the gyro alive? : " + !isDead);
    }

    public boolean isDead()
    {
        return false;
    }
}
