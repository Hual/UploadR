package bg.abv.king_hell.launcher;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LauncherFrame extends JFrame implements WindowListener
{
	private static final long serialVersionUID = 2414251485683800692L;

	private JProgressBar progressBar;
	private JLabel actionLabel;
	
	public LauncherFrame(String init, BufferedImage icon)
	{
		this.setTitle("UploadR - Updater");
		this.setIconImage(icon);
		this.setResizable(false);
		this.setType(Type.NORMAL);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, 340, 81);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		progressBar = new JProgressBar();
		progressBar.setValue(0);
		contentPane.add(progressBar, BorderLayout.SOUTH);
		
		actionLabel = new JLabel(init);
		actionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(actionLabel, BorderLayout.CENTER);
		
		this.addWindowListener(this);
		this.setVisible(true);
	}
	
	public void setAction(String action)
	{
		actionLabel.setText(action);
	}
	
	public String getAction()
	{
		return actionLabel.getText();
	}
	
	public void setProgress(int progress)
	{
		progressBar.setValue(progress);
	}
	
	public void setMaximum(int max)
	{
		progressBar.setMaximum(max);
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		if(Util.confirm(this, "Are you sure you want to abort the download?", JOptionPane.WARNING_MESSAGE))
		{
			if(Launcher.project.getFile().exists() && Network.getCurrentFile() != null && Network.cancelDownload())
				Network.getCurrentFile().delete();
			System.exit(0);
		}
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		
	}
}
