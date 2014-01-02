package bg.abv.king_hell.frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bg.abv.king_hell.capture.AreaSelection;
import bg.abv.king_hell.util.Configuration;
import bg.abv.king_hell.util.Network;
import bg.abv.king_hell.util.Output;

public class SettingsEventHandler implements ActionListener
{
	SettingsFrame frame;
	
	public SettingsEventHandler(SettingsFrame _frame)
	{
		this.frame = _frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		int id = e.getActionCommand().charAt(0)+e.getActionCommand().charAt(1);
		switch(id)
		{
			case 181:
			{
				String text = Network.reformatURL(frame.textFields[0].getText());
				frame.textFields[0].setText(text);
				Output.info(frame, Network.processInfoRequest(text, 'i', "The server is an UploadR server!", "The server is NOT an UploadR server!"));
				break;
			}
			case 188:
			{
				String text = Network.reformatURL(frame.textFields[0].getText());
				frame.textFields[0].setText(text);
				Output.info(frame, Network.processInfoRequest(text, 'k', "The server requires an UploadR key! Visit "+text+" to get one", "The server does NOT require an UploadR key!"));
				break;
			}
			case 183:
			{
				try
				{
					String colText = frame.textFields[2].getText();
					if(colText.length() > 6) throw new NumberFormatException();
					new Color(Integer.parseInt(colText, 16));
					Output.info(frame, "The number is in the correct hexadecimal format!");
				}
				catch(NumberFormatException ex)
				{
					Output.info(frame, "The number is NOT in the correct hexadecimal format!");
				}
				break;
			}
			case 180:
			{
				Configuration.overwriteSetting("server", frame.textFields[0].getText());
				Configuration.overwriteSetting("key", frame.textFields[1].getText());
				Configuration.overwriteSetting("colour", frame.textFields[2].getText());
				AreaSelection.updateColours();
				Configuration.overwriteSetting("action", Integer.toString(frame.boxes[0].getSelectedIndex()));
				Configuration.overwriteSetting("format", Integer.toString(frame.boxes[1].getSelectedIndex()));
				Configuration.save();
			}
			case 186:
			{
				frame.dispose();
				break;
			}
			case 100:
			{
				frame.textFields[0].setText("http://80.241.211.41/uploadr/");
				frame.textFields[1].setText("");
				frame.textFields[2].setText("808080");
				frame.boxes[0].setSelectedIndex(0);
				frame.boxes[1].setSelectedIndex(0);
				break;
			}
		}
	}
}
