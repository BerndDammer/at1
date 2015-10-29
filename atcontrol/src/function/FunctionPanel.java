package function;

import effect.ArcTan;
import effect.ArcTan2;
import effect.ArcTan3;
import effect.LoopMono;
import effect.LoopMul1;
import effect.OutputTest;
import effect.Rotator1;
import effect.Tuner;
import gui_help.BorderPanel;
import gui_help.MyGC;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class FunctionPanel extends BorderPanel
{
	private static final Class<?>[] effects = { OutputTest.class, LoopMono.class, LoopMul1.class, Rotator1.class,
			ArcTan.class, ArcTan2.class, ArcTan3.class, Tuner.class };
	
	private final MyGC gc = new MyGC();

	public FunctionPanel()
	{
		super("Function");
		setLayout(new GridBagLayout());
		gc.reset();

		for (int i = 0; i < effects.length; i++)
		{
			gc.gridx = i % 2;
			gc.gridy = i / 2;
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
				clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e1)
			{
				e1.printStackTrace();
			}
		}
	}

}
