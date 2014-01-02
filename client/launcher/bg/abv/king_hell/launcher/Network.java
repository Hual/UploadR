package bg.abv.king_hell.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public final class Network
{
	private static InputStream is;
	private static FileOutputStream os;
	private static File currentFile;
	
	public static void downloadFile(URL url, String file, LauncherFrame progressTracker, String message) throws IOException
	{
		is = null;
		os = null;
		currentFile = new File(file);
		try
		{
			URLConnection con = url.openConnection();
			int size = con.getContentLength();
			progressTracker.setMaximum(size);
			progressTracker.setAction(message+": 0kB/"+size+"kB");
			is = con.getInputStream();
			currentFile.getParentFile().mkdirs();
			os = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len, total = 0;
			while((len = is.read(buf)) > 0)
			{
				os.write(buf, 0, len);
				progressTracker.setProgress(total+=len);
				progressTracker.setAction(message+": "+total+"kB/"+size+"kB");
			}
		}
		finally
		{
			cancelDownload();
			currentFile = null;
		}
	}
	
	public static boolean cancelDownload()
	{
		try
		{
			if(is != null) is.close();
			if(os != null)
			{
				os.flush();
				os.close();
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static File getCurrentFile()
	{
		return currentFile;
	}
	
	public static boolean downloadHookLibrary(LauncherFrame frame, String baseURL)
	{
		Util.Family family = Util.getFamily();
		Util.Arch arch = Util.getArchitecture();
		String extension = Util.getNativeExtension();
		if(family == Util.Family.UNSUPPORTED || arch == Util.Arch.UNSUPPORTED)
			return false;
		String file = ("hooklib_"+family+"_"+arch+extension).toLowerCase();
		try
		{
			downloadFile(new URL(baseURL+"lib/"+file), Util.getDataFolder()+"hooklib"+extension, frame, "Downloading hook library");
			return true;
		}
		catch (IOException e) 
		{
			return false;
		}
	}
	
	public static void downloadFiles(String baseURL, String version, String path, LauncherFrame tracker, boolean requireHookLib) throws IOException,InterruptedException
	{
		Network.downloadFile(new URL(baseURL+"core/"+version+".prt"), path, tracker, "Update found! Downloading core file");
		if(requireHookLib || !Util.hookLibraryExists())
			Network.downloadHookLibrary(tracker, baseURL);
	}
	
	public static String getRemoteManifestFromFile(URL url)
	{
		String str = "";
		InputStream is = null;
		try
		{
			URLConnection con = url.openConnection();
			is = con.getInputStream();
			byte[] buf = new byte[128];
			while(is.read(buf) > 0) str += new String(buf);
		}
		catch(Exception e)
		{
			str = "";
		}
		if(is != null)
		try
		{
				is.close();
		}
		catch (IOException e) {}
		return str;
	}
}