package frc.team1983.subsystems.utilities;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
Class that wraps the PIDController class provided by WpiLib. Outputs pidvalues to a csv file in the format:
error,p,i,d,f,setpoint
*/
public class PidControllerWrapper extends PIDController
{
    private double[] constantsValues;
    private double setpoint, error;

    private PrintWriter outputStream;
    private String thisName;
    private DateFormat dateFormat;
    private Date date;

    public PidControllerWrapper(double p, double i, double d, double f, PIDSource source, PIDOutput output)
    {
        super(p, i, d, f, source, output);
        this.initFile();
        constantsValues = new double[4];
    }

    public PidControllerWrapper(double p, double i, double d, PIDSource source, PIDOutput output)
    {
        this(p, i, d, 0.0D, source, output); //0.0D is what wpilib passes itself as f anyways.
    }

    private void initFile()
    {
        dateFormat = new SimpleDateFormat("MM-dd-HH-mm-ss");
        date = new Date();
        thisName = this.getClass().getSimpleName();

        try
        {
            outputStream = new PrintWriter(new FileWriter(
                    new File("/home/lvuser/" + thisName + "-" + dateFormat.format(date) + ".txt")));
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
        constantsValues[0] = this.getP();
        constantsValues[1] = this.getI();
        constantsValues[2] = this.getD();
        constantsValues[3] = this.getF();
        setpoint = this.getSetpoint();
        error = this.getError();

        outputStream.print(String.valueOf(error));

        for(double value : constantsValues)
        {
            outputStream.print("," + String.valueOf(value));
        }

        outputStream.print("," + setpoint);

        outputStream.println("");
    }

    public void closeFile()
    {
        outputStream.close();
        System.out.println("Closed outputstream for " + thisName);
    }
}
