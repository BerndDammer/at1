package controller;

import gui_help.BorderPanel;

import java.util.logging.Logger;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public abstract class ControllerPanelBase extends BorderPanel implements Receiver
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger("miditest");

	private Transmitter tm = null;


	public ControllerPanelBase(MidiDevice.Info mi, String title)
	{
		super( title );

	}


	///////////////////////////////////////////////////
	//implement MidiReceiver
	@Override
	public void send(MidiMessage message, long timeStamp)
	{
	    gotMidiMessage(message);
		int key = getMidiCombined(message);
		gotMidiCombined(key);
	}
    protected abstract void gotMidiMessage(MidiMessage mm);
    protected abstract void gotMidiCombined(int midiCode);
	

	private int getMidiCombined(MidiMessage mm)
	{
	    int result = 0;
	    for( int i = 0; i < mm.getLength(); i++ )
	    {
	        result <<= 8;
	        result |= mm.getMessage()[i];
	    }
			return result;
	}

	@Override
	public void close()
	{

	}

	// ///////////////////////////////////////////////////////////////////////
	public void killDialog()
	{
		logger.info("Start closing midi");
		tm.close();
		logger.info("Midi Closed");
	}
}
