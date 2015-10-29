package platform.asio;

import javax.swing.JPanel;

import platform_base.PlatformBase;
import function.IFunction;
import function.IPlayer;

public class PlatformAsio extends PlatformBase
{
	private final PlatformParameterAsio platformParameter;
	
	public PlatformAsio()
	{
		platformParameter = new PlatformParameterAsio();
	}

	@Override
	public JPanel getParameterPanel()
	{
		// TODO Auto-generated method stub
		return platformParameter;
	}

	@Override
	public IPlayer getPlayerForFunction(IFunction operation)
	{
		return new PlatformPlayerAsio( platformParameter, operation);
	}

}
