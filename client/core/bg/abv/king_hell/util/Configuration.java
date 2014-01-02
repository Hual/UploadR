package bg.abv.king_hell.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Configuration
{
	private static Map<String, String> settings = new HashMap<String, String>();
	
	public static String getFilePath()
	{
		return Util.getDataFolder()+"config.cfg";
	}
	
	public static boolean fileExists()
	{
		return new File(getFilePath()).exists();
	}
	
	public static void setToDefault()
	{
		settings.clear();
		settings.put("server", "http://80.241.211.41/uploadr/");
		settings.put("key", "");
		settings.put("colour", "808080");
		settings.put("action", "0");
		settings.put("format", "0");
		settings.put("shortcuts", "11049|11050|11051|11052");
		settings.put("shortcuts-enabled", "true");
	}
	
	public static boolean save()
	{
		String settingsString = "";
		for(String key:settings.keySet())
		{
			settingsString += key+"= "+settings.get(key)+"\n";
		}
		try
		{
			Util.writeToFile(settingsString, getFilePath());
			return true;
		}
		catch(IOException e)
		{
			return false;
		}
	}
	
	public static void overwriteSetting(String setting, String value)
	{
		if(settings.containsKey(setting)) settings.remove(setting);
		settings.put(setting, value);
	}
	
	public static boolean load()
	{
		try
		{
			String[] lines = Util.readFileAsString(getFilePath()).split("\n");
			for(String line:lines)
			{
				String[] kv = line.split("=");
				settings.put(kv[0].trim(), kv[1].trim());
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static String getSetting(String setting)
	{
		return settings.get(setting);
	}
	
}
