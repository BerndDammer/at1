package starter;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class JFrame2 extends JFrame implements WindowListener
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private static final boolean MAX = false;

	public JFrame2()
	{
		addWindowListener(this);
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
	}

	@Override
	public void setVisible(boolean on)
	{
		if (on)
		{
			pack();
			Dimension screenSize = getToolkit().getScreenSize();
			int sparex, sparey;
			if (MAX)
			{
				setUndecorated(true);
				sparex = sparey = 0;
				setLocation(0, 0);
				setExtendedState(MAXIMIZED_BOTH);
			} else
			{
				sparex = screenSize.width - getWidth();
				sparey = screenSize.height - getHeight();
			}
			setLocation(sparex / 2, sparey / 2);
		}
		super.setVisible(on);
	}
}
