package frc.team1983.utility.sensors.psoc;

import edu.wpi.first.wpilibj.SPI;

//This class does not represent the PSoC, but instead is the thread that we use to interact with the PSoC
public class PSoC implements Runnable
{
    private static SPI SensorDaq; //The SPI port
    private int[] sensorData; //The decoded data from the psoc
    private byte[] sentBytes = {1,7,23,0,1,1,0,0,0,0,0,0}; //The bytes we send to the psoc for a transaction


    public PSoC()
    {
        SensorDaq = new SPI(SPI.Port.kOnboardCS0);
        initSPISensor(SensorDaq);
    }

    //The method called every time the thread runs
    @Override
    public void run() {
        sensorData = doTransaction(sentBytes);
    }

    public synchronized void setSentBytes(byte[] newSentBytes)
    {
        if(sentBytes.length != 12)
        {
            System.out.println("WARNING: Attempted to set new sent bytes value, but given value was not 12 bytes");
        }
    }

    public synchronized int getSensor(PSoCSensors sensor)
    {
        return sensorData[sensor.posInPacket];
    }

    public synchronized int[] getSensorData()
    {
        return sensorData.clone(); //we clone for thread-safety
    }

    //Init the SPI port on the RoboRIO
    private static void initSPISensor(SPI SensorDaq)
    {
        SensorDaq.setClockRate(1000000);      // other tested spds also worked without changes to slave
        SensorDaq.setChipSelectActiveLow();  // Low
        SensorDaq.setMSBFirst();             // msb
        SensorDaq.setSampleDataOnFalling();  // falling
        SensorDaq.setClockActiveLow();      //  Low
    }

    /**
     * Does a transaction with the psoc
     * @param DaqOutputBuffer a twelve byte array to send to the psoc.
     * @return the decoded packet from the psoc, is 4 ints in an array
     */
    private int[] doTransaction(byte[] DaqOutputBuffer)
    {
        // local variables to decode data packet (DaqInputBuffer).
        // Packet {Data0HB, Data0LB, Data1HB, Data1LB, Data2HB, Data2LB, Data3HB, Data3LB, KEY, Spare, Checksum1, Checksum2}
        int SensorVal[]={0,0,0,0};
        int HiByte=0;
        int LoByte=0;
        int keyBit=0;
        int keyBit2=0;
        int index, sum1=0, sum2=0;
        byte[] DaqInputBuffer = {0,0,0,0,0,0,0,0,0,0,0,0};

        try
        {
            PSoC.SensorDaq.transaction(DaqOutputBuffer, DaqInputBuffer, 12);
        }
        catch(Exception e)
        {
            System.out.println("ERROR IN SPI TRANSACTION: " + e);
        }

        // Fletchers 16bit checksum algorithm
        for( index = 0; index < 8; ++index )
        {
            sum1 = (sum1 + DaqInputBuffer[index]) % 128; // sum of all bytes
            sum2 = (sum2 + sum1) % 128;       // sum of all bytes plus sum of check byte
        }

        if (sum1==DaqInputBuffer[10]  &&  sum2==DaqInputBuffer[9])
        {
            // decode 8 bytes using key byte as 9th bit of each byte. assemble as 4 integers (0-65535).
            HiByte = DaqInputBuffer[0];
            keyBit = (DaqInputBuffer[8] & 0x01) >> 0;
            HiByte = HiByte + (keyBit << 7);

            LoByte = DaqInputBuffer[1];
            keyBit = (DaqInputBuffer[8] & 0x02) >> 1;
            LoByte = LoByte + (keyBit << 7);

            SensorVal[0] = (HiByte << 8) | LoByte;

            HiByte = DaqInputBuffer[2];
            keyBit = (DaqInputBuffer[8] & 0x04) >> 2;
            HiByte = HiByte + (keyBit * 128);

            LoByte = DaqInputBuffer[3];
            keyBit = (DaqInputBuffer[8] & 0x08) >> 3;
            LoByte = LoByte + (keyBit * 128);

            SensorVal[1] = (HiByte << 8) | LoByte;

            HiByte = DaqInputBuffer[4];
            keyBit = (DaqInputBuffer[8] & 0x16) >> 4;
            HiByte = HiByte + (keyBit * 128);

            LoByte = DaqInputBuffer[5];
            keyBit = (DaqInputBuffer[8] & 0x32) >> 5;
            LoByte = LoByte + (keyBit * 128);

            SensorVal[2] = (HiByte << 8) | LoByte;

            HiByte = DaqInputBuffer[6];
            keyBit = (DaqInputBuffer[8] & 0x64) >> 6;
            HiByte = HiByte + (keyBit * 128);

            LoByte = DaqInputBuffer[7];
            keyBit = (DaqInputBuffer[8] & 0x128) >> 7;
            keyBit = (keyBit > 1) ? 1 : keyBit ;    // if keyBit is greater than 1, keyBit=1 else keyBit=keyBit
            LoByte = LoByte + (keyBit * 128);
            SensorVal[3] = (HiByte << 8) | LoByte;
        }
        return SensorVal;
    }
}

