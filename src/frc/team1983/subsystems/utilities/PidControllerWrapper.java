package frc.team1983.subsystems.utilities;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.settings.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*Class that wraps the PIDController class provided by WpiLib. Not used for any extra functionality yet, but should be
  the default implementation in our codebase.
*/
public class PidControllerWrapper extends PIDController
{
    private double[] newValues;
    private double[] oldValues = {0,0,0,0};
    private double setpointDelta, error;

    private PrintWriter outputStream;
    private String thisName;
    private DateFormat dateFormat;
    private Date date;

    public PidControllerWrapper(double p, double i, double d, double f, PIDSource source, PIDOutput output)
    {
        super(p, i, d, f, source, output);
        this.initFile();
        newValues = new double[4];
    }

    public PidControllerWrapper(double p, double i, double d, PIDSource source, PIDOutput output)
    {
        this(p, i, d, 0.0D, source, output); //0.0D is what wpilib passes itself as f anyways.
    }

    public void initFile()
    {
        dateFormat = new SimpleDateFormat("yyyy/MM/dd:HH:mm:ss");
        date = new Date();
        thisName = this.getClass().getSimpleName();

        try
        {
            outputStream = new PrintWriter(new FileWriter(
                    new File("/home/lvuser/" + thisName + ":" + dateFormat.format(date) + ".txt")));
            System.out.println("Opened outputStream for " + thisName);
            outputStream.println("PIDF Log for " + thisName);
        }
        catch (IOException e)
        {
            System.out.println("IO exception when creating outputstream for " + thisName);
            System.out.println("Exception was " + e);
        }
    }

    public void writeToFile()
    {
        outputStream.println("---------------------------");

        newValues[0] = this.getP();
        newValues[1] = this.getI();
        newValues[2] = this.getD();
        newValues[3] = this.getF();
        setpointDelta = this.getDeltaSetpoint();
        error = this.getError();

        for(int i = 0; i < newValues.length; i++)
        {
            if(newValues[i] != oldValues[i])
            {
                outputStream.println("Constant " + Constants.PidConstants.PIDLETTERS[i]
                                             + " changed to " + String.valueOf(newValues[i]));
                oldValues[i] = newValues[i];
            }
        }

        if(setpointDelta != 0)
        {
            outputStream.println("The setpoint changed by " + setpointDelta);
        }

        outputStream.println("The error is " + String.valueOf(error));
    }

    public void closeFile()
    {
        outputStream.close();
        System.out.println("Closed outputstream for " + thisName);
    }
}
