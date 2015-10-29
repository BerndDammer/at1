package channel;

import javax.sound.sampled.Mixer;

// no sub class if channel is simply identified by String
public class AudioChannelIdentifierJavaSound extends AudioChannelIdentifier
{
	private final Mixer.Info mi;
	
	public AudioChannelIdentifierJavaSound(Mixer.Info mi)
	{
		super( mi.getName() );
		this.mi = mi;
	}
	@Override
	public String toString()
	{
		return mi.getName();
	}

	public Mixer.Info getMixerInfo()
	{
		return mi;
	}
}
