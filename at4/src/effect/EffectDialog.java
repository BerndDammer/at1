package effect;

import function.IFunction;
import function.IPlayer;
import gui_help.BorderPanel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;

import controller.ControlReceiveParameter;
import starter.Central;
import starter.MainFrame;

public abstract class EffectDialog extends BorderPanel implements IFunction
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getAnonymousLogger();

	private RemView remView;
    protected MainFrame parent;

	public EffectDialog()
	{
	    super( "EffectDialog");
	    remView = new RemView();

	    add(new StopButton());
        add(remView);
        
        Central.getMainFrame().setEffectPanel(this);
	}
	private IPlayer player;
	protected void startIt()
	{
		logger.info("Starting effect ...");
		player = Central.getSelectedPlatform().getPlayerForFunction( this );
		player.startEffect();
		logger.info("Starting done");
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

    public MainFrame getParent()
    {
        return parent;
    }

    public void setParent(MainFrame parent)
    {
        this.parent = parent;
    }
    ///////////////////////////////////////////
    //intended to be overwritten
    public void commitControls()
    {
    }
}
