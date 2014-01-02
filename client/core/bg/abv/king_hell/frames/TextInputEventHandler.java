package bg.abv.king_hell.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bg.abv.king_hell.capture.TextUpload;

public class TextInputEventHandler implements ActionListener
{
	private TextInputFrame frame;
	
	public TextInputEventHandler(TextInputFrame _frame)
	{
		frame = _frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand().charAt(0))
		{
			case 'u':
			{
				if(!TextUpload.upload(frame.text.getText())) break;
			}
			case 'c':
			{
				frame.dispose();
				break;
			}
		}
	}
}
