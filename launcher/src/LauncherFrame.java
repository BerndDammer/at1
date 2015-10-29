import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import h1.*;
//import Launcher1.*;

class LauncherFrame extends Frame3 implements Runnable
{
    class DeveloperSwitch extends JRadioButton implements ActionListener
    //class DeveloperSwitch extends JToggleButton implements ActionListener
    {
        DeveloperSwitch()
        {
            st();
            addActionListener( this );
        }
        public boolean isEnabled_x()
        {
            return( isSelected() );
        }
        public void actionPerformed( ActionEvent ae )
        {
            st();
            CreateAndShowGUI();
        }
        private void st()
        {
            setText( "Developer Extends " + ( isEnabled_x() ? "ON" : "OFF" ) );
        }
    }
    private DeveloperSwitch ds = new DeveloperSwitch();
    private ApplicationGroup[] agf;
    //------------------------------ main entry
    LauncherFrame(String[] args, ApplicationGroup[] ag )
    {
        super( args );
        this.agf = ag;

        OsIdentifier.check();
        SwingUtilities.invokeLater( this );
    }
    //----------------- cant put in Frame3 .... executed in wrong sequence .. locals not inited
    public void run()
    {
        LAFButton3.setNative( this );
        CreateAndShowGUI();
    }
    protected void CreateAndShowGUI()
    {
        //------------------------ Frame it'
        setVisible( false );
        dispose();
        setUndecorated( true );
        getRootPane().setBorder( new LineBorder( Color.LIGHT_GRAY, 4 , true ) );

        //--------------------------------------- do the layout
        getContentPane().removeAll();
        setTitle( PG.FRAME_HEADLINE );
        // --------------main grid
        setLayout( new GridBagLayout() );
        GridBagConstraints g = new GridBagConstraints();

        g.insets = new Insets( 2, 2, 2, 2 );
        g.ipadx = 3;
        g.ipady = 2;
        g.gridheight = 1;
        g.gridwidth = 1;
        g.gridx = 0;
        g.gridy = -1; //---------------- every column increments
        g.weightx = 50.0;
        g.weighty = 0.0;
        g.anchor = GridBagConstraints.NORTH;
        g.fill = GridBagConstraints.HORIZONTAL;

        //------------------------------------ headline
        g.gridy++;
        g.gridwidth = GridBagConstraints.REMAINDER;
        add( new JLabel( PG.FRAME_HEADLINE, SwingConstants.CENTER ), g );
        g.gridwidth = 1;

        //--------------------------------- make appication frames
        g.gridy++;
        int groupIndex = -1;
        for( ApplicationGroup ag : agf)
        {
            if( !ag.developer || ds.isEnabled_x() )
            {
                g.gridx = ++groupIndex ;
                JPanel gp = new JPanel();
                gp.setBorder( new TitledBorder( new SoftBevelBorder( BevelBorder.RAISED ), ag.headline ) );
                gp.setLayout( new GridLayout( 0, 2 ) );

                for( ApplicationEntry a : ag.list )
                {
                    gp.add( new ApplicationButton( a ) );
                    gp.add( new JLabel( a.getComment(), SwingConstants.RIGHT), g );
                }
                g.weighty = 100.0;
                g.fill = GridBagConstraints.BOTH;
                add( gp, g );
                g.fill = GridBagConstraints.HORIZONTAL;
                g.weighty = 0.0;
            }
        }
        g.weighty = 0.0;
        //--------------- insert foregin row if exists
        if( ds.isEnabled_x() && ForeginLauncher.hasOther() )
        {
            g.gridy++;
            g.gridx = 0;
            g.gridwidth = GridBagConstraints.REMAINDER;
            add( ForeginLauncher.getPanel(), g );
        }
        //----------------------------- make final row on bottom
        // make it cleaner
        g.gridy++;
        JPanel ll = new JPanel();
        ll.setBorder( new SoftBevelBorder( BevelBorder.RAISED ) );
        ll.setLayout( new GridLayout( 1, 0) );
        ll.add( ds );
        ll.add( new LAFButton3( this ) );
        ll.add( new ApplicationButton( new ApplicationEntryExit() ) );
        g.gridx = 0;
        g.gridwidth = GridBagConstraints.REMAINDER;
        add( ll, g );
        //--------------------------------------- layout done ... set Frame/Window
        //------------------ I like it native !!!
        pack();
        //setSize( 900, 500 );
        center();
        setVisible( true );
    }
}

