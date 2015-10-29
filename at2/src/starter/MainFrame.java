package starter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import old.InputTest;
import old.Loop2;
import old.LoopTest;
import old.OutputTest;
import operating_mode.OperatingModePanel;
import platform.PlatformBase;
import platform.PlatformPanel;
import portaudiotest.PALoopTest;
import testjasio.JAsioTest1;
import utils.MyGC;
import act.LoopMul1;
import background.MixerView;
import channel.ChannelSelectorBase;

import com.synthbot.jasiohost.AsioDriver;

public class MainFrame extends JFrame2 implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ChannelSelectorBase channelSelector = null;
	private final MyGC gc = new MyGC();
	
	public void newPlatform( PlatformBase pb)
	{
		if(channelSelector != null) remove(channelSelector);
		channelSelector = pb.getChannelSelector();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.HORIZONTAL;

		add(channelSelector, gc);
		gc.gridwidth = 1;
		invalidate();
		pack();
	}
	public MainFrame()
	{
		SwingUtilities.invokeLater(this);
	}

	private class myPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		myPanel()
		{
			setLayout(new GridBagLayout());
			//GridBagConstraints gc = new GridBagConstraints();
			gc.reset();

			//row 0 platform selector
			gc.fill = GridBagConstraints.HORIZONTAL;
			add( new PlatformPanel(), gc );
			gc.fill = GridBagConstraints.NONE;
			
			//row 1 Channel Selector
			gc.nextRow();
			//placed later
			
			//row 2 Parameter Panel
			gc.nextRow();
			gc.pushHorizontal();
			add( new OperatingModePanel(), gc);
			gc.popHorizontal();
			
			gc.nextRow();
			add(Central.getGinputSelector(), gc);
			gc.gridx++;
			add(Central.getGoutputSelector(), gc);
			
			gc.gridy++;
			gc.gridx = 0;
			add( new ViewStarter(), gc);
			gc.gridx++;
			//add( new OutputStart(), gc);
			
			gc.gridy++;
			gc.gridx = 0;
			add( new InputStart(), gc);
			gc.gridx++;
			add( new OutputStart(), gc);
			
			gc.gridy++;
			gc.gridx = 0;
			add( new LoopStart(), gc);
			gc.gridx++;
			add( new Loop2Start(), gc);
			
			gc.gridy++;
			gc.gridx = 0;
			add( new Mul1(), gc);
			gc.gridx++;
			add( new PAT1(), gc);

			gc.gridy++;
			gc.gridx = 0;
			add( new JAT1(), gc);
			gc.gridx++;
			add( new JATCP(), gc);

			gc.gridy++;
			gc.gridx = 0;
			add( new JATCP_B(), gc);
			gc.gridx++;
			//add( new JATCP(), gc);
		}
	}

	@Override
	public void run()
	{
		setContentPane(new myPanel());
		setTitle("AudioSplicer");
		setVisible(true);
	}
	//---------------------GUI_Elements
	private class ViewStarter extends JButton implements ActionListener
	{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ViewStarter()
		{
            setText( "Mixer Tree View");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new MixerView( MainFrame.this);
		} 
	}
	private class OutputStart extends JButton implements ActionListener
	{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		OutputStart()
		{
            setText( "Output Test");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new OutputTest();
		} 
	}
	private class InputStart extends JButton implements ActionListener
	{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		InputStart()
		{
            setText( "Input Test");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new InputTest();
		} 
	}
	private class LoopStart extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		LoopStart()
		{
            setText( "Loop Test");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new LoopTest();
		} 
	}
	private class Loop2Start extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		Loop2Start()
		{
            setText( "Continuous Loop");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new Loop2( MainFrame.this );
		} 
	}
	private class Mul1 extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		Mul1()
		{
            setText( "Mul1");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new LoopMul1( MainFrame.this );
		} 
	}
	
	private class PAT1 extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		PAT1()
		{
            setText( "PortAudioTest1");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new PALoopTest( );
		} 
	}
	
	private class JAT1 extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		JAT1()
		{
            setText( "JAsio Test1");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new JAsioTest1( );
		} 
	}
	
	private class JATCP extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		JATCP()
		{
            setText( "JAsio Control Panel");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			//AsioDriver.getCurrentDriver().openControlPanel();
			AsioDriver.getDriver("Yamaha Steinberg USB ASIO").openControlPanel();
			
		} 
	}
	
	private class JATCP_B extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		JATCP_B()
		{
            setText( "JAsio Control Panel Behringer");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			//AsioDriver.getCurrentDriver().openControlPanel();
			AsioDriver.getDriver("BEHRINGER USB AUDIO").openControlPanel();
		} 
	}
}
