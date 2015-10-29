package at1;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class InputTest
{
	private static final int BUFFER_SIZE = 48000 * 4;
	private static final byte[] bb = new byte[48000 * 4];
	private long startTime;
	
	private static final int READSIZE = 480*4;
	private static final int READCOUNT = 100;
	
	
	InputTest()
	{
		int[] rv = new int[READCOUNT];
		String[] tv = new String[READCOUNT];
		
		TargetDataLine tdl = Central.getGinputSelector().getTargetDataLine();
		AudioFormat af = new AudioFormat( 48000.0f, 16, 2, true, false);
		startTime = System.currentTimeMillis();
		try
		{
			tdl.open(af, 48000 * 4);
			tdl.start();
			error.log("start");
			for(int i = 0; i < READCOUNT; i++)
			{
				rv[i] = tdl.read(bb, 0, READSIZE * 4);
				tv[i] = t();
			}
			for(int i = 0; i < READCOUNT; i++)
			{
				error.log("Time : " + tv[i] + "  retVal " + rv[i] );
			}

		} catch (LineUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			tdl.close();
		}
	}
	String t()
	{
		return Long.toString( System.currentTimeMillis() - startTime);
	}
}
