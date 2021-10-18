package jewelhunter.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import jewelhunter.gfx.Animation;

public class Tile {
	
	public static Tile[] tiles=new Tile[256];
	public static Tile grassTile =new GrassTile(0);
	public static Tile dirtTile =new DirtTile(1);
	public static Tile rockTile =new RockTile(2);
	public static Tile outTile =new OutTile(3);
	public static Tile iceWallTile =new IceWallTile(5);
	public static Tile steelWallTile =new SteelWallTile(6);
	public static Tile iceLandTile =new IceLandTile(7);
	
	public static final int TILEWIDTH = 64, TILEHEIGHT =64;
	protected BufferedImage[] texture;
	protected BufferedImage tex;
	protected final int id;
	private Animation tileAnim;
	
	public Tile(BufferedImage texture,int id) {
		this.tex=texture;
		this.id=id;
		
		tiles[id]=this;
	}
	public Tile(BufferedImage[] texture,int id) {
		this.texture=texture;
		this.id=id;
		tileAnim = new Animation(500, texture);
		tiles[id]=this;
	}
	public Tile(Animation tileAnim,int id) {
		this.tileAnim = tileAnim;
		this.id=id;
		tiles[id]=this;
	}
	public void tick(){
	}
	
	public void render(Graphics g, int x,int y){
		
		if(tileAnim!=null){
			g.drawImage( tileAnim.getCurrentFrame(), x, y, TILEWIDTH,TILEHEIGHT,null);
		}
		g.drawImage(tex, x, y, TILEWIDTH,TILEHEIGHT,null);
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public boolean isExit() {
		return false;
	}
	
	public int getId(){
		return id;
	}
	public boolean isWater() {
		return false;
	}
}
