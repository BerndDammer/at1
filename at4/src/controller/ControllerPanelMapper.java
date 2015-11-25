package controller;

import gui_help.BorderPanel;

import java.awt.GridBagConstraints;
import java.util.logging.Logger;

import starter.MainFrame;
import controller.interfaces.IControlOutTransmitter.ControlReceiveParameter;
import controller.interfaces.IFromMidi;
import controller.interfaces.IPotsOut;

public class ControllerPanelMapper extends BorderPanel implements IFromMidi, IPotsOut
{
    private static final long serialVersionUID = 1L;
    //private static final Logger logger = Logger.getLogger("miditest");

    private static final int BRIDGE_COUNT = 16;

    private final double writeableForMidi[] = new double[BRIDGE_COUNT];
    private final double readableForEffect[] = new double[BRIDGE_COUNT];
    private final SimpleLock lockTransfer = new SimpleLock();
    

    private static final int POT_COUNT = 16;

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
            writeableForMidi[i] = 0;
            readableForEffect[i] = 0;
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

    // ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////
    // /syncronizing of data traversal
    //
    //
    private final class SimpleLock
    {
        private static final long TIMEOUT = 700;
        //private int counter = 0;
        private boolean isReading = false;
        private boolean isWriting = false;

        public synchronized void lockWriting()
        {
            long time;
            if( isWriting ) problem("Double Write Entry");
            isWriting = true;
            time = System.currentTimeMillis();
            if(isReading)
            {
                try
                {
                    wait();
                }
                catch (InterruptedException e)
                {
                    problem("Write Interrupted");
                }
                if( System.currentTimeMillis() - time > TIMEOUT) problem("Long Write lock");
            }
        }
        
        public synchronized void unlockWriting()
        {
            if( !isWriting ) problem("Unexpected Write unlock");
            if( isReading)
                notify();
            isWriting = false;
        }
        public synchronized void lockReading()
        {
            long time;
            if( isReading ) problem("Double Read Entry");
            isReading = true;
            time = System.currentTimeMillis();
            if(isWriting)
            {
                try
                {
                    wait();
                }
                catch (InterruptedException e)
                {
                    problem("Read Interrupted");
                }
                if( System.currentTimeMillis() - time > TIMEOUT) problem("Long Read lock");
            }
        }
        
        public synchronized void unlockReading()
        {
            if( !isReading ) problem("Unexpected Write unlock");
            if( isWriting)
                notify();
            isReading = false;
        }
        private void problem(String text)
        {
            throw new Error( text );
        }
    }

    @Override
    public double[] lockValuesForSample()
    {
        lockTransfer.lockReading();
        return readableForEffect;
    }
    @Override
    public void freeValuesForSample()
    {
        lockTransfer.unlockReading();
    }

    public void potChanged(double d, int index)
    {
        writeableForMidi[index] = d;
        updatePotValues();
    }

    // ////////////////////////////////////////
    private final void updatePotValues()
    {
        lockTransfer.lockWriting();
        System.arraycopy(writeableForMidi, 0, 
                readableForEffect, 0, POT_COUNT);
        lockTransfer.unlockWriting();
    }

}
