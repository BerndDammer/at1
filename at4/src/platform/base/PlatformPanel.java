package platform.base;

import gui_help.BorderPanel;
import gui_help.MyGC;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import starter.Central;
import starter.error;

public class PlatformPanel extends BorderPanel
{
	private final JLabel lOsName = new JLabel("OS Name : ", JLabel.RIGHT);
	private final JLabel viewOsName = new JLabel(System.getProperty("os.name"), JLabel.LEFT);
	private final JLabel lOsArch = new JLabel("OS Arch : ", JLabel.RIGHT);
	private final JLabel viewOsArch = new JLabel(System.getProperty("os.arch"), JLabel.LEFT);
	private final JLabel lSelect = new JLabel("Coose Binding : ");
	private final PlatformChoice selApi;
	private final MyGC gc = new MyGC();

	public PlatformPanel()
	{
		super("Select Platform");

		selApi = new PlatformChoice();

		setLayout(new GridBagLayout());
		gc.reset();
		
		add(lOsName, gc);
		gc.gridx++;
		add(viewOsName, gc);
		gc.gridx++;
		add(lOsArch, gc);
		gc.gridx++;
		add(viewOsArch, gc);

		gc.gridy++;
		gc.gridx = 0;
		add(lSelect, gc);
		gc.gridx++;
		add(selApi, gc);

	}

	class PlatformChoice extends JComboBox<PLATFORM> implements ActionListener
	{
		PlatformChoice()
		{
			for (PLATFORM platform : PLATFORM.values())
			{
				addItem(platform);
			}
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			PLATFORM platformEnum = getItemAt(getSelectedIndex());
			PlatformBase platform;
			try
			{
				platform = platformEnum.getPlatform().newInstance();
				Central.setSelectedPlatform(platform);
			} catch (InstantiationException | IllegalAccessException e1)
			{
				// TODO Auto-generated catch block
				error.exception(e1);
			}
		}
	}
}
