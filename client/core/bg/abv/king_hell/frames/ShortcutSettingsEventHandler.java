package bg.abv.king_hell.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import bg.abv.king_hell.shortcut.KeyProcessor;
import bg.abv.king_hell.shortcut.LowLevelKeyListener;
import bg.abv.king_hell.util.Configuration;
import bg.abv.king_hell.util.Constants;
import bg.abv.king_hell.util.Output;
import bg.abv.king_hell.util.Util;

public class ShortcutSettingsEventHandler implements KeyListener, ActionListener
{
	ShortcutSettingsFrame frame;
	String[] values = new String[4];
	byte selectedIndex;
	String keyString;
	
	public ShortcutSettingsEventHandler(ShortcutSettingsFrame _frame)
	{
		frame = _frame;
		selectedIndex = -1;
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(selectedIndex != -1)
		{
			String chr = KeyEvent.getKeyText(e.getKeyCode());
			if(frame.buttons[selectedIndex].getText().indexOf(chr+" + ") == -1)
				frame.buttons[selectedIndex].setText(frame.buttons[selectedIndex].getText()+chr+" + ");
			
			if(e.getKeyCode() < 16 || e.getKeyCode() > 18)
			{
				keyString = (Boolean.toString(e.isShiftDown())+Boolean.toString(e.isControlDown())+Boolean.toString(e.isAltDown())).replace("true", "1").replace("false", "0")+keyString+e.getKeyCode();
				values[selectedIndex] = keyString;
				String lValue = frame.buttons[selectedIndex].getText();
				frame.buttons[selectedIndex].setText(lValue.substring(0, lValue.length()-3));
				frame.buttons[selectedIndex].setSelected(false);
				selectedIndex = -1;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(selectedIndex != -1)
		{
			frame.buttons[selectedIndex].setText(frame.buttons[selectedIndex].getText().replace(KeyEvent.getKeyText(e.getKeyCode())+" + ", ""));
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		switch(cmd.charAt(0))
		{
			case 'a':
			{
				boolean shouldRestart = false;
				Configuration.overwriteSetting("shortcuts", values[0]+'|'+values[1]+'|'+values[2]+'|'+values[3]);
				if(frame.chkBox.isSelected())
				{
					if(!GlobalScreen.libraryExists())
					{
						if(Output.confirm("Key shortcuts require a native library to work. Would you like to download it now?"))
						{
							Configuration.overwriteSetting("shortcuts-enabled", "true");
							shouldRestart = true;
						}
					}
					else if(!LowLevelKeyListener.isEnabled())
					{
						try
						{
							LowLevelKeyListener.initialize();
						}
						catch (NativeHookException ex)
						{
							Constants.KEYS_ENABLED = false;
						}
					}
					Constants.KEYS_ENABLED = true;
				}
				else
				{
					Constants.KEYS_ENABLED = false;
					Configuration.overwriteSetting("shortcuts-enabled", "false");
				}
				Configuration.save();
				if(shouldRestart)
					Util.restartApplication("-update-hook");
			}
			case 'c':
			{
				frame.dispose();
				break;
			}
			case 'd':
			{
				if(selectedIndex != -1)
				{
					frame.buttons[selectedIndex].setSelected(false);
					selectedIndex = -1;
				}
				values[0] = "11049";
				values[1] = "11050";
				values[2] = "11051";
				values[3] = "11052";
				for(byte i=0;i<frame.buttons.length;++i)
					frame.buttons[i].setText(KeyProcessor.compressedToString(values[i]));
				frame.chkBox.setSelected(true);
				break;
			}
			default:
			{
				byte index = Byte.parseByte(cmd);
				if(frame.buttons[index].isSelected())
				{
					if(selectedIndex != -1)
					{
						frame.buttons[selectedIndex].setText(KeyProcessor.compressedToString(values[selectedIndex]));
						frame.buttons[selectedIndex].setSelected(false);
					}
					selectedIndex = index;
					keyString = "";
					frame.buttons[selectedIndex].setText("");
				}
				else
				{
					frame.buttons[selectedIndex].setText(KeyProcessor.compressedToString(values[index]));
					selectedIndex = -1;
				}
			}
		}
	}

}
