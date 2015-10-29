package operating_mode;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import starter.Central;
import utils.BorderPanel;
import utils.MyGC;

public class OperatingModePanel extends BorderPanel
{
	private final JLabel lMode = new JLabel("Mode : ", JLabel.RIGHT);
	private final OpChoice opChoice = new OpChoice();
	
	public OperatingModePanel()
	{
		super("Operating Mode");
		setLayout(new GridBagLayout());
		MyGC gc = new MyGC();
		
		gc.reset();
		add( lMode, gc );
		gc.gridx++;
		add( opChoice, gc );
		gc.gridx++;
		add( new Start(), gc );
	}

	/*
	public void newPlatform( PlatformBase platform)
	{
		
	}
	*/
	
	private class OpChoice extends JComboBox<OperatorBase>
	{
		OpChoice()
		{
			addItem( new OperatorOutputTest() );
			addItem( new OperatorLoopTest() );
			addItem( new OperatingEffectGroup1() );
		}
	}
	private class Start extends JButton implements ActionListener
	{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Start()
		{
            setText( "Start");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			Central.getSelectedPlatform().startOperation( opChoice.getItemAt( opChoice.getSelectedIndex() ));
		} 
	}
}
