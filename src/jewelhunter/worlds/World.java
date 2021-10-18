package jewelhunter.worlds;

import java.awt.Graphics;

import jewelhunter.Handler;
import jewelhunter.entities.EntityManager;
import jewelhunter.entities.creatures.Player;
import jewelhunter.gfx.Assets;
import jewelhunter.hud.Hud;
import jewelhunter.item.ItemManager;
import jewelhunter.tiles.Tile;
import jewelhunter.utils.Utils;

public class World {

	private Handler handler;
	private int width,height,spawnX, spawnY,outX,outY;
	private int[][] tiles;
	private EntityManager entityManager;
	private ItemManager itemManager;
	
	private Hud hud;
	
	public World(Handler handler,String path) {
		this.handler=handler;
		entityManager = new EntityManager(handler, new Player(handler, spawnX, spawnY));
		itemManager = new ItemManager(handler);
		loadWorld(path);
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
		
		hud = new Hud(handler);
	}
	public void init(){
		
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void tick(){
		entityManager.tick();
		itemManager.tick();
		getTile(0, 0).tick();
	}
	
	public void render(Graphics g){
		int xStart=(int)Math.max(0, handler.getGameCamera().getxOffset()/Tile.TILEWIDTH);
		int xEnd=(int)Math.min(width, (handler.getGameCamera().getxOffset()+ handler.getWidth())/Tile.TILEWIDTH+1);
		int yStart=(int)Math.max(0, handler.getGameCamera().getyOffset()/Tile.TILEHEIGHT);
		int yEnd=(int)Math.min(width, (handler.getGameCamera().getyOffset()+ handler.getWidth())/Tile.TILEHEIGHT+1);
		
		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				if(getTile(x, y).isExit()){
						g.drawImage(Assets.dirt, (int)(x*Tile.TILEWIDTH-handler.getGameCamera().getxOffset()), (int)(y*Tile.TILEHEIGHT-handler.getGameCamera().getyOffset()),Tile.TILEWIDTH,Tile.TILEHEIGHT, null);
				}	
				getTile(x,y).render(g, (int)(x*Tile.TILEWIDTH-handler.getGameCamera().getxOffset()), (int)(y*Tile.TILEHEIGHT-handler.getGameCamera().getyOffset()));
			}
		}
		
		itemManager.render(g);
		entityManager.render(g);
		
	}
	
	public Tile getTile(int x,int y){
		
		if(x<0||y<0||x>=width||y>=height){
			return Tile.grassTile;
		}
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t==null)
			return Tile.dirtTile;
		return t;
	}
	
	private void loadWorld(String path) {
		
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		tiles= new int[width][height];
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				tiles[x][y] = Utils.parseInt(tokens[(x+y*width)+4]);
				if(tiles[x][y]==3){
					setOutX(x*Tile.TILEWIDTH);
					setOutY(y*Tile.TILEHEIGHT);
				}
								
			}				
		}
	}
	public int getWidth(){
		return width;
		
	}
	public int getHeight(){
		return height;
	}
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public ItemManager getItemManager() {
		return itemManager;
	}
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	public int getOutX() {
		return outX;
	}
	public void setOutX(int outX) {
		this.outX = outX;
	}
	public int getOutY() {
		return outY;
	}
	public void setOutY(int outY) {
		this.outY = outY;
	}
	
	public Hud getHud() {
		return hud;
	}
	public int getSpawnX() {
		return spawnX;
	}
	public int getSpawnY() {
		return spawnY;
	}
	
}
