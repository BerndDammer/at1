package controller;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;

import starter.MainFrame;

public class ControllerGroupMidiFighterTwister extends ControllerGroupBase
{
    private static final int POT_COUNT = 16;
    private final MidiPotControl pots[] = new MidiPotControl[POT_COUNT];
    private final MainFrame parent;
    
    public ControllerGroupMidiFighterTwister( MainFrame parent, MidiDevice.Info mi)
    {
        super(mi, "LDP8");
        this.parent = parent;

        for (int i = 0; i < POT_COUNT; i++)
        {
            pots[i] = new MidiPotControl( 0XB00000 + 0X0100 * i);
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
        for (int i = 0; i < POT_COUNT; i++)
        {
            if( pots[i].test(midiCode) )
            {
                // TODO Control value changed
            }
        }
    }
}
