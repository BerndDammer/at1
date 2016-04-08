package effect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import function.IFunction2;
import gui_help.BorderPanel;

public class Tuner implements IFunction2
{
	private static final long serialVersionUID = 1L;
	private static final double SAMPLE_FREQ = 48000.0;
	// private static final double SIG_FREQ = 1000.0;
	// private static final double AGL_FACT = SIG_FREQ * 2.0 * Math.PI /
	// SAMPLE_FREQ;

	transient private double agl_factor = 1000.0 * 2.0 * Math.PI / SAMPLE_FREQ;

	long sampleCounter;

	public Tuner()
	{
		EffectDialog2 d = new EffectDialog2(this);
		d.startIt();
		d.setVisible(true);
	}

	@Override
	public double nextSample(double l, double r)
	{
		sampleCounter++;
		double al = (double) (sampleCounter) * agl_factor;
		double result = Math.sin(al);
		result = result * result * result * 0.27;
		return result;
	}

	@Override
	public JPanel getPanel()
	{
		return myPanel;
	}

	private final MyPanel myPanel = new MyPanel();

	private class MyPanel extends BorderPanel
	{
		class NoteChoice extends JComboBox<DNote> implements ActionListener
		{
			NoteChoice()
			{
                addItem( new DNote(-2,11));
                addItem( new DNote(-1,4));
				addItem( new DNote(-1,9));
				addItem( new DNote(0,2));
				addItem( new DNote(0,7));

                addItem( new DNote(-1,11));
                addItem( new DNote(0,4));
				addItem( new DNote(0,9));
				addItem( new DNote(1,2));
				addItem( new DNote(1,7));
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent e)
			{
				DNote note = getItemAt(getSelectedIndex());
				agl_factor = note.getFrequency()
				* 2.0 * Math.PI / SAMPLE_FREQ;
			}
		}

		private MyPanel()
		{	
			super("Select Note");
			add( new NoteChoice());
		}
	}
}
