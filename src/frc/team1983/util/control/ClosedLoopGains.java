package frc.team1983.util.control;

public class ClosedLoopGains
{
    private double kP, kI, kD, kF;
    private double kS, kV, kA;

    public ClosedLoopGains(double kP, double kI, double kD, double kF, double kS, double kV, double kA)
    {
        set(kP, kI, kD, kF, kS, kV, kA);
    }

    public ClosedLoopGains(double kP, double kI, double kD, double kF)
    {
        set(kP, kI, kD, kF);
    }

    public ClosedLoopGains(double kS, double kV, double kA)
    {
        set(kS, kV, kA);
    }

    public void set(double kP, double kI, double kD, double kF, double kS, double kV, double kA)
    {
        config_kP(kP);
        config_kI(kI);
        config_kD(kD);
        config_kF(kF);

        config_kS(kS);
        config_kV(kV);
        config_kA(kA);
    }

    public void set(double kP, double kI, double kD, double kF)
    {
        config_kP(kP);
        config_kI(kI);
        config_kD(kD);
        config_kF(kF);
    }

    public void set(double kS, double kV, double kA)
    {
        config_kS(kS);
        config_kV(kV);
        config_kA(kA);
    }

    public void config_kP(double kP)
    {
        this.kP = kP;
    }

    public void config_kI(double kI)
    {
        this.kI = kI;
    }

    public void config_kD(double kD)
    {
        this.kD = kD;
    }

    public void config_kF(double kF)
    {
        this.kF = kF;
    }

    public void config_kS(double kS)
    {
        this.kS = kS;
    }

    public void config_kV(double kV)
    {
        this.kV = kV;
    }

    public void config_kA(double kA)
    {
        this.kA = kA;
    }


    public double get_kP()
    {
        return kP;
    }

    public double get_kI()
    {
        return kI;
    }

    public double get_kD()
    {
        return kD;
    }

    public double get_kF()
    {
        return kF;
    }

    public double get_kS()
    {
        return kS;
    }

    public double get_kV()
    {
        return kV;
    }

    public double get_kA()
    {
        return kA;
    }
}
