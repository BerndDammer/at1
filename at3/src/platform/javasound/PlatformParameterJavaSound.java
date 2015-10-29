package platform.javasound;

import gui_help.BorderPanel;
import gui_help.MyGC;

import java.awt.GridBagLayout;
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
import javax.swing.JButton;
import javax.swing.JComboBox;

import starter.Central;
import starter.MainFrame;
import util.DIR;

public class PlatformParameterJavaSound extends BorderPanel
{
	private static final long serialVersionUID = 1L;

	private final MixerChoice inputMixerChoice;
	private final LineChoice inputLineChoice;
	private final MixerChoice outputMixerChoice;
	private final LineChoice outputLineChoice;

	private final MyGC gc = new MyGC();

	public PlatformParameterJavaSound()
	{
		super("Parametre for Java Sound");

		inputLineChoice = new LineChoice(DIR.INPUT);
		inputMixerChoice = new MixerChoice(DIR.INPUT);
		outputLineChoice = new LineChoice(DIR.OUTPUT);
		outputMixerChoice = new MixerChoice(DIR.OUTPUT);

		setLayout( new GridBagLayout());
		gc.reset();
		add(inputMixerChoice, gc);
		gc.gridx++;
		add(outputMixerChoice, gc);

		gc.nextRow();
		add(inputLineChoice, gc);
		gc.gridx++;
		add(outputLineChoice, gc);

		gc.nextRow();
		add(new ViewStarter(), gc);
		gc.gridx++;
		//add(outputLineChoice, gc);

		
	}

	// ----------------result-functions
	public SourceDataLine getSourceDataLine()
	{
		return (SourceDataLine) outputLineChoice.getSelectedItem();
	}

	public TargetDataLine getTargetDataLine()
	{
		return (TargetDataLine) inputLineChoice.getSelectedItem();
	}

	public class Element
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

	// ///////////////////////----Gui-Elements
	private class MixerChoice extends JComboBox<Element> implements ActionListener
	{
		private final DIR dir;

		private MixerChoice(DIR dir)
		{
			this.dir = dir;
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
				switch (dir)
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
						switch (dir)
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
				switch (dir)
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
			switch (dir)
			{
			case INPUT:
				//PlatformParameterJavaSound.this.inputLineChoice.newLines(getItemAt(getSelectedIndex()));
				inputLineChoice.newLines(getItemAt(getSelectedIndex()));
				break;
			case OUTPUT:
				outputLineChoice.newLines(getItemAt(getSelectedIndex()));
				break;
			default:
				assert false;
			}

			super.actionPerformed(e);
		}
	}

	private class LineChoice extends JComboBox<DataLine>
	{
		private final DIR dir;

		LineChoice(DIR dir)
		{
			this.dir = dir;

		}

		private void newLines(Element element)
		{
			if (element == null)
				return;
			removeAllItems();
			switch (dir )
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
	private class ViewStarter extends JButton implements ActionListener
	{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ViewStarter()
		{
            setText( "Mixer Tree View");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new MixerView( Central.getMainFrame());
		} 
	}

}
