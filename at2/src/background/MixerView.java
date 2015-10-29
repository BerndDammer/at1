package background;

import java.awt.BorderLayout;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MixerView extends JDialog
{
	private static final long serialVersionUID = 1L;

	public MixerView(JFrame parent)
	{
		super(parent, true);
		setTitle("Mixer View");
		setBounds(30, 40, 600, 400);
		setContentPane(new myPanel());

		setVisible(true);
	}

	private class myPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		myPanel()
		{
			setLayout(new BorderLayout());
			// add( new myTree(), BorderLayout.CENTER);
			add(new myScrollPane(), BorderLayout.CENTER);
		}
	}

	private class myScrollPane extends JScrollPane
	{
		private static final long serialVersionUID = 1L;

		myScrollPane()
		{
			super(new myTree());
		}
	}

	private class myTree extends JTree
	{
		private static final long serialVersionUID = 1L;

		DefaultTreeModel model = new DefaultTreeModel(new L0Top());
		private myTree()
		{
			setModel(model);

		}

		private class L0Top extends DefaultMutableTreeNode
		{
			private static final long serialVersionUID = 1L;

			private L0Top()
			{
				Mixer.Info[] fmi = AudioSystem.getMixerInfo();
				for (Mixer.Info mi : fmi)
				{
					add(new L1Mixer(mi));
				}
			}

			@Override
			public String toString()
			{
				return ("Mixer Lines ---->>>>>");
			}

		}

		private class L1Mixer extends DefaultMutableTreeNode
		{
			private static final long serialVersionUID = 1L;

			private final Mixer.Info mi;

			private L1Mixer(Mixer.Info mi)
			{
				this.mi = mi;
				Mixer mixer = AudioSystem.getMixer(mi);

				for (Line.Info li : mixer.getSourceLineInfo())
				{
					add(new L2Line(mixer, li));
				}
				for (Line.Info li : mixer.getTargetLineInfo())
				{
					add(new L2Line(mixer, li));
				}
			}

			@Override
			public String toString()
			{
				return (mi.getName());
			}

		}

		private class L2Line extends DefaultMutableTreeNode
		{
			private static final long serialVersionUID = 1L;

			private final Line.Info li;
			DefaultMutableTreeNode controls = new DefaultMutableTreeNode(
					"Controls");

			private L2Line(Mixer mixer, Line.Info li)
			{
				this.li = li;
				add(new DefaultMutableTreeNode("Implementer Class : "
						+ li.getClass().getName()));
				try
				{
					Line line = mixer.getLine(li);
					if (line instanceof Port)
					{
						Port p = (Port) line;
						p.open();
						// TODO cast
						Port.Info pi = (Port.Info)p.getLineInfo();
						String cHeader = "Controls : ... Port.Info" + pi.getName() + " : ";
						controls = new DefaultMutableTreeNode(cHeader);

						Control[] fcc = p.getControls();
						if (fcc.length > 0)
						{
							for (Control cc : fcc)
							{
								controls.add(new L3Control(cc));
							}
							add(controls);
						}
						p.close();
					}

					// ----try-controls-of-line
					// if( li.matches( new Line.Info( SourceDataLine.Info.class
					// )))
					DefaultMutableTreeNode tn;
					if (line instanceof SourceDataLine)
					{
						tn = new DefaultMutableTreeNode("Source Audio Formats");
						if (buildAudioFormats((DataLine.Info) li, tn))
							add(tn);
					}
					// if( li.matches( new Line.Info( TargetDataLine.Info.class
					// )))
					if (line instanceof TargetDataLine)
					{
						tn = new DefaultMutableTreeNode("Target Audio Formats");
						if (buildAudioFormats((DataLine.Info) li, tn))
							add(tn);
					}

				} catch (LineUnavailableException e)
				{
					add(new DefaultMutableTreeNode("Error getting Line : "
							+ e.getMessage()));
				}
			}

			private boolean buildAudioFormats(DataLine.Info dli,
					DefaultMutableTreeNode tn)
			{
				boolean result;
				AudioFormat[] faf = dli.getFormats();
				result = faf.length > 0;
				if (result)
				{
					for (AudioFormat af : faf)
					{
						tn.add(new L3AudioFormat(af));
					}
				}
				return result;
			}

			@Override
			public String toString()
			{
				return (li.getLineClass().getName());
			}
		}

		private class L3Control extends DefaultMutableTreeNode
		{
			private static final long serialVersionUID = 1L;

			private final Control cc;

			private L3Control(Control cc)
			{
				this.cc = cc;
			}
			@Override
			public String toString()
			{
				String name = cc.getType().toString();
				return ( name );
			}
		}

		private class L3AudioFormat extends DefaultMutableTreeNode
		{
			private static final long serialVersionUID = 1L;

			private final AudioFormat af;

			private L3AudioFormat(AudioFormat af)
			{
				this.af = af;
			}

			@Override
			public String toString()
			{
				return (af.toString());
			}
		}
	}
}
