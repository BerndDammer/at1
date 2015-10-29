package utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MyGC extends GridBagConstraints
{
	private GridBagConstraints stack = null;
	public void reset()
	{
		anchor = GridBagConstraints.CENTER;
		fill = GridBagConstraints.NONE;
		insets = new Insets(1, 4, 4, 1);
		ipadx = 3;
		ipady = 0;
		weightx = 0.0;
		weighty = 0.0;

		gridheight = 1;
		gridwidth = 1;

		gridx = 0;
		gridy = 0;
	}

	public void nextRow()
	{
		gridx = 0;
		gridy++;
	}

	public void pushHorizontal()
	{
		fill = GridBagConstraints.HORIZONTAL;
		weightx = 1.0;
		gridwidth = GridBagConstraints.REMAINDER;
	}

	public void popHorizontal()
	{
		fill = GridBagConstraints.NONE;
		weightx = 0.0;
		gridwidth = 1;
	}
	
	
	// TODO muss alle werte manuell kopieren
	/*
	private void push()
	{
		stack = (GridBagConstraints) this.clone();
	}
	pop()
	{
		
	}
	*/
};
