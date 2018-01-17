package org.usfirst.frc.team1983.robot.subsystems.utilities;

public class DigitalOutput extends edu.wpi.first.wpilibj.DigitalOutput
{
    /**
     * Create an instance of a digital output. Create an instance of a digital output given a
     * channel.
     *
     * @param channel the DIO channel to use for the digital output. 0-9 are on-board, 10-25 are on
     *                the MXP
     */
    public DigitalOutput(int channel)
    {
        super(channel);
    }
}
