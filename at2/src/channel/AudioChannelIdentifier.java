package channel;

// no sub class if channel is simply identified by String
public class AudioChannelIdentifier
{
	private final String name;
	
	public AudioChannelIdentifier(String name)
	{
		this.name = name;
	}
	@Override
	public String toString()
	{
		return name;
	}
}
