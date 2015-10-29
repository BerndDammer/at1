package at1;

import at1.MixerSelector.MODE;

public class Central
{
	private static final MixerSelector gInputSelector;
	private static final MixerSelector gOutputSelector;
	
	@SuppressWarnings("unused")
	// TODO need this qqqqq
	private static MainFrame mf;
	
	static
	{
		gInputSelector = new MixerSelector(MODE.INPUT);
		gOutputSelector = new MixerSelector(MODE.OUTPUT);
	}
	
	private Central()
	{
		error.exit("static only class");
	}

	public static MixerSelector getGinputSelector()
	{
		return gInputSelector;
	}

	public static MixerSelector getGoutputSelector()
	{
		return gOutputSelector;
	}

	
	public static void start()
	{
		mf = new MainFrame();
	}
}
