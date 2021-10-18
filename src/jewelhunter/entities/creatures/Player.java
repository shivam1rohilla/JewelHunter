package jewelhunter.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


import jewelhunter.Handler;
import jewelhunter.audio.Sound;
import jewelhunter.entities.Entity;
import jewelhunter.entities.statics.Crate;
import jewelhunter.gfx.Animation;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;
import jewelhunter.inventory.Inventory;
import jewelhunter.item.Item;
import jewelhunter.states.GameStateStage3;
import jewelhunter.story.Story;
import jewelhunter.tiles.Tile;

public class Player extends Creature {
	
	private Animation animDown,animUp,animLeft,animRight;
	private Animation animAttackDown,animAttackUp,animAttackLeft,animAttackRight;
	private long lastAttackTimer,attackCoolDown=400,attackTimer=attackCoolDown;
	private Inventory inventory;
	
	private int playerHealth;
	private long ticks,flinchTicks;
	private boolean compass,storyC,storyPick,storyCrate,storyCrateCantMove,storySpeedSlower,storySword,sword;
	private boolean runningStory,died=false,speedSlower,stopGameCam;
	boolean a=false,flinch=false,ac=false;
	private int speedSlowerCount=0;
	private int speedSlowerPick;
	
	//hurted //
	private Entity hurtedEntity;
	private boolean hurted;
	private long hurtedTicks=0;
	private int hurtedX,hurtedY;	
	
	
	//Attack//
	private int attack;
	private static final int AR=1,AL=2,AU=3,AD=4;
	
	
	Story story;	

