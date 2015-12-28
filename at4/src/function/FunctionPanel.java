package function;

import effect.ArcTan4;
import effect.CubeMix;
import effect.Delay;
import effect.EffectDialog;
import effect.LoopMono;
import effect.LoopMul1;
import effect.LowMix;
import effect.Noiser;
import effect.OutputTest;
import effect.Reverb;
import effect.Rotator2;
import effect.Tuner;
import effect.Waschbrett;
import gui_help.BorderPanel;
import gui_help.MyGC;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import starter.MainFrame;

public class FunctionPanel extends BorderPanel
{
    private static final long serialVersionUID = 1L;

    private static final int X_PER_LINE = 4;

    // private static final Class<?>[] effects = { OutputTest.class,
    // LoopMono.class, LoopMul1.class, Rotator1.class,
    // ArcTan.class, ArcTan2.class, ArcTan3.class, Tuner.class,
    // Waschbrett.class, Rotator2.class };
    private static final Class<?>[] effects = { OutputTest.class, LoopMono.class, LoopMul1.class, ArcTan4.class, Tuner.class,
            Waschbrett.class, Rotator2.class, CubeMix.class, Reverb.class, Delay.class, Noiser.class, LowMix.class };

    private final MyGC gc = new MyGC();

    private final MainFrame parent;

    public FunctionPanel(MainFrame parent)
    {
        super("Function");
        this.parent = parent;

        setLayout(new GridBagLayout());
        gc.reset();

        for (int i = 0; i < effects.length; i++)
        {
            gc.gridx = i % X_PER_LINE;
            gc.gridy = i / X_PER_LINE;
            add(new EffectStarter(effects[i]), gc);
        }
    }

    private class EffectStarter extends JButton implements ActionListener
    {
        private static final long serialVersionUID = 1L;
        private final Class<?> clazz;

        EffectStarter(Class<?> effects)
        {
            this.clazz = effects;
            setText(effects.getSimpleName());
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                EffectDialog ed = (EffectDialog) clazz.newInstance();
                ed.setParent(parent);
                ed.commitControls();
            }
            catch (InstantiationException | IllegalAccessException e1)
            {
                e1.printStackTrace();
            }
        }
    }

}
