package starter;

import function.FunctionPanel;
import gui_help.MyGC;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import platform_base.PlatformBase;
import util.Util;
import controller.ControllerPanel;

public class MainFrame extends JFrame2 implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final MyGC gc = new MyGC();
	private JPanel platformParameter;

	public void newPlatform(PlatformBase pb)
	{
		if (platformParameter != null)
			remove(platformParameter);
		platformParameter = pb.getParameterPanel();

		gc.gridx = 0;
		gc.gridy = 1;
		gc.pushHorizontal();
		add(platformParameter, gc);
		gc.popHorizontal();

		invalidate();
		pack();
		Util.centerFrame(this);
	}

	public MainFrame()
	{
		platformParameter = null;
		SwingUtilities.invokeLater(this);
	}

	private class myPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		myPanel()
		{
			setLayout(new GridBagLayout());
			gc.reset();

			// row 0 platform selector
			gc.fill = GridBagConstraints.HORIZONTAL;
			//add(new PlatformPanel(), gc);
			gc.fill = GridBagConstraints.NONE;

			// row 1 PlatformParameter
			gc.nextRow();
			// placed later

			// row 2 Function
			gc.nextRow();
			gc.pushHorizontal();
			add(new FunctionPanel(), gc);
			gc.popHorizontal();

			gc.nextRow();
			gc.pushHorizontal();
			add(new ControllerPanel(), gc);
			gc.popHorizontal();

			gc.nextRow();
			gc.pushHorizontal();
			add( CentralControl.getServerView(), gc);
			gc.popHorizontal();

		}
	}

	@Override
	public void run()
	{
		setContentPane(new myPanel());
		setTitle("AudioSplicer");
		setVisible(true);
	}
	// ---------------------GUI_Elements

}
