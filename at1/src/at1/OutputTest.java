package at1;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class OutputTest
{
	private static final int BUFFER_SIZE = 48000 * 4;
	private static final byte[] bb = new byte[48000 * 4];

	public OutputTest()
	{
		// ---------------
		getOutput();
	}

	private final void getOutput()
	{
		fill3();
		SourceDataLine sdl = null;
		try
		{
			sdl = Central.getGoutputSelector().getSourceDataLine();
			AudioFormat af = new AudioFormat((float) 48000.0, 16, 2, true,
					false);
			sdl.open(af, 48000 * 4);
			int retVal = sdl.write(bb, 0, 48000 * 4);
			error.log("retVal " + retVal);
			sdl.start();
		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
		finally
		{
			//sdl.flush();
			//sdl.close();
			// TRAP
			//sudden close stops output
		}
	}

	private void fill()
	{
		for (int i = 0; i < 48000; i++)
		{
			bb[i] = (byte) i;
		}
	}

	private void fill2()
	{
		for (int i = 0; i < 48000; i++)
		{
			if (i % 4 == 0)
				bb[i] = (byte) i;
			else
				bb[i] = 0;
		}
	}

	private void fill3()
	{
		double f1 = 2.0 * Math.PI * 300.0;
		double f2 = 2.0 * Math.PI * 450.0;
		for (int i = 0; i < 48000; i++)
		{
			double t, l, r;
			t = ((double) i) / 48000.0;
			l = Math.sin(f1 * t);
			r = Math.sin(f2 * t);
			short sl = (short) (l * 32767.0);
			short sr = (short) (r * 32767.0);
			int index = i * 4;
			if (i < 24000)
			{
				bb[index] = (byte) (sl);
				index++;
				bb[index] = (byte) (sl >> 8);
				index++;
			} else
			{
				index++;
				index++;
				bb[index] = (byte) (sr);
				index++;
				bb[index] = (byte) (sr >> 8);
				index++;
			}
		}
	}
}
