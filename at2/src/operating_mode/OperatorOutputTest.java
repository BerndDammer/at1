package operating_mode;

import platform.PlatformBase;
import starter.Central;

public class OperatorOutputTest extends OperatorBase implements IOperation, Runnable
{
	int sampleCounter;
	//private Thread worker;
	private PlatformBase platformBase;
	private double sampleFrequency;
	
	@Override
	public void start()
	{
		//worker.start();
	}

	@Override
	public void drain()
	{
		
	}

	@Override
	public void stop()
	{
	}

	@Override
	public void nextChunck_signedInt16Stereo(byte[] in, byte[] out, int sampleCount)
	{
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(PlatformBase platformBase, double sampleFrequency)
	{
		this.platformBase = platformBase;
		this.sampleFrequency = sampleFrequency;
		
		sampleCounter = 0;
		//worker = new Thread("Output Test Worker");
		

	}

}
