package executer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class RunButton extends JButton implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final MyPane list;
    private final Hangman hm;
    
    RunButton(String text, MyPane list, Hangman hm)
    {
        setText(text);
        this.list = list;
        this.hm = hm;
        
        addActionListener( this );
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Element target;
        target = list.getSelected();
        hm.doIt( target );
        
    }
}
