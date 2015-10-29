package controller;

import gui_help.MyGC;
import gui_help.ShowText;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import starter.Central;
import util.Util;

public class ControllerTestDialog extends JDialog implements WindowListener, Receiver
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger("miditest");

	private final MyGC gc = new MyGC();
	private Transmitter tm;

	private final Map<Integer, ShowText> groupViews;

	public ControllerTestDialog(MidiDevice.Info mi)
	{
		super(Central.getMainFrame(), true);
		addWindowListener(this);
		setTitle("Controller Display");
		setBounds(30, 40, 600, 400);
		setContentPane(new myPanel());
		Util.centerFrame(this);

		groupViews = new TreeMap<>();

		logger.info("Start init midi");
		try
		{
			MidiDevice md = MidiSystem.getMidiDevice(mi);
			tm = md.getTransmitter();
			md.open();
			tm.setReceiver(this);
			logger.info("End init midi");
		} catch (MidiUnavailableException e)
		{
			e.printStackTrace();
			logger.info("Problem init midi");
		}
		// init thread blocks here
		setVisible(true);
	}

	private class myPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public myPanel()
		{
			setLayout(new GridBagLayout());
			gc.reset();
			add(new StopButton(), gc);
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
			killDialog();
		}
	}

	// /////////////////////////////////////////////////////////////////////
	// Midi Receiver
	private static final int VIEW_ROWS = 5;

	@Override
	public void send(MidiMessage message, long timeStamp)
	{
		// /build key Value
		ShowText actView = null;
		int key = getKey( message);

		if (!groupViews.containsKey(key))
		{
			actView = new ShowText();
			groupViews.put(key, actView);
			gc.gridy = 1 + (groupViews.size() -1)/ VIEW_ROWS;
			gc.gridx = (groupViews.size()-1) % VIEW_ROWS;
			add(actView, gc);
		} else
		{
			actView = groupViews.get(key);
		}
		StringBuilder sb = actView.get();
		sb.setLength(0);
		for (int i = 0; i < message.getLength(); i++)
		{
			addHexByte(sb, message.getMessage()[i]);
			sb.append(' ');
		}
		sb.append("       ");
		actView.put();
	}

	private int getKey(MidiMessage mm)
	{
		int result = 0;
		if (mm.getLength() > 0)
			result |= mm.getMessage()[0] << 24;
		if (mm.getLength() > 1)
			result |= (mm.getMessage()[1] << 16) & 0X00FF0000;
		if (mm.getLength() > 2)
			result |= (mm.getMessage()[2] << 8) & 0X0000FF00;
		if (mm.getLength() > 3)
			result |= (mm.getMessage()[3] << 0) & 0X000000FF;

		if( (result & 0XFF00_0000) == 0XB000_0000)
		{
			result &= 0XFFFF0000;
		}
		else
		{
			result &= 0XFF00_0000;
		}
		return result;
	}

	private static final String HEXCHAR = "0123456789ABCDEF";

	private void addHexByte(StringBuilder sb, byte b)
	{
		sb.append(HEXCHAR.charAt((b >> 4) & 15));
		sb.append(HEXCHAR.charAt((b >> 0) & 15));
	}

	@Override
	public void close()
	{

	}

	// ///////////////////////////////////////////////////////////////////////
	private void killDialog()
	{
		logger.info("Start closing midi");
		tm.close();
		logger.info("Midi Closed");
		setVisible(false);
		dispose();
		logger.info("Dialog dispose");
	}

	// ///////////////////////////////////////////////////////////////////////
	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		killDialog();
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
