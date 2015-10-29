package platform;

import operating_mode.IOperation;
import player.PlatformPlayerBase;
import channel.ChannelSelectorBase;


public abstract class PlatformBase
{
	public abstract String getName();
	public abstract boolean hasSubChannels();
	public abstract ChannelSelectorBase getChannelSelector();


	/*
	public abstract List<AudioChannelIdentifier> getInputChannels();
	public abstract List<AudioChannelIdentifier> getOutputChannels();
	*/
	
	@Override
	public String toString()
	{
		return getName();
	}
	
	public abstract PlatformPlayerBase getPlayer();
	
	public abstract void startOperation( IOperation operation);
}
