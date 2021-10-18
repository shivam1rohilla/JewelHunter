package jewelhunter.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;

import jewelhunter.Handler;
import jewelhunter.entities.Entity;
import jewelhunter.entities.statics.Crate;
import jewelhunter.gfx.Animation;
import jewelhunter.gfx.Assets;
import jewelhunter.tiles.Tile;

public class BarrelEnemy extends Creature {

	private Animation animBarrel;
	private float actualX,actualY;
	public boolean contiueRoll;
	private long randTicks;
	private boolean stop;
	
	
	public BarrelEnemy(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH-2, Creature.DEFAULT_CREATURE_HEIGHT-10);
		bounds = new Rectangle(1,1, Creature.DEFAULT_CREATURE_WIDTH-2, Creature.DEFAULT_CREATURE_HEIGHT-2);
		animBarrel = new Animation(100, Assets.barrel);
		actualX = x;
		actualY = y;
		speed = DEFAULT_SPEED+1;
		this.randBarrelStop=101;
	}

	@Override
	public void tick() {
		animBarrel.tick();
		if(stop){
			randTicks++;
		}else
			verticalMove();
		
		if(randTicks>(int)((Math.random() * randBarrelStop)+50)){
			stop=false;
			randTicks=0;
		}
	}
	
	@Override
	public void die(){
		
	}

	@Override
	public void render(Graphics g) {
		barrelRender(g);
	}
	
	private void barrelRender(Graphics g){
		
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(new Rectangle((int)x, (int)(y+yMove), Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT))
				|| y<=actualY+height-20)
			return;
		g.drawImage(animBarrel.getCurrentFrame(),(int) (x+1-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()), width,height,null);
		
	}
	private void verticalMove(){
		yMove = speed;
		if(collisionWithTile((int)((int)(x)/Tile.TILEWIDTH),(int)(y+yMove+bounds.height)/Tile.TILEHEIGHT)||check(0f, 0f)){
			x=actualX;
			y=actualY;
			stop=true;
		}
		moveY();
	}
	private boolean check(float xOffset,float yOffset){
		
		for(Entity e:handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this)||y<actualY+Creature.DEFAULT_CREATURE_HEIGHT)
				continue;
			if(e instanceof Crate&&e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))&&handler.getWorld().getEntityManager().getPlayer().getHealth()>0){
				
				e.hurt(1);
				return true;
			}
			if(e instanceof Player&&e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(0f, handler.getWorld().getEntityManager().getPlayer().getyMove()))){
				handler.getWorld().getEntityManager().getPlayer().setFlinchTicks(1);
				e.hurt(1);
				handler.getWorld().getEntityManager().getPlayer().playerHurt(true, handler.getWorld().getEntityManager().getPlayer());
				return true;
			}
		}
		return false;
	}
	
}
