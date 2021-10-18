package jewelhunter.entities.statics;

import java.awt.Graphics;
import java.awt.Rectangle;

import jewelhunter.Handler;
import jewelhunter.gfx.Assets;
import jewelhunter.item.Item;
import jewelhunter.tiles.Tile;

public class Chest extends StaticEntity {
	
	
	private boolean chestOpened = false;
	private Item item;
	private int no;

	public Chest(Handler handler, float x, float y, Item item,int no) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);	
		bounds.x=0;
		bounds.y=0;
		bounds.width=64;
		bounds.height=64;
		this.item = item;
		this.no = no;
	}

	@Override
	public void tick() {
		openedChest=chestOpened;
		if(chestOpened)
			return;
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(new Rectangle((int)x, (int)y, width, height))){
			chestOpened = true;	
			die();
		}
	}
	
	@Override
	public void die() {
		for(int i=0;i<no;i++)
			handler.getWorld().getItemManager().addItem(item);
	}
	

	@Override
	public void render(Graphics g) {
		if(chestOpened)
			g.drawImage(Assets.chest[1], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		else
			g.drawImage(Assets.chest[0], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}
}