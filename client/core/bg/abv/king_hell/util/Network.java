package bg.abv.king_hell.util;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

public final class Network
{
	
	public static String getServerAddress()
	{
		return Network.reformatURL(Configuration.getSetting("server"));
	}
	
	public static byte[] getFormattedImageData(BufferedImage image) throws IOException
	{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byte format = Byte.parseByte(Configuration.getSetting("format"));
		ImageIO.write(image, format == 0 ? ("PNG") : (format == 1 ? ("JPG") : ("GIF")), byteStream);
		byteStream.flush();
		String key = Configuration.getSetting("key");
		byte[] header = {
			'u',
			(key.length() == 16 ? (byte)'1' : (byte)'0')
		};
		if(key.length() == 16) header = Util.concat(header, key.getBytes());
		header = Util.concat(header, new byte[] {format == 0 ? ((byte)'p') : (format == 1 ? ((byte)'j') : ((byte)'g'))});
		byteStream.close();
		return Util.concat(header, byteStream.toByteArray());
	}
	
	public static byte[] getFormattedTextData(String text) throws IOException
	{
		String key = Configuration.getSetting("key");
		byte[] header = {
			'u',
			(key.length() == 16 ? (byte)'1' : (byte)'0')
		};
		if(key.length() == 16) header = Util.concat(header, key.getBytes());
		header = Util.concat(header, new byte[] {'t'});
		return Util.concat(header, text.getBytes());
	}
	
	public static boolean processRequest(HttpURLConnection con, byte[] data) throws ProtocolException, IOException
	{
		try
		{
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "UploadR Client/1.0");
			con.setRequestProperty("Content-Type", "application/octet-stream");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(data);
			wr.flush();
			wr.close();
			con.getOutputStream().close();
			return true;
		}
		catch(ConnectException e)
		{
			Output.error("Could not connect to server: "+e.getLocalizedMessage());
			return false;
		}
	}
	
	public static boolean processUploadResponse(HttpURLConnection con) throws IOException, URISyntaxException
	{
		String response = processResponse(con);
		boolean success = response.charAt(0) == 'S';
		if(success)
		{
			String URL = Network.getServerAddress()+response.substring(1);
			if(Desktop.isDesktopSupported())
				Util.openBrowser(URL);
			else
				Util.copyToClipboard(URL);
		}
		else
			Output.error(response.substring(1));
		con.getInputStream().close();
		return success;
	}
	
	public static String processResponse(HttpURLConnection con) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String response = reader.readLine();
		reader.close();
		return response;
	}
	
	public static String processInfoRequest(String server, char header, String successMsg, String failureMsg)
	{
		try
		{
			HttpURLConnection con = (HttpURLConnection)new URL(server+"internal/processor.php").openConnection();
			Network.processRequest(con, new byte[] {(byte)header});
			return Network.processResponse(con) != null ? successMsg:failureMsg;
		}
		catch(IOException ex)
		{
			return "Could not read/write from/to server: "+ex.getLocalizedMessage();
		}
	}
	
	public static String reformatURL(String url)
	{
		if(!url.startsWith("http://")) url = "http://"+url;
		if(!url.endsWith("/")) url += "/";
		return url;
	}
}
