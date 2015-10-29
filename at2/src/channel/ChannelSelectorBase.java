package channel;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import starter.error;
import utils.BorderPanel;
import utils.MyGC;

public abstract class ChannelSelectorBase extends BorderPanel implements ActionListener
{
	/////////////////////////////////////////////////
	protected abstract boolean hasSub();
	protected abstract List<AudioChannelIdentifier> getInputChannels(); 
	protected abstract List<AudioChannelIdentifier> getOutputChannels();

	protected List<AudioSubChannelIdentifier> getInputSubChannels(AudioChannelIdentifier aci)
	{
		return null;
	}
	protected List<AudioSubChannelIdentifier> getOutputSubChannels(AudioChannelIdentifier aci)
	{
		return null;
	}
	
	protected abstract boolean hasSubPanel();
	protected abstract JPanel getSubPanel();
	//////////////////////////////////////////////////
	
	
	protected final JLabel lInput = new JLabel("Select Input");
	protected final JLabel lOutput = new JLabel("Select Output");
	protected final AudioChannelChoice sInput = new AudioChannelChoice( null );
	protected final AudioChannelChoice sOutput = new AudioChannelChoice( null );
	protected final AudioChannelChoice subInput = new AudioChannelChoice( null );
	protected final AudioChannelChoice subOutput = new AudioChannelChoice( null );
	

	public ChannelSelectorBase(String name)
	{
		super( name );
		setLayout( new GridBagLayout());
		MyGC gc = new MyGC();
		gc.reset();
		
		gc.gridy = 0;
		add(lInput, gc);
		gc.gridx++;
		add( lOutput, gc);
		
		gc.nextRow();
		sInput.newValues( getInputChannels() );
		add( sInput, gc);
		gc.gridx++;
		sOutput.newValues( getOutputChannels() );
		add( sOutput, gc);

		if( hasSub())
		{
			gc.nextRow();
			add( subInput, gc);
			gc.gridx++;
			add( subOutput, gc);
			sInput.addActionListener(this);
			sOutput.addActionListener(this);
		}
		if( hasSubPanel())
		{
			gc.nextRow();
			gc.pushHorizontal();
			add( getSubPanel(), gc);
		}
	};

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if( e.getSource() == sInput )
		{
			subInput.newSubValues( getInputSubChannels( sInput.getItemAt( sInput.getSelectedIndex() ))  );
		}
		else if( e.getSource() == sOutput )
		{
			subOutput.newSubValues( getOutputSubChannels( sOutput.getItemAt( sOutput.getSelectedIndex() ))  );
		}
		else
			error.exit("Unknown event source");
	}
}
