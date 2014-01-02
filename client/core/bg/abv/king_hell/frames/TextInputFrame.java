package bg.abv.king_hell.frames;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import bg.abv.king_hell.util.Constants;

public class TextInputFrame extends JFrame
{
	private static final long serialVersionUID = 8663876835182354178L;
	public JTextArea text;
	public TextInputEventHandler handler;

	public TextInputFrame()
	{
		this.setTitle("UploadR - Text file upload");
		this.setIconImage(Constants.ICON);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 600, 430);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(contentPane);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new CardLayout(0, 0));
		text = new JTextArea();
		panel_1.add(text);

		handler = new TextInputEventHandler(this);
		JPanel panel = new JPanel();
		panel.setBorder(null);
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		JButton bCancel = new JButton("Cancel");
		bCancel.setActionCommand("c");
		bCancel.addActionListener(handler);
		panel.add(bCancel);
		JButton bUpload = new JButton("Upload");
		bUpload.setActionCommand("u");
		bUpload.addActionListener(handler);
		panel.add(bUpload);
		this.setVisible(true);
	}
}
