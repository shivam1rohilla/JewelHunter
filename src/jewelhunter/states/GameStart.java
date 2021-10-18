package jewelhunter.states;

import java.awt.Color;
import java.awt.Graphics;

import jewelhunter.Handler;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;

public class GameStart extends State {

	
	private int ticks,ticks1;
	private boolean info,sg1;
	
	
	public GameStart(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
	}
	@Override
	public void init() {
	}

	@Override
	public void tick() {
		
		if(handler.getKeyManager().enter&&!info){
			handler.getKeyManager().enter=false;
			sg1 = true;
			info=true;
			
		}if(sg1){
			ticks1++;
				if(handler.getKeyManager().enter&&ticks1>=10){
					ticks=0;
					sg1=false;
				}
		}
		if(!sg1&&info){
			ticks++;
			if(handler.getKeyManager().escape){
				ticks=150;
			}
		}
		if(ticks==150)
			gsm.setState(GameStateManager.STAGE1);
		
	}

	@Override
	public void render(Graphics g) {
	
		g.drawImage(Assets.back, 0, 0,640,480, null);
		g.setColor(new Color(0, 0, 0 , 200));
		g.fillRoundRect(5, 5, handler.getWidth()-10, handler.getHeight()-10, 15, 15);
		if(!info){
			g.drawImage(Assets.flag, 10, 10, 128, 64 , null);
			g.drawImage(Assets.kDimond, handler.getWidth()-100, handler.getHeight()-100, 80, 64 , null);
			Text.drawString(g, "UK Games", handler.getWidth()/2+20, 50, true, Color.LIGHT_GRAY, Assets.bloc_r);
			Text.drawString(g, "First time in UK, Queen of England", handler.getWidth()/2, handler.getHeight()/2-100, true, Color.LIGHT_GRAY, Assets.font28);
			Text.drawString(g, "Announced UK Games. Player need", handler.getWidth()/2, handler.getHeight()/2-50, true, Color.LIGHT_GRAY, Assets.font28);
			Text.drawString(g, "To find kohinoor Diamond & collect ", handler.getWidth()/2, handler.getHeight()/2, true, Color.LIGHT_GRAY, Assets.font28);
			Text.drawString(g, "diamonds. Player Need to creative ", handler.getWidth()/2, handler.getHeight()/2+50, true, Color.LIGHT_GRAY, Assets.font28);
			Text.drawString(g, "to find paths. Best Player will ", handler.getWidth()/2, handler.getHeight()/2+100, true, Color.LIGHT_GRAY, Assets.font28);
			Text.drawString(g, "be announced as Knight.", handler.getWidth()/2, handler.getHeight()/2+150, true, Color.LIGHT_GRAY, Assets.font28);
			Text.drawString(g, "Press Enter to Proceed..", handler.getWidth()/2, handler.getHeight()/2+220, true, Color.CYAN, Assets.font28);
		}
		if(sg1){
			g.drawImage(Assets.back1, 0, 0,640,480, null);
			g.setColor(new Color(0, 0, 0 , 200));
			g.fillRoundRect(5, 5, handler.getWidth()-10, handler.getHeight()-10, 15, 15);
			g.drawImage(Assets.flag, 10, 10, 128, 64 , null);
			g.drawImage(Assets.kDimond, handler.getWidth()-100, handler.getHeight()-100, 80, 64 , null);
			Text.drawString(g, "UK Games", handler.getWidth()/2+20, 50, true, Color.LIGHT_GRAY, Assets.bloc_r);
			Text.drawString(g, "Knight is a honaray title in UK", handler.getWidth()/2, handler.getHeight()/2-100, true, Color.LIGHT_GRAY, Assets.font28);
			Text.drawString(g, "Now Get ready for the adventure", handler.getWidth()/2, handler.getHeight()/2-50, true, Color.LIGHT_GRAY, Assets.font28);
			Text.drawString(g, "To Become Knight of the country", handler.getWidth()/2, handler.getHeight()/2, true, Color.LIGHT_GRAY, Assets.font28);
			Text.drawString(g, "You need to clear 4 stages in one", handler.getWidth()/2, handler.getHeight()/2+50, true, Color.LIGHT_GRAY, Assets.font28);
			Text.drawString(g, "Flow. And Do it fast as you can", handler.getWidth()/2, handler.getHeight()/2+100, true, Color.LIGHT_GRAY, Assets.font28);
			Text.drawString(g, "", handler.getWidth()/2, handler.getHeight()/2+150, true, Color.LIGHT_GRAY, Assets.font28);
			if(ticks1>50)
			Text.drawString(g, "Press Enter to Proceed..", handler.getWidth()/2, handler.getHeight()/2+220, true, Color.CYAN, Assets.font28);
		}
		if(!sg1&&info){
			Text.drawString(g, "Stage 1...", handler.getWidth()/2, handler.getHeight()/2-150, true, Color.LIGHT_GRAY, Assets.bloc_r);
			g.drawImage(Assets.player_right[1], handler.getWidth()/2-130, handler.getHeight()/2-40, 80, 80,null);
			Text.drawString(g, "X", handler.getWidth()/2, handler.getHeight()/2, true, Color.WHITE, Assets.head72);
			Text.drawString(g, "3", handler.getWidth()/2+100, handler.getHeight()/2, true, Color.WHITE, Assets.bloc_r);
		}
			
	}

	
	
	
}
