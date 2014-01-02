package bg.abv.king_hell.capture;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JDialog;

import bg.abv.king_hell.util.Constants;
import bg.abv.king_hell.util.Util;

public class TransparentWindow extends JDialog
{
	private static final long serialVersionUID = 9132172635305954758L;

	private MouseListener mouseListener;
	
	public TransparentWindow()
	{
		Initialize();
	}
	
	private void Initialize()
	{
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.setSize(Util.getGraphicsDevice().getDisplayMode().getWidth(), Util.getGraphicsDevice().getDisplayMode().getHeight());
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		mouseListener = new MouseListener();
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
		this.setBackground(Constants.COLOR_TRANSPARENT);
		this.setVisible(true);
	}
	
    public void paint(Graphics graphics)
    {
    	super.paint(graphics);
    	Toolkit.getDefaultToolkit().sync();
        Graphics2D g = (Graphics2D)graphics;
    	if(AreaSelection.isEnabled())
    	{
	        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	        g.setColor(Constants.COLOR_GRAY_OPAQUE);
	        g.fillRect((int)AreaSelection.getArea().getX()+1, (int)AreaSelection.getArea().getY()+1, (int)AreaSelection.getArea().getWidth()-1, (int)AreaSelection.getArea().getHeight()-1);
	        g.setColor(Constants.COLOR_GRAY_BORDER);
	        g.drawRect((int)AreaSelection.getArea().getX(), (int)AreaSelection.getArea().getY(), (int)AreaSelection.getArea().getWidth(), (int)AreaSelection.getArea().getHeight());
    	}
    }
}
