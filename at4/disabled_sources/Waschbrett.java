package effect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import function.IFunction2;
import gui_help.BorderPanel;

public class Waschbrett implements IFunction2
{
	private static final long serialVersionUID = 1L;
	private static final double SAMPLE_FREQ = 48000.0;

	transient private double agl_factor = 1000.0 * 2.0 * Math.PI / SAMPLE_FREQ;

    private static final double wellendicke = 0.1;
    private static final double wellenzahl = 5;
    private static final double ff = wellenzahl * Math.PI * 2.0;
	
	long sampleCounter;

	public Waschbrett()
	{
		EffectDialog2 d = new EffectDialog2(this);
		d.startIt();
		d.setVisible(true);
	}

	@Override
	public double nextSample(double l, double r)
	{
	    double s = (l + r) * 0.5;
	    double result = s;
	    s = Math.sin( s * ff) * wellendicke;
	    result += s;
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
				addItem( new DNote(-1,4));
				addItem( new DNote(-1,9));
				addItem( new DNote(0,2));
				addItem( new DNote(0,7));
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
			super("Out of function");
			add( new NoteChoice());
		}
	}
}
