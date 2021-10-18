package jewelhunter.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import jewelhunter.Handler;
import jewelhunter.entities.Entity;
import jewelhunter.entities.creatures.Creature;
import jewelhunter.entities.creatures.LaserBeam;
import jewelhunter.gfx.Assets;

public class Laser extends StaticEntity {

	public Entity e;
	
	public final static int LEFT = 0;
	public final static int RIGHT = 1;
	public int face;
	public boolean shoot;
	
	public Laser(Handler handler, float x, float y, int face) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);		
		bounds.x=0;
		bounds.y=0;
		bounds.width=width;
		bounds.height=height;
		if(face==LEFT||face==RIGHT){
			bounds.y = 8;
			bounds.height-=8;
		}
		
		this.face = face;
		e = new LaserBeam(handler, x, y,face);
		handler.getWorld().getEntityManager().addEntity(e);
	}

	@Override
	public void tick(){
		
	}
	
	@Override
	public void die() {
		e.hurt(3);	
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getFrame(), (int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()), width,height, null);
	}
	
	public BufferedImage getFrame(){
		
		if(face==LEFT){
			if(e.isShootLaserBeam())
				return Assets.laserLeft[1];
			return Assets.laserLeft[0];
		}
		if(face==RIGHT){
			if(e.isShootLaserBeam())
				return Assets.laserRight[1];
			return Assets.laserRight[0];
		}
		
		return null;
		
	}
	
	
}
