package frc.team1983.subsystems.sensors;

import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.command.Scheduler;

public class PSoC
{
    public static SPI SensorDaq;



    public PSoC()
    {
        SensorDaq = new SPI(SPI.Port.kOnboardCS0);
        initSPISensor(SensorDaq);
    }

    public static void initSPISensor(SPI SensorDaq)
    {
        SensorDaq.setClockRate(1000000);      // other tested spds also worked without changes to slave
        SensorDaq.setChipSelectActiveLow();  // Low
        SensorDaq.setMSBFirst();             // msb
        SensorDaq.setSampleDataOnFalling();  // falling
        SensorDaq.setClockActiveLow();      //  Low
    }

    public int[] getSensorValue(byte[] DaqOutputBuffer, byte[] DaqInputBuffer)
    {
        // local variables to decode data packet (DaqInputBuffer).
        // Packet {Data0HB, Data0LB, Data1HB, Data1LB, Data2HB, Data2LB, Data3HB, Data3LB, KEY, Spare, Checksum1, Checksum2}
        int SensorVal[]={0,0,0,0};
        int HiByte=0;
        int LoByte=0;
        int keyBit=0;
        int keyBit2=0;
        int index, sum1=0, sum2=0;

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

        System.out.println("Input");
        for(byte b: DaqInputBuffer)
        {
            System.out.println(b);
        }

        if (sum1==DaqInputBuffer[10]  &&  sum2==DaqInputBuffer[9])
        {
            System.out.println();
            System.out.println("Checksum Match  *******************************************************");
            // decode 8 bytes using key byte as 9th bit of each byte. assemble as 4 integers (0-65535).
            HiByte = DaqInputBuffer[0];
            keyBit = (DaqInputBuffer[8] & 0x01) >> 0;
            HiByte = keyBit == 0x01 ? HiByte << 7 : HiByte; //equal to HiByte << 7 if keyBit == 1

            LoByte = DaqInputBuffer[1];
            keyBit = (DaqInputBuffer[8] & 0x02) >> 1;
            LoByte = LoByte + (keyBit * 128);

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

