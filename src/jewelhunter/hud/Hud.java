package jewelhunter.hud;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Random;

import jewelhunter.Handler;
import jewelhunter.gfx.Animation;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;
import jewelhunter.item.Item;
import jewelhunter.states.GameStateStage4;
import jewelhunter.tiles.Tile;

public class Hud {
	
	private Handler handler;
	int minutes,seconds,attackCoolDown;
	private float progress = 0.0f;
	private int animationTime = 150;
    int width;
    int progressWidth;
    int i=0,rotationX,rotationY,r,pH;
    private double rotationAngle = Math.toRadians(0);
    private int compassX, compassY;
    private boolean speedSlowActive;
    private int ticks=0;
    private Animation animHeart;
    private int xT;
    Random random=new Random();
    

	public Hud(Handler handler) {
		this.handler = handler;
		animationTime = 150;
        width = handler.getWidth();
        compassX = 25;
        compassY = handler.getHeight()-70;
        animHeart = new Animation(1000, Assets.healthHearts);
        
	}
	
	public void tick() {
		animHeart.tick();
		progress();
		if(handler.getWorld().getEntityManager().getPlayer().getTicks()%100==0){
			rotationAngle = rotationAngle + Math.toRadians(15);
		}
		if(handler.getWorld().getEntityManager().getPlayer().getPlayerHealth()==6){
			xT= 60;
		}else{
			xT= 0;
		}
	}

	
	public void render(Graphics g) {
		
		if(handler.getWorld().getEntityManager().getPlayer().isSpeedSlower()){
        	g.drawImage(Assets.speedItem[1], handler.getGame().getWidth()-70-30, handler.getGame().getHeight()-60-30, 60, 60,null);
        	
        	g.setColor(new Color(0, 134, 97));
        	
    		if(speedSlowActive){
    			r=(int)(++ticks*2)/30;
    			g.fillOval(handler.getGame().getWidth()-70-r, handler.getGame().getHeight()-60-r, r*2, r*2);
    			if(r==30){
    				r=0;
    				ticks=0;
    				speedSlowActive=false;
    				handler.getWorld().getEntityManager().getPlayer().setSpeedSlower(speedSlowActive);
    			}
    			if(handler.getWorld().getEntityManager().getPlayer().getHealth()==0){
    				r=30;
    				
    			}
    		}
        }		
		
        progressBarRender(g);
		energryBarAndCompassRender(g);
		
        g.setColor(new Color(51, 51, 51 , 150));
		g.fillRoundRect(handler.getWidth()/2-75+xT, 12, 150, 55, 10, 10);
		
        if(minutes < 10) {
			if(seconds < 10) Text.drawString(g, "0" + minutes + ":0" + seconds, handler.getWidth()/2+xT,40,true,Color.BLACK,Assets.font28bold);
			else Text.drawString(g, "0" + minutes + ":" + seconds, handler.getWidth()/2+xT, 40,true,Color.BLACK,Assets.font28bold);
		}
		else {
			if(seconds < 10) Text.drawString(g, minutes + ":0" + seconds,handler.getWidth()/2+xT, 40,true,Color.BLACK,Assets.font28bold);
			else Text.drawString(g, minutes + ":" + seconds, handler.getWidth()/2+xT, 40,true,Color.BLACK,Assets.font28bold);
		}
        if(handler.getWorld().getEntityManager().getPlayer().hasSword()){
        	Graphics2D g2d = (Graphics2D) g;
        	g2d.setStroke(new BasicStroke(1));
        	g.drawRect(8, 10, 27, 64);
        	g2d.setStroke(new BasicStroke(8));
        	g.drawImage(Assets.sword, 10, 30-15, null);
        }
        
        if(handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.speedSlower)>0){
        	g.drawImage(Assets.speedItem[1], handler.getWidth()-200, 30-15, 32,32, null);
        	Text.drawString(g, handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.speedSlower)+"", handler.getWidth()-200+16, 75-15,true,Color.BLACK,Assets.font28bold);
        }
		g.drawImage(Assets.cryst[2], handler.getWidth()-180+30, 30-15, null);
		g.drawImage(Assets.cryst_green[2], handler.getWidth()-180+80, 30-15, null);
		g.drawImage(Assets.cryst_grey[2], handler.getWidth()-180+130, 30-15, null);
		Text.drawString(g, handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.crystItem)+"", handler.getWidth()-180+46, 75-15,true,Color.BLACK,Assets.font28bold);
		Text.drawString(g, handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.crystgreenItem)+"", handler.getWidth()-180+96, 75-15,true,Color.BLACK,Assets.font28bold);
		Text.drawString(g, handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.crystgreyItem)+"", handler.getWidth()-180+146, 75-15,true,Color.BLACK,Assets.font28bold);
        
        for(int i=0; i<handler.getWorld().getEntityManager().getPlayer().getPlayerHealth();i++){
        	g.drawImage(animHeart.getCurrentFrame(),40+40*i,30,38,38 , null);
        }
        
	}
	
	
	private void progress(){
		minutes = (int) (handler.getWorld().getEntityManager().getPlayer().getTicks() / 3600);
		seconds = (int) ((handler.getWorld().getEntityManager().getPlayer().getTicks() / 60) % 60);
		attackCoolDown = (int)(handler.getWorld().getEntityManager().getPlayer().getAttackCoolDown());
		progress = (float)(handler.getWorld().getEntityManager().getPlayer().getTicks())/60 / animationTime;
        progressWidth = (int) (width * attackCoolDown/400);
        if(minutes*60+seconds==animationTime){
        	animationTime=0;
        }
	}
	
	private void progressBarRender(Graphics g){
		g.setColor(new Color(6, 123, 179));
        g.fillRect(0, handler.getHeight()-5, progressWidth, 5);
        
        g.setColor(new Color(157, 52, 176));
        g.fillRect(progressWidth, handler.getHeight()-5, width-progressWidth, 5);
        Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(Color.BLACK);
        g2d.drawOval(handler.getGame().getWidth()-70-30, handler.getGame().getHeight()-60-30, 60, 60);
        
        int progressArc = (int)(360*progress);
        g2d.setStroke(new BasicStroke(8));
        g2d.setColor(new Color(157, 52, 176));
        g2d.drawArc(handler.getGame().getWidth()-70-30, handler.getGame().getHeight()-60-30, 60, 60, 89, 359-progressArc);
    }
	private void energryBarAndCompassRender(Graphics g){
		
		rotationX=compassX+90/2;
        rotationY=compassY+20/2;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(Color.BLACK);
        g2d.drawOval(70-30, 420-30, 60, 60);
        g2d.setStroke(new BasicStroke(8));
        g2d.setColor(new Color(224, 0, 16));
        
        
        if(handler.getWorld().getEntityManager().getPlayer().getHealth()>=1){
        	g2d.drawArc(70-30, 420-30, 60, 60, 89, 120);
        }
        g2d.setColor(new Color(231, 0, 17));
        if(handler.getWorld().getEntityManager().getPlayer().getHealth()>=2){
        	g2d.drawArc(70-30, 420-30, 60, 60, 209, 120);
        }
        g2d.setColor(new Color(236, 0, 19));	
        if(handler.getWorld().getEntityManager().getPlayer().getHealth()==3){
        	g2d.drawArc(70-30, 420-30, 60, 60, 329, 120);
        }
        if(handler.getWorld().getEntityManager().getPlayer().hasCompass()){
        	AffineTransform t = new AffineTransform();
        	t.rotate((getRotationAngle()/180)*Math.PI,rotationX,rotationY);
        	t.translate(compassX, compassY);
        	g2d.drawImage(Assets.compass, t,null);
        }
	}
	public float getRotationAngle(){
		int playerX=(int) (handler.getWorld().getEntityManager().getPlayer().getX()+handler.getWorld().getEntityManager().getPlayer().getWidth()/2-handler.getGameCamera().getxOffset()),
				playerY=(int)(handler.getWorld().getEntityManager().getPlayer().getY()+handler.getWorld().getEntityManager().getPlayer().getHeight()/2-handler.getGameCamera().getyOffset()),
				outX=(int)(handler.getWorld().getOutX()+Tile.TILEWIDTH/2-handler.getGameCamera().getxOffset()),
				outY=(int)(handler.getWorld().getOutY()+Tile.TILEHEIGHT/2-handler.getGameCamera().getyOffset());
		if(handler.getGame().getGameStateManger().getState() instanceof GameStateStage4){
			outX=(int)(928+Tile.TILEWIDTH/2-handler.getGameCamera().getxOffset());
			outY=(int)(192+Tile.TILEHEIGHT/2-handler.getGameCamera().getyOffset());
		}
		return (float) (90-Math.toDegrees(Math.atan2(outX-playerX,outY-playerY)));
	}

	public int getAnimationTime() {
		return animationTime;
	}

	public void setAnimationTime(int animationTime) {
		this.animationTime = animationTime;
	}

	public boolean isSpeedSlowActive() {
		return speedSlowActive;
	}

	public void setSpeedSlowActive(boolean speedSlowActive) {
		this.speedSlowActive = speedSlowActive;
	}
	
	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}
}
