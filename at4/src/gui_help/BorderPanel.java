package gui_help;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

public class BorderPanel extends JPanel
{
    protected final MyGC gc = new MyGC();

    public BorderPanel(String groupName)
    {
        setLayout(new GridBagLayout());

        Border o1 = new BevelBorder(BevelBorder.RAISED);
        Border i1 = new BevelBorder(BevelBorder.LOWERED);
        Border b1 = new CompoundBorder(o1, i1);
        TitledBorder tb = new TitledBorder(b1, groupName);
        setBorder(tb);
    }

}
