package frc.team1983.utility.sensors.psoc;

//The enum that holds the position of each sensors data in the returned PSoC packet
//Reminder that it is zero indexed, and unused sensors should be commented out
public enum PSoCSensors {
    POTENTIOMETER(0),
    RANGEFINDER(1);

    public final int posInPacket;
    PSoCSensors(int posInPacket)
    {
        this.posInPacket = posInPacket;
    }
}
