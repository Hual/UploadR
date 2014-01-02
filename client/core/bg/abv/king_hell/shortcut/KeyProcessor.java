package bg.abv.king_hell.shortcut;

import java.awt.event.KeyEvent;

import bg.abv.king_hell.capture.AreaSelection;
import bg.abv.king_hell.capture.ScreenCapture;
import bg.abv.king_hell.capture.TextUpload;
import bg.abv.king_hell.frames.TextInputFrame;
import bg.abv.king_hell.util.Util;

public final class KeyProcessor
{
	public static String compressedToString(String str)
	{
		String strBuild="";
		if(str.charAt(1) == '1') strBuild += "Ctrl + ";
		if(str.charAt(0) == '1') strBuild += "Shift + ";
		if(str.charAt(2) == '1') strBuild += "Alt + ";
		strBuild += KeyEvent.getKeyText(Integer.parseInt(str.substring(3)));
		return strBuild;
	}
	
	public static boolean checkMenuKeys(String keys)
	{
		for(byte j=0;j<ControlKeys.getKeys();++j)
		{
			if((keys.charAt(j) == '1' && !ControlKeys.isKeyPressed(j)) || (keys.charAt(j) == '0' && ControlKeys.isKeyPressed(j)))
				return false;
		}
		return true;
	}
	
	public static void processShortcut(byte id)
	{
		switch(id)
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
		}
	}
}
