package bg.abv.king_hell.launcher;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public final class Launcher
{
	public static Project project;
	public static BufferedImage icon;
	private static final LauncherFrame progressTracker = new LauncherFrame("Fetching latest version data", icon);
	
	public static void main(String[] args) throws MalformedURLException
	{
		String
			path = Util.getDataFolder()+"core.prt",
			baseURL = "http://80.241.211.41/uploadr/client/",
			lVersion = (args.length == 2 && args[1].startsWith("-version=") ? args[1].substring(9).trim() : Util.getPropFromString(Network.getRemoteManifestFromFile(new URL(baseURL+"info.dat")), "Latest-Version"));
		try
		{
			icon = ImageIO.read(Launcher.class.getResource("icon_s.png"));
			project = new Project(path);
			if(args.length > 0)
			{
				if(args[0].equals("-update-core"))
					Network.downloadFile(new URL(baseURL+"core/"+lVersion+".prt"), path, progressTracker, "Update found! Downloading core file");
				else if(args[0].equals("-update-hook"))
					Network.downloadHookLibrary(progressTracker, baseURL);
				else if(args[0].equals("-update-all"))
					Network.downloadFiles(baseURL, lVersion, path, progressTracker, true);
			}
			project.reloadProperties();
			progressTracker.setAction("Checking for updates");
			if(lVersion != null && args.length == 0)
			{
				if(!project.exists())
					Network.downloadFiles(baseURL, lVersion, path, progressTracker, false);
				else if(!lVersion.equals(project.getVersion()) && Util.confirm(progressTracker, "New update found: \nCurrent version: "+project.getVersion()+"\nLatest version: "+lVersion+"\nWould you like to update?", JOptionPane.INFORMATION_MESSAGE))
					Network.downloadFile(new URL(baseURL+"core/"+lVersion+".prt"), path, progressTracker, "Update found! Downloading core file");
				project.reloadProperties();
			}
			progressTracker.setAction("Loading core classes");
			project.load("bg.abv.king_hell.Main", "start", project.getVersion(), icon);
			if(!project.isLoaded())
				throw new Exception("Could not load core classes ("+project.getLastException().getClass()+ " "+project.getLastException().getLocalizedMessage()+")");
			progressTracker.dispose();
		}
		catch(Exception e)
		{
			if(Util.confirm(null, "Oops! Something went wrong.\n\nAction: "+progressTracker.getAction()+"\nIssue: "+e.getLocalizedMessage()+"\nWould you like to redownload all files?", JOptionPane.ERROR_MESSAGE))
				main(new String[] {"-update-all"});
			else
				System.exit(0);
		}
	}
}

