package bg.abv.king_hell.tray;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import bg.abv.king_hell.util.Constants;

public final class MenuTrayIcon
{
	
	private static TrayIcon icon;
	private static Menu menu;
	
	public static boolean create()
	{
		menu = new Menu();
		try
		{
			icon = new TrayIcon(Constants.TRAY_ICON, "UploadR", menu);
			icon.addMouseListener(menu.getItemHandler());
			SystemTray.getSystemTray().add(icon);
			icon.setToolTip("UploadR");
			return true;
		}
		catch(AWTException e)
		{
			return false;
		}
	}
	
	public static boolean isSupported()
	{
		return SystemTray.isSupported();
	}
	
	public static TrayIcon getIcon()
	{
		return icon;
	}
	
	public static Menu getMenu()
	{
		return menu;
	}
}