	public Player(Handler handler, float x, float y) {
		super(handler,x, y,Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		bounds.x=22;
		bounds.y=30;
		bounds.width=19;
		bounds.height=33;
		
		animDown = new Animation(100, Assets.player_down);
		animUp = new Animation(100, Assets.player_up);
		animLeft = new Animation(100, Assets.player_left);
		animRight = new Animation(100, Assets.player_right);
		animAttackDown = new Animation(100, Assets.player_attack_down);
		animAttackUp = new Animation(100, Assets.player_attack_up);
		animAttackLeft = new Animation(100, Assets.player_attack_left);
		animAttackRight = new Animation(100, Assets.player_attack_right);
			
		inventory = new Inventory(handler);
		
		story = new Story(handler);	
		boat=true;
		playerHealth=3;
	}
	public void init(){
		{
			animDown = new Animation(100, Assets.player_down_hood);
			animUp = new Animation(100, Assets.player_up_hood);
			animLeft = new Animation(100, Assets.player_left_hood);
			animRight = new Animation(100, Assets.player_right_hood);
			animAttackDown = new Animation(100, Assets.player_attack_down_hood);
			animAttackUp = new Animation(100, Assets.player_attack_up_hood);
			animAttackLeft = new Animation(100, Assets.player_attack_left_hood);
			animAttackRight = new Animation(100, Assets.player_attack_right_hood);
		}
	}

	@Override
	public void tick() {
		
		storyStatus();
		animDown.tick();
		animUp.tick();
		animLeft.tick();
		animRight.tick();

		animAttackDown.tick();
		animAttackUp.tick();
		animAttackLeft.tick();
		animAttackRight.tick();
		
		speedSlowCal();
		getInput();
		move();
		if(!stopGameCam)
			handler.getGameCamera().CenterOnEntity(this);
		if(stopGameCam){
			handler.getGameCamera().setxOffset(0);
			handler.getGameCamera().setyOffset(0);
		}
		if(this.hasSword())
			checkAttacks();
		
		if(runningStory)
			return;
		inventory.tick();
		if(inventory.isActive())
			return;
		ticks++;
		flinch();
		hurtedFlinch();
		
		
	}

	public long getTicks() {
		return ticks;
	}

	private void checkAttacks(){
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		
		if(attackTimer<attackCoolDown)
			return;
		
		if (inventory.isActive()||runningStory) {
			return;
		}
		
		Rectangle cb = getCollisionBounds(0, 0);
		Rectangle ar = new Rectangle();
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;
		
		if(handler.getKeyManager().aUp){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		}else if(handler.getKeyManager().aDown){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		}else if(handler.getKeyManager().aLeft){
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		}else if(handler.getKeyManager().aRight){
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		}else{
			return;
		}
		
		attackTimer = 0;
		
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this)||!(e instanceof Enemy))
				continue;
			if(e.getCollisionBounds(0, 0).intersects(ar)&&e instanceof Enemy){
				hurted=true;
				hurtedEntity=e;
				hurtedTicks++;
				hurtedX=(int) (e.getX()+e.getWidth()/2);
				hurtedY=(int) (e.getY());
				e.hurt(1);
				return;
			}	
			
		}
	}
	
	private void getInput(){
				
		xMove=0;
		yMove=0;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
			stopStory();
		}
		if (inventory.isActive()||runningStory) {
			return;
		}
		if(attack!=0)
			return;
		
		if(handler.getKeyManager().up)
			yMove = -speed;
		if(handler.getKeyManager().down)
			yMove = speed;
		if(handler.getKeyManager().left)
			xMove = -speed;
		if(handler.getKeyManager().right)
			xMove = speed;
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)){
			for(Entity e : handler.getWorld().getEntityManager().getEntities()){
				if(e instanceof Crate){
					System.out.println((handler.getGameCamera().getxOffset())+"::"+(handler.getGameCamera().getyOffset()));
					if(e.getX()<=handler.getGameCamera().getxOffset()+handler.getWidth()*Tile.TILEWIDTH&&e.getX()>=handler.getGameCamera().getxOffset()
							&&(e.getY()<=handler.getWorld().getHeight()*Tile.TILEHEIGHT+handler.getGameCamera().getyOffset())&&e.getY()>=handler.getGameCamera().getyOffset()&&
							(e.getX()!=e.actualXCrate||e.getY()!=e.actualYCrate)){
						e.setX(e.actualXCrate);
						e.setY(e.actualYCrate);
						handler.getWorld().getEntityManager().getPlayer().hurt(handler.getWorld().getEntityManager().getPlayer().getHealth());
					}
				}
			}
		}
		
		if(handler.getKeyManager().aUp){
			attack=AU;
		}else if(handler.getKeyManager().aDown){
			attack=AD;
		}else if(handler.getKeyManager().aLeft){
			attack=AL;
		}else if(handler.getKeyManager().aRight){
			attack=AR;
		}
		if(attack!=0&&this.hasSword()){
			Sound.playAudio("whoosh");
		}
		if(!this.hasSword())
			attack=0;
		
	}
	
	public void  die(){
		if(health<=0&&!handler.getWorld().getEntityManager().getPlayer().active){
			handler.getWorld().getEntityManager().getPlayer().spawn(handler.getWorld().getSpawnX(),	handler.getWorld().getSpawnY());
			handler.getWorld().getEntityManager().getPlayer().setHealth(DEFAULT_HEALTH);
			handler.getWorld().getEntityManager().getPlayer().playerHealth(1);
			handler.getWorld().getEntityManager().getPlayer().setActive(true);
		}
		if(playerHealth<=0)
			died=true;
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(getCurrentAnimationFrame(),(int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()), width,height,null);
		if(hurted){
			if(hurtedEntity instanceof Enemy)
				Text.drawString(g, hurtedEntity.getHealth()+"",(int) (hurtedX-handler.getGameCamera().getxOffset()),(int) (hurtedY-handler.getGameCamera().getyOffset()), true, Color.RED.brighter(), Assets.font28);
			else
				Text.drawString(g, hurtedEntity.getHealth()+"",(int) (hurtedX-handler.getGameCamera().getxOffset()),(int) (hurtedY-handler.getGameCamera().getyOffset()), true, Color.pink, Assets.font28);
		}
	}
	
	public void postRender(Graphics g){
		inventory.render(g);
		storys(g);
	}
	
	
	private BufferedImage getCurrentAnimationFrame() {
		if(flinch)
			return null;
		
		else{
			if(attack==AU){
				if(animAttackUp.getIndex()==animAttackUp.getFrameSize()-1)
					attack=0;
				return animAttackUp.getCurrentFrame();
			}else if(attack==AD){
				if(animAttackDown.getIndex()==animAttackDown.getFrameSize()-1)
					attack=0;
				return animAttackDown.getCurrentFrame();
			}else if(attack==AL){
				if(animAttackLeft.getIndex()==animAttackLeft.getFrameSize()-1)
					attack=0;
				return animAttackLeft.getCurrentFrame();
			}else if(attack==AR){
				if(animAttackRight.getIndex()==animAttackRight.getFrameSize()-1)
					attack=0;
				return animAttackRight.getCurrentFrame();
			}else if(attack==0){
				attackFrameIntital();
				if(xMove<0){
					return animLeft.getCurrentFrame();
				}else if(xMove>0){
					return animRight.getCurrentFrame();
				}else if(yMove<0){
					return animUp.getCurrentFrame();
				}else if(yMove>0){		
					return animDown.getCurrentFrame();
				}
				else{
					if(handler.getGame().getGameStateManger().getState() instanceof GameStateStage3)
						return Assets.player_down_hood[0];
					else
						return Assets.player_down[0];
				}
			}
			
		}
		if(handler.getGame().getGameStateManger().getState() instanceof GameStateStage3)
			return Assets.player_down_hood[0];
		else
			return Assets.player_down[0];
	}
	
	//hurted
	public void hurtedFlinch(){
		if(hurtedTicks!=0){
			hurtedTicks++;
			hurtedY--;
		if(hurtedTicks%10==0&&hurtedTicks<40)
			hurted=!hurted;			
		}
		if(hurtedTicks>80){
			hurted=false;
			hurtedTicks=0;
			if(hurtedEntity.getHealth()==0){
				hurted=false;
			}
		}
	}
	
	public void playerHurt(boolean hurt,Entity e){
		hurted=hurt;
		hurtedEntity=e;
		hurtedTicks++;
		hurtedX=(int) (e.getX()+e.getWidth()/2);
		hurtedY=(int) (e.getY());
		if(e.equals(this)){
			Sound.setVolume("option", -40);
			Sound.playAudio("option");
			Sound.setVolume("option", -30);
		}
	}
	
	
	//////\\\\\\ STORY CARDS //////\\\\\\
	
	public void storys(Graphics g){
		if(storyC)
			story.storyCompass(g);		
		else if(storySword)
			story.storySword(g);
		else if(storyPick)
			story.storyPick(g);
		else if(storyCrate)
			story.storyCrate(g);
		else if(storyCrateCantMove)
			story.storyCrateCantMove(g);
		else if(storySpeedSlower)
			story.storySpeedSlower(g);
		else
			story.storyBlink(g);
	}
	
	
	//\\\\\\ STORY CARDS ///////\\
	
	public void flinch(){
		if(flinchTicks!=0){
			flinchTicks++;
		if(flinchTicks%10==0&&flinchTicks<80)
			flinch=!flinch;
		}
		if(flinchTicks>80){
			flinch=false;
			flinchTicks=0;
		}
	}
	
	////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////
	
	
	private void speedSlowCal(){
		if(handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.speedSlower)==0)
			speedSlower=false;
		if(handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.speedSlower)>=1)
			speedSlower=true;
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)&&speedSlower) {
			handler.getWorld().getHud().setSpeedSlowActive(true);
			speedSlowerCount++;
			if(speedSlowerCount==1){
					for(Entity e:handler.getWorld().getEntityManager().getEntities()){
						if(e.equals(this))
							continue;
						if(e.toString().contains("Flame")){
							e.speed=e.speed+400;
						}
						else
							e.speed=e.speed-2;
					}
				speedSlowerCount++;
			}
		}
		if(speedSlowerCount>1&&!handler.getWorld().getHud().isSpeedSlowActive()){
			for(Entity e:handler.getWorld().getEntityManager().getEntities()){
				if(e.equals(this))
					continue;
				if(e.toString().contains("Flame")){
					e.speed=e.speed-400;
				}
				else
					e.speed=e.speed+2;

			}
			speedSlowerCount=0;
			for(Item i : handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems()){
				if(i.getId() == Item.speedSlower.getId()){
					i.setCount(i.getCount() - 1);
					return;
				}
			}

		}
	}
	
	
	///////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////
	
	private void attackFrameIntital(){
		animAttackDown.setIndex(0);
		animAttackUp.setIndex(0);
		animAttackRight.setIndex(0);
		animAttackLeft.setIndex(0);
	}

	///////Getters AND Setters\\\\\\\
	
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public long getAttackCoolDown() {
		if(attackTimer<attackCoolDown)
			return attackTimer;
		return attackCoolDown;
	}
	

	public boolean hasCompass() {
		return compass;
	}

	public void setCompass(boolean compass) {
		this.compass = compass;
	}

	public boolean isStoryC() {
		return storyC;
	}
	public boolean isStorySword() {
		return storySword;
	}
	public void setStoryPick(boolean storyPick) {
		this.storyPick = storyPick;
	}

	public void setFlinchTicks(long flickTicks) {
		this.flinchTicks = flickTicks;
	}

	public void setStoryCrate(boolean storyCrate) {
		this.storyCrate = storyCrate;
	}
	
	public void setStoryC(boolean storyC) {
		this.storyC = storyC;
	}
	public void setStorySword(boolean storySword) {
		this.storySword = storySword;
	}

	public boolean isStoryCrateCantMove() {
		return storyCrateCantMove;
	}

	public void setStoryCrateCantMove(boolean storyCrateCantMove) {
		this.storyCrateCantMove = storyCrateCantMove;
	}
	private void storyStatus(){
		if(storyC||storyCrate||storyPick||storyCrateCantMove||storySpeedSlower||storySword)
			runningStory=true;
		else{
			runningStory=false;
		}		
	}
	private void stopStory(){
		storyC=storyCrate=storyPick=storyCrateCantMove=storySpeedSlower=storySword=false;
	}
	
	

	public boolean isRunningStory() {
		return runningStory;
	}

	public boolean hasBoat() {
		return boat;
	}
	public void setBoat(boolean boat) {
		this.boat=boat;
	}

	public boolean isStoryPick() {
		return storyPick;
	}

	public void setStorySpeedSlower(boolean storySpeedSlower) {
		this.storySpeedSlower = storySpeedSlower;
	}

	public boolean isDied() {
		return died;
	}

	public int getPlayerHealth() {
		return playerHealth;
	}

	
	public void setPlayerHealth(int playerHealth) {
		this.playerHealth = playerHealth;
	}

	public void oneUpPlayerHealth(int playerHealth) {
		this.playerHealth += playerHealth;
	}
	
	public void playerHealth(int amt){
		playerHealth -=amt;		
	}

	public synchronized boolean isSpeedSlower() {
		return speedSlower;
	}

	public synchronized void setSpeedSlower(boolean speedSlower) {
		this.speedSlower = speedSlower;
	}
	public void speedSlowerPick(int amt) {
		speedSlowerPick+=amt;
	}

	public int getSpeedSlowerPick() {
		return speedSlowerPick;
	}
	public void setStopGameCam(boolean stopGameCam) {
		this.stopGameCam = stopGameCam;
	}
	public boolean hasSword() {
		return sword;
	}
	public void setSword(boolean sword) {
		this.sword = sword;
	}
	
}
