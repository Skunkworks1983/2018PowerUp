package frc.team1983.subsystems.utilities.inputwrappers;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public abstract class PidInputWrapper implements PIDSource
{
    //Most common type of source type, so have it be the default
    PIDSourceType sourceType = PIDSourceType.kDisplacement;

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        sourceType = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return sourceType;
    }

    public abstract double pidGet();
}
