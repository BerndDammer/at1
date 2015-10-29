package effect;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import starter.CentralControl;
import util.Util;
import function.IFunction;
import function.IPlayer;

public abstract class EffectDialog extends JDialog implements IFunction, WindowListener
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getAnonymousLogger();

	private RemView remView;

	public EffectDialog()
	{
		super(CentralControl.getMainFrame(), true);
		addWindowListener(this);
		remView = new RemView();
		setTitle("XXX PPP XXX");
		setBounds(30, 40, 600, 400);
		setContentPane(new myPanel());
		// setVisible(true);
		Util.centerFrame(this);
	}
	private IPlayer player;
	protected void startIt()
	{
		logger.info("Starting effect ...");
		player = CentralControl.getSelectedPlatform().getPlayerForFunction( this );
		player.startEffect();
		logger.info("Starting done");
	}

	private class myPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public myPanel()
		{
			add(new StopButton());
			add(remView);
		}
	}

	private class StopButton extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		StopButton()
		{
			setText("Stop");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			logger.info("Stopping effect ...");
			player.stopEffect();
			EffectDialog.this.setVisible( false );
			logger.info("Stopping done!");
		}
	}

	private class RemView extends JLabel
	{
		private Syncer<String> mySyncer = new Syncer<>();
		private int counter = 0;
		private final int div = 100;
		private int retAccu = 0;
		private int showCounter = 0;
		private int min;
		private int max;
		private boolean firstFlag;

		RemView()
		{
			super("XXXXXXXX", JLabel.LEFT);
			countReset();
		}

		@Override
		public void paint(Graphics g)
		{
			if (mySyncer.has())
			{
				setText(mySyncer.get());
				showCounter++;
			} else
				super.paint(g);
		}

		public void afterWrite(int act)
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
			if (counter == div)
			{
				retAccu /= div;
				String show;
				show = "C : " + showCounter + " Mean : " + retAccu + " min : " + min + " max : " + max + "----";
				mySyncer.put(show);
				repaint();
				countReset();
			}
		}

		private void countReset()
		{
			counter = 0;
			retAccu = 0;
			min = 0;
			max = 0;
			firstFlag = true;
		}

		private class Syncer<T>
		{
			private T value;
			private boolean has = false;

			public Syncer()
			{
			}

			public synchronized void put(T value)
			{
				this.value = value;
				has = true;
			}

			public synchronized boolean has()
			{
				return this.has;
			}

			public synchronized T get()
			{
				has = false;
				return value;
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		player.stopEffect();
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}
}
