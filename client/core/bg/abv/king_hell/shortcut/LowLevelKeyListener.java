package bg.abv.king_hell.shortcut;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import bg.abv.king_hell.util.Configuration;
import bg.abv.king_hell.util.Constants;

public class LowLevelKeyListener implements NativeKeyListener
{
	private static LowLevelKeyListener listener;
	private static boolean enabled;
	
	public static void initialize() throws NativeHookException
	{
		GlobalScreen.initialize();
		GlobalScreen.registerNativeHook();
		if(GlobalScreen.isNativeHookRegistered())
		{
			listener = new LowLevelKeyListener();
			GlobalScreen.getInstance().addNativeKeyListener(listener);
			enabled = true;
		}
		else 
		{
			enabled = false;
			throw new NativeHookException("Could not register hook");
		}
	}
	
	public static boolean isEnabled()
	{
		return enabled;
	}
	
	public static void dispose()
	{
		GlobalScreen.getInstance().removeNativeKeyListener(listener);
		GlobalScreen.unregisterNativeHook();
	}
	
	public static LowLevelKeyListener getInstance()
	{
		return listener;
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e)
	{
		if(e.isMenuKey()) ControlKeys.setKeyPressed(e.getKeyCode()-16, true);
		else if(Constants.KEYS_ENABLED)
		{
			String[] scs = Configuration.getSetting("shortcuts").split("\\|");
			for(byte i=0;i<scs.length;++i)
			{
				if(Byte.parseByte(scs[i].substring(3)) != (byte)e.getKeyCode() || !KeyProcessor.checkMenuKeys(scs[i])) continue;
				KeyProcessor.processShortcut(i);
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e)
	{
		if(e.isMenuKey()) ControlKeys.setKeyPressed(e.getKeyCode()-16, false);
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e)
	{
		
	}
}