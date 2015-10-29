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

import starter.Central;
import starter.MainFrame;

public class ControllerSelectPanel extends BorderPanel
{
    private static final long serialVersionUID = 1L;

    private final JLabel lSelect = new JLabel("Coose control Input : ");
	private final ControllerChoice controllerChoice;

	private final MyGC gc = new MyGC();
	private final MainFrame parent;
	
	public ControllerSelectPanel(MainFrame parent)
	{
		super("Midi Controller");
		this.parent = parent;
		
		controllerChoice = new ControllerChoice();

		setLayout(new GridBagLayout());
		gc.reset();

		add(lSelect, gc);
		gc.gridx++;
		add(controllerChoice, gc);

        gc.gridx++;
        add( new TestButton(), gc);

        gc.gridx++;
        add( new BridgeButton(), gc);

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
            MainFrame mf = Central.getMainFrame();
            mf.newMidiControl(new ControllerPanelTest( controllerChoice.get() ));
        }
    }
    class BridgeButton extends JButton implements ActionListener
    {
        BridgeButton()
        {
            setText("Bridge");
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
//            MainFrame mf = Central.getMainFrame();
//            mf.newMidiControl(new ControllerPanelMapper( parent ));
            ControllerGroupBase cgb = new ControllerGroupLDP8(parent, controllerChoice.get() );
            //ControllerGroupBase cgb = new ControllerGroupLDP8_Test(parent, controllerChoice.get() );
            parent.getControllerPanelMapper().newControllerGroupBase(  cgb );
        }
    }
}
