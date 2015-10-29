package splicing_sleeve;

import gui_help.BorderPanel;
import gui_help.MyGC;
import gui_help.ShowText;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.net.InetAddress;

import javax.swing.JButton;

public class ServerViewPanel extends BorderPanel
{
	private final MyGC gc = new MyGC();

	private String sBuff = "+++++";

	private final MTShowText ipShow = new MTShowText();
	
	private class MTShowText extends ShowText
	{
		@Override
		public void paint(Graphics g)
		{
			setText( sBuff);
			super.paint(g);
		}

		public void newText(String text)
		{
			synchronized (sBuff)
			{
				// geht das ????
				sBuff = text;
			}
			ipShow.repaint();
		}
	};

	public ServerViewPanel()
	{
		super("Server IP View");
		setLayout( new GridBagLayout());
		gc.reset();
		//gc.nextRow();
		//gc.pushHorizontal();
		add(ipShow, gc);
		//gc.popHorizontal();
	}

	public void newIP(InetAddress address)
	{
		ipShow.newText(address.getHostAddress());
		//ipShow.repaint();
	}
}
