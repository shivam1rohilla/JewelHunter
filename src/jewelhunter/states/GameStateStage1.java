package jewelhunter.states;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyEvent;

import jewelhunter.Handler;
import jewelhunter.audio.Sound;
import jewelhunter.entities.Entity;
import jewelhunter.entities.creatures.Enemy;
import jewelhunter.entities.statics.Chest;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;
import jewelhunter.item.Item;
import jewelhunter.tiles.Tile;
import jewelhunter.worlds.World;

public class GameStateStage1 extends State {
	

	private World  world;
	private boolean healthStat=false,compassStory=false;
	int pH;
	long healthStatTicks;
	
	int outx,outy,crateCounts=0,i;
	
	public GameStateStage1(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
		
		world =new World(handler,"worlds/Stage1.map");
		world.init();
		
		handler.setWorld(world);
		handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 720, 650,Enemy.HORIZONTAL));
		handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 550, 500,Enemy.VERTICAL));
		handler.getWorld().getEntityManager().addEntity(new Chest(handler, 12*64, 5*64,Item.compass.createNew(12*64, 4*64),1));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(250,80));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(350,80));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(450,80));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(500,80));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(700,780));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(850,780));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(700,730));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(850,730));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(850,680));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(700,680));
		
		
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(550,80));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(600,80));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(750,80));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(800,80));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(750,780));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(900,780));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(750,730));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(900,730));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(900,680));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(750,680));
		
		
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(250,530));
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(200,530));
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(150,530));
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(100,530));		
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(800,780));		
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(950,780));		
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(800,730));		
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(950,730));		
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(800,680));		
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(950,680));
		pH=handler.getWorld().getEntityManager().getPlayer().getPlayerHealth();
	}
	@Override
	public void init() {
		handler.getWorld().getHud().setAnimationTime(75);
		Sound.loopAudio("background", true);
	}
	@Override
	public void tick() {
		
		if((pH-1)==handler.getWorld().getEntityManager().getPlayer().getPlayerHealth()/*&&handler.getWorld().getEntityManager().getPlayer().getHealth()==0*/){
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
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			Sound.stop("background");
			gsm.setPaused(true);
		}
		i++;
		world.tick();		
		
		if(!handler.getWorld().getEntityManager().getPlayer().isRunningStory()&&!handler.getWorld().getEntityManager().getPlayer().getInventory().isActive())
			handler.getWorld().getHud().tick();
		
		outx=(int)(handler.getWorld().getEntityManager().getPlayer().getX());
		outy=(int)(handler.getWorld().getEntityManager().getPlayer().getY()+handler.getWorld().getEntityManager().getPlayer().getHeight()/2);
		
		if(outx>handler.getWorld().getOutX()&&outx<handler.getWorld().getOutX()+Tile.TILEWIDTH&&
				outy>handler.getWorld().getOutY()&&outy<handler.getWorld().getOutY()+Tile.TILEHEIGHT){
			Sound.stop("background");
			Sound.playAudio("finish");
			items=handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems();
			inv = handler.getWorld().getEntityManager().getPlayer().getInventory();
			gsm.min += handler.getWorld().getHud().getMinutes();
			gsm.sec += handler.getWorld().getHud().getSeconds();
			gsm.setState(GameStateManager.GAMETRANSITION);
		}
		
		if(handler.getWorld().getEntityManager().getPlayer().isDied()){
			Sound.stop("background");
			Sound.playAudio("finish");Sound.playAudio("finish");
			gsm.setState(GameStateManager.GAMEOVER);
		}
		if(handler.getWorld().getHud().getAnimationTime()<=0){
			items=handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems();
			inv = handler.getWorld().getEntityManager().getPlayer().getInventory();
			Sound.stop("background");
			Sound.playAudio("finish");
			gsm.setState(GameStateManager.GAMEOVER);
		}
		
		
		if(handler.getWorld().getEntityManager().getPlayer().getX()<(world.getSpawnX()-32)){
			handler.getWorld().getEntityManager().getPlayer().setxMove(+10);
			handler.getWorld().getEntityManager().getPlayer().moveX();
		}
		
		compassPickCheck();
		checkCrate();
		
		if(handler.getWorld().getEntityManager().getPlayer().hasCompass()&&!compassStory){
			handler.getWorld().getEntityManager().getPlayer().setStoryC(true);
			compassStory = true;
		}
	}

	@Override
	public void render(Graphics g) {
		if(healthStat){
			g.drawImage(Assets.back, 0, 0,640,480, null);
			g.setColor(new Color(0, 0, 0 , 200));
			g.fillRoundRect(5, 5, handler.getWidth()-10, handler.getHeight()-10, 15, 15);
			Text.drawString(g, "STAGE 1",handler.getWidth()/2, handler.getHeight()/2-150, true, Color.WHITE, Assets.bloc_r);
			g.drawImage(Assets.player_right[1], handler.getWidth()/2-150, handler.getHeight()/2-45, 85, 85,null);
			Text.drawString(g, "X", handler.getWidth()/2, handler.getHeight()/2, true, Color.WHITE, Assets.head72);
			Text.drawString(g, handler.getWorld().getEntityManager().getPlayer().getPlayerHealth()+"", handler.getWidth()/2+100, handler.getHeight()/2, true, Color.WHITE, Assets.bloc_r);
			
		}else{
			world.render(g);
			if(!handler.getWorld().getEntityManager().getPlayer().isRunningStory()&&!handler.getWorld().getEntityManager().getPlayer().getInventory().isActive())
				handler.getWorld().getHud().render(g);
			handler.getWorld().getEntityManager().getPlayer().postRender(g);
		}
	}
	
	public void compassPickCheck() {
		if(!handler.getWorld().getEntityManager().getPlayer().hasCompass()&&handler.getWorld().getEntityManager().getPlayer().getX()<640&&handler.getWorld().getEntityManager().getPlayer().getX()>620
				&&handler.getWorld().getEntityManager().getPlayer().getY()>180&&handler.getWorld().getEntityManager().getPlayer().getY()<470){
			handler.getWorld().getEntityManager().getPlayer().setStoryPick(true);
			handler.getWorld().getEntityManager().getPlayer().setxMove(+10);
			handler.getWorld().getEntityManager().getPlayer().moveX();
			
		}
	}
	
	public void checkCrate(){
		for(Entity e:handler.getWorld().getEntityManager().getEntities()){
			if(e.toString().contains("Crate")&&e.getX()<=640+handler.getGameCamera().getxOffset()&&e.getY()<=480+handler.getGameCamera().getyOffset()){
					
				crateCounts++;
			}
		}
		if(crateCounts==1){
			handler.getWorld().getEntityManager().getPlayer().setStoryCrate(true);
		}
	}
	

}
