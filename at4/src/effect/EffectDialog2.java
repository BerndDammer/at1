package effect;

import function.IFunction2;
import function.IPlayer;
import gui_help.BorderPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;

import starter.Central;
import starter.MainFrame;

public class EffectDialog2 extends BorderPanel
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getAnonymousLogger();

	private final IFunction2 effect;
	
	public EffectDialog2(IFunction2 effect)
	{
		//super(Central.getMainFrame(), true);
		super( effect.toString() );
		this.effect = effect;

        setLayout( new BorderLayout());
        add(new StopButton(), BorderLayout.NORTH);
        JPanel effectPanel = effect.getPanel();
        if( effectPanel != null)
            add( effectPanel, BorderLayout.CENTER);
		
		
		MainFrame mf = Central.getMainFrame();
		mf.setEffectPanel(this);
	}
	private IPlayer player;
	protected void startIt()
	{
		logger.info("Starting effect ...");
		player = Central.getSelectedPlatform().getPlayerForFunction( effect );
		player.startEffect();
		logger.info("Starting done");
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
	        Central.getMainFrame().setEffectPanel(null);
		}
	}
}
