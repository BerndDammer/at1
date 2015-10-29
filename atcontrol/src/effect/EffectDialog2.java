package effect;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import starter.CentralControl;
import util.Util;
import function.IFunction;
import function.IFunction2;
import function.IPlayer;

public class EffectDialog2 extends JDialog implements WindowListener
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getAnonymousLogger();

	private final IFunction2 effect;
	
	public EffectDialog2(IFunction2 effect)
	{
		super(CentralControl.getMainFrame(), true);
		this.effect = effect;
		
		addWindowListener(this);
		setTitle("XXX PPP XXX");
		setBounds(30, 40, 600, 400);
		setContentPane(new myPanel());
		Util.centerFrame(this);
	}
	private IPlayer player;
	protected void startIt()
	{
		logger.info("Starting effect ...");
		player = CentralControl.getSelectedPlatform().getPlayerForFunction( effect );
		player.startEffect();
		logger.info("Starting done");
	}

	private class myPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public myPanel()
		{
			setLayout( new BorderLayout());
			add(new StopButton(), BorderLayout.NORTH);
			JPanel effectPanel = effect.getPanel();
			if( effectPanel != null)
				add( effectPanel, BorderLayout.CENTER);
		}
	}

	private class StopButton extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		StopButton()
		{
			setText("Stop");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			logger.info("Stopping effect ...");
			player.stopEffect();
			EffectDialog2.this.setVisible( false );
			logger.info("Stopping done!");
		}
	}


	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		player.stopEffect();
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}
}
