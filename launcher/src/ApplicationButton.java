import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


class ApplicationButton extends JButton implements ActionListener
{
    private Dimension minSize = new Dimension( 80, 30 );
    private ApplicationEntry a;
	ApplicationButton( ApplicationEntry a )
	{
	    this.a = a;
		setText( a.ButtonCaption );
		addActionListener( this );
	}
	public void actionPerformed(ActionEvent e )
	{
        if( PG.LOG_LAUNCHS ) myLogger.get().finer("Appl " + a );
        a.launch();
	}
	/*
    public Dimension getMinimumSize( )
	{
	    return( minSize );
    }
    */
}

