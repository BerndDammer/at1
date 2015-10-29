package controller;

import gui_help.BorderPanel;

import java.awt.GridBagConstraints;
import java.util.logging.Logger;

import starter.MainFrame;
import controller.interfaces.IFromMidi;
import controller.interfaces.IControlOutTransmitter.ControlReceiveParameter;
import controller.interfaces.IPotsOut;

public class ControllerPanelMapper extends BorderPanel implements IFromMidi, IPotsOut
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger("miditest");

    // private final List<MidiPotControl> pots = new LinkedList<>();
    private static final int BRIDGE_COUNT = 16;
    // private final MidiPotControl pots[] = new MidiPotControl[BRIDGE_COUNT];

    private final double left[] = new double[BRIDGE_COUNT];
    private final double mid[] = new double[BRIDGE_COUNT];
    private final double right[] = new double[BRIDGE_COUNT];

    // private final MidiPotControl pots[] = new MidiPotControl[BRIDGE_COUNT];

    private static final int POT_COUNT = 8;

    private final ControllerElementView[] views = new ControllerElementView[POT_COUNT];

    public ControllerPanelMapper(MainFrame parent)
    {
        super("Mapper");
        // setLayout( new GridBagLayout());
        gc.reset();
        // gc.fill = GridBagConstraints.BOTH;
        gc.fill = GridBagConstraints.NONE;
        // gc.weightx = 1.0;
        // gc.weighty = 1.0;

        // for (MidiPotControl mpc : pots)
        // {
        // add(mpc, gc);
        // gc.gridx++;
        // if (gc.gridx >= 4)
        // {
        // gc.nextRow();
        // }
        // }

        for (int i = 0; i < POT_COUNT; i++)
        {
            ControllerElementView v = new ControllerElementView(this, i);
            views[i] = v;
            gc.gridx = i % 4;
            gc.gridy = i / 4;
            add(v, gc);
        }

        for (int i = 0; i < BRIDGE_COUNT; i++)
        {
            left[i] = 0;
            mid[i] = 0;
            right[i] = 0;

        }

    }

    // /////////////////////////////////////////
    public void newControllerGroupBase(ControllerGroupBase controllerGroupBase)
    {

    }

    // /////////////////////////////
    // from midi
    @Override
    public void add(MidiPotControl mpc, int index)
    {
        views[index].connectPot(mpc);
    }

    public void add(ControlReceiveParameter mpc, int index)
    {
        views[index].setUseParameter(mpc);
    }

    // /////////////////////////////////
    // to Effect
//    public double get0()
//    {
//        return right[0];
//    }
//
//    public double get1()
//    {
//        return right[1];
//    }
//
//    public double get2()
//    {
//        return right[2];
//    }
//
//    public double get3()
//    {
//        return right[3];
//    }

    // ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////
    // /syncronizing of data traversal
    //
    //
    private final SimpleLock lockLeft = new SimpleLock();
    private final SimpleLock lockRight = new SimpleLock();
    
    private final class SimpleLock
    {
        private int counter = 0;
        public synchronized void lock()
        {
            while(counter != 0)
                try
                {
                    wait();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            counter++;
        }
        
        public synchronized void unlock()
        {
            counter--;
            if(counter != 0)
                notify();
        }
    }

    @Override
    public double[] lockValuesForSample()
    {
        lockRight.lock();
        return right;
    }
    @Override
    public void freeValuesForSample()
    {
        lockRight.unlock();
    }

    public void potChanged(double d, int index)
    {
        left[index] = d;
        left2mid();
    }

    // ////////////////////////////////////////
    private final synchronized void left2mid()
    {
        lockLeft.lock();
        System.arraycopy(left, 0, mid, 0, POT_COUNT);
        lockLeft.unlock();
    }

    private final synchronized void mid2right()
    {
        lockLeft.lock();
        lockRight.lock();
        System.arraycopy(mid, 0, right, 0, POT_COUNT);
        lockRight.unlock();
        lockLeft.unlock();
    }
}
