package starter;

import platform_base.PlatformBase;
import splicing_sleeve.AutomaticNetworkInterface;
import splicing_sleeve.ControlServer;
import splicing_sleeve.MonitorPortClient;
import splicing_sleeve.ServerViewPanel;

public class CentralControl
{
	private static PlatformBase selectedPlatform = null;
	//private static ChannelSelectorBase channelSelector = null;
	private static ControlServer controlServer = null;
	private static ServerViewPanel serverViewPanel = new ServerViewPanel();
	private static MonitorPortClient monitorPortClient;
	
	private static MainFrame mf;

	public static MainFrame getMainFrame()
	{
		return mf;
	}

	private CentralControl()
	{
		error.exit("static only class");
	}

	public static void start()
	{
		AutomaticNetworkInterface.scan();
		mf = new MainFrame();
		controlServer = new ControlServer();
		controlServer.test();
		monitorPortClient = new MonitorPortClient();
	}

	

	///////////////////////////////////////////////////////////platform

	public static PlatformBase getSelectedPlatform()
	{
		return selectedPlatform;
	}

	public static void setSelectedPlatform(PlatformBase platform)
	{
		CentralControl.selectedPlatform = platform;
		mf.newPlatform(getSelectedPlatform());
	}

	public static ServerViewPanel getServerView()
    {
	    return serverViewPanel;
    }
}
