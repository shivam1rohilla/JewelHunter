package jewelhunter.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import jewelhunter.Handler;
import jewelhunter.entities.Entity;
import jewelhunter.entities.creatures.Player;
import jewelhunter.gfx.Assets;
import jewelhunter.tiles.Tile;

public class TeleportButton extends StaticEntity {
	
	Entity e;
	
	public TeleportButton(Handler handler, int x, int y) {
		super(handler, x, y, Tile.TILEWIDTH/2, Tile.TILEHEIGHT/2);
		
		bounds.x = 5;
		bounds.y = 5;
		bounds.width = width-10;
		bounds.height = height-10;
	}

	@Override
	public void tick() {
		for(Entity e:handler.getWorld().getEntityManager().getEntities()){
			if((e instanceof Player)&&e.getCollisionBounds(0, 0).intersects(getCollisionBounds(0f, 0f))){
				teleport = true;
				this.e = e;
				//continue;
			}
			if((e instanceof Crate)&&e.getCollisionBounds(0, 0).intersects(getCollisionBounds(0f, 0f))){
				teleport = true;
				this.e = e;
				//continue;
			}
			if(teleport&&!this.e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(0f, 0f)))
				teleport = false;
		}
		
			
		checkTeleport();
	}
	
	@Override
	public void die() {
		
	}
	

	@Override
	public void render(Graphics g) {
		//((Graphics2D)g).draw(getCollisionBounds(0f, 0f));
		g.drawImage(getButtonFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		//g.fill3DRect((int)(x-handler.getGameCamera().getxOffset()), (int)(y+bounds.y-handler.getGameCamera().getyOffset()), bounds.width, bounds.height, true);
	}
	private void checkTeleport(){
	}
	
	private BufferedImage getButtonFrame(){
		if(teleport)
			return Assets.teleportButton[1];
		else
			return Assets.teleportButton[0];
	}	
	
}
