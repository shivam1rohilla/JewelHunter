package jewelhunter.entities.statics;

import jewelhunter.Handler;
import jewelhunter.entities.Entity;
import jewelhunter.tiles.Tile;

public abstract class StaticEntity extends Entity {

	protected float xMove,yMove;
	
	public StaticEntity(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
	}

	public void moveX(){
		if(xMove>0){
			int tx=(int)(x+xMove+bounds.x+bounds.width)/Tile.TILEWIDTH;
			if(!collisionWithTile(tx, (int)(y+bounds.y)/Tile.TILEHEIGHT)&&
					!collisionWithTile(tx, (int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)){
				x+=xMove;
			}else{
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			}
		}else if(xMove<0){
			int tx=(int)(x+xMove+bounds.x)/Tile.TILEWIDTH;
			if(!collisionWithTile(tx, (int)(y+bounds.y)/Tile.TILEHEIGHT)&&
					!collisionWithTile(tx, (int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)){
				x+=xMove;
			}else{
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
			}
		}
		
	}
	public void moveY(){
		if(yMove<0){
			int ty=(int)(y+yMove+bounds.y)/Tile.TILEHEIGHT;
			if(!collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty)&&
					!collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)){
				y+=yMove;
			}else{
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			}
		}else if(yMove>0){
			int ty=(int)(y+yMove+bounds.y+bounds.height)/Tile.TILEHEIGHT;
			if(!collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty)&&
					!collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)){
				y+=yMove;
			}else{
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			}
		}
	}
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();		
	}
	public float getxMove() {
		return xMove;
	}


	public float getyMove() {
		return yMove;
	}
	
	
}
