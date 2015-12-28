package platform.asio;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

import javax.sound.sampled.AudioFormat;

import com.synthbot.jasiohost.AsioChannel;
import com.synthbot.jasiohost.AsioDriver;
import com.synthbot.jasiohost.AsioDriverListener;

import function.IFunction;
import function.IPlayer;

public class PlatformPlayerAsio implements Runnable, IPlayer, AsioDriverListener
{
	private static final int SAMPLES_PER_BUFFER = 48000;
	private static final int READ_BUFFER_SIZE = SAMPLES_PER_BUFFER * 4;
	private static final int WRITE_BUFFER_SIZE = SAMPLES_PER_BUFFER * 4;

	private static final int SAMPLES_PER_CHUNK = 480;
	private static final int TRANS_SIZE = SAMPLES_PER_CHUNK * 4;
	
	// private static final byte[] sbMove = new byte[TRANS_SIZE];
	private  final PlatformParameterAsio parameter;
	private  final IFunction function;
	
	private static final AudioFormat af = new AudioFormat((float) 48000.0, 16, 2, true, false);

	private final Thread worker;
	
	public PlatformPlayerAsio(PlatformParameterAsio parameter, IFunction function)
	{
		this.parameter = parameter;
		this.function = function;
		
		worker = new Thread(this, "Java Sound Sampler");
	}
	
	/////////////runtime vars
	private AsioDriver inputDriver;
	private AsioDriver outputDriver;
	
	private AsioChannel lin, rin, lout, rout;
	private boolean equal;
	@Override
	public void run()
	{
		String input = parameter.getInput();
		String output = parameter.getOutput();
		
		equal = input.equals(output);
		
		inputDriver = AsioDriver.getDriver( input );
		outputDriver = AsioDriver.getDriver( output );

		//Set<AsioChannel> inputChannels = new HashSet<AsioChannel>();
		//Set<AsioChannel> outputChannels = new HashSet<AsioChannel>();

		Set<AsioChannel> activeChannels = new HashSet<AsioChannel>();

		// configure the ASIO driver to use the given channels
		lin = inputDriver.getChannelInput(0);
		rin = inputDriver.getChannelInput(1);
		lout = outputDriver.getChannelOutput(0);
		rout = outputDriver.getChannelOutput(1);

		activeChannels.add( lin );
		activeChannels.add( rin);

		activeChannels.add(lout);
		activeChannels.add(rout);

		if( equal )
		{
			inputDriver.addAsioDriverListener(this);
			// create the audio buffers and prepare the driver to run
			inputDriver.createBuffers(activeChannels);
			inputDriver.start();
		}
		else
		{
			inputDriver.addAsioDriverListener(this);
			outputDriver.addAsioDriverListener(this);
			// create the audio buffers and prepare the driver to run
			inputDriver.createBuffers(activeChannels);
			outputDriver.createBuffers(activeChannels);
			inputDriver.start();
			outputDriver.start();
		}
		// start the driver
		//myDriver.addAsioDriverListener(this);


		/*
		byte[] bfiin = new byte[TRANS_SIZE];
		byte[] bfout = new byte[TRANS_SIZE];

		ByteBuffer bbin = ByteBuffer.wrap(bfiin).order(ByteOrder.LITTLE_ENDIAN);
		ByteBuffer bbout = ByteBuffer.wrap(bfout).order(ByteOrder.LITTLE_ENDIAN);

		ShortBuffer sbIn = bbin.asShortBuffer();
		ShortBuffer sbOut = bbout.asShortBuffer();
		try
		{
			tdl.open(af, READ_BUFFER_SIZE);
			sdl.open(af, WRITE_BUFFER_SIZE);

			sdl.start();
			tdl.start();
			// --preload 2 transmitt buffers
			sdl.write(bfout, 0, TRANS_SIZE);
			sdl.write(bfout, 0, TRANS_SIZE);

			while (checkRunning())
			{
				tdl.read(bfiin, 0, TRANS_SIZE);
				sbIn.position(0);
				sbOut.clear();
				for(int i = 0; i < SAMPLES_PER_CHUNK; i++)
				{
					short iil =sbIn.get();
					short iir = sbIn.get();
					double l = (double)iil * ( 1.0 / 32768.0);
					double r = (double)iir * ( 1.0 / 32768.0);
					double a = function.nextSample(l, r);
					short ia = (short)( a * 32767.0);
					sbOut.put(ia);
					sbOut.put(ia);
					//sbOut.put(iil);
					//sbOut.put(iir);
				}
				sdl.write(bfout, 0, TRANS_SIZE);
				//sdl.write(bfiin, 0, TRANS_SIZE);
			}
			sdl.drain();
			tdl.close();
			sdl.close();
		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
		error.log("Audio Loop Stopped");
		*/
	}


	// ////////////////////Stop-logic
	private boolean runFlag = true;
	private int counter = 0;


	@Override
	public void startEffect()
	{
		worker.start();
	}
	@Override
	public void stopEffect()
	{
		if( equal )
		{
			inputDriver.stop();
			inputDriver.disposeBuffers();
		}
		else
		{
			inputDriver.stop();
			outputDriver.stop();
			inputDriver.disposeBuffers();
			outputDriver.disposeBuffers();
		}
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
	private static final double DI = (double)(Integer.MAX_VALUE);
	private static final double II = 1.0 / DI;
	@Override
	public void bufferSwitch(long sampleTime, long samplePosition, Set<AsioChannel> activeChannels)
	{
		
		if( activeChannels.size() != 4)
		{
			p("not 4");
			return;
		}
		ByteBuffer bblin = lin.getByteBuffer();
		ByteBuffer bbrin = rin.getByteBuffer();
		
		ByteBuffer bblout = lout.getByteBuffer();
		ByteBuffer bbrout = rout.getByteBuffer();
		
		{
			int sampleCount = bblin.limit() / 4;
			int i, li, ri, o;
			for(i = 0; i < sampleCount; i++)
			{
				li = bblin.getInt();
				ri = bbrin.getInt();
				Double a = function.nextSample((double)li * II, (double)ri * II);
				o = (int)( a * DI );
				bblout.putInt(o);
				bbrout.putInt(o);
			}
		}
	}
	private void p(String s)
	{
		System.out.println(s);
	}
}
