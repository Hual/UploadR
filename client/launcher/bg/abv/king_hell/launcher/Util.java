package bg.abv.king_hell.launcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class Util
{
	
	public enum Family
	{
		FREEBSD,
		OPENBSD,
		OSX,
		SOLARIS,
		LINUX,
		WINDOWS,
		UNSUPPORTED
	}

	public enum Arch
	{
		ALPHA,
		ARM,
		IA64_32,
		IA64,
		MIPS,
		SPARC,
		SPARC64,
		PPC,
		PPC64,
		x86,
		x86_64,
		UNSUPPORTED
	}
	
	public static Family getFamily()
	{
		String name = System.getProperty("os.name");
		if(name.equalsIgnoreCase("freebsd")) return Family.FREEBSD;
		else if(name.equalsIgnoreCase("openbsd")) return Family.OPENBSD;
		else if(name.equalsIgnoreCase("mac os x")) return Family.OSX;
		else if(name.equalsIgnoreCase("solaris") || name.equalsIgnoreCase("sunos")) return Family.SOLARIS;
		else if(name.equalsIgnoreCase("linux")) return Family.LINUX;
		else if(name.toLowerCase().startsWith("windows")) return Family.WINDOWS;
		return Family.UNSUPPORTED;
	}

	public static Arch getArchitecture()
	{
		String name = System.getProperty("os.arch");
		if(name.equalsIgnoreCase("alpha")) return Arch.ALPHA;
		else if(name.toLowerCase().startsWith("arm")) return Arch.ARM;
		else if(name.equalsIgnoreCase("ia64_32")) return Arch.IA64_32;
		else if(name.equalsIgnoreCase("ia64")) return Arch.IA64;
		else if(name.equalsIgnoreCase("mips")) return Arch.MIPS;
		else if(name.equalsIgnoreCase("sparc"))	return Arch.SPARC;
		else if(name.equalsIgnoreCase("sparc64")) return Arch.SPARC64;
		else if(name.equalsIgnoreCase("ppc") ||
				name.equalsIgnoreCase("powerpc")) return Arch.PPC;
		else if(name.equalsIgnoreCase("ppc64") ||
				name.equalsIgnoreCase("powerpc64")) return Arch.PPC64;
		else if(name.equalsIgnoreCase("x86") ||
			name.equalsIgnoreCase("i386") ||
			name.equalsIgnoreCase("i486") ||
			name.equalsIgnoreCase("i586") ||
			name.equalsIgnoreCase("i686") ||
			System.getProperty("sun.arch.data.model").equals("32")) return Arch.x86;
		else if(name.equalsIgnoreCase("x86_64") ||
				name.equalsIgnoreCase("amd64") ||
				name.equalsIgnoreCase("k8") ||
				System.getProperty("sun.arch.data.model").equals("64")) return Arch.x86_64;
		return Arch.UNSUPPORTED;
	}
	
	public static String getDataFolder()
	{
		return "/"+(System.getProperty("user.home")+"/UploadR/").replaceAll("\\\\", "/");
	}
	
	public static String getApplicationPath() throws UnsupportedEncodingException
	{
		String path = URLDecoder.decode(Launcher.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"); 
		return path.charAt(0) == '/' ? path.substring(1) : path;
	}
	
	public static boolean confirm(JFrame parent, String query, int type)
	{
		return JOptionPane.showConfirmDialog(parent, query, "UploadR", JOptionPane.YES_NO_OPTION, type) == 0;
	}

	public static void error(String message)
	{
		JOptionPane.showMessageDialog(null, message, "UploadR", JOptionPane.ERROR_MESSAGE);  
	}

	public static String readZippedFileAsString(File zipFile, String file) throws IOException
	{
		String fs = "";
		ZipFile zf = new ZipFile(zipFile);
		ZipEntry ze = zf.getEntry(file);
		InputStream is = zf.getInputStream(ze);
		byte[] buf = new byte[128];
		while(is.read(buf) > 0)  fs += new String(buf);
		is.close();
		zf.close();
		return fs;
	}
	
	public static String getPropFromString(String str, String prop)
	{
		for(String i:str.split("\n"))
		{
			if(i.startsWith(prop+":"))
			{
				return i.substring(prop.length()+1).trim();
			}
		}
		return (String)null;
	}
	
	public static boolean hookLibraryExists()
	{
		return new File(getDataFolder()+"hooklib"+getNativeExtension()).exists();
	}
	
	public static String getNativeExtension()
	{
		return System.mapLibraryName("").substring(System.mapLibraryName("").lastIndexOf('.'));
	}
}
