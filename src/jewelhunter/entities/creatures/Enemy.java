package jewelhunter.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import jewelhunter.Handler;
import jewelhunter.entities.Entity;
import jewelhunter.gfx.Animation;
import jewelhunter.gfx.Assets;
import jewelhunter.tiles.Tile;

public class Enemy extends Creature {
	
	private Animation animDown,animUp,animRight,animLeft;
	private boolean upActive=true,downActive=false,rightActive=true,leftActive=false;
	private int axis;
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 0;

	public Enemy(Handler handler, float x, float y,int axis) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		bounds = new Rectangle(10,0,Creature.DEFAULT_CREATURE_WIDTH-20,Creature.DEFAULT_CREATURE_HEIGHT);
		this.axis = axis;
		animDown = new Animation(500, Assets.zombie_down);
		animUp = new Animation(500, Assets.zombie_up);
		animLeft = new Animation(500, Assets.zombie_left);
		animRight = new Animation(500, Assets.zombie_right);
		speed = DEFAULT_SPEED+3;
	}

	@Override
	public void tick() {
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		
		if(axis == HORIZONTAL){
			horizontalMove();
		}
		if(axis == VERTICAL){
			verticalMove();
		}		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(),(int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()), width,height,null);
	}
	
	private BufferedImage getCurrentAnimationFrame() {
		if(xMove<0){
			return animLeft.getCurrentFrame();
		}else if(xMove>0){
			return animRight.getCurrentFrame();
		}else if(yMove<0){
			return animUp.getCurrentFrame();
		}else{		
			return animDown.getCurrentFrame();
		}		
	}
	
	private void verticalMove(){
				
		if(collisionWithTile((int)((x+bounds.x)/Tile.TILEWIDTH),(int)(y+yMove+bounds.y+bounds.height)/Tile.TILEHEIGHT)){
			upActive=true;
			downActive=false;
		}
		if(collisionWithTile((int)((x+bounds.x)/Tile.TILEWIDTH),(int)(y+yMove+bounds.y)/Tile.TILEHEIGHT)){
			upActive=false;
			downActive=true;
		}
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f,0f).intersects(getCollisionBounds(0f, 0f))){
			upActive=!upActive;
			downActive=!downActive;
			hurtPlayer();
		}
		for(Entity e:handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this)||e.toString().contains("Player"))
				continue;
			
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(0f, 0f))){
				upActive=!upActive;
				downActive=!downActive;
			}
		}
		if(upActive)
			yMove = -speed;
		if(downActive)
			yMove = speed;	
		moveY();
	}
	private void horizontalMove(){
			
		if(collisionWithTile((int)((int)(x+xMove+bounds.x+bounds.width)/Tile.TILEWIDTH),(int)(y+bounds.y)/Tile.TILEHEIGHT)){
			rightActive=true;
			leftActive=false;
		}
		if(collisionWithTile((int)((x+xMove+bounds.x)/Tile.TILEWIDTH),(int)(y+bounds.y)/Tile.TILEHEIGHT)){
			rightActive=false;
			leftActive=true;
		}
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f,0f).intersects(getCollisionBounds(0f, 0f))){
			rightActive=!rightActive;
			leftActive=!leftActive;
			hurtPlayer();
		}
		for(Entity e:handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this)||e.toString().contains("Player"))
				continue;
			if(e instanceof BarrelEnemy&&e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(0f, 0f))){
				if(e.getX()+e.getWidth()/2+2>this.getX()){
					rightActive=!false;
					leftActive=!true;
				}if(e.getX()+e.getWidth()/2-2<this.getX()){
					rightActive=!true;
					leftActive=!false;
				}
				continue;
			}
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(0f, 0f))){
				rightActive=!rightActive;
				leftActive=!leftActive;
			}
		}
		if(rightActive)
			xMove = -speed;
		if(leftActive)
			xMove = speed;		
		moveX();
	}
	
	private void hurtPlayer(){
		handler.getWorld().getEntityManager().getPlayer().hurt(1);
		handler.getWorld().getEntityManager().getPlayer().setFlinchTicks(1);
		handler.getWorld().getEntityManager().getPlayer().playerHurt(true, handler.getWorld().getEntityManager().getPlayer());
	}

}
