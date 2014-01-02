package bg.abv.king_hell.util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class Output
{
	
	public static void error(String message)
	{
		JOptionPane.showMessageDialog(null, message, "UploadR", JOptionPane.ERROR_MESSAGE);  
	}
	
	public static void warning(String message)
	{
		JOptionPane.showMessageDialog(null, message, "UploadR", JOptionPane.WARNING_MESSAGE);  
	}
	
	public static void info(String message)
	{
		JOptionPane.showMessageDialog(null, message, "UploadR", JOptionPane.INFORMATION_MESSAGE);  
	}
	
	public static boolean confirm(String message)
	{
		return JOptionPane.showConfirmDialog(null, message, "UploadR", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0;
	}
	
	public static void error(JFrame parent, String message)
	{
		JOptionPane.showMessageDialog(parent, message, "UploadR", JOptionPane.ERROR_MESSAGE);  
	}
	
	public static void warning(JFrame parent, String message)
	{
		JOptionPane.showMessageDialog(parent, message, "UploadR", JOptionPane.WARNING_MESSAGE);  
	}
	
	public static void info(JFrame parent, String message)
	{
		JOptionPane.showMessageDialog(parent, message, "UploadR", JOptionPane.INFORMATION_MESSAGE);  
	}
	
	public static boolean confirm(JFrame parent, String message)
	{
		return JOptionPane.showConfirmDialog(parent, message, "UploadR", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0;
	}
}
