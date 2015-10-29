package portaudiotest;


import starter.error;

import com.portaudio.DeviceInfo;
import com.portaudio.HostApiInfo;
import com.portaudio.PortAudio;

public class PALoopTest
{
	//private static final AudioFormat af = new AudioFormat((float) 48000.0, 16, 2, true, false);
	private static final int BUFFER_SIZE = 48000 * 4;
	private static final byte[] sbIn = new byte[BUFFER_SIZE];
	private static final byte[] sbOut = new byte[BUFFER_SIZE];

	private static final int READSIZE = 480*4;
	private static final int READCOUNT = 1000;

	private static final byte[] sbMove = new byte[READSIZE];
	
	private long startTime;

	public PALoopTest()
	{
		PortAudio.initialize();
		int dCount = PortAudio.getDeviceCount();
		error.log("PA device count : " + dCount );
		for(int i = 0; i < dCount; i++)
		{
			DeviceInfo dInfo = PortAudio.getDeviceInfo(i);
			error.log("Name : " + dInfo.name );
		}
		int hCount = PortAudio.getHostApiCount();
		error.log("Host api count : " + hCount );
		for(int i = 0; i < hCount; i++)
		{
			HostApiInfo hInfo = PortAudio.getHostApiInfo(i);
			error.log("Name : " + hInfo.name );
		}
		PortAudio.terminate();
		/*
		SourceDataLine sdl = Central.getGoutputSelector().getSourceDataLine();
		TargetDataLine tdl = Central.getGinputSelector().getTargetDataLine();

		int[] rv = new int[READCOUNT];
		String[] tv = new String[READCOUNT];

		try
		{
			tdl.open(af, BUFFER_SIZE);
			sdl.open(af, BUFFER_SIZE);
			
			sdl.start();
			tdl.start();
			for(int i = 0; i < READCOUNT; i++)
			{
				rv[i] = tdl.read(sbMove, 0, READSIZE);
				tv[i] = t();
				sdl.write(sbMove, 0, READSIZE);
			}
			try
			{
				Thread.sleep(1000L);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			tdl.close();
			sdl.close();

		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
		error.log("Loop test done!");
		*/
	}
	String t()
	{
		return Long.toString( System.currentTimeMillis() - startTime);
	}

}
