package controller;

import gui_help.MyGC;
import gui_help.ShowText;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Transmitter;

public class ControllerPanelTest extends ControllerPanelBase
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger("miditest");

    private final MyGC gc = new MyGC();
    private Transmitter tm;

    private final Map<Integer, ShowText> groupViews;

    public ControllerPanelTest(MidiDevice.Info mi)
    {
        super(mi, "MidiControl");

        groupViews = new TreeMap<>();

    }

//    private class myPanel extends JPanel
//    {
//        private static final long serialVersionUID = 1L;
//
//        public myPanel()
//        {
//            setLayout(new GridBagLayout());
//            gc.reset();
//            add(new StopButton(), gc);
//        }
//    }

//    private class StopButton extends JButton implements ActionListener
//    {
//        private static final long serialVersionUID = 1L;
//
//        StopButton()
//        {
//            setText("Stop");
//            addActionListener(this);
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e)
//        {
//            killDialog();
//        }
//    }

    private static final int VIEW_ROWS = 5;

    @Override //nothing to do here
    protected void gotMidiCombined(int midiCode)
    {
    }

    @Override
    protected void gotMidiMessage(MidiMessage message)
    {
        // /build key Value
        ShowText actView = null;
        int key = getKey(message);

        if (!groupViews.containsKey(key))
        {
            actView = new ShowText();
            groupViews.put(key, actView);
            gc.gridy = 1 + (groupViews.size() - 1) / VIEW_ROWS;
            gc.gridx = (groupViews.size() - 1) % VIEW_ROWS;
            add(actView, gc);
        }
        else
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
        if (mm.getLength() > 0) result |= mm.getMessage()[0] << 24;
        if (mm.getLength() > 1) result |= (mm.getMessage()[1] << 16) & 0X00FF0000;
        if (mm.getLength() > 2) result |= (mm.getMessage()[2] << 8) & 0X0000FF00;
        if (mm.getLength() > 3) result |= (mm.getMessage()[3] << 0) & 0X000000FF;

        if ((result & 0XFF00_0000) == 0XB000_0000)
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
}
