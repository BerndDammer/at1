package platform_base;

import javax.swing.JPanel;

import function.IFunction;
import function.IPlayer;



public abstract class PlatformBase
//public interface PlatformBase

{
	//public abstract String getName();
	//public abstract boolean hasSubChannels();
	//public abstract ChannelSelectorBase getChannelSelector();


	/*
	public abstract List<AudioChannelIdentifier> getInputChannels();
	public abstract List<AudioChannelIdentifier> getOutputChannels();
	*/
	
//	@Override
//	public String toString()
//	{
//		return getName();
//	}
	
	//public abstract PlatformPlayerBase getPlayer();
	
	//public abstract void startOperation( IOperation operation);
	public abstract IPlayer getPlayerForFunction( IFunction operation);
	public abstract JPanel getParameterPanel();
}
