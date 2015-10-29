package platform.asio;

import gui_help.BorderPanel;
import gui_help.MyGC;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.synthbot.jasiohost.AsioDriver;
import com.synthbot.jasiohost.AsioException;

class PlatformParameterAsio extends BorderPanel
{
	private static final List<String> backlist = new LinkedList<>();

	static
	{
		backlist.add("ASIO DirectX Full Duplex Driver");
		backlist.add("Generic Low Latency ASIO Driver");
	}

	private final JComboBox<String> inputChannel;
	private final JComboBox<String> outputChannel;
	private final MyGC gc = new MyGC();

	PlatformParameterAsio()
	{
		super("Parameter for ASIO");
		setLayout( new GridBagLayout());
		inputChannel = new JComboBox<>();
		outputChannel = new JComboBox<>();
		fillSelectors();
		
		gc.reset();
		add( inputChannel, gc);
		gc.gridx++;
		add( outputChannel, gc);
		
		gc.nextRow();
		add( new PanelStarter(inputChannel), gc);
		gc.gridx++;
		add( new PanelStarter(outputChannel), gc);
	}

	private void fillSelectors()
	{
		// List<AudioChannelIdentifier> result = new LinkedList<>();
		List<String> driverNameList = AsioDriver.getDriverNames();
		for (String test : driverNameList)
		{
			if (!backlist.contains(test))
			{
				try
				{
					AsioDriver myDriver = AsioDriver.getDriver(test);
					if (myDriver.getNumChannelsInput() > 0)
						inputChannel.addItem(test);
					if (myDriver.getNumChannelsOutput() > 0)
						outputChannel.addItem(test);
					// TODO Yamaha start sometimes not
					// myDriver.exit();
				} catch (AsioException e)
				{
					// ASIO Driver without hardware connected
					// error.exception(e);
				}
			}
		}
	}

	private class PanelStarter extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		private final JComboBox<String> selector;
		
		PanelStarter( JComboBox<String> selector)
		{
			this.selector = selector;
		
			setText("Start " + "dir" + " Panel");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			AsioDriver asioDriver = null;
			asioDriver = AsioDriver.getDriver(selector.getItemAt(selector.getSelectedIndex()));

			// asioDriver.disposeBuffers();
			asioDriver.openControlPanel();
		}
	}
	public String getInput()
	{
		return(inputChannel.getItemAt(inputChannel.getSelectedIndex() ));
	}
	public String getOutput()
	{
		return(outputChannel.getItemAt(outputChannel.getSelectedIndex() ));
		
	}
}
