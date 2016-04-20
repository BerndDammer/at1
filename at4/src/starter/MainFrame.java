package starter;

import function.FunctionPanel;
import gui_help.BorderPanel;
import gui_help.MyGC;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import platform.base.PlatformBase;
import platform.base.PlatformPanel;
import util.Util;
import controller.ControllerPanelBase;
import controller.ControllerPanelMapper;
import controller.ControllerSelectPanel;

public class MainFrame extends JFrame2 implements Runnable
{
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    private final MyGC gc = new MyGC();

    // ---------------------GUI_Elements
    private JPanel platformParameter = null;
    private JPanel panelEffect = null;
    private JPanel panelMidiControl = null;
    private final ControllerPanelMapper controllerPanelMapper = new ControllerPanelMapper(this);

    public MainFrame()
    {
        logger.info("Starting");
        SwingUtilities.invokeLater(this);
    }

    private class myPanel extends JPanel
    {
        private static final long serialVersionUID = 1L;

        myPanel()
        {
            setLayout(new GridBagLayout());
            // GridBagConstraints gc = new GridBagConstraints();
            gc.reset();

            // row 0 platform selector
            gc.fill = GridBagConstraints.HORIZONTAL;
            add(new PlatformPanel(), gc);
            gc.fill = GridBagConstraints.NONE;

            // row 1 PlatformParameter
            platformParameter = new BorderPanel("PlatformParameter");
            gc.nextRow();
            gc.pushHorizontal();
            add(platformParameter, gc);
            gc.popHorizontal();
            // placed later

            // row 2 Function
            gc.nextRow();
            gc.pushHorizontal();
            add(new FunctionPanel(MainFrame.this), gc);
            gc.popHorizontal();

            panelEffect = new BorderPanel("EffectPanel");
            gc.nextRow();
            gc.pushHorizontal();
            add(panelEffect, gc);
            gc.popHorizontal();

            // panelMidiControl = new BorderPanel("MidiControlPanel");
            // panelMidiControl = new
            // ControllerPanelRun(MidiSystem.getMidiDeviceInfo()[0]);
            panelMidiControl = controllerPanelMapper;
            gc.nextRow();
            gc.pushHorizontal();
            add(panelMidiControl, gc);
            gc.popHorizontal();

            gc.nextRow();
            gc.pushHorizontal();
            add(new ControllerSelectPanel(MainFrame.this), gc);
            gc.popHorizontal();
        }
    }

    @Override
    public void run()
    {
        setContentPane(new myPanel());
        setTitle("AudioSplicer");
        setVisible(true);
    }

    public void setEffectPanel(JPanel panel)
    {
        Container c = getContentPane();

        if (panelEffect != null) c.remove(panelEffect);
        panelEffect = panel != null ? panel : new BorderPanel("Off");

        gc.gridx = 0;
        gc.gridy = 3;
        gc.pushHorizontal();
        c.add(panelEffect, gc);
        gc.popHorizontal();

        invalidate();
        pack();
        Util.centerFrame(this);
    }

    public void newPlatform(PlatformBase pb)
    {
        Container c = getContentPane();

        if (platformParameter != null) c.remove(platformParameter);
        platformParameter = pb.getParameterPanel();

        gc.gridx = 0;
        gc.gridy = 1;
        gc.pushHorizontal();
        c.add(platformParameter, gc);
        gc.popHorizontal();

        invalidate();
        pack();
        Util.centerFrame(this);
        repaint();
    }

    public void newMidiControl(ControllerPanelBase cp)
    {
        Container c = getContentPane();

        if (panelMidiControl != null) c.remove(panelMidiControl);
        panelMidiControl = cp;
        gc.gridx = 0;
        gc.gridy = 4;
        gc.pushHorizontal();
        c.add(panelMidiControl, gc);
        gc.popHorizontal();

        invalidate();
        pack();
        Util.centerFrame(this);
        repaint();
    }

    public ControllerPanelMapper getControllerPanelMapper()
    {
        return controllerPanelMapper;
    }
}
