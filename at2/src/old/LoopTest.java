package old;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import starter.Central;
import starter.error;

public class LoopTest
{
	private static final AudioFormat af = new AudioFormat((float) 48000.0, 16, 2, true, false);
	private static final int BUFFER_SIZE = 48000 * 4;
	private static final byte[] sbIn = new byte[BUFFER_SIZE];
	private static final byte[] sbOut = new byte[BUFFER_SIZE];

	private static final int READSIZE = 480*4;
	private static final int READCOUNT = 1000;

	private static final byte[] sbMove = new byte[READSIZE];
	
	private long startTime;

	public LoopTest()
	{
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
	}
	String t()
	{
		return Long.toString( System.currentTimeMillis() - startTime);
	}

}
