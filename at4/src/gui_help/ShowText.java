package gui_help;

import javax.swing.JLabel;

public class ShowText extends JLabel
{
	private final StringBuilder stringBuilder; 
	public ShowText()
	{
		stringBuilder = new StringBuilder();
	}
	public ShowText(String string)
    {
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        put();
    }
    public StringBuilder get()
	{
		return stringBuilder;
	}
    public void put()
    {
        setText( stringBuilder.toString() );
    }
    public void put(String string)
    {
        stringBuilder.setLength(0);
        stringBuilder.append(string);
        put();
    }
}	
