package jewelhunter.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;

import jewelhunter.Handler;
import jewelhunter.gfx.Animation;
import jewelhunter.gfx.Assets;

public class FlameThrower extends Creature {
	
	private Animation animFlame;
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 0;
	protected Rectangle bounds;
	double ticks;
	int i;
	int actualWidth;
	private int index,incHealth;
	private long lastTime,timer;

	public FlameThrower(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		bounds = new Rectangle(0,0,Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		speed = 500;
		animFlame = new Animation(500, Assets.flameThrower);
		actualWidth = width;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
		health = 4;
	}

	@Override
	public void tick() {
		
		animFlame.tick();
		
		timer+=System.currentTimeMillis()-lastTime;
		lastTime = System.currentTimeMillis();
		if (timer>speed){
			index++;
			timer = 0;
			width = actualWidth*(index+1);
			if (index >= 4) {
				index = 0;
				width = actualWidth;
				incHealth=0;
			}
			
		}
		checkAttack();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.flameThrower[index],(int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()), width,height,null);
	}
	

	private void checkAttack(){
		health = handler.getWorld().getEntityManager().getPlayer().getHealth();
		
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(new Rectangle((int)(x+actualWidth), (int)(y+4), width-2-actualWidth, 50))&&index>0){
			incHealth++;
		}
		else{
			incHealth=0;
		}
		if(incHealth==1 && handler.getWorld().getEntityManager().getPlayer().getHealth()>0){
			handler.getWorld().getEntityManager().getPlayer().hurt(1);
			handler.getWorld().getEntityManager().getPlayer().setFlinchTicks(1);
			handler.getWorld().getEntityManager().getPlayer().playerHurt(true, handler.getWorld().getEntityManager().getPlayer());
		}
	}
	
}
