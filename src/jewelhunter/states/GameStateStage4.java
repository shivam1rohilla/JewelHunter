package jewelhunter.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import java.awt.event.KeyEvent;

import jewelhunter.Handler;
import jewelhunter.audio.Sound;
import jewelhunter.entities.Entity;
import jewelhunter.entities.creatures.BarrelEnemy;
import jewelhunter.entities.creatures.Enemy;
import jewelhunter.entities.statics.BarrelThrower;
import jewelhunter.entities.statics.Crate;
import jewelhunter.entities.statics.Laser;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;
import jewelhunter.item.Item;
import jewelhunter.worlds.World;

public class GameStateStage4 extends State {
	

	private World  world;
	private boolean healthStat=false;
	int pH,countE,countI;
	long healthStatTicks;
	int outx,outy,crateCounts=0;
	private long randTicks;
	private boolean stop;
	
	public GameStateStage4(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
		world =new World(handler,"worlds/Stage4.map");
		world.init();
		if(handler.getWorld()!=null)
			pH=handler.getWorld().getEntityManager().getPlayer().getPlayerHealth();
		else
			pH=3;
		handler.setWorld(world);
		handler.getWorld().getEntityManager().getPlayer().setStoryHintStage4(true);
		if(inv!=null){
		Iterator<Item> it = inv.getInventoryItems().iterator();
		while(it.hasNext()){
			Item i=it.next();
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(i);			
		}}
		handler.getWorld().getEntityManager().getPlayer().setPlayerHealth(pH);
		
		
		handler.getWorld().getEntityManager().addEntity(new Laser(handler, 0, 320,Laser.RIGHT));
		handler.getWorld().getEntityManager().addEntity(new Laser(handler, 512, 64,Laser.LEFT));
		
		
		handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 323, 100,Enemy.VERTICAL));
		handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 195, 200,Enemy.VERTICAL));
		handler.getWorld().getEntityManager().addEntity(new Crate(handler, 8*64,3*64));
		handler.getWorld().getEntityManager().addEntity(new Crate(handler, 9*64,3*64));
		
		handler.getWorld().getEntityManager().addEntity(new BarrelThrower(handler, 128, 5));
		
		handler.getWorld().getEntityManager().addEntity(new BarrelThrower(handler, 256, 5));
		handler.getWorld().getEntityManager().addEntity(new BarrelThrower(handler, 384, 5));

		handler.getWorld().getItemManager().addItem(Item.kohinoor.createNew(928,192));
		handler.getWorld().getEntityManager().getPlayer().setCompass(true);
		handler.getWorld().getEntityManager().getPlayer().setSword(true);
	}
	@Override
	public void init() {
		handler.getWorld().getHud().setAnimationTime(90);
		Sound.loopAudio("background", true);
		handler.getWorld().getEntityManager().getPlayer().setStopGameCam(true);
		for(Entity e:handler.getWorld().getEntityManager().getEntities()){
			if(e instanceof BarrelEnemy){
				e.randBarrelStop=200;
			}
			if(e instanceof Enemy){
				e.speed-=1;
				e.setHealth(e.getHealth()+3);
			}
		}
	}
	@Override
	public void tick() {
		Iterator<Entity> itr = handler.getWorld().getEntityManager().getEntities().iterator();
		while (itr.hasNext()){
			Entity e=itr.next();
			if(e instanceof Enemy){
				countE++;
			}
		}
		if(!itr.hasNext()){
			if(countE==0){
				handler.getWorld().getEntityManager().getPlayer().setStopGameCam(false);
				for(Entity e:handler.getWorld().getEntityManager().getEntities()){
					if(e instanceof Crate)
						e.hurt(e.getHealth());
				}
			}
			countE=0;
		}
		Iterator<Item> itrI = handler.getWorld().getItemManager().getItems().iterator();
		while (itrI.hasNext()){
			Item e=itrI.next();
			if(e.getId() == Item.speedSlower.getId()){
				countI++;
			}
		    
		}
		if(!itrI.hasNext()){
			if(countI==0&&handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.speedSlower)<2){
				stop=true;
				if(randTicks>(int)((Math.random() * 100)+300)){
					stop=false;
					randTicks=0;
				}
				if(stop){
					randTicks++;
				}else{
					if((int)((Math.random() * 2)+1)==2)
						handler.getWorld().getItemManager().addItem(Item.speedSlower.createNewAnim(85,330));
					else
						handler.getWorld().getItemManager().addItem(Item.speedSlower.createNewAnim(456,70));
				}
				
			}
			countI=0;
		}
		
		if((pH-1)==handler.getWorld().getEntityManager().getPlayer().getPlayerHealth()){
			Sound.stop("background");
			healthStat=true;
			pH=handler.getWorld().getEntityManager().getPlayer().getPlayerHealth();
		}
		
		if(healthStat){
			healthStatTicks++;
			if(healthStatTicks==100){
				healthStat=false;
				healthStatTicks=0;
				Sound.loopAudio("background", true);
			}
		}
		
		if(handler.getWorld().getEntityManager().getPlayer().isDied()){
			Sound.stop("background");
			Sound.playAudio("finish");
			gsm.setState(GameStateManager.GAMEOVER);
		}
		if(handler.getWorld().getHud().getAnimationTime()<=0){
			items=handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems();
			inv = handler.getWorld().getEntityManager().getPlayer().getInventory();
			Sound.stop("background");
			Sound.playAudio("finish");
			gsm.setState(GameStateManager.GAMEOVER);
		}
		if(handler.getWorld().getEntityManager().getPlayer().getY()<0){
			handler.getWorld().getEntityManager().getPlayer().setyMove(+10);
			handler.getWorld().getEntityManager().getPlayer().moveY();
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			Sound.stop("background");
			gsm.setPaused(true);
		}
		
		world.tick();		
		if(!handler.getWorld().getEntityManager().getPlayer().isStoryC()&&!handler.getWorld().getEntityManager().getPlayer().getInventory().isActive())
		handler.getWorld().getHud().tick();
		
		if(handler.getWorld().getEntityManager().getPlayer().getX()<(world.getSpawnX()-80)){
			handler.getWorld().getEntityManager().getPlayer().setxMove(+10);
			handler.getWorld().getEntityManager().getPlayer().moveX();
		}
		if(handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.kohinoor)>0){
			
			Sound.stop("background");
			Sound.playAudio("finish");
			gsm.min += handler.getWorld().getHud().getMinutes();
			gsm.sec += handler.getWorld().getHud().getSeconds();
			gsm.setState(GameStateManager.GAMECOMPLETE);
		}
	}

	@Override
	public void render(Graphics g) {
		if(healthStat){
			g.drawImage(Assets.back, 0, 0,640,480, null);
			g.setColor(new Color(0, 0, 0 , 200));
			g.fillRoundRect(5, 5, handler.getWidth()-10, handler.getHeight()-10, 15, 15);
			Text.drawString(g, "STAGE 3",handler.getWidth()/2, handler.getHeight()/2-150, true, Color.WHITE, Assets.bloc_r);
			g.drawImage(Assets.player_right[1], handler.getWidth()/2-150, handler.getHeight()/2-45, 85, 85,null);
			Text.drawString(g, "X", handler.getWidth()/2, handler.getHeight()/2, true, Color.WHITE, Assets.head72);
			Text.drawString(g, handler.getWorld().getEntityManager().getPlayer().getPlayerHealth()+"", handler.getWidth()/2+100, handler.getHeight()/2, true, Color.WHITE, Assets.bloc_r);
			
		}else{
			world.render(g);
			if(!handler.getWorld().getEntityManager().getPlayer().isStoryC()&&!handler.getWorld().getEntityManager().getPlayer().getInventory().isActive())
				handler.getWorld().getHud().render(g);
			handler.getWorld().getEntityManager().getPlayer().postRender(g);
		}
		
	}
	
	

}
