package starter;

import java.util.LinkedList;
import java.util.List;

import operating_mode.OperatingModePanel;
import platform.PlatformBase;
import platform.PlatformJAsioHost;
import platform.PlatformJavaSound;
import background.MixerSelector;
import background.MixerSelector.MODE;

public class Central
{
	private static final List<PlatformBase> platforms;
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

	

	static
	{
		gInputSelector = new MixerSelector(MODE.INPUT);
		gOutputSelector = new MixerSelector(MODE.OUTPUT);
		platforms = new LinkedList<>();
		platforms.add(new PlatformJavaSound());
		platforms.add(new PlatformJAsioHost());
	}

	
	///////////////////////////////////////////////////////////platform
	public static List<PlatformBase> getPlatforms()
	{
		return platforms;
	}

	public static PlatformBase getSelectedPlatform()
	{
		return selectedPlatform;
	}

	public static void setSelectedPlatform(PlatformBase selectedPlatform)
	{
		Central.selectedPlatform = selectedPlatform;
		mf.newPlatform(getSelectedPlatform());
		//parameterPanel.newPlatform( selectedPlatform );
	}

	public static void setSelectedPlatform(String name)
	{
		
		// TODO name logic ?????
		// Central.selectedPlatform = null;
		for (PlatformBase pb : getPlatforms())
		{
			if (pb.getName().equals(name))
				setSelectedPlatform(pb);
		}
	}
	///////////////////////////////////////////////////////////////////player
	private static OperatingModePanel operatinModePanel = new OperatingModePanel();

	public static OperatingModePanel getOperatinModePanel()
	{
		return operatinModePanel;
	}

	////////////////////////////////////////////////////////////////
	private static final MixerSelector gInputSelector;
	private static final MixerSelector gOutputSelector;

	public static MixerSelector getGinputSelector()
	{
		return gInputSelector;
	}

	public static MixerSelector getGoutputSelector()
	{
		return gOutputSelector;
	}

}
