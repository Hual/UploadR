package bg.abv.king_hell.util;

import java.awt.Desktop;
import java.awt.GraphicsDevice;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public final class Util
{
	private static GraphicsDevice device;
	
	public static String getDataFolder()
	{
		return "/"+(System.getProperty("user.home")+"/UploadR/").replaceAll("\\\\", "/");
	}
	
	public static void restartApplication(String arg)
	{
		String[] params = new String[] {
			System.getProperty("os.name").toLowerCase().startsWith("windows") ? "javaw" : "java",
			"-jar",
			Constants.JAR_LOCATION,
			arg
		};
		try
		{
			new ProcessBuilder(params).start();
		}
		catch(IOException e) { }
		System.exit(0);
	}
	
	public static byte[] concat(byte[] first, byte[] second)
	{
		int fLen = first.length, sLen = second.length;
		byte[] out = new byte[fLen+sLen];
		System.arraycopy(first, 0, out, 0, fLen);
		System.arraycopy(second, 0, out, fLen, sLen);
		return out;
	}
	
	public static GraphicsDevice getGraphicsDevice()
	{
		return device;
	}
	
	public static void setGraphicsDevice(GraphicsDevice gd)
	{
		device = gd;
	}
	
	public static void openBrowser(String url)throws URISyntaxException, IOException
	{
		Desktop.getDesktop().browse(new URI(url));
	}
	
	public static void copyToClipboard(String url)
	{
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(url), null);
	}
	
	public static String getClipboardContents() throws HeadlessException, UnsupportedFlavorException, IOException
	{
		return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
	}
	
	public static String readFileAsString(String filePath) throws java.io.IOException
	{
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1)
        {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
	}
	
	public static void writeToFile(String str, String path) throws IOException
	{
		File file = new File(path);
		if(!file.exists())
		{
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		FileOutputStream erasor = new FileOutputStream(path);
		erasor.write((new String()).getBytes());
		erasor.close();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		writer.write(str);
		writer.flush();
		writer.close();
	}
}
