package starter;

import platform_base.PlatformBase;
import splicing_sleeve.AutomaticNetworkInterface;
import splicing_sleeve.DeamonServer;
import splicing_sleeve.MonitorPort;

public class CentralDeamon
{
	private static PlatformBase selectedPlatform = null;
	private static DeamonServer deamonServer = null;
	private static MonitorPort monitorPort = null;


	private CentralDeamon()
	{
		error.exit("static only class");
	}

	public static void start()
	{
		AutomaticNetworkInterface.scan();
		deamonServer = new DeamonServer();
		monitorPort = new MonitorPort(true);
		//monitorPort.
	}

	

	///////////////////////////////////////////////////////////platform

	public static PlatformBase getSelectedPlatform()
	{
		return selectedPlatform;
	}

	public static void setSelectedPlatform(PlatformBase platform)
	{
		CentralDeamon.selectedPlatform = platform;
		// mf.newPlatform(getSelectedPlatform());
	}
}
