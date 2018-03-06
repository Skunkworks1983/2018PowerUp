//package frc.team1983.commands.drivebase;
//
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.command.CommandGroup;
//import edu.wpi.first.wpilibj.command.WaitCommand;
//import frc.team1983.commands.CommandBase;
//import frc.team1983.commands.autonomous.Wait;
//import frc.team1983.subsystems.Drivebase;
//import frc.team1983.util.path.Path;
//import frc.team1983.util.path.PathComponent;
//import frc.team1983.util.path.PathTanarc;
//import frc.team1983.util.path.PathTanline;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FollowPath extends CommandGroup
//{
//    private Drivebase drivebase;
//    private Path path;
//
//    private List<CommandBase> commands;
//
//    public FollowPath(Drivebase drivebase, Path path)
//    {
//        requires(drivebase);
//
//        this.drivebase = drivebase;
//        this.path = path;
//
//        commands = new ArrayList<>();
//
//        for(int i = 0; i < path.getComponentCount(); i++)
//        {
//            PathComponent component = path.getComponent(i);
//
//            if(component instanceof PathTanarc)
//            {
//                PathTanarc arc = (PathTanarc) component;
//                commands.add(new DriveArc(drivebase, arc.getRadius(), arc.getAngle(), arc.getTime(), false));
//                commands.add(new Wait(arc.getDelay()));
//            }
//            else if(component instanceof PathTanline)
//            {
//                PathTanline line = (PathTanline) component;
//                commands.add(new DriveFeet(drivebase, line.getDistance(), line.getTime()));
//            }
//
//            if(component.getDelay() > 0)
//            {
//                commands.add(new Wait(component.getDelay()));
//            }
//        }
//
//        List<DriveProfile> driveProfiles = new ArrayList<>();
//
//        for(Command command : commands)
//        {
//            if(command instanceof DriveArc || command instanceof DriveFeet)
//            {
//                driveProfiles.add((DriveProfile) command);
//            }
//        }
//
//        DriveProfile.stitch(driveProfiles);
//
//        for(Command command : commands)
//        {
//            addSequential(command);
//        }
//    }
//
//    @Override
//    protected void initialize()
//    {
//
//    }
//
//    @Override
//    protected void execute()
//    {
//
//    }
//
//    @Override
//    protected boolean isFinished()
//    {
//        boolean allFinished = true;
//
//        for(CommandBase command : commands)
//        {
//            if(!command.isFinished())
//            {
//                allFinished = false;
//            }
//        }
//
//        return allFinished;
//    }
//
//    @Override
//    protected void interrupted()
//    {
//        end();
//    }
//
//    @Override
//    public void end()
//    {
//
//    }
//}
