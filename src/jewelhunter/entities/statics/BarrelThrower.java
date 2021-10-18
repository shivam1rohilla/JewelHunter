package jewelhunter.entities.statics;

import java.awt.Graphics;

import jewelhunter.Handler;
import jewelhunter.entities.Entity;
import jewelhunter.entities.creatures.BarrelEnemy;
import jewelhunter.entities.creatures.Creature;
import jewelhunter.gfx.Assets;

public class BarrelThrower extends StaticEntity {

	public Entity e;
	public BarrelThrower(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		bounds.x=0;
		bounds.y=0;
		bounds.width=width;
		bounds.height=height;
		e = new BarrelEnemy(handler, x, y);
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
		g.drawImage(Assets.barrelGate,(int)(x-handler.getGameCamera().getxOffset()), (int)(y-handler.getGameCamera().getyOffset()), width, height, null);
	}
	
	
	
}
