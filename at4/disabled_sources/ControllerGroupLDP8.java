package controller;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;

import starter.MainFrame;

public class ControllerGroupLDP8 extends ControllerGroupBase
{
    private final MidiPotControl pots[] = new MidiPotControl[8];
    private final MainFrame parent;
    
    public ControllerGroupLDP8( MainFrame parent, MidiDevice.Info mi)
    {
        super(mi, "LDP8");
        this.parent = parent;

        for (int i = 0; i < 8; i++)
        {
            pots[i] = new MidiPotControl( 0XB00000 + 0X0100 * (i+1));
            parent.getControllerPanelMapper().add(pots[i], i);
        }
    }

    /////////////////////////////////////////////
    @Override
    protected void gotMidiMessage(MidiMessage mm)
    {
        // dummy must implement
    }

    @Override
    protected void gotMidiCombined(int midiCode)
    {
        for (int i = 0; i < 8; i++)
        {
            if( pots[i].test(midiCode) )
            {
                // TODO Control value changed
            }
        }
    }
}
