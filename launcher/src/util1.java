import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class util1
{
    //------------------------ don't create instance
    private util1()
    {
    }

    public static void centerWindow( Container w )
    {
        Dimension me = w.getSize();
        Dimension sc = w.getToolkit().getScreenSize();

        int x, y;
        x = sc.width / 2 - me.width / 2;
        y = sc.height /2 - me.height / 2;
        w.setLocation( x, y );
    }
}
