package jewelhunter.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;

import jewelhunter.Handler;
import jewelhunter.entities.statics.Laser;
import jewelhunter.gfx.Animation;
import jewelhunter.gfx.Assets;

public class LaserBeam extends Creature {
	
	public boolean shootBeam;
	Animation anim,animBurst;
	private int incHealth,axis,burstX,burstY;
	private long ticks;
	
	public LaserBeam(Handler handler, float x, float y, int axis) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH-2, Creature.DEFAULT_CREATURE_HEIGHT-10);
		bounds = new Rectangle(0,0, 0, 0);
		if(axis==Laser.RIGHT){
			this.x+=57;
			burstX=32;
			this.y+=5;
			anim= new Animation(100, Assets.laserBeamH);
		}
		if(axis==Laser.LEFT){
			this.x-=59;
			burstX=-27;
			this.y+=5;
			anim= new Animation(100, Assets.laserBeamH);
		}
		this.axis = axis;
		
		speed = DEFAULT_SPEED+1;
		animBurst = new Animation(100, Assets.laserBeamBurst);
	}

	@Override
	public void tick() {
		anim.tick();
		animBurst.tick();
		if(++ticks%100==0){
			this.setShootLaserBeam(!this.isShootLaserBeam());;
		}
		
		if(!this.isShootLaserBeam())
			incHealth=0;
		
		if(isShootLaserBeam()){
			if(axis==Laser.RIGHT||axis==Laser.LEFT)
				bounds = new Rectangle(0,25,64, 14);
			else
				bounds = new Rectangle(0,0, 0, 0);
			checkAttack();
		}
		
		
	}
	
	@Override
	public void die(){
		
	}

	@Override
	public void render(Graphics g) {
		if(this.isShootLaserBeam()){
			g.drawImage(anim.getCurrentFrame(), (int) (x+2-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()), width,height,null);
			g.drawImage(animBurst.getCurrentFrame(), (int) (x+1+burstX-handler.getGameCamera().getxOffset()),(int) (y+burstY-handler.getGameCamera().getyOffset()), width,height,null);
		}
		
	}
	
	
	private void checkAttack(){
		health = handler.getWorld().getEntityManager().getPlayer().getHealth();
		
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(getCollisionBounds(0f, 0f))){
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
