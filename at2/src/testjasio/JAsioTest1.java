package testjasio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.SetChangeListener;
import starter.error;

import com.synthbot.jasiohost.AsioChannel;
import com.synthbot.jasiohost.AsioDriver;
import com.synthbot.jasiohost.AsioDriverListener;



public class JAsioTest1 implements AsioDriverListener
{
	final Collector setCountCollector = new Collector();
	final Collector sampleTimeCollector = new Collector();
	final Collector samplePositionCollector = new Collector();

	public JAsioTest1()
	{
		int driverCount;
		List<String> driverNameList = AsioDriver.getDriverNames();
		driverCount = driverNameList.size();
		
		
		
		for(String s : driverNameList)
		{
			error.log("Diver Name : " + s );

		}

		AsioDriver myDriver = AsioDriver.getDriver("Yamaha Steinberg USB ASIO");
		error.log( "getNumChannelsInput : " + myDriver.getNumChannelsInput() );
		error.log( "getNumChannelsOutput : " + myDriver.getNumChannelsInput() );
		//myDriver.addAsioDriverListener(this);

		// create a Set of AsioChannels, defining which input and output channels will be used
		Set<AsioChannel> activeChannels = new HashSet<AsioChannel>();

		// configure the ASIO driver to use the given channels
		activeChannels.add(myDriver.getChannelOutput(0));
		activeChannels.add(myDriver.getChannelOutput(1));

		activeChannels.add(myDriver.getChannelInput(0));
		activeChannels.add(myDriver.getChannelInput(1));

		/*
		myDriver.addAsioDriverListener( new AsioDriverListener() 
		{
			
			@Override
			public void sampleRateDidChange(double sampleRate)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void resyncRequest()
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void resetRequest()
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
				// TODO Auto-generated method stub
			}
			
			@Override
			public void bufferSizeChanged(int bufferSize)
			{
				// TODO Auto-generated method stub
				
			}
		});
		*/
		myDriver.addAsioDriverListener(this);
		// create the audio buffers and prepare the driver to run
		myDriver.createBuffers(activeChannels);

		// start the driver
		//myDriver.addAsioDriverListener(this);

		myDriver.start();
		try
		{
			Thread.sleep(3000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myDriver.stop();
		myDriver.disposeBuffers();
		error.log("-----------------------");
		error.log("CallbackCount : " + setCountCollector.getNum() );
		error.log("SetCount : " + setCountCollector.getMean() );
	}

	@Override
	public void sampleRateDidChange(double sampleRate)
	{
		error.log("You should not be here!" );

	}

	@Override
	public void resetRequest()
	{
		error.log("You should not be here!" );
	}

	@Override
	public void resyncRequest()
	{
		error.log("You should not be here!" );
	}

	@Override
	public void bufferSizeChanged(int bufferSize)
	{
		error.log("You should not be here!" );
	}

	@Override
	public void latenciesChanged(int inputLatency, int outputLatency)
	{
		error.log("You should not be here!" );
	}

	@Override
	public void bufferSwitch(long sampleTime, long samplePosition, Set<AsioChannel> activeChannels)
	{
		setCountCollector.add( activeChannels.size() );

	}
	///////////////////////////////////////////////////////////////////////////////
	private class Collector
	{
		private int counter = 0;
		private int retAccu = 0;
		private int showCounter = 0;
		private int min;
		private int max;
		private boolean firstFlag;

		Collector()
		{
			countReset();
		}


		public void add(int act)
		{
			if (firstFlag)
			{
				min = act;
				max = act;
				firstFlag = false;
			} else
			{
				if (act < min)
					min = act;
				if (act > max)
					max = act;
			}
			retAccu += act;
			counter++;
		}

		private void countReset()
		{
			counter = 0;
			retAccu = 0;
			min = 0;
			max = 0;
			firstFlag = true;
		}
		public void sum()
		{
			retAccu /= counter;
		}
		public int getNum()
		{
			return counter;
		}
		public int getMean()
		{
			return retAccu / counter;
		}
	}
}