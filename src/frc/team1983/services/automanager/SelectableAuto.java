package frc.team1983.services.automanager;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;

interface SelectableAuto
{
    Command createCommand(Drivebase drivebase, Collector collector, Elevator elevator,
                          DashboardWrapper dashboardWrapper, StatefulDashboard statefulDashboard,
                          AutoManager autoManager);
}