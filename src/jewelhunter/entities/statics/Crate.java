package jewelhunter.entities.statics;

import java.awt.Graphics;
import java.awt.Rectangle;


import jewelhunter.Handler;
import jewelhunter.gfx.Assets;
import jewelhunter.tiles.Tile;

public class Crate extends StaticEntity {
	
	boolean up,right,left,down;
	int reset;
	int pH = 0;
	int ticks;
	public Crate(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);	
		bounds.x=1;
		bounds.y=1;
		bounds.width=62;
		bounds.height=62;
		this.actualXCrate=x;
		this.actualYCrate=y;
	}

	@Override
	public void tick() {
		crateMove();		
	}
	
	@Override
	public void die() {
	}
	

	@Override
	public void render(Graphics g) {
			g.drawImage(Assets.crate, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	private void crateMove(){
		if(!checkEntityCollisions(handler.getWorld().getEntityManager().getPlayer().getxMove(), handler.getWorld().getEntityManager().getPlayer().getyMove()))
		{
			if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(handler.getWorld().getEntityManager().getPlayer().getxMove(), 0).intersects(new Rectangle((int)x, (int)y, width, height))){
				xMove=handler.getWorld().getEntityManager().getPlayer().getxMove();
				int a=(int) (handler.getWorld().getEntityManager().getPlayer().getY()+handler.getWorld().getEntityManager().getPlayer().getHeight()/2);
				if(a>y&&a<y+bounds.height)
				moveX();
			}
			if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, handler.getWorld().getEntityManager().getPlayer().getyMove()).intersects(new Rectangle((int)x, (int)y, width, height))){
				yMove=handler.getWorld().getEntityManager().getPlayer().getyMove();
				int a=(int) (handler.getWorld().getEntityManager().getPlayer().getX()+handler.getWorld().getEntityManager().getPlayer().getWidth()/2);
				if(a>x&&a<x+bounds.width)
				moveY();
			}
		}
	}
	
}