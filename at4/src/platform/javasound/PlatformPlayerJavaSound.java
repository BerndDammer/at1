package platform.javasound;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import starter.error;
import function.IFunction;
import function.IPlayer;

public class PlatformPlayerJavaSound implements Runnable, IPlayer
{
	private static final int SAMPLES_PER_BUFFER = 48000;
	private static final int READ_BUFFER_SIZE = SAMPLES_PER_BUFFER * 4;
	private static final int WRITE_BUFFER_SIZE = SAMPLES_PER_BUFFER * 4;

	private static final int SAMPLES_PER_CHUNK = 480;
	private static final int TRANS_SIZE = SAMPLES_PER_CHUNK * 4;
	
	// private static final byte[] sbMove = new byte[TRANS_SIZE];
	private  final PlatformParameterJavaSound parameter;
	private  final IFunction function;
	
	private static final AudioFormat af = new AudioFormat((float) 48000.0, 16, 2, true, false);

	private final Thread worker;
	
	public PlatformPlayerJavaSound(PlatformParameterJavaSound parameter, IFunction function)
	{
		this.parameter = parameter;
		this.function = function;
		
		worker = new Thread(this, "Java Sound Sampler");
	}
	@Override
	public void run()
	{
		SourceDataLine sdl = parameter.getSourceDataLine();
		TargetDataLine tdl = parameter.getTargetDataLine();

		byte[] bfiin = new byte[TRANS_SIZE];
		byte[] bfout = new byte[TRANS_SIZE];

		ByteBuffer bbin = ByteBuffer.wrap(bfiin).order(ByteOrder.LITTLE_ENDIAN);
		ByteBuffer bbout = ByteBuffer.wrap(bfout).order(ByteOrder.LITTLE_ENDIAN);

		ShortBuffer sbIn = bbin.asShortBuffer();
		ShortBuffer sbOut = bbout.asShortBuffer();
		try
		{
			tdl.open(af, READ_BUFFER_SIZE);
			sdl.open(af, WRITE_BUFFER_SIZE);

			sdl.start();
			tdl.start();
			// --preload 2 transmitt buffers
			sdl.write(bfout, 0, TRANS_SIZE);
			sdl.write(bfout, 0, TRANS_SIZE);

			while (checkRunning())
			{
				tdl.read(bfiin, 0, TRANS_SIZE);
				sbIn.position(0);
				sbOut.clear();
				for(int i = 0; i < SAMPLES_PER_CHUNK; i++)
				{
					short iil =sbIn.get();
					short iir = sbIn.get();
					double l = (double)iil * ( 1.0 / 32768.0);
					double r = (double)iir * ( 1.0 / 32768.0);
					double a = function.nextSample(l, r);
					short ia = (short)( a * 32767.0);
					sbOut.put(ia);
					sbOut.put(ia);
					//sbOut.put(iil);
					//sbOut.put(iir);
				}
				sdl.write(bfout, 0, TRANS_SIZE);
				//sdl.write(bfiin, 0, TRANS_SIZE);
			}
			sdl.drain();
			tdl.close();
			sdl.close();
		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
		error.log("Audio Loop Stopped");
	}


	// ////////////////////Stop-logic
	private boolean runFlag = true;
	private int counter = 0;

	public boolean checkRunning()
	{
		return runFlag;
	}

	public synchronized void stopp()
	{
		runFlag = false;
	}
	@Override
	public void startEffect()
	{
		worker.start();
	}
	@Override
	public void stopEffect()
	{
		// TODO Auto-generated method stub
		stopp();
	}

}
