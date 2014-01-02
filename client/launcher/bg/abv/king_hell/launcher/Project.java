package bg.abv.king_hell.launcher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class Project
{
	private File _path;
	private String _manifest, _version;
	private ClassLoader _loader;
	private boolean _loaded;
	private Exception _exception;
	
	public Project(String path) throws IOException
	{
		_path = new File(path);
	}
	
	public boolean reloadProperties() throws IOException
	{
		if(_path.exists())
		{
			_manifest = Util.readZippedFileAsString(_path, "META-INF/MANIFEST.MF");
			_version = getProp("Project-Version");
			return true;
		}
		return false;
	}
	
	public boolean load(String mainClass, String mainMethod, String version, BufferedImage icon)
	{
		try
		{
			_loader = new URLClassLoader(new URL[]{_path.toURI().toURL()}, Launcher.class.getClassLoader());
			Class<?> cl = _loader.loadClass(mainClass);
			cl.getMethod(mainMethod, new Class<?>[]{BufferedImage.class, String.class, String.class}).invoke(null, new Object[] {icon, version, Util.getApplicationPath()});
			_loaded = true;
			return true;
		}
		catch(Exception e)
		{
			_exception = e;
			_loaded = false;
			return false;
		}
	}
	
	public File getFile()
	{
		return _path;
	}
	
	public boolean isLoaded()
	{
		return _loaded;
	}
	
	public ClassLoader getClassLoader()
	{
		return _loader;
	}
	
	public Exception getLastException()
	{
		return _exception;
	}
	
	public String getVersion()
	{
		return _version;
	}
	
	public void setVersion(String version)
	{
		_version = version;
	}
	
	public String getManifest()
	{
		return _manifest;
	}
	
	public void setManifest(String manifest)
	{
		_manifest = manifest;
	}
	
	public boolean exists()
	{
		return _path.exists();
	}
	
	public String getProp(String prop)
	{
		return Util.getPropFromString(_manifest, prop);
	}
	
	public boolean propExists(String prop)
	{
		return !(getProp(prop) == null);
	}
}
