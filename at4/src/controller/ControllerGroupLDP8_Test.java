package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.swing.Timer;

import starter.MainFrame;

public class ControllerGroupLDP8_Test extends ControllerGroupLDP8 implements ActionListener
{
    int data = 0;

    public ControllerGroupLDP8_Test( MainFrame parent, MidiDevice.Info mi)
    {
        super(parent, mi);
        Timer timer = new Timer(3000, this);
        timer.setInitialDelay( 3000 );
        timer.setDelay(1000);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        data = (data + 1) & 127;
        try
        {
            MidiMessage mime = new ShortMessage( ShortMessage.CONTROL_CHANGE, (data & 3) +1, data);
            send( mime, 0l );
        }
        catch (InvalidMidiDataException e)
        {
            e.printStackTrace();
        }
    }
}
