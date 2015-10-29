package player;

import operating_mode.IOperation;
import platform.PlatformBase;

public class PlatformPlayerJavaSound extends PlatformPlayerBase
{
	private IOperation iOperation = null;

	/*
	@Override
	protected boolean hasEffects()
	{
		// TODO Auto-generated method stub
		return false;
	}
	*/

	@Override
	public void start()
	{
		iOperation.start();
	}


	@Override
	public void stop()
	{
		iOperation.stop();
	}

	@Override
	public void setOperator(IOperation iOperation)
	{
		this.iOperation = iOperation;
	}



	@Override
	public void drain()
	{
	}


	@Override
	public void init(PlatformBase platformBase, double sampleFrequency)
	{
		// TODO Auto-generated method stub
		
	}
}
