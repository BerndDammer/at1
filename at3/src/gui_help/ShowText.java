package gui_help;

import javax.swing.JLabel;

public class ShowText extends JLabel
{
	private final StringBuilder stringBuilder; 
	public ShowText()
	{
		stringBuilder = new StringBuilder();
	}
	public StringBuilder get()
	{
		return stringBuilder;
	}
	public void put()
	{
		setText( stringBuilder.toString() );
	}
}	
