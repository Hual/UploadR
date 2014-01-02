package bg.abv.king_hell.frames;

import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import bg.abv.king_hell.util.Configuration;
import bg.abv.king_hell.util.Constants;

public class SettingsFrame extends JFrame
{
	private static final long serialVersionUID = 5317058629071722545L;
	public JTextField[] textFields = new JTextField[3];
	@SuppressWarnings("unchecked")
	public JComboBox<String>[] boxes = new JComboBox[2];
	private SettingsEventHandler handler;
	
	public SettingsFrame()
	{
		this.setTitle("UploadR - Settings");
		this.setIconImage(Constants.ICON);
		this.handler = new SettingsEventHandler(this);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 450, 337);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel sLabel = new JLabel("Settings");
		sLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sLabel.setBounds(0, 0, 444, 25);
		contentPane.add(sLabel);
		
		JPanel mainPanel = new JPanel((LayoutManager)null);
		mainPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainPanel.setBounds(10, 27, 424, 271);
		contentPane.add(mainPanel);
		
		JPanel[] subPanels = new JPanel[5];
		for(byte i=0;i<subPanels.length;++i)
		{
			subPanels[i] = new JPanel();
			subPanels[i].setLayout(null);
			subPanels[i].setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			subPanels[i].setBounds(10, 11+(46*i), 404, 35);
			mainPanel.add(subPanels[i]);
		}
		
		JLabel
			pServerLabel = new JLabel("Proxy server:"),
			pKeyLabel = new JLabel("Proxy upload key:"),
			sColourLabel = new JLabel("Selection rect. colour (hex):"),
			iDoubleClickActionLabel = new JLabel("Icon double-click action:"),
			iQualityLabel = new JLabel("Image quality (format):");
		
		pServerLabel.setBounds(10, 0, 104, 35);
		subPanels[0].add(pServerLabel);
		pKeyLabel.setBounds(10, 0, 104, 35);
		subPanels[1].add(pKeyLabel);
		sColourLabel.setBounds(10, 0, 168, 35);
		subPanels[2].add(sColourLabel);
		iDoubleClickActionLabel.setBounds(10, 0, 143, 35);
		subPanels[3].add(iDoubleClickActionLabel);
		iQualityLabel.setBounds(10, 0, 143, 35);
		subPanels[4].add(iQualityLabel);
		
		textFields[0] = new JTextField(Configuration.getSetting("server"));
		textFields[0].setBounds(124, 7, 171, 20);
		subPanels[0].add(textFields[0]);
		textFields[1] = new JTextField(Configuration.getSetting("key"));
		textFields[1].setBounds(124, 7, 171, 20);
		subPanels[1].add(textFields[1]);
		textFields[2] = new JTextField(Configuration.getSetting("colour"));
		textFields[2].setBounds(188, 7, 107, 20);
		subPanels[2].add(textFields[2]);
		
		boxes[0] = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] {
			"Screen capture (Area)",
			"Screen capture (Full screen)",
			"Upload text (Form)",
			"Upload text (Clipboard)",
			"Open settings menu", 
			"Open shortcut settings menu", 
			"Exit UploadR"
		}));		
		boxes[0].setBounds(163, 7, 231, 20);
		boxes[0].setSelectedIndex(Integer.parseInt(Configuration.getSetting("action")));
		subPanels[3].add(boxes[0]);
		boxes[1] = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] {
			"PNG-24 (lossless/direct)", 
			"JPEG (lossy/direct)", 
			"GIF (lossless/indexed)"
		}));
		boxes[1].setBounds(163, 7, 231, 20);
		boxes[1].setSelectedIndex(Integer.parseInt(Configuration.getSetting("format")));
		subPanels[4].add(boxes[1]);
		
		JButton[] buttons = new JButton[]
		{
			new JButton("Check"),
			new JButton("Check"),
			new JButton("Check"),
			new JButton("Apply"),
			new JButton("Cancel"),
			new JButton("Defaults")
		};
		
		buttons[0].setToolTipText("Check whether the proxy server is an UploadR server");
		buttons[0].setBounds(305, 6, 89, 23);
		subPanels[0].add(buttons[0]);
		buttons[1].setToolTipText("Check whether the proxy server requires an upload key");
		buttons[1].setBounds(305, 6, 89, 23);
		subPanels[1].add(buttons[1]);
		buttons[2].setToolTipText("Check whether the colour format is correct");
		buttons[2].setBounds(305, 6, 89, 23);
		subPanels[2].add(buttons[2]);
		buttons[3].setFont(new Font("Tahoma", Font.BOLD, 11));
		buttons[3].setToolTipText("Apply the current settings");
		buttons[3].setBounds(325, 238, 89, 23);
		mainPanel.add(buttons[3]);
		buttons[4].setToolTipText("Cancel and close the window");
		buttons[4].setBounds(226, 238, 89, 23);
		mainPanel.add(buttons[4]);
		buttons[5].setToolTipText("Reset settings to their default values ");
		buttons[5].setBounds(10, 238, 89, 23);
		mainPanel.add(buttons[5]);
		
		for(byte i=0;i<buttons.length;++i)
		{
			buttons[i].setActionCommand(buttons[i].getText().substring(0, 1)+buttons[i].getToolTipText().substring(buttons[i].getToolTipText().length()-1));
			buttons[i].addActionListener(handler);
		}
		this.setVisible(true);
	}
}
