package old;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import starter.Central;
import starter.error;

public class InputTest
{
	//private static final int BUFFER_SIZE = 48000 * 4;
	private static final byte[] bb = new byte[48000 * 4];
	private long startTime;
	
	private static final int READSIZE = 480*4;
	private static final int READCOUNT = 100;
	
	
	public InputTest()
	{
		int[] rv = new int[READCOUNT];
		String[] tv = new String[READCOUNT];
		
		TargetDataLine tdl = Central.getGinputSelector().getTargetDataLine();
		AudioFormat af = new AudioFormat( 48000.0f, 16, 2, true, false); // yes
		//AudioFormat af = new AudioFormat( 44100.0f, 16, 2, true, false); // yes
		//AudioFormat af = new AudioFormat( 44100.0f, 24, 2, true, false); // no
		try
		{
			tdl.open(af, 48000 * 4);
			tdl.start();
			error.log("start");
			startTime = System.nanoTime();
			for(int i = 0; i < READCOUNT; i++)
			{
				tdl.read(bb, 0, READSIZE);
				rv[i] = tdl.available();
				tv[i] = t();
			}
			tdl.close();
			for(int i = 0; i < READCOUNT; i++)
			{
				error.log("Time : " + tv[i] + " Value : " + rv[i] );
			}

		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
		finally
		{
			tdl.close();
		}
	}
	String t()
	{
		//return Long.toString( System.currentTimeMillis() - startTime);
		return Long.toString( (System.nanoTime() - startTime) / 1000 );
	}
}
