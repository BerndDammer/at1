package executer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class mainframe extends JFrame2 implements Runnable
{
    private static final long serialVersionUID = 1L;
    
    private static final int SX = 600;
    private static final int SY = 400;
    private final Parameter parameter;
    private final MyPane lSD;
    private final MyPane lHD;
    private final RunButton bSD;
    private final RunButton bHD;
    private final Hangman hm;
    
    public void run()
    {
        setTitle("executer");
        
        setContentPane( new myPanel() );
        Dimension screenSize = getToolkit().getScreenSize();
        int sparex, sparey;

        sparex = screenSize.width - SX;
        sparey = screenSize.height - SY;
        setLocation(sparex / 2, sparey / 2);
        setSize(SX, SY);
        revalidate();
        setVisible(true);
    }
    private class myPanel extends JPanel
    {
        myPanel()
        {
            setLayout(new GridBagLayout());
            GridBagConstraints gc = new GridBagConstraints();
            gc.anchor = GridBagConstraints.CENTER;
            gc.fill = GridBagConstraints.BOTH;
            gc.insets = new Insets(1, 4, 4, 1);
            gc.ipadx = 3;
            gc.ipady = 0;
            gc.weightx = 1.0;
            gc.weighty = 1.0;

            gc.gridheight = 1;
            gc.gridwidth = 1;

            gc.gridy = 0;
            gc.gridx = 0;
            add(lSD, gc);
            gc.gridx++;
            add(lHD, gc);

            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.weightx = 0.0;
            gc.weighty = 0.0;
            gc.gridy++;
            gc.gridx = 0;
            gc.anchor = GridBagConstraints.WEST;
            add(bSD, gc);
            gc.gridx++;
            gc.anchor = GridBagConstraints.EAST;
            add(bHD, gc);
            
            gc.fill = GridBagConstraints.BOTH;
            gc.weightx = 1.0;
            gc.weighty = 1.0;

            gc.gridy++;
            gc.gridx = 0;
            gc.gridwidth = 2;
            gc.weighty = 1.0;
            add(hm, gc);
            gc.gridwidth = 1;
        }
    }
    mainframe(Parameter parameter)
    {
        this.parameter = parameter;
        hm = new Hangman("------", parameter);
        lSD = new MyPane( parameter.getSD(), hm );
        lHD = new MyPane( parameter.getHD(), hm );
        bSD = new RunButton( "Run SD", lSD, hm );
        bHD = new RunButton( "Run HD", lHD, hm );
        System.setOut( hm.getPrintStream() );

        SwingUtilities.invokeLater( this );
    }
}
