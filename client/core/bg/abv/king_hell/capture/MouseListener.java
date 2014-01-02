package bg.abv.king_hell.capture;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public final class MouseListener implements MouseInputListener
{
	public MouseListener()
	{
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		switch(e.getButton())
		{
			case MouseEvent.BUTTON1:
			{
				AreaSelection.renew(e.getLocationOnScreen());
				break;
			}
			case MouseEvent.BUTTON2:
			{
				if(AreaSelection.isEnabled())
				{
					AreaSelection.cancelSelection();
				}
				else
				{
					System.exit(0);
				}
				break;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		AreaSelection.completeSelection(e.getLocationOnScreen());
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(AreaSelection.isEnabled())
		{
			AreaSelection.processCoords(e.getLocationOnScreen());
			AreaSelection.update();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		
	}

}
