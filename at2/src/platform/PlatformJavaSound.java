package platform;

import operating_mode.IOperation;
import player.PlatformPlayerBase;
import player.PlatformPlayerJavaSound;
import channel.ChannelSelectorBase;
import channel.ChannelSelectorJavaSound;


public class PlatformJavaSound extends PlatformBase
{
	private final ChannelSelectorJavaSound channelSelector = new ChannelSelectorJavaSound();

	@Override
	public String getName()
	{
		return "JavaSound";
	}

	@Override
	public boolean hasSubChannels()
	{
		return true;
	}

	@Override
	public ChannelSelectorBase getChannelSelector()
	{
		return channelSelector;
	}

	@Override
	public PlatformPlayerBase getPlayer()
	{
		return new PlatformPlayerJavaSound();
	}

	@Override
	public void startOperation(IOperation operation)
	{
		// TODO Auto-generated method stub
		
	}
}
