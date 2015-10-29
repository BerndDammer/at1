import java.lang.*;
import java.util.*;
import java.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

  /**
  for usage in Applet Application env
  Container is common parent !!!

  */


public class LAFButton3 extends JButton implements ActionListener
{
    private JFrame root_frame;

    LAFButton3( JFrame parent )
    {
        this.root_frame = parent;
        //setText("LAF");
        setText("Look And Feel");
   		addActionListener( this );
   	}
   	public void actionPerformed(ActionEvent e )
   	{
        //new LAFSelect( root_frame ).setVisible( true );
        new LAFSelect( root_frame ).setVisible( true );
   	}

    //---------------------------------------------- inner class .. make selection
    class bh extends JButton implements ActionListener
    {
    	UIManager.LookAndFeelInfo f;
    	JDialog d;

    	bh( JDialog d, UIManager.LookAndFeelInfo f)
    	{
    		super( f.getName() );
    		this.f = f;
    		this.d = d;
    		addActionListener( this );
    	}
    	public void actionPerformed(ActionEvent e )
    	{
            try
            {
                UIManager.setLookAndFeel( f.getClassName() );
                SwingUtilities.updateComponentTreeUI( root_frame);
                root_frame.pack();
            }
            catch( Exception e2 ){}
    	    d.setVisible( false );
    	}
    }

    class LAFSelect extends JDialog
    {
        LAFSelect( JFrame parent )
        //LAFSelect()
        {
            //-- cant set parent ... needs Frame or dialog !?!?
            //super( parent, "Select LAF" );
            setTitle( "Select LAF" );

            setLayout( new GridLayout( 0, 1 ) );

            try
            {
                int i;
                UIManager.LookAndFeelInfo[] vfi = UIManager.getInstalledLookAndFeels();
                for ( i = 0; i < vfi.length; i++ )
                {
                    add( new bh( this, vfi[i] ) );
                }
            }
            catch( Exception e)
            {
                add( new JLabel( " OOOps Problem" ) );
            }
            pack();
            util1.centerWindow( this );
        }
    }
    public static void setNative( JFrame f)
    {
        try
        {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
            SwingUtilities.updateComponentTreeUI( f );
        }
        catch( Exception e )
        {
        }
    }
}



