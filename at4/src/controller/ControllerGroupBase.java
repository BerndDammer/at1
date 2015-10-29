package controller;

import java.util.logging.Logger;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public abstract class ControllerGroupBase implements Receiver
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger("miditest");

    private Transmitter tm = null;
    private MidiDevice md = null;

    public ControllerGroupBase(MidiDevice.Info mi, String title)
    {
        logger.info("Start init midi");
        try
        {
            md = MidiSystem.getMidiDevice(mi);
            tm = md.getTransmitter();
            md.open();
            tm.setReceiver(this);
            logger.info("End init midi");
        }
        catch (MidiUnavailableException e)
        {
            logger.info("Problem init midi");
            logger.throwing("ControllerGroupBase", "init", e);
        }
    }

    // /////////////////////////////////////////////////
    // implement MidiReceiver
    @Override
    public void send(MidiMessage message, long timeStamp)
    {
        gotMidiMessage(message);
        int key = getMidiCombined(message);
        gotMidiCombined(key);
    }

    @Override
    public void close()
    {

    }

    // //////////////////////////////////////////////////
    protected abstract void gotMidiMessage(MidiMessage mm);

    protected abstract void gotMidiCombined(int midiCode);

    private int getMidiCombined(MidiMessage mm)
    {
        int result = 0;
        for (int i = 0; i < mm.getLength(); i++)
        {
            result <<= 8;
            result |= mm.getMessage()[i];
        }
        return result;
    }

    // ///////////////////////////////////////////////////////////////////////
    public void killDialog()
    {
        logger.info("Start closing midi");
        tm.close();
        logger.info("Midi Closed");
    }
}
