package controller;

import gui_help.MyGC;
import gui_help.ShowText;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class ControllerElementView extends JPanel implements IPot127Listener
{
    private static final Color COLOR_BACKGROUND = new Color(100, 100, 100);
    private MidiPotControl midiPotControl = null;

    private final MyGC gc = new MyGC();

    private final ShowText functionName = new ShowText("Function");
    private final ShowText functionValue = new ShowText("OutputVal");
    private final ShowText controllerName = new ShowText("Input");
    private final ShowText controllerValue = new ShowText("Value");

    private final ControllerPanelMapper parent;
    private final int index;
    private ControlReceiveParameter controlReceiveParameter = null;
    private double factor, offset;
    
    public ControllerElementView(ControllerPanelMapper parent, int index)
    {
        this.parent = parent;
        this.index = index;
        setLayout(new GridBagLayout());
        gc.reset();
        add(functionName, gc);
        gc.nextRow();
        add(functionValue, gc);
        gc.nextRow();
        add(controllerName, gc);
        gc.nextRow();
        add(controllerValue, gc);
        gc.nextRow();
        updateViewState();
    }

    public void connectPot(MidiPotControl midiPotControl)
    {
        this.midiPotControl = midiPotControl;
        midiPotControl.setView(this);
        updateViewState();
    }

    @Override
    public void newPotValue127(int rawValue)
    {
        rawValue &= 127;
        double va;
        controllerValue.setText(Integer.toString(rawValue));
        if( controlReceiveParameter != null)
        {
            va = (double)rawValue * factor + offset;
        }
        else va = 0.0;
        functionValue.setText( String.format("%+5.2f", va));
        parent.potChanged(va, index);
    }

    public void setUseParameter(ControlReceiveParameter controlReceiveParameter)
    {
        this.controlReceiveParameter = controlReceiveParameter;
        functionName.setText(controlReceiveParameter.name);
        factor = (controlReceiveParameter.max - controlReceiveParameter.min) / 127.0;
        offset = controlReceiveParameter.min;
        updateViewState();
    }

    private void updateViewState()
    {
        int r, g, b;
        r = COLOR_BACKGROUND.getRed();
        g = COLOR_BACKGROUND.getGreen();
        b = COLOR_BACKGROUND.getBlue();
        if (midiPotControl != null) r += 60;
        if (controlReceiveParameter != null) b += 80;
        Color act = new Color(r, g, b);
        setBackground(act);
    }
}
