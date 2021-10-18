package jewelhunter.item;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import jewelhunter.Handler;
import jewelhunter.audio.Sound;
import jewelhunter.gfx.Animation;
import jewelhunter.gfx.Assets;

public class Item {
	
	
	public static Item[] items = new Item[256];
	public static Item crystItem = new Item(Assets.cryst, "Blue Crystal", 2);
	public static Item crystgreenItem = new Item(Assets.cryst_green, "Green Crystal", 3);
	public static Item crystgreyItem = new Item(Assets.cryst_grey, "Grey Crystal", 4);
	public static Item compass = new Item(Assets.compasspick, "Compass", 5);
	public static Item speedSlower = new Item(Assets.speedItem, "Speed Slower", 6);
	public static Item sword = new Item(Assets.sword, "Sword", 7);
	public static Item kohinoor = new Item(Assets.kDimond, "Kohinoor", 8);
	
	private Animation crystAnim;

	public static final int ITEMWIDTH=32 , ITEMHEIGHT=32;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected BufferedImage[] tex;
	protected String name;
	protected final int id;
	
	protected Rectangle bounds;
	
	protected int x,y,count;
	protected boolean pickedUp=false;	
	
	public Item(BufferedImage texture, String name, int id) {
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;
		if(id==5)
			bounds = new Rectangle(x, y, ITEMWIDTH*2	, ITEMHEIGHT*2);
		else
			bounds = new Rectangle(x, y, ITEMWIDTH	, ITEMHEIGHT);
		items[id] =  this;
	}
	public Item(BufferedImage[] texture, String name, int id) {
		this.tex = texture;
		this.name = name;
		this.id = id;
		count = 1;
		bounds = new Rectangle(x, y, ITEMWIDTH	, ITEMHEIGHT);
		items[id] =  this;
		crystAnim = new Animation(100, tex);
	}
	
	
	public void tick(){
		if(crystAnim!=null)
		crystAnim.tick();
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)){
			Sound.playAudio("collect");
			pickedUp = true;
			if(this.id==Item.compass.getId()){
				 handler.getWorld().getEntityManager().getPlayer().setCompass(pickedUp);
			}
			if(this.id==Item.speedSlower.getId()){
				 handler.getWorld().getEntityManager().getPlayer().setSpeedSlower(pickedUp);
				 handler.getWorld().getEntityManager().getPlayer().speedSlowerPick(1);
			}				
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
		}
		
	}
	
	public void render(Graphics g){
		if(handler == null)
			return;
		render(g,(int)(x - handler.getGameCamera().getxOffset()),(int)(y - handler.getGameCamera().getyOffset()));
		
	}
	
	public void render(Graphics g, int x , int y){
		if(id== compass.getId()||id==kohinoor.getId())
			g.drawImage(texture, x, y, ITEMWIDTH*2,ITEMHEIGHT*2,null);
		else if(id == sword.getId())
			g.drawImage(texture, x, y, 25,50,null);
		else
			g.drawImage(texture, x, y, ITEMWIDTH,ITEMHEIGHT,null);
		if(crystAnim!=null)
		g.drawImage(crystAnim.getCurrentFrame(), x, y, ITEMWIDTH,ITEMHEIGHT,null);
		
		
	}
	
	public Item createNew(int x,int y){
		Item i = new Item(texture, name, id);
		i.setPosition(x, y);
		return i;
	}
	public static Item[] getItems() {
		return items;
	}
	public Item createNewAnim(int x,int y){
		Item i = new Item(tex, name, id);
		i.setPosition(x, y);
		return i;
	}
	
	public void setPosition(int x, int y){
		this.x=x;
		this.y=y;
		bounds.x = x;
		bounds.y = y;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public BufferedImage getTex() {
		return tex[0];
	}
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}
	
	
	
}
