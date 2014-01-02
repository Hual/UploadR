package bg.abv.king_hell;

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import bg.abv.king_hell.capture.AreaSelection;
import bg.abv.king_hell.capture.ScreenCapture;
import bg.abv.king_hell.shortcut.LowLevelKeyListener;
import bg.abv.king_hell.tray.MenuTrayIcon;
import bg.abv.king_hell.util.Configuration;
import bg.abv.king_hell.util.Constants;
import bg.abv.king_hell.util.Output;
import bg.abv.king_hell.util.Util;

public final class Main
{
	
	public static void start(BufferedImage icon, String version, String location) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		try
		{
			Constants.TRAY_ICON = ImageIO.read(Main.class.getResource("resources/icon_xxs.png"));
			Constants.ICON = icon;
			Constants.VERSION = version;
			Constants.JAR_LOCATION = location;
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			if(!Configuration.fileExists() || !Configuration.load())
			{
				Configuration.setToDefault();
				if(!Configuration.save()) Output.error("Could not write to the configuration file.");
			}
			AreaSelection.updateColours();
			if(Boolean.parseBoolean(Configuration.getSetting("shortcuts-enabled")) && GlobalScreen.libraryExists())
			{
				LowLevelKeyListener.initialize();
				Constants.KEYS_ENABLED = true;
			}
			else
				Constants.KEYS_ENABLED = false;
			Util.setGraphicsDevice(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
			if(!MenuTrayIcon.isSupported() || !MenuTrayIcon.create())
			{
				ScreenCapture.captureOnce();
			}
		}
		catch(NativeHookException e)
		{
			if(GlobalScreen.isNativeHookRegistered()) LowLevelKeyListener.dispose();
			Constants.KEYS_ENABLED = false;
			Output.warning("Could not listen for low-level keyboard input. Key shortcuts not available.");
		}
		catch(IOException e) { }
	}
}
