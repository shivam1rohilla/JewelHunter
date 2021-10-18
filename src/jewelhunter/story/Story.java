package jewelhunter.story;

import java.awt.Color;
import java.awt.Graphics;

import jewelhunter.Handler;
import jewelhunter.entities.Entity;
import jewelhunter.entities.statics.Chest;
import jewelhunter.entities.statics.Crate;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;
import jewelhunter.states.GameStateStage1;
import jewelhunter.states.GameStateStage2;
import jewelhunter.tiles.Tile;

public class Story {
	
	private boolean a=false,ac=false,chestOpen=false;
	private int blinkX,blinkY,blinkW,blinkH;
	private long storyticks;
	private Handler handler;
	
	
	public Story(Handler handler) {
		this.handler = handler;
	}
	
	public void storyCompass(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRoundRect(10, 10, 620, 460, 10, 10);
		g.drawImage(Assets.compasspick, 50, 100, 100, 100, null);
		Text.drawString(g, "HINT", 320, 75,true,Color.LIGHT_GRAY,Assets.firecat72);
		Text.drawString(g, "Compass", 320, 150,true,Color.WHITE,Assets.font28bold);
		Text.drawString(g, "YOU FOUND A Compass!", 320, 210,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "It will help you find your Way Out.", 320, 260,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Compass is at Bottom right corner.", 320, 310,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Red points towards your way out.", 320, 360,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Press Enter to close Hint.", 320, 410,true,Color.CYAN,Assets.font28);
		storyticks++;
		a=true;	
		ac=true;
		blinkDim(20, 370, 100, 100);
	}
	public void storySword(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRoundRect(10, 10, 620, 460, 10, 10);
		g.drawImage(Assets.sword, 75, 100, 30, 64, null);
		Text.drawString(g, "HINT", 320, 75,true,Color.LIGHT_GRAY,Assets.firecat72);
		Text.drawString(g, "Sword", 320, 150,true,Color.WHITE,Assets.font28bold);
		Text.drawString(g, "YOU FOUND A Sword!", 320, 210,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "It will help you in damaging Enemy.", 320, 260,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "use this sword by W-A-S-D Keys.", 320, 310,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Press Enter to close Hint.", 320, 410,true,Color.CYAN,Assets.font28);
		
	}
	public void storyPick(Graphics g){
		for(Entity e:handler.getWorld().getEntityManager().getEntities()){
			if(e.toString().contains("Chest")){
				chestOpen=e.openedChest;
			}
		}
		if(!handler.getWorld().getEntityManager().getPlayer().hasCompass()&&chestOpen&&handler.getGame().getGameStateManger().getState() instanceof GameStateStage1){
			g.fillRoundRect(10, 10, 620, 250, 10, 10);
			Text.drawString(g, "HINT", 320, 75,true,Color.LIGHT_GRAY,Assets.firecat72);
			Text.drawString(g, "Pick Compass first", 320, 160,true,Color.WHITE,Assets.font28);
			Text.drawString(g, "Press Enter to close Hint.", 320, 200,true,Color.CYAN,Assets.font28);
			storyticks++;
			a=true;	
			blinkDim(12*64-10, 4*64-10,84, 84);
		}
		else if(!handler.getWorld().getEntityManager().getPlayer().hasSword()&&chestOpen&&handler.getGame().getGameStateManger().getState() instanceof GameStateStage2){
			g.fillRoundRect(10, 10, 620, 250, 10, 10);
			Text.drawString(g, "HINT", 320, 75,true,Color.LIGHT_GRAY,Assets.firecat72);
			Text.drawString(g, "Pick Sword first", 320, 160,true,Color.WHITE,Assets.font28);
			Text.drawString(g, "Press Enter to close Hint.", 320, 200,true,Color.CYAN,Assets.font28);
			
		}
		else{
			g.fillRoundRect(10, 10, 620, 250, 10, 10);
			Text.drawString(g, "HINT", 320, 75,true,Color.LIGHT_GRAY,Assets.firecat72);
			Text.drawString(g, "OPEN CRATE FIRST", 320, 160,true,Color.WHITE,Assets.font28);
			Text.drawString(g, "Press Enter to close Hint.", 320, 200,true,Color.CYAN,Assets.font28);
			storyticks++;
			a=true;	
			for(Entity e:handler.getWorld().getEntityManager().getEntities()){
				if((e instanceof Chest)&&e.getX()<=handler.getGameCamera().getxOffset()+handler.getWidth()*Tile.TILEWIDTH&&e.getX()>=handler.getGameCamera().getxOffset()
						&&(e.getY()<=handler.getWorld().getHeight()*Tile.TILEHEIGHT+handler.getGameCamera().getyOffset())&&e.getY()>=handler.getGameCamera().getyOffset())
					blinkDim((int)e.getX()-10, (int)e.getY()-10,84, 84);
			}
		}
	}
	public void storyCrate(Graphics g){
		g.fillRoundRect(10, 10, 620, 460, 10, 10);
		Text.drawString(g, "HINT", 320, 75,true,Color.LIGHT_GRAY,Assets.firecat72);
		Text.drawString(g, "Crate", 320, 150,true,Color.WHITE,Assets.font28bold);
		Text.drawString(g, "YOU Found Crate", 320, 210,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Help you to block out Enemies.", 320, 260,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "You can push crate.", 320, 310,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "", 320, 360,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Press Enter to close Hint.", 320, 410,true,Color.CYAN,Assets.font28);
		storyticks++;
		a=true;	
		for(Entity e:handler.getWorld().getEntityManager().getEntities())
			if(e instanceof Crate&&e.getX()<=handler.getGameCamera().getxOffset()+handler.getWidth()*Tile.TILEWIDTH&&e.getX()>=handler.getGameCamera().getxOffset()
					&&(e.getY()<=handler.getWorld().getHeight()*Tile.TILEHEIGHT+handler.getGameCamera().getyOffset())&&e.getY()>=handler.getGameCamera().getyOffset())
					blinkDim((int)e.getX()-10, (int)e.getY()-10, 84, 84);
	}
	
	public void storyCrateCantMove(Graphics g){
		g.fillRoundRect(10, 10, 620, 460, 10, 10);
		Text.drawString(g, "HINT", 320, 75,true,Color.LIGHT_GRAY,Assets.firecat72);
		Text.drawString(g, "Crate", 320, 150,true,Color.WHITE,Assets.font28bold);
		Text.drawString(g, "You can't move this crate", 320, 210,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Further. So to reset this carte", 320, 260,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Press \"C\". But you will die Once", 320, 310,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "in this process.", 320, 360,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Press Enter to close Hint.", 320, 410,true,Color.CYAN,Assets.font28);
		storyticks++;
		a=true;	
		
	}
	
	public void storySpeedSlower(Graphics g){
		g.fillRoundRect(10, 10, 620, 460, 10, 10);
		Text.drawString(g, "HINT", 320, 75,true,Color.LIGHT_GRAY,Assets.firecat72);
		Text.drawString(g, "Speed Slower", 320, 150,true,Color.WHITE,Assets.font28bold);
		Text.drawString(g, "You can Decrease the speed", 320, 210,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "of the enimies around you For", 320, 260,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "limited period of time Down-Right", 320, 310,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "corner indicates the this power", 320, 360,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Press Enter to close Hint.", 320, 410,true,Color.CYAN,Assets.font28);
		storyticks++;
		ac=true;
		a=true;	
		blinkDim(handler.getGame().getWidth()-70-50, handler.getGame().getHeight()-60-50, 100, 100);
	}
	
	public void storyBlink(Graphics g){
		if(storyticks!=0){
			storyticks++;
		if(storyticks%30==0&&storyticks<330)
			a=!a;
		}
		if(storyticks>330){
			a=false;
			storyticks=0;
			ac=false;
		}
		if(a){
			if(ac)
				g.drawRoundRect(blinkX, blinkY, blinkW, blinkH, 10, 10);
			else
				g.drawRoundRect(blinkX-(int)handler.getGameCamera().getxOffset(), blinkY-(int)handler.getGameCamera().getyOffset(), blinkW, blinkH, 10, 10);
			
		}
	}
	
	public void storyHintStage4(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRoundRect(10, 10, 620, 460, 10, 10);
		Text.drawString(g, "HINT", 320, 75,true,Color.LIGHT_GRAY,Assets.firecat72);
		Text.drawString(g, "Stage 4", 320, 150,true,Color.WHITE,Assets.font28bold);
		Text.drawString(g, "You are on last Stage!", 320, 210,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Fight with all the enemies", 320, 260,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Then you can find a door!", 320, 310,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Don't forget to use speedSlower!", 320, 360,true,Color.WHITE,Assets.font28);
		Text.drawString(g, "Press Enter to close Hint.", 320, 410,true,Color.CYAN,Assets.font28);
		storyticks++;
		a=true;	
		ac=true;
		blinkDim(20, 370, 100, 100);
	}
	
	private void blinkDim(int x,int y, int width, int height){
		blinkX=x;
		blinkY=y;
		blinkW=width;
		blinkH=height;
	}
}
