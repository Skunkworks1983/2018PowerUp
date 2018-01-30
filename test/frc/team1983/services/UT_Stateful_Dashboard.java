package frc.team1983.services;

import edu.wpi.first.wpilibj.hal.HAL;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_Stateful_Dashboard
{
    static
    {
        HAL.initialize(500, 0);
    }

    private StatefulDashboard dashboard;
    private File tempFile;
    private ArgumentCaptor<String> keyCaptor, stringCaptor;
    private ArgumentCaptor<Boolean> booleanCaptor;
    private ArgumentCaptor<Double> doubleCaptor;
    private AtomicReference<HashMap<String, String>> dashMockMap;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Mock
    private DashboardWrapper dashboardWrapper;

    @Before
    public void setup() throws IOException
    {
        initMocks(this);


        tempFile = testFolder.newFile("testDashboardValues.txt");

        dashboard = new StatefulDashboard(dashboardWrapper, tempFile);

        dashMockMap = new AtomicReference<>(new HashMap<>());

        keyCaptor = ArgumentCaptor.forClass(String.class);
        booleanCaptor = ArgumentCaptor.forClass(Boolean.class);
        doubleCaptor = ArgumentCaptor.forClass(Double.class);
        stringCaptor = ArgumentCaptor.forClass(String.class);

        doAnswer(new Answer<Void>()
        {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                dashMockMap.get().put(keyCaptor.getValue(), booleanCaptor.getValue().toString());
                return null;
            }
        }).when(dashboardWrapper).putBoolean(keyCaptor.capture(), booleanCaptor.capture());

        doAnswer(new Answer<Void>()
        {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                dashMockMap.get().put(keyCaptor.getValue(), doubleCaptor.getValue().toString());
                return null;
            }
        }).when(dashboardWrapper).putNumber(keyCaptor.capture(), doubleCaptor.capture());

        doAnswer(new Answer<Void>()
        {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                dashMockMap.get().put(keyCaptor.getValue(), stringCaptor.getValue());
                return null;
            }
        }).when(dashboardWrapper).putString(keyCaptor.capture(), stringCaptor.capture());

        when(dashboardWrapper.getBoolean(keyCaptor.capture(), booleanCaptor.capture())).thenAnswer(new Answer<Boolean>()
        {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                return Boolean.valueOf(dashMockMap.get().get(keyCaptor.getValue()));
            }
        });

        when(dashboardWrapper.getString(keyCaptor.capture(), stringCaptor.capture())).thenAnswer(new Answer<String>()
        {
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                return dashMockMap.get().get(keyCaptor.getValue());
            }
        });

        when(dashboardWrapper.getNumber(keyCaptor.capture(), doubleCaptor.capture())).thenAnswer(new Answer<Double>()
        {
            @Override
            public Double answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                return Double.parseDouble(dashMockMap.get().get(keyCaptor.getValue()));
            }
        });

        when(dashboardWrapper.getType(keyCaptor.capture())).then(new Answer<String>()
        {
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                return dashboard.getTypeFromString(dashMockMap.get().get(keyCaptor.getValue()));
            }
        });
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void addDoesNotOverride() throws InterruptedException
    {
        dashboard.add("addtest", "double", 1.0); //add something to not be overidden
        dashboard.add("addtest", "double", 2.0); //add does not override
        simulateRioRestart();
        assertThat(dashboard.getDouble("addtest", "double"), is(1.0));
    }

    @Test
    public void addDistinguishesCommand() throws InterruptedException
    {
        dashboard.add("addtest2", "double", 3.0); //add distinguishes command
        simulateRioRestart();
        assertThat(dashboard.getDouble("addtest2", "double"), is(3.0));
    }

    @Test
    public void addDistinguishesKey() throws InterruptedException
    {
        dashboard.add("addtest", "double2", 4.0); //add distinguishes key
        simulateRioRestart();
        assertThat(dashboard.getDouble("addtest", "double2"), is(4.0));
    }

    @Test
    public void addAcceptsStrings() throws InterruptedException
    {
        dashboard.add("addtest", "string", "add test string"); //add accepts strings
        simulateRioRestart();
        assertThat(dashboard.getString("addtest", "string"), is("add test string"));
    }

    @Test
    public void addAcceptsBooleans() throws InterruptedException
    {
        dashboard.add("addtest", "boolean", true); //add accepts booleans
        simulateRioRestart();
        assertThat(dashboard.getBoolean("addtest", "boolean"), is(true));
    }

    @Test
    public void setOverwrites() throws InterruptedException
    {
        dashboard.add("settest", "string", "added"); //add something to be ovewritten
        dashboard.set("settest", "string", "set"); //set overwrites
        simulateRioRestart();
        assertThat(dashboard.getString("settest", "string"), is("set"));
    }

    @Test
    public void setDistinguishesCommand() throws InterruptedException
    {
        dashboard.set("settest2", "string", "command"); //set distinguishes command
        simulateRioRestart();
        assertThat(dashboard.getString("settest2", "string"), is("command"));
    }

    @Test
    public void setDistinguishesKey() throws InterruptedException
    {
        dashboard.set("settest", "string2", "key"); //set distinguishes key
        simulateRioRestart();
        assertThat(dashboard.getString("settest", "string2"), is("key"));
    }

    @Test
    public void setAcceptsDoubles() throws InterruptedException
    {
        dashboard.set("settest", "double", 1.0); //set accepts doubles
        simulateRioRestart();
        assertThat(dashboard.getDouble("settest", "double"), is(1.0));
    }

    @Test
    public void setAcceptsBoolean() throws InterruptedException
    {
        dashboard.set("settest", "boolean", true); //set accepts booleans
        simulateRioRestart();
        assertThat(dashboard.getBoolean("settest", "boolean"), is(true));
    }

    public void simulateRioRestart()
    {
        dashboard.store();

        //Clear
        dashboard.getKeySet().clear();
        dashMockMap.get().clear();

        dashboard.populate();
    }
}
