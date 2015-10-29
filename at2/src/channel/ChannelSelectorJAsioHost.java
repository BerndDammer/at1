package channel;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import utils.BorderPanel;
import utils.DIR;
import utils.MyGC;

import com.synthbot.jasiohost.AsioDriver;
import com.synthbot.jasiohost.AsioException;

public class ChannelSelectorJAsioHost extends ChannelSelectorBase
{
	private static final List<String> backlist = new LinkedList<>(); 

	static
	{
		backlist.add("ASIO DirectX Full Duplex Driver");
		backlist.add("Generic Low Latency ASIO Driver");
	}

	public ChannelSelectorJAsioHost()
	{
		super("Select Channels for JASIO Host");
	}

	@Override
	protected boolean hasSub()
	{
		return false;
	}

	@Override
	protected List<AudioChannelIdentifier> getInputChannels()
	{
		return getChannels( DIR.INPUT );
	}

	@Override
	protected List<AudioChannelIdentifier> getOutputChannels()
	{
		return getChannels( DIR.OUTPUT);
	}
	// TODO differ input output
	private List<AudioChannelIdentifier> getChannels(DIR dir)
	{
		List<AudioChannelIdentifier> result = new LinkedList<>();
		List<String> driverNameList = AsioDriver.getDriverNames();
		for(String test : driverNameList)
		{
			if( ! backlist.contains(test))
			{
				try
				{
					AsioDriver myDriver = AsioDriver.getDriver( test );
					switch(dir)
					{
					case INPUT:
						if( myDriver.getNumChannelsInput() > 0)
							result.add( new AudioChannelIdentifier( test));
						break;
					case OUTPUT:
						if( myDriver.getNumChannelsOutput() > 0)
							result.add( new AudioChannelIdentifier( test));
						break;
					}
					// TODO Yamaha start sometimes not
					//myDriver.exit();
				}
				catch(AsioException e)
				{
					// ASIO Driver without hardware connected
					//error.exception(e);
				}
			}
		}
		
		return result;
	}

	@Override
	protected boolean hasSubPanel()
	{
		return true;
	}

	@Override
	protected JPanel getSubPanel()
	{
		return new JAsioHostSubPanel();
	}


	private class JAsioHostSubPanel extends BorderPanel
	{
		public JAsioHostSubPanel()
		{
			super("J Asio Host Sub Panel");
			setLayout(new GridBagLayout());
			MyGC gc = new MyGC();
			gc.reset();

			add(new PanelStarter( DIR.INPUT ), gc);
			gc.gridx++;
			add(new PanelStarter( DIR.OUTPUT ), gc);
		}

		private class PanelStarter extends JButton implements ActionListener
		{
			private static final long serialVersionUID = 1L;
			private final DIR dir;
			PanelStarter( DIR dir)
			{
				this.dir = dir;
				setText("Start " + dir.name() + " Panel");
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent e)
			{
				AsioDriver asioDriver = null;
				switch( dir )
				{
				case INPUT:
					asioDriver = AsioDriver.getDriver( sInput.getItemAt( sInput.getSelectedIndex() ).toString() );
					break;
				case OUTPUT:
					asioDriver = AsioDriver.getDriver( sOutput.getItemAt( sOutput.getSelectedIndex() ).toString() );
					break;
				}
				//asioDriver.disposeBuffers();
				asioDriver.openControlPanel();
			}
		}
	}


	public AudioChannelIdentifier getInputChannel()
	{
		return sInput.getItemAt( sInput.getSelectedIndex() );
	}
	public AudioChannelIdentifier getOutputChannel()
	{
		return sOutput.getItemAt( sOutput.getSelectedIndex() );
	}
}
