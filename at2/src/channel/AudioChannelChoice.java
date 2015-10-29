package channel;

import java.util.List;

import javax.swing.JComboBox;

class AudioChannelChoice extends JComboBox<AudioChannelIdentifier>
{
	AudioChannelChoice(List<AudioChannelIdentifier> ids)
	{
		newValues(ids);
	}
	public void newValues(List<AudioChannelIdentifier> aci)
	{
		removeAll();
		if( aci != null)
		{
			for (AudioChannelIdentifier ai : aci)
			{
				addItem( ai );
			}
		}
		if( getItemCount() > 1)
		{
			setEnabled( true );
		}
		else
		{
			setEnabled( false );
		}
		repaint();
	}
	public void newSubValues(List<AudioSubChannelIdentifier> aci)
	{
		removeAll();
		if( aci != null)
		{
			for (AudioSubChannelIdentifier ai : aci)
			{
				addItem( ai );
			}
		}
		if( getItemCount() > 1)
		{
			setEnabled( true );
		}
		else
		{
			setEnabled( false );
		}
	}
}
