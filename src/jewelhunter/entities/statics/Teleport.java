package jewelhunter.entities.statics;

import java.awt.Graphics;
import java.awt.Rectangle;

import jewelhunter.Handler;
import jewelhunter.entities.Entity;
import jewelhunter.gfx.Assets;
import jewelhunter.tiles.Tile;

public class Teleport extends StaticEntity {
	Entity tele;
	public static final int HORIZONTAL=1,VERTICAL=2;
	int axis;
	public Teleport(Handler handler, int tileX, int tileY,int axis,int tX, int tY) {
		super(handler, tileX*Tile.TILEWIDTH, tileY*Tile.TILEHEIGHT, Tile.TILEWIDTH, Tile.TILEHEIGHT);
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
		
		if(VERTICAL==axis){
			bounds.x+=2;
			bounds.width-=4;
		}if(HORIZONTAL==axis){
			bounds.y+=2;
			bounds.height-=4;
		}
		this.axis=axis;
		tele = new TeleportButton(handler, tX, tY);
		handler.getWorld().getEntityManager().addEntity(tele);
	}

	@Override
	public void tick() {
		if(!handler.getWorld().getTile((int)x/Tile.TILEWIDTH, (int)y/Tile.TILEHEIGHT).isSolid())
			hurt(3);
		if(tele.teleport)
			checkTeleport();
	}
	
	@Override
	public void die() {
		
	}
	

	@Override
	public void render(Graphics g) {
		if(tele.teleport){
			if(axis==VERTICAL){
				g.drawImage(Assets.tDown, (int) (x - handler.getGameCamera().getxOffset()), (int) (y+64 - handler.getGameCamera().getyOffset()), width, height, null);
				g.drawImage(Assets.tUp, (int) (x- handler.getGameCamera().getxOffset()), (int) (y-64 - handler.getGameCamera().getyOffset()), width, height, null);
			}
			if(axis==HORIZONTAL){
				g.drawImage(Assets.tLeft, (int) (x-64 - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
				g.drawImage(Assets.tRight, (int) (x+64 - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			}
		}
	}
	private void checkTeleport(){
			int xx=(int) (handler.getWorld().getEntityManager().getPlayer().getX()+handler.getWorld().getEntityManager().getPlayer().getWidth()/2);
			int yy=(int) (handler.getWorld().getEntityManager().getPlayer().getY()+handler.getWorld().getEntityManager().getPlayer().getHeight()/2);
			if(handler.getWorld().getTile((int)x/Tile.TILEWIDTH, (int)y/Tile.TILEHEIGHT).isSolid())
			{if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(handler.getWorld().getEntityManager().getPlayer().getxMove(), 0).intersects(new Rectangle((int)x, (int)y, bounds.width, bounds.height))){
				if(yy>y&&yy<y+bounds.height)
				{
					if(xx<x)
						handler.getWorld().getEntityManager().getPlayer().setX(handler.getWorld().getEntityManager().getPlayer().getX()+96);
					if(xx>x+bounds.width+1)
						handler.getWorld().getEntityManager().getPlayer().setX(handler.getWorld().getEntityManager().getPlayer().getX()-96);
				}
			}
			if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, handler.getWorld().getEntityManager().getPlayer().getyMove()).intersects(new Rectangle((int)x, (int)y, bounds.width, bounds.height))){
				if(xx>x&&xx<x+bounds.width)
				{
					if(yy<y)
						handler.getWorld().getEntityManager().getPlayer().setY(handler.getWorld().getEntityManager().getPlayer().getY()+96);
					if(yy>y+bounds.height)
						handler.getWorld().getEntityManager().getPlayer().setY(handler.getWorld().getEntityManager().getPlayer().getY()-96);
				}
			}
			}
		}

	public boolean isTeleport() {
		return teleport;
	}

	public void setTeleport(boolean teleport) {
		this.teleport = teleport;
	}
	
}
