package frc.team1983.commands.debugging;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.services.OI;
import frc.team1983.subsystems.utilities.Motor;

public class RunOneMotor extends Command {
   private boolean continuousPress = false;
   private int motorIndex = 0;
   private OI oi;

   private Motor motors[]= new Motor[16];

   public RunOneMotor(OI oi){
       this.oi = oi;
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
            if(oi.isPressed(0,0)){ //triggers are button 0
                if(motorIndex == 0){
                    motorIndex = 15;
                }
                else {
                    motorIndex -= 1;
                }
                continuousPress = true;
       }
       else if (oi.isPressed(1,0)){
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
        if (oi.isDown(2,2)){
            System.out.println(motors[motorIndex].getSelectedSensorPosition(0));
        }
   double joy = oi.getAxis(0, 1)
           //Robot.getInstance().getOI().getLeftStickY();
   if (joy > - 0.05 && joy < 0.05) {
       joy = 0;
   }

   //find motor in array, then set output(from joystick input)
   //motors.get() = setOutput (joy);
       motors[motorIndex].set(joy);
}
protected boolean isFinished(){

       return false;
   }
 protected void End()
 {

 }
    protected void Interrupted()
    {

    }
}
