package bg.abv.king_hell.capture;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import bg.abv.king_hell.util.Configuration;
import bg.abv.king_hell.util.Constants;

public final class AreaSelection
{
	private static Rectangle area;
	private static Point origin;
	private static boolean enabled;
	public static TransparentWindow window;
	
	public static void enable()
	{
		window = new TransparentWindow();
		enabled = false;
		area = new Rectangle();
		origin = new Point();
	}
	
	public static void updateColours()
	{
		Color lc = new Color(Integer.parseInt(Configuration.getSetting("colour"), 16));
		Constants.COLOR_GRAY_OPAQUE = new Color(lc.getRed(), lc.getGreen(), lc.getBlue(), 26);
		Constants.COLOR_GRAY_BORDER = new Color(lc.getRed(), lc.getGreen(), lc.getBlue(), 102);
	}
	
	public static void disable()
	{
		if(window != null)
		{
			window.setVisible(false);
			window.dispose();
		}
	}
	
	public static Point getOrigin()
	{
		return origin;
	}
	
	public static void setOrigin(Point point)
	{
		origin = (Point)point.clone();
	}
	
	public static Rectangle getArea()
	{
		return area;
	}
	
	public static boolean isEnabled()
	{
		return enabled;
	}
	
	public static void setEnabled(boolean toggle)
	{
		enabled = toggle;
	}
	
	public static void update()
	{
		window.repaint();
	}
	
	public static void renew(Point p)
	{
		setEnabled(true);
		setOrigin(p);
		processCoords(p);
		update();	
	}
	
	public static void completeSelection(Point p)
	{
		processCoords(p);
		setEnabled(false);
		disable();
		update();
		if(area.width > 5 && area.height > 5)
			ScreenCapture.captureRectSync(area);
	}
	
	public static void cancelSelection()
	{
		enabled = false;
		area = new Rectangle();
		origin = new Point();
		update();
	}
	
	public static void processCoords(Point p)
	{
		if(origin.x > p.x)
		{
			area.x = p.x;
			area.width = origin.x-p.x;
		}
		else
		{
			area.x = origin.x;
			area.width = p.x-origin.x;
		}
		if(origin.y> p.y)
		{
			area.y = p.y;
			area.height= origin.y-p.y;
		}
		else
		{
			area.y = origin.y;
			area.height = p.y-origin.y;
		}
	}
	
}
