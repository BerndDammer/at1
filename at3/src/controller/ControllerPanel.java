package controller;

import gui_help.BorderPanel;
import gui_help.MyGC;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class ControllerPanel extends BorderPanel
{
	private final JLabel lSelect = new JLabel("Coose control Input : ");
	private final ControllerChoice selApi;

	private final MyGC gc = new MyGC();

	public ControllerPanel()
	{
		super("Midi Controller");

		selApi = new ControllerChoice();

		setLayout(new GridBagLayout());
		gc.reset();

		add(lSelect, gc);
		gc.gridx++;
		add(selApi, gc);

		gc.gridx++;
		add( new TestButton(), gc);

		gc.gridy++;
	}

	class ControllerChoice extends JComboBox<MidiDevice.Info> implements ActionListener
	{
		ControllerChoice()
		{
			for (MidiDevice.Info mi : MidiSystem.getMidiDeviceInfo())
			{
				try
				{
					MidiDevice md = MidiSystem.getMidiDevice(mi);
					int recCount = md.getMaxTransmitters();
					if( recCount > 0 || recCount == -1)
						addItem(mi);
				} catch (MidiUnavailableException e)
				{
					e.printStackTrace();
				}
			}
			addActionListener(this);
		}
		public MidiDevice.Info get()
		{
			return getItemAt( getSelectedIndex() );
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
		}
	}
	class TestButton extends JButton implements ActionListener
	{
		TestButton()
		{
			setText("Test");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new ControllerTestDialog( selApi.get() );
		}
	}

}
