package controller;

import javax.swing.JPanel;

public class ControllerElementBase extends JPanel
{
    private final ControllerPanelMapper parent;
    
    public ControllerElementBase(ControllerPanelMapper parent)
    {
        this.parent = parent;
    }
    protected void newDouble(double d)
    {
        StringBuilder sb = get();
        sb.setLength(0);
        sb.append(String.format("%+3.2f", d));
        put();
    }
}
