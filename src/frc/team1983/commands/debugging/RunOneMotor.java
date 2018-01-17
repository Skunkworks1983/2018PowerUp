package frc.team1983.commands.debugging;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.subsystems.utilities.Motor;

public class RunOneMotor extends Command {
   private boolean continuousPress = false;
   private int motorIndex = 0;

   private Motor motors[]= new Motor[16];

   public RunOneMotor(){
       for(int i = 0; i<16; i++){
           motors[i] = new Motor (i, NeutralMode.Coast,  false);
//           Motor m = new Motor (i, NeutralMode.Coast, false);
//           motors[i] = m;
       }
   }

   protected void initialize(){
// leave blank??? most happens within constructor
   }

   protected void execute(){
       //motors[i].set(???){
        if(!continuousPress){
            if(getLeftTriggerPressed){ //TODO: assign left trigger, and the functionality while pressed
                if(motorIndex == 0){
                    motorIndex = 15;
                }
                else {
                    motorIndex -= 1;
                }
                continuousPress = true; //TODO: define what a continuous press is and what it does
       }
       else if (getRightTriggerPressed){ //TODO: assign right trigger, and functionality while pressed
                if (motorIndex == 15){
                    motorIndex = 0;
                }
                else {
                    motorIndex += 1;
                }
                continuousPress = true;
            }
   }
   else {
            continuousPress = false;
        }
        if (button2Pushed){ //TODO: assign button value and funtionality (aprt from retrieving data)
            motorIndex(getPosition());
        }
   float joy = getLeftStickY(); //TODO: assign left stick value, and assign Y axis of joystick
   if (joy > - 0.05 && joy < 0.05) {
       joy = )
   }
   motorIndex (motorIndex) = setOutput (joy); //TODO: get output and joystick values from OI
}
boolean isFinished (){
       return false;
   }
   void End (){
       // TODO: figure out what goes on in here
    }
    void Interrupted (){
       //TODO: find out what goes in here
    }
}
