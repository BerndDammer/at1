package platform;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import starter.Central;
import utils.BorderPanel;

public class PlatformPanel extends BorderPanel
{
	private final JLabel lOsName = new JLabel("OS Name : ", JLabel.RIGHT);
	private final JLabel viewOsName = new JLabel( System.getProperty ("os.name"), JLabel.LEFT );
	private final JLabel lOsArch = new JLabel("OS Arch : ", JLabel.RIGHT);
	private final JLabel viewOsArch = new JLabel( System.getProperty ("os.arch"), JLabel.LEFT );
	private final JLabel lSelect = new JLabel("Coose Binding : ");
	private final PlatformChoice selApi;
	
	public PlatformPanel()
	{
		super("Select Platform");
		
		selApi = new PlatformChoice();
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(1, 4, 4, 1);
		gc.ipadx = 3;
		gc.ipady = 0;
		gc.weightx = 0.0;
		gc.weighty = 0.0;

		gc.gridheight = 1;
		gc.gridwidth = 1;

		gc.gridx = 0;
		gc.gridy = 0;

		add(lOsName, gc);
		gc.gridx++;
		add(viewOsName, gc);
		gc.gridx++;
		add(lOsArch, gc);
		gc.gridx++;
		add(viewOsArch, gc);
		
		gc.gridy++;
		gc.gridx = 0;
		add( lSelect, gc);
		gc.gridx++;
		add( selApi, gc);
		
	}
	class PlatformChoice extends JComboBox<String> implements ActionListener
	{
		PlatformChoice()
		{
			for( PlatformBase pb : Central.getPlatforms())
			{
				addItem(pb.getName());
			}
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			Central.setSelectedPlatform( (String)getSelectedItem() );
		}
	}
}
