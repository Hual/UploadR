package bg.abv.king_hell.capture;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import bg.abv.king_hell.util.Network;
import bg.abv.king_hell.util.Output;

public final class TextUpload
{

	public static boolean upload(String text)
	{
		try
		{
			byte[] data = Network.getFormattedTextData(text);
			HttpURLConnection con = (HttpURLConnection) new URL(Network.getServerAddress() + "internal/processor.php").openConnection();
			if (!Network.processRequest(con, data))
				return false;
			if (!Network.processUploadResponse(con))
				return false;
			return true;
		}
		catch (IOException | URISyntaxException ex)
		{
			return false;
		}
	}

	public static void uploadFromClipboard()
	{
		try
		{
			upload((String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
		}
		catch (Exception e)
		{
			Output.error("The application has encountered an issue whilst uploading text: "+e.getLocalizedMessage());
		}
	}
}
