package bg.abv.king_hell.shortcut;

public final class ControlKeys
{
	public static final int KEY_SHIFT = 0;
	public static final int KEY_CONTROL = 1;
	public static final int KEY_MENU = 2;
	
	private static boolean[] keys = new boolean[3];
	
	public static boolean isKeyPressed(int key)
	{
		return keys[key];
	}
	
	public static void setKeyPressed(int key, boolean value)
	{
		keys[key] = value;
	}
	
	public static int getKeys()
	{
		return keys.length;
	}
}
