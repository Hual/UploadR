package bg.abv.king_hell.tray;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import bg.abv.king_hell.capture.AreaSelection;
import bg.abv.king_hell.capture.ScreenCapture;
import bg.abv.king_hell.capture.TextUpload;
import bg.abv.king_hell.frames.SettingsFrame;
import bg.abv.king_hell.frames.ShortcutSettingsFrame;
import bg.abv.king_hell.frames.TextInputFrame;
import bg.abv.king_hell.util.Configuration;
import bg.abv.king_hell.util.Util;

public class ItemHandler implements ActionListener, MouseListener
{
	public static final String[][] items = 
	{
		{"s", ""},
		{"Capture Area (Live)", "l"},
		{"Capture Screen", "s"},
		{"s", ""},
		{"Upload text (Form)", "u"},
		{"Upload text (Clipboard)", "c"},
		{"s", ""},
		{"Settings", "t"},
		{"Shortcut settings", "o"},
		{"s", ""},
		{"Exit", "e"}
	};

	@Override
	public void actionPerformed(ActionEvent e)
	{
		try { Thread.sleep(500); } catch(InterruptedException ex) { }
		switch(e.getActionCommand().charAt(0))
		{
			case 'l':
			{
				AreaSelection.enable();
				break;
			}
			case 's':
			{
				ScreenCapture.captureScreen(Util.getGraphicsDevice());
				break;
			}
			case 'e':
			{
				System.exit(0);
				break;
			}
			case 't':
			{
				new SettingsFrame();
				break;
			}
			case 'u':
			{
				new TextInputFrame();
				break;
			}
			case 'c':
			{
				TextUpload.uploadFromClipboard();
				break;
			}
			case 'o':
			{
				new ShortcutSettingsFrame();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e.getClickCount() >= 2)
		{
			byte setting = Byte.parseByte(Configuration.getSetting("action"));
			try { Thread.sleep(500); } catch(InterruptedException ex) { }
			switch(setting)
			{
				case 0:
				{
					AreaSelection.enable();
					break;
				}
				case 1:
				{
					ScreenCapture.captureScreen(Util.getGraphicsDevice());
					break;
				}
				case 2:
				{
					new TextInputFrame();
					break;
				}
				case 3:
				{
					TextUpload.uploadFromClipboard();
					break;
				}
				case 4:
				{
					new SettingsFrame();
					break;
				}
				case 5:
				{
					new ShortcutSettingsFrame();
					break;
				}
				case 6:
				{
					System.exit(0);
					break;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}
}
