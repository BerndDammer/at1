package platform.javasound;

import javax.swing.JPanel;

import platform_base.PlatformBase;
import function.IFunction;
import function.IPlayer;

public class PlatformJavaSound extends PlatformBase
{
	private final PlatformParameterJavaSound platformParameter;

	public PlatformJavaSound()
	{
		platformParameter = new PlatformParameterJavaSound();

	}

	@Override
	public JPanel getParameterPanel()
	{
		return platformParameter;
	}

	@Override
	public IPlayer getPlayerForFunction(IFunction operation)
	{
		return new PlatformPlayerJavaSound(platformParameter, operation);
	}

}
