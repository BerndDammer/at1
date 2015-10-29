package starter;

import java.util.List;

import platform_base.PlatformBase;

public class Central
{
	private static PlatformBase selectedPlatform = null;
	//private static ChannelSelectorBase channelSelector = null;

	private static MainFrame mf;

	public static MainFrame getMainFrame()
	{
		return mf;
	}

	private Central()
	{
		error.exit("static only class");
	}

	public static void start()
	{
		mf = new MainFrame();
	}

	

	///////////////////////////////////////////////////////////platform

	public static PlatformBase getSelectedPlatform()
	{
		return selectedPlatform;
	}

	public static void setSelectedPlatform(PlatformBase platform)
	{
		Central.selectedPlatform = platform;
		mf.newPlatform(getSelectedPlatform());
	}

//	///////////////////////////////////////////////////////////////////player
//	private static OperatingModePanel operatinModePanel = new OperatingModePanel();
//
//	public static OperatingModePanel getOperatinModePanel()
//	{
//		return operatinModePanel;
//	}
//
	////////////////////////////////////////////////////////////////

}
