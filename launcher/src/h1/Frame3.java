package h1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class Frame3 extends JFrame implements WindowListener
{
    protected String[] args;
    public Frame3(String[] args)
    {
        addWindowListener( this );
        this.args = args;
        //------------------------------------- this executes Create and show gui too early
        //SwingUtilities.invokeLater( this );
    }
    /*
    public void run()
    {
        CreateAndShowGUI();
    }
    protected abstract void CreateAndShowGUI();
    */

    protected void center()
    {
        Dimension me = getSize();
        Dimension sc = getToolkit().getScreenSize();

        int x, y;
        x = sc.width / 2 - me.width / 2;
        y = sc.height /2 - me.height / 2;
        setLocation( x, y );
    }

    //--------------------------------------- window Listener on myself
    public void windowClosing(WindowEvent e)
    {
        stop();
    }
    public void windowActivated  ( WindowEvent e ) {} //--- dont wanna have adapter
    public void windowClosed     ( WindowEvent e ) {}
    public void windowDeactivated( WindowEvent e ) {}
    public void windowDeiconified( WindowEvent e ) {}
    public void windowIconified  ( WindowEvent e ) {}
    public void windowOpened     ( WindowEvent e ) {}


    /*
    protected abstract void start();

    public static void main( String[] args )
    {
        ArcnetRawTerm i = new ArcnetRawTerm();
        i.start();
    }
    */
    protected void stop()
    {
        setVisible( false );
        dispose();
        if( ! no_system_exit() ) System.exit( 0 );
    }
    protected boolean no_system_exit()
    {
        for( String s : args )
        {
            if( s.equals("-no_system_exit" ) )return( true );
        }
        return( false );
    }
    protected boolean argsHas(String p)
    {
        for( String s : args )
        {
            if( s.equals( p ) )return( true );
        }
        return( false );
    }
}

