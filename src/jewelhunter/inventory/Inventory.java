package jewelhunter.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.sun.glass.events.KeyEvent;

import jewelhunter.Handler;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;
import jewelhunter.item.Item;

public class Inventory {

	private Handler handler;
	private boolean active = false;
	private ArrayList<Item> inventoryItems;
	private int invX = 64, invY=48, invWidth=512, invHeight = 384,
			invListCenterX = invX + 171,
			invListCenterY = invY + 209,
			invListSpacing = 30;
	
	private int invImageX = 452, invImageY = 122, invImageWidth = 64, invImageHeight = 64;
	
	private int invCountX = 484,invCountY = 212;
	
	private int selectedItem = 0;
	
	public Inventory(Handler handler) {
		this.handler = handler;
		inventoryItems = new ArrayList<Item>();
		
	}
	
	public void tick() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_I)){
			active = !active;
		}
		if(!active)
			return;
		
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
			selectedItem--;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
			selectedItem++;
		
		if(selectedItem < 0)
			selectedItem = inventoryItems.size() - 1;
		else if(selectedItem >= inventoryItems.size())
			selectedItem = 0;
	}
	
	public void render(Graphics g) {
		if(!active)
			return;
		
		//g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight, null);
		g.setColor(Color.BLACK);
		g.drawRoundRect(invX-1, invY-1, invWidth+1, invHeight+1, 30, 30);
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(invX, invY, invWidth, invHeight, 30, 30);
		Text.drawString(g, "Inventory", (invWidth+invX+75)/2, invY+40, true, Color.MAGENTA, Assets.head72);
		if(inventoryItems.isEmpty())
		{
			Text.drawString(g, " EMPTY!", (invWidth+invX)/2, (invHeight+invY)/2, true, Color.MAGENTA	, Assets.head72);
			return;
		}
		g.setColor(Color.BLACK);
		g.drawRoundRect(invImageX-11, invImageY-11, invImageWidth+21, invImageHeight+21, 30, 30);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRoundRect(invImageX-10, invImageY-10, invImageWidth+20, invImageHeight+20, 30, 30);
		g.setColor(Color.BLACK);
		g.drawRoundRect(invCountX-11, invCountY-11, 21, 22, 4, 4);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRoundRect(invCountX-10, invCountY-10, 20,20, 4, 4);
		g.setColor(Color.BLACK);
		g.drawRoundRect(invX+18, invY+62, invImageX-147, invHeight-87, 30, 30);
		g.setColor(Color.GRAY);
		g.fillRoundRect(invX+19, invY+63, invImageX-148, invHeight-88, 30, 30);
		g.setColor(Color.BLACK);
		g.drawRoundRect(invImageX-11, invImageY+168, invImageWidth+21, invImageHeight+56, 30, 30);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRoundRect(invImageX-10, invImageY+169, invImageWidth+20, invImageHeight+55, 30, 30);
		g.drawImage(Assets.player_down[1], invImageX-5, invImageY+186,70,70, null);
		int len = inventoryItems.size();
		if(len == 0)
			return;
		
		for (int i = -5; i < 6; i++) {
			if(selectedItem + i < 0 || selectedItem + i >=len)
				continue;
			if(i==0){
				Text.drawString(g, "> "+inventoryItems.get(selectedItem + i).getName()+" <", invListCenterX, invListCenterY + i*invListSpacing, true, Color.YELLOW	, Assets.font28);
			}
			else{
			Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), invListCenterX, invListCenterY + i*invListSpacing, true, Color.WHITE	, Assets.font28);
			}
		}
		Item item = inventoryItems.get(selectedItem);
		
		if(item.getTexture()!=null)
		g.drawImage(item.getTexture(), invImageX, invImageY,invImageWidth,invImageHeight, null);
		else if(item.getTex()!=null)
			g.drawImage(item.getTex(), invImageX, invImageY,invImageWidth,invImageHeight, null);
		Text.drawString(g, Integer.toString(item.getCount()), invCountX, invCountY, true, Color.WHITE, Assets.font28);
		
	}
	
	public void addItem(Item item){
		for(Item i : inventoryItems){
			if(i.getId() == item.getId()){
				i.setCount(i.getCount() + item.getCount());
				return;
			}
		}
		inventoryItems.add(item);
	}
	public void removeItem(Item item){
		
		for(Item i : inventoryItems){
			if(i.getId() == item.getId()){
				i.setCount(i.getCount() - 1);
				return;
			}
		}
		
	}
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isActive() {
		return active;
	}
	public int getItemCount(Item item){
		for(Item i : inventoryItems){
			if(i.getId() == item.getId()){
				return i.getCount();
			}
		}
		return 0;
	}

	public ArrayList<Item> getInventoryItems() {
		return inventoryItems;
	}
	
	
}
