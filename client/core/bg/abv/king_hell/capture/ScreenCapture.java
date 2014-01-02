package bg.abv.king_hell.capture;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.SwingUtilities;

import bg.abv.king_hell.util.Network;

public final class ScreenCapture
{
	private static boolean once = false;
	
	static Runnable onPaintFinish = new Runnable()
	{
		public void run()
		{
			ScreenCapture.captureRect(AreaSelection.getArea());
		}
	};
	
	public static void captureOnce()
	{
		ScreenCapture.once = true;
		AreaSelection.enable();
	}
	
	public static void captureRectSync(Rectangle area)
	{
		SwingUtilities.invokeLater(onPaintFinish);
	}
	
	public static void captureScreen(GraphicsDevice gd)
	{
		captureRect(new Rectangle(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight()));
	}
	
	public static void captureRect(Rectangle area)
	{
		try
		{
			Robot robot = new Robot();
			BufferedImage screen = robot.createScreenCapture(area);
			byte[] data = Network.getFormattedImageData(screen);
			HttpURLConnection con = (HttpURLConnection)new URL(Network.getServerAddress()+"internal/processor.php").openConnection();
			if(!Network.processRequest(con, data)) return;
			Network.processUploadResponse(con);
			con.disconnect();
		}
		catch(AWTException |URISyntaxException | IOException e) { }
		finally
		{
			if(once) System.exit(0);
		}
	}
}
