package at1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class MixerSelector extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final MODE mode;
	private final MixerChoice mixerChoice;
	private final LineChoice lineChoice;

	// ----------------result-functions
	public SourceDataLine getSourceDataLine()
	{
		switch (mode)
		{
		case OUTPUT:
			return (SourceDataLine) lineChoice.getSelectedItem();
		default:
			error.exit("wrong mode");
			break;
		}
		return null;
	}

	public TargetDataLine getTargetDataLine()
	{
		switch (mode)
		{
		case INPUT:
			return (TargetDataLine) lineChoice.getSelectedItem();
		default:
			error.exit("wrong mode");
			break;
		}
		return null;
	}

	// //////////////////////construct
	public enum MODE
	{
		INPUT, OUTPUT;
	}

	protected class Element
	{
		public Mixer.Info mi;
		public List<SourceDataLine> sources;
		public List<TargetDataLine> targets;

		public int selectedIndex;

		public Element()
		{
			selectedIndex = 0;
		}

		@Override
		public String toString()
		{
			return mi.getName();
		}
	}

	public MixerSelector(MODE mode)
	{
		this.mode = mode;
		lineChoice = new LineChoice();
		mixerChoice = new MixerChoice();

		setLayout(new BorderLayout());
		add(mixerChoice, BorderLayout.NORTH);
		add(lineChoice, BorderLayout.SOUTH);
	}

	// ///////////////////////----Gui-Elements
	private class MixerChoice extends JComboBox<MixerSelector.Element>
			implements ActionListener
	{
		private MixerChoice()
		{
			Mixer.Info[] fmi = AudioSystem.getMixerInfo();
			addActionListener(this);
			for (Mixer.Info mi : fmi)
			{
				Mixer mixer = AudioSystem.getMixer(mi);

				Element e = new Element();
				e.mi = mi;
				e.sources = new LinkedList<>();
				e.targets = new LinkedList<>();
				Line.Info[] lines;
				switch (mode)
				{
				case INPUT:
					lines = AudioSystem.getMixer(mi).getTargetLineInfo();
					break;
				case OUTPUT:
					lines = AudioSystem.getMixer(mi).getSourceLineInfo();
					break;
				default:
					lines = null;
					assert false;
				}
				for (Line.Info li : lines)
				{
					Line line;
					try
					{
						line = mixer.getLine(li);
						switch (mode)
						{
						case INPUT:
							if (line instanceof TargetDataLine)
							{
								e.targets.add((TargetDataLine) line);
							}
							break;
						case OUTPUT:
							if (line instanceof SourceDataLine)
							{
								e.sources.add((SourceDataLine) line);
							}
							break;
						default:
							assert false;
							break;
						}
					} catch (LineUnavailableException e1)
					{
						// TODO must do something qqqq
						assert false;
					}
				}
				switch (mode)
				{
				case INPUT:
					if (!e.targets.isEmpty())
					{
						addItem(e);
					}
					break;
				case OUTPUT:
					if (!e.sources.isEmpty())
					{
						addItem(e);
					}
					break;
				default:
					assert false;
					break;
				}
			}
			{
				int test = getItemCount();
				assert test > 0;
			}
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			lineChoice.newLines(getItemAt(getSelectedIndex()));
			super.actionPerformed(e);
		}
	}

	private class LineChoice extends JComboBox<DataLine>
	{
		private void newLines(Element element)
		{
			if (element == null)
				return;
			removeAllItems();
			switch (mode)
			{
			case INPUT:
				for (TargetDataLine tdl : element.targets)
				{
					addItem(tdl);
				}
				break;
			case OUTPUT:
				for (SourceDataLine tdl : element.sources)
				{
					addItem(tdl);
				}
				break;
			default:
				assert false;
			}
			setEnabled(getItemCount() != 1);
		}
	}
}
