package channel;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JPanel;

import starter.Central;
import starter.error;
import utils.BorderPanel;
import utils.MyGC;
import background.MixerView;

public class ChannelSelectorJavaSound extends ChannelSelectorBase
{

	public ChannelSelectorJavaSound()
	{
		super("Select Channel for Java Sound");
	}


	//////////////////////////////////////////////channels
	@Override
	protected List<AudioChannelIdentifier> getInputChannels()
	{
		List<AudioChannelIdentifier> result = new LinkedList<>();

		Mixer.Info[] fmi = AudioSystem.getMixerInfo();
		for (Mixer.Info mi : fmi)
		{
			Mixer mixer = AudioSystem.getMixer(mi);

			List<TargetDataLine> dataLines = new LinkedList<>();
			Line.Info[] lines;
			lines = AudioSystem.getMixer(mi).getTargetLineInfo();
			for (Line.Info li : lines)
			{
				Line line;
				try
				{
					line = mixer.getLine(li);
					if (line instanceof TargetDataLine)
					{
						dataLines.add((TargetDataLine) line);
					}
				} catch (LineUnavailableException e1)
				{
					// TODO must do something qqqq
					error.log("LineUnavailableException");
				}
			}
			if (!dataLines.isEmpty())
			{
				result.add(new AudioChannelIdentifierJavaSound(mi));
			}
		}
		return result;
	}

	@Override
	protected List<AudioChannelIdentifier> getOutputChannels()
	{
		List<AudioChannelIdentifier> result = new LinkedList<>();

		Mixer.Info[] fmi = AudioSystem.getMixerInfo();
		for (Mixer.Info mi : fmi)
		{
			Mixer mixer = AudioSystem.getMixer(mi);

			List<SourceDataLine> dataLines = new LinkedList<>();
			Line.Info[] lines;
			lines = AudioSystem.getMixer(mi).getSourceLineInfo();
			for (Line.Info li : lines)
			{
				Line line;
				try
				{
					line = mixer.getLine(li);
					if (line instanceof SourceDataLine)
					{
						dataLines.add((SourceDataLine) line);
					}
				} catch (LineUnavailableException e1)
				{
					// TODO must do something qqqq
					error.log("LineUnavailableException");
				}
			}
			if (!dataLines.isEmpty())
			{
				result.add(new AudioChannelIdentifierJavaSound(mi ));
			}
		}
		return result;
	}
	
	////////////////////sub-channels
	@Override
	protected boolean hasSub()
	{
		return true;
	}

	protected List<AudioSubChannelIdentifier> getInputSubChannels(AudioChannelIdentifier aci)
	{
		Mixer.Info mi = ((AudioChannelIdentifierJavaSound)aci).getMixerInfo();
		Mixer mixer = AudioSystem.getMixer(mi);

		List<AudioSubChannelIdentifier> result = new LinkedList<>();

		for (Line.Info li : mixer.getTargetLineInfo())
		{
			try
			{
				Line line = mixer.getLine(li);
				if (line instanceof TargetDataLine)
				{
					result.add( new AudioSubChannelIdentifier( ((TargetDataLine)line).toString() ) );
				}
			} catch (LineUnavailableException e1)
			{
				// TODO must do something qqqq
				error.log("LineUnavailableException");
			}
		}
		return result;
	}
	protected List<AudioSubChannelIdentifier> getOutputSubChannels(AudioChannelIdentifier aci)
	{
		Mixer.Info mi = ((AudioChannelIdentifierJavaSound)aci).getMixerInfo();
		Mixer mixer = AudioSystem.getMixer(mi);

		List<AudioSubChannelIdentifier> result = new LinkedList<>();

		for (Line.Info li : mixer.getSourceLineInfo())
		{
			try
			{
				Line line = mixer.getLine(li);
				if (line instanceof SourceDataLine)
				{
					result.add( new AudioSubChannelIdentifier( ((SourceDataLine)line).toString() ) );
				}
			} catch (LineUnavailableException e1)
			{
				// TODO must do something qqqq
				error.log("LineUnavailableException");
			}
		}
		return result;
	}

	///////////////////////////////sub-panel
	@Override
	protected boolean hasSubPanel()
	{
		return true;
	}

	@Override
	protected JPanel getSubPanel()
	{
		return new JavaSoundSubPanel();
	}

	private class JavaSoundSubPanel extends BorderPanel
	{
		public JavaSoundSubPanel()
		{
			super("Java Sound Sub Panel");
			setLayout(new GridBagLayout());
			MyGC gc = new MyGC();
			gc.reset();
			add(new ViewStarter(), gc);
		}

		private class ViewStarter extends JButton implements ActionListener
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			ViewStarter()
			{
				setText("Mixer Tree View");
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent e)
			{
				new MixerView(Central.getMainFrame());
			}
		}
	}
}
