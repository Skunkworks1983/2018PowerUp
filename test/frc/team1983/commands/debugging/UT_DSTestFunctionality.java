package frc.team1983.commands.debugging;


import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.hal.HAL;
import frc.team1983.subsystems.utilities.Motor;
import org.hamcrest.Matchers;
import org.hamcrest.number.IsCloseTo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UT_DSTestFunctionality
{
    static
    {
        HAL.initialize(500,0);
    }

    private DSTestFunctionality testFunct;


}
