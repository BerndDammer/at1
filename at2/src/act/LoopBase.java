package act;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import starter.Central;
import starter.error;

public abstract class LoopBase extends JDialog
{
	private static final long serialVersionUID = 1L;

	private Runner runner;
	private RemView remView;

	public LoopBase(JFrame parent)
	{
		super(parent, true);
		remView = new RemView();
		setTitle("XXX PPP XXX");
		setBounds(30, 40, 600, 400);
		setContentPane(new myPanel());
		runner = new Runner();
		// setVisible(true);
	}

	protected void startIt()
	{
		runner.start();
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
			runner.stopp();
			try
			{
				runner.join();
			} catch (InterruptedException e1)
			{
				assert (false);
			}
			LoopBase.this.setVisible(false);
			LoopBase.this.dispose();
		}
	}

	private class RemView extends JLabel
	{
		private Syncer<String> mySyncer = new Syncer<>();
		private int counter = 0;
		private final int div = WRITE_BUFFER_SIZE / TRANSFER_SIZE;
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

	protected static final int READ_BUFFER_SIZE = 48000 * 4;
	protected static final int WRITE_BUFFER_SIZE = 48000 * 4;
	protected static final int TRANSFER_SIZE = 480 * 4;

	protected abstract byte[] OnBuffer(byte[] bin);

	// //////////////////////
	private class Runner extends Thread
	{
		// ///////////////////////declaration for runner
		private final AudioFormat af = new AudioFormat(48000.0f, 16, 2, true, false);
		// private static final int BUFFER_SIZE = 48000 * 4;

		// Broken noise
		// private static final int READ_BUFFER_SIZE = 960 * 4;
		// private static final int WRITE_BUFFER_SIZE = 960 * 4;

		// broken too
		// private static final int READ_BUFFER_SIZE = 960 * 4;
		// private static final int WRITE_BUFFER_SIZE = 48000 * 4;

		// works WITH delay
		// private static final int READ_BUFFER_SIZE = 480 * 4 * 32;
		// private static final int WRITE_BUFFER_SIZE = 48000 * 4;

		// ---------------
		// private static final byte[] sbIn = new byte[BUFFER_SIZE];
		// private static final byte[] sbOut = new byte[BUFFER_SIZE];

		private final byte[] sbMove = new byte[TRANSFER_SIZE];

		// ////////////////////

		public void run()
		{
			// --------------init
			SourceDataLine sdl = Central.getGoutputSelector().getSourceDataLine();
			TargetDataLine tdl = Central.getGinputSelector().getTargetDataLine();
			int watch;

			try
			{
				tdl.open(af, READ_BUFFER_SIZE);
				sdl.open(af, WRITE_BUFFER_SIZE);

				// --preload 2 transmitt buffers
				sdl.start();
				sdl.write(sbMove, 0, TRANSFER_SIZE);
				sdl.write(sbMove, 0, TRANSFER_SIZE);
				tdl.start();
				while (checkRunning())
				{
					tdl.read(sbMove, 0, TRANSFER_SIZE);
					byte[] answer = OnBuffer(sbMove);
					watch = tdl.available();
					sdl.write(answer, 0, TRANSFER_SIZE);
					remView.afterWrite(watch);
				}
				tdl.drain();
				tdl.close();
				sdl.close();
			} catch (LineUnavailableException e)
			{
				e.printStackTrace();
			}
			error.log("Continuous Loop Stopped");
		}

		// ////////////////////Stop-logic
		private boolean runFlag = true;

		public synchronized boolean checkRunning()
		{
			return runFlag;
		}

		public synchronized void stopp()
		{
			runFlag = false;
		}
	}
}
