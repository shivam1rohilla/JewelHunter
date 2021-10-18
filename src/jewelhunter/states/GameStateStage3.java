package jewelhunter.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import java.awt.event.KeyEvent;

import jewelhunter.Handler;
import jewelhunter.audio.Sound;
import jewelhunter.entities.creatures.Enemy;
import jewelhunter.entities.creatures.FlameThrower;
import jewelhunter.entities.statics.BarrelThrower;
import jewelhunter.entities.statics.Crate;
import jewelhunter.entities.statics.Laser;
import jewelhunter.entities.statics.Teleport;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;
import jewelhunter.item.Item;
import jewelhunter.tiles.Tile;
import jewelhunter.worlds.World;

public class GameStateStage3 extends State {
	

	private World  world;
	private boolean healthStat=false;
	int pH;
	long healthStatTicks;
	int outx,outy,crateCounts=0;
	
	public GameStateStage3(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
		
		world =new World(handler,"worlds/Stage3.map");
		world.init();
		if(handler.getWorld()!=null)
			pH=handler.getWorld().getEntityManager().getPlayer().getPlayerHealth();
		else
			pH=3;
		handler.setWorld(world);
		if(inv!=null){
		Iterator<Item> it = inv.getInventoryItems().iterator();
		while(it.hasNext()){
			Item i=it.next();
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(i);			
		}}
		handler.getWorld().getEntityManager().getPlayer().setPlayerHealth(pH);
		
		
		handler.getWorld().getEntityManager().addEntity(new Laser(handler, 130+64, 384,Laser.RIGHT));
		
		
		handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 720, 640,Enemy.HORIZONTAL));
		handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 520, 500,Enemy.VERTICAL));
			
		handler.getWorld().getEntityManager().addEntity(new BarrelThrower(handler, 639, 5));
		
		handler.getWorld().getEntityManager().addEntity(new BarrelThrower(handler, 832, 194));
		handler.getWorld().getEntityManager().addEntity(new BarrelThrower(handler, 513, 5));
		handler.getWorld().getEntityManager().addEntity(new FlameThrower(handler, 832, 130));
		handler.getWorld().getEntityManager().addEntity(new Crate(handler, 66+128, 384+128+64));
		handler.getWorld().getEntityManager().addEntity(new Crate(handler, 17*64, 3*64));
		
		handler.getWorld().getEntityManager().addEntity(new Teleport(handler, 7, 2,Teleport.HORIZONTAL,1*64+15,10*64+15));
		handler.getWorld().getEntityManager().addEntity(new Teleport(handler, 12, 6,Teleport.HORIZONTAL,18*64+15,5*64+15));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(2*64+10,3*64+10));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(580,240));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(620,200));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(580,280));
		
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(540,240));
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(620,240));
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(620,280));
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(580,200));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(660,240));
		
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(700,780));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(850,780));
		handler.getWorld().getItemManager().addItem(Item.speedSlower.createNewAnim(410,600));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(900,780));
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(950,780));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(700,730));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(750,730));
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(800,730));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(850,730));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(900,730));
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(950,730));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(700,680));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(750,680));
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(800,680));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(850,680));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(900,680));
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(950,680));
		
		handler.getWorld().getItemManager().addItem(Item.crystgreenItem.createNewAnim(440,520));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(440,560));
		handler.getWorld().getItemManager().addItem(Item.crystgreyItem.createNewAnim(440,600));
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(410,560));
		
		handler.getWorld().getItemManager().addItem(Item.crystItem.createNewAnim(593,30));
		handler.getWorld().getEntityManager().getPlayer().setCompass(true);
		handler.getWorld().getEntityManager().getPlayer().setSword(true);
	}
	@Override
	public void init() {
		handler.getWorld().getHud().setAnimationTime(90);
		Sound.loopAudio("background", true);
		handler.getWorld().getEntityManager().getPlayer().init();
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
		
		if(handler.getWorld().getEntityManager().getPlayer().getX()<(world.getSpawnX()-32)){
			handler.getWorld().getEntityManager().getPlayer().setxMove(+10);
			handler.getWorld().getEntityManager().getPlayer().moveX();
		}
		
	}

	@Override
	public void render(Graphics g) {
		if(healthStat){
			g.drawImage(Assets.back, 0, 0,640,480, null);
			g.setColor(new Color(0, 0, 0 , 200));
			g.fillRoundRect(5, 5, handler.getWidth()-10, handler.getHeight()-10, 15, 15);
			Text.drawString(g, "STAGE 3",handler.getWidth()/2, handler.getHeight()/2-150, true, Color.WHITE, Assets.bloc_r);
			if(handler.getGame().getGameStateManger().getState() instanceof GameStateStage3)
				g.drawImage(Assets.player_right_hood[1], handler.getWidth()/2-150, handler.getHeight()/2-45, 85, 85,null);
			else
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
