package bg.abv.king_hell.frames;

import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import bg.abv.king_hell.shortcut.KeyProcessor;
import bg.abv.king_hell.util.Configuration;
import bg.abv.king_hell.util.Constants;

public class ShortcutSettingsFrame extends JFrame
{
	private static final long serialVersionUID = -1986177235873184475L;
	public JToggleButton[] buttons = new JToggleButton[4];
	public JCheckBox chkBox;
	
	public ShortcutSettingsFrame()
	{
		ShortcutSettingsEventHandler handler = new ShortcutSettingsEventHandler(this);
		this.setResizable(false);
		this.setIconImage(Constants.ICON);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 450, 337);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel sLabel = new JLabel("Shortcut keys");
		sLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sLabel.setBounds(0, 0, 444, 25);
		contentPane.add(sLabel);
		
		JPanel mainPanel = new JPanel((LayoutManager)null);
		mainPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainPanel.setBounds(10, 27, 424, 271);
		contentPane.add(mainPanel);
		
		JPanel subPanel = new JPanel();
		subPanel.setLayout(null);
		subPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		subPanel.setBounds(10, 11, 404, 35);
		mainPanel.add(subPanel);
		
		for(byte i=0;i<buttons.length;++i)
		{
			buttons[i] = new JToggleButton();
			buttons[i].setBounds(205, 6, 189, 23);
			buttons[i].setActionCommand(Byte.toString(i));
			buttons[i].addActionListener(handler);
			buttons[i].addKeyListener(handler);
		}
		subPanel.add(buttons[0]);
		
		JLabel label = new JLabel("Area capture:");
		label.setBounds(10, 0, 185, 35);
		subPanel.add(label);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 57, 404, 35);
		mainPanel.add(panel);
		panel.add(buttons[1]);
		
		JLabel lblFullScreenCapture = new JLabel("Full screen capture:");
		lblFullScreenCapture.setBounds(10, 0, 185, 35);
		panel.add(lblFullScreenCapture);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 103, 404, 35);
		mainPanel.add(panel_1);
		panel_1.add(buttons[2]);
		
		JLabel lblTextUpload = new JLabel("Text upload:");
		lblTextUpload.setBounds(10, 0, 185, 35);
		panel_1.add(lblTextUpload);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 149, 404, 35);
		mainPanel.add(panel_2);
		panel_2.add(buttons[3]);
		
		JLabel lblClipboardTextUpload = new JLabel("Clipboard text upload:");
		lblClipboardTextUpload.setBounds(10, 0, 185, 35);
		panel_2.add(lblClipboardTextUpload);
		
		JButton bApply = new JButton("Apply");
		bApply.setActionCommand("a");
		bApply.addActionListener(handler);
		bApply.setFont(new Font("Tahoma", Font.BOLD, 11));
		bApply.setToolTipText("Apply the current settings");
		bApply.setBounds(325, 238, 89, 23);
		mainPanel.add(bApply);
		JButton bCancel = new JButton("Cancel");
		bCancel.setActionCommand("c");
		bCancel.addActionListener(handler);
		bCancel.setToolTipText("Cancel and close the window");
		bCancel.setBounds(226, 238, 89, 23);
		mainPanel.add(bCancel);
		JButton bDefaults = new JButton("Defaults");
		bDefaults.setActionCommand("d");
		bDefaults.addActionListener(handler);
		bDefaults.setToolTipText("Reset settings to their default values ");
		bDefaults.setBounds(10, 238, 89, 23);
		mainPanel.add(bDefaults);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 192, 404, 35);
		mainPanel.add(panel_3);
		
		JLabel lblShortcutKeysEnabled = new JLabel("Shortcut keys enabled:");
		lblShortcutKeysEnabled.setBounds(10, 0, 185, 35);
		panel_3.add(lblShortcutKeysEnabled);
		
		chkBox = new JCheckBox("(Hook library not loaded)");
		chkBox.setBounds(201, 7, 197, 21);
		chkBox.setSelected(Constants.KEYS_ENABLED);
		panel_3.add(chkBox);
		
		String[] shortcuts = Configuration.getSetting("shortcuts").split("\\|");
		for(byte i=0;i<shortcuts.length;++i)
		{
			buttons[i].setText(KeyProcessor.compressedToString(shortcuts[i]));
			handler.values[i] = shortcuts[i];
		}
		
		this.setVisible(true);
	}
	
}
