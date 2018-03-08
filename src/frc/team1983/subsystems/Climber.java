package frc.team1983.subsystems;


import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.ServoWrapper;

public class Climber extends Subsystem {
    private ServoWrapper stabilizer;
    private ServoWrapper magicJacks;
    private ServoWrapper camLeft, camRight;
    private ServoWrapper hooks;
    private Motor motor;

    public Climber(){
        stabilizer = new ServoWrapper(Constants.MotorMap.Climber.STABILIZER);

        magicJacks = new ServoWrapper(Constants.MotorMap.Climber.MAGIC_JACKS);
        motor = new Motor(Constants.MotorMap.Climber.CLIMBER_MOTOR, Constants.MotorMap.Climber.CLIMBER_MOTOR_REVERSED); // Not sure if this is going to be used

        camLeft = new ServoWrapper(Constants.MotorMap.Climber.CAM_LEFT);
        camRight = new ServoWrapper(Constants.MotorMap.Climber.CAM_RIGHT);

        hooks = new ServoWrapper(Constants.MotorMap.Climber.HOOKS);
    }

    public void stabilize(){
        stabilizer.set(1);
    }

    public void destabilize(){
        stabilizer.set(0);
    }

    public void jackUp(){
        magicJacks.set(1); // applejacks
    }

    public void jackDown(){
        magicJacks.set(0);
    }

    public void engage(){
        camLeft.set(1);
        camRight.set(1);
    }

    public void disengage(){
        camLeft.set(0);
        camRight.set(0);
    }

    public void deployHooks(){
        hooks.set(1);
    }

    public void undeployHooks(){
        hooks.set(0);
    }


    public void initDefaultCommand() {

    }
}

