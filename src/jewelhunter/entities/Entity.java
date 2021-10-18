package jewelhunter.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import jewelhunter.Handler;

public abstract class Entity {
	
		public static final int DEFAULT_HEALTH=3;
		protected Handler handler;
		protected float x,y;
		protected int width, height;
		protected Rectangle bounds; 
		protected boolean active = true;
		public boolean teleport,openedChest;
		public float speed;
		protected int health;
		private boolean shootLaserBeam;
		public int randBarrelStop;
		public float actualXCrate;
		public float actualYCrate;
		
		public Entity(Handler handler,float x, float y, int width,int height){
			this.x=x;
			this.handler=handler;
			this.y=y;
			this.width=width;
			this.height=height;
			bounds = new Rectangle(0, 0, width, height);
			health = DEFAULT_HEALTH;
		}
		
		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public abstract void tick();
		
		public abstract void render(Graphics g);
		
		public Rectangle getCollisionBounds(float xOffset,float yOffset) {
			return new Rectangle((int)(x+bounds.x+xOffset), (int)(y+bounds.y+yOffset ), bounds.width,bounds.height);
			
		}
		
		public boolean checkEntityCollisions(float xOffset,float yOffset){
			for(Entity e:handler.getWorld().getEntityManager().getEntities()){
				if(e.equals(this)||e.toString().contains("Chest")||e.toString().contains("TeleportButton")||e.toString().contains("LaserBeam"))
					continue;
				if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
					return true;
			}
			return false;
		}

		public void die() {
		}
		
		
		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public int getHealth() {
			return health;
		}

		public void setHealth(int health) {
			this.health = health;
		}

		
		public void hurt(int amt){
			health -=amt;
			if(health <= 0){
				active=false;
				die();
			}
			
		}
		public void spawn(float x,float y){
			this.x = x;
			this.y = y;
		}

		public boolean isShootLaserBeam() {
			return shootLaserBeam;
		}

		public void setShootLaserBeam(boolean shootLaserBeam) {
			this.shootLaserBeam = shootLaserBeam;
		}
		
}
