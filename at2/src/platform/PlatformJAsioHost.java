package platform;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

import operating_mode.IOperation;
import player.PlatformPlayerBase;
import player.PlatformPlayerJAsioHost;
import starter.error;
import channel.AudioChannelIdentifier;
import channel.ChannelSelectorBase;
import channel.ChannelSelectorJAsioHost;

import com.synthbot.jasiohost.AsioChannel;
import com.synthbot.jasiohost.AsioDriver;
import com.synthbot.jasiohost.AsioDriverListener;


public class PlatformJAsioHost extends PlatformBase implements AsioDriverListener
{
	private final ChannelSelectorJAsioHost channelSelector = new ChannelSelectorJAsioHost();

	@Override
	public String getName()
	{
		return "JAsioHost";
	}

	@Override
	public boolean hasSubChannels()
	{
		return false;
	}

	@Override
	public ChannelSelectorBase getChannelSelector()
	{
		return channelSelector;
	}

	@Override
	public PlatformPlayerBase getPlayer()
	{
		return new PlatformPlayerJAsioHost();
	}

	private IOperation operation;
	AsioChannel bridgeIn, neckIn, lOut, rOut;
	int sampleCount;
	@Override
	public void startOperation(IOperation operation)
	{
		final int BUFFER_SIZE = 512;
		
		this.operation = operation;
		sampleCount = 0;
	
		AudioChannelIdentifier in = channelSelector.getInputChannel();
		AudioChannelIdentifier out = channelSelector.getOutputChannel();

		AsioDriver inputChannel = AsioDriver.getDriver( in.toString() );
		AsioDriver outputChannel = AsioDriver.getDriver( out.toString() );

		inputChannel.setSampleRate( 48000.0 );
		outputChannel.setSampleRate( 48000.0 );
		Set<AsioChannel> activeChannels = new HashSet<AsioChannel>();

		
		bridgeIn = inputChannel.getChannelInput(0);
		neckIn = inputChannel.getChannelInput(1);
		activeChannels.add( bridgeIn );
		activeChannels.add( neckIn );

		lOut = outputChannel.getChannelOutput(0);
		rOut = outputChannel.getChannelOutput(1);

		activeChannels.add( lOut );
		activeChannels.add( rOut );

		// TODO input and output equal
		inputChannel.addAsioDriverListener(this);

		// create the audio buffers and prepare the driver to run
		inputChannel.createBuffers(activeChannels);

		// start the driver

		inputChannel.start();

	}

	@Override
	public void sampleRateDidChange(double sampleRate)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetRequest()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resyncRequest()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bufferSizeChanged(int bufferSize)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void latenciesChanged(int inputLatency, int outputLatency)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bufferSwitch(long sampleTime, long samplePosition, Set<AsioChannel> activeChannels)
	{
		if( activeChannels.size() == 4)
		{
			ByteBuffer bblin = bridgeIn.getByteBuffer();
			ByteBuffer bbrin = neckIn.getByteBuffer();
			ByteBuffer bblout = lOut.getByteBuffer();
			ByteBuffer bbrout = rOut.getByteBuffer();
			bblout.put( bblin);
			bbrout.put( bbrin );
		}
		else
			error.log( "not 4 channels");
		//if( samplePosition > 480000 ) error.exit("sample end");
	}
}
