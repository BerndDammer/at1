package old;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import starter.Central;
import starter.error;

public class Loop2 extends JDialog
{
	private final Runner runner;

	public Loop2(JFrame parent)
	{
		super(parent, true);
		setTitle("Mixer View");
		setBounds(30, 40, 600, 400);
		setContentPane(new myPanel());
		runner = new Runner();
		runner.start();

		setVisible(true);
	}

	private class myPanel extends JPanel
	{
		public myPanel()
		{
			add(new StopButton());
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
				e1.printStackTrace();
			}
			Loop2.this.setVisible(false);
			Loop2.this.dispose();
		}
	}

	// ///////////////////////declaration for runner
	private static final AudioFormat af = new AudioFormat((float) 48000.0, 16, 2, true, false);
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

	private static final int READ_BUFFER_SIZE = 48000 * 4;
	private static final int WRITE_BUFFER_SIZE = 48000 * 4;

	// ---------------
	// private static final byte[] sbIn = new byte[BUFFER_SIZE];
	// private static final byte[] sbOut = new byte[BUFFER_SIZE];

	private static final int READSIZE = 48 * 4;
	// private static final int READCOUNT = 1000;

	private static final byte[] sbMove = new byte[READSIZE];

	private class Runner extends Thread
	{

		public void run()
		{
			// --------------init
			SourceDataLine sdl = Central.getGoutputSelector().getSourceDataLine();
			TargetDataLine tdl = Central.getGinputSelector().getTargetDataLine();

			try
			{
				tdl.open(af, READ_BUFFER_SIZE);
				sdl.open(af, WRITE_BUFFER_SIZE);

				sdl.start();
				tdl.start();
				while (checkRunning())
				{
					tdl.read(sbMove, 0, READSIZE);
					sdl.write(sbMove, 0, READSIZE);
				}
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
