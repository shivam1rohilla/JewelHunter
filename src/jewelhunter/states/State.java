package jewelhunter.states;

import java.awt.Graphics;
import java.util.ArrayList;

import jewelhunter.Handler;
import jewelhunter.inventory.Inventory;
import jewelhunter.item.Item;

public abstract class State {

	protected Handler handler;
	protected GameStateManager gsm;
	protected boolean mute=false;
	protected static ArrayList<Item> items;
	protected static Inventory inv;
	
	public State(Handler handler,GameStateManager gsm) {
		this.handler = handler;
		this.gsm = gsm;
	}
	
	public abstract void tick();
	public abstract void init();
	public abstract void render(Graphics g);
	
	
}
