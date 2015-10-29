package at1;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame2 implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MainFrame()
	{
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
			GridBagConstraints gc = new GridBagConstraints();
			gc.anchor = GridBagConstraints.CENTER;
			gc.fill = GridBagConstraints.NONE;
			gc.insets = new Insets(1, 4, 4, 1);
			gc.ipadx = 3;
			gc.ipady = 0;
			gc.weightx = 1.0;
			gc.weighty = 0.0;

			gc.gridheight = 1;
			gc.gridwidth = 1;

			gc.gridx = 0;
			gc.gridy = 0;

			add(Central.getGinputSelector(), gc);
			gc.gridx++;
			add(Central.getGoutputSelector(), gc);
			
			gc.gridy++;
			gc.gridx = 0;
			add( new ViewStarter(), gc);
			gc.gridx++;
			//add( new OutputStart(), gc);
			
			gc.gridy++;
			gc.gridx = 0;
			add( new InputStart(), gc);
			gc.gridx++;
			add( new OutputStart(), gc);
			
			gc.gridy++;
			gc.gridx = 0;
			add( new LoopStart(), gc);
			gc.gridx++;
			add( new Loop2Start(), gc);
			
			gc.gridy++;
			gc.gridx = 0;
			add( new Mul1(), gc);
			gc.gridx++;
			//add( new Loop2Start(), gc);
		}
	}

	@Override
	public void run()
	{
		setContentPane(new myPanel());
		setTitle("AudioSplicer");
		setVisible(true);
	}
	//---------------------GUI_Elements
	private class ViewStarter extends JButton implements ActionListener
	{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ViewStarter()
		{
            setText( "Mixer Tree View");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new MixerView( MainFrame.this);
		} 
	}
	private class OutputStart extends JButton implements ActionListener
	{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		OutputStart()
		{
            setText( "Output Test");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new OutputTest();
		} 
	}
	private class InputStart extends JButton implements ActionListener
	{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		InputStart()
		{
            setText( "Input Test");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new InputTest();
		} 
	}
	private class LoopStart extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		LoopStart()
		{
            setText( "Loop Test");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new LoopTest();
		} 
	}
	private class Loop2Start extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		Loop2Start()
		{
            setText( "Continuous Loop");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new Loop2( MainFrame.this );
		} 
	}
	private class Mul1 extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		Mul1()
		{
            setText( "Mul1");
            addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e)
		{
			new LoopMul1( MainFrame.this );
		} 
	}
}
