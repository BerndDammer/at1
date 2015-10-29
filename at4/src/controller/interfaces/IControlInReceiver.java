package controller.interfaces;

import controller.MidiPotControl;

public interface IControlInReceiver
{
    public void connectPot(MidiPotControl midiPotControl);
    void newPotValue127(int rawValue);
}
