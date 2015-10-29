package controller;

import gui_help.MyGC;
import controller.interfaces.IControlInReceiver;
import controller.interfaces.IControlInTransmitter;

public class MidiPotControl implements IControlInTransmitter
{
    private final int triggerValue;
    private final int triggerMask;
    private final int valueMask;
    //private double pot = 0.0;
    private int actPot = 0;
    private IControlInReceiver controllerElementView;
    
    ////////////////Gui Helper
    private final MyGC gc = new MyGC();

    
    public MidiPotControl(int value)
    {
        triggerValue = value;
        triggerMask = 0XFFFF80;
        valueMask = 0X7F;
    }
    public boolean test( int actValue)
    {
        boolean trigger = false;
        trigger = (actValue & triggerMask) == triggerValue;
        if( trigger)
        {
            actPot = actValue & valueMask;
            controllerElementView.newPotValue127(actValue);
        }
        return trigger;
    }
    @Override
    public void setView(IControlInReceiver controllerElementView)
    {
        this.controllerElementView =controllerElementView;
    }
    //////////////////////////////////////////////
}
