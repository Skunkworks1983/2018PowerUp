package frc.team1983.commands.debugging;

import edu.wpi.first.wpilibj.command.Command;

public class TestPrint extends Command
{
    private String message;

    public TestPrint(String message)
    {
        this.message = message;
    }

    @Override
    public void initialize()
    {
        System.out.println(message);
    }

    @Override
    protected boolean isFinished()
    {
        return true;
    }
}
