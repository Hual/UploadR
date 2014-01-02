package bg.abv.king_hell.tray;

import java.awt.MenuItem;
import java.awt.PopupMenu;

import bg.abv.king_hell.util.Constants;

public class Menu extends PopupMenu
{
	private static final long serialVersionUID = 9116764014703550231L;
	private ItemHandler ih;
	
	public Menu()
	{
		super();
		createItems();
	}
	
	private void createItems()
	{
		ih = new ItemHandler();
		MenuItem info = new MenuItem("(c) UploadR v"+Constants.VERSION +" | shortcuts "+(Constants.KEYS_ENABLED ? "enabled" : "disabled"));
		info.setEnabled(false);
		this.add(info);
		for(byte i=0;i<ItemHandler.items.length;++i)
		{
			if(ItemHandler.items[i][0].equals("s"))
			{
				this.addSeparator();
				continue;
			}
			MenuItem item = new MenuItem(ItemHandler.items[i][0]);
			item.setActionCommand(ItemHandler.items[i][1]);
			item.addActionListener(ih);
			this.add(item);
		}
	}

	public ItemHandler getItemHandler()
	{
		return ih;
	}
	
}
