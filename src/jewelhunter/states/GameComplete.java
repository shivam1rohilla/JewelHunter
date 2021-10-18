package jewelhunter.states;

import java.awt.Color;
import java.awt.Graphics;

import jewelhunter.Handler;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;

public class GameComplete extends State {


	
	public GameComplete(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
	}
	@Override
	public void init() {
	}

	@Override
	public void tick() {
		if(handler.getKeyManager().enter)
			gsm.setState(GameStateManager.MENU);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.back, 0, 0,640,480, null);
		g.setColor(new Color(0, 0, 0 , 200));
		g.fillRoundRect(5, 5, handler.getWidth()-10, handler.getHeight()-10, 15, 15);
		g.drawImage(Assets.playerAward, 40, 40,128,128, null);
		Text.drawString(g, "UK Games", handler.getWidth()/2+20, 50, true, Color.LIGHT_GRAY, Assets.bloc_r);
		Text.drawString(g, "Congratulations!", handler.getWidth()/2, handler.getHeight()/2-100, true, Color.LIGHT_GRAY, Assets.font28);
		Text.drawString(g, "You Successfully Cleared This Game", handler.getWidth()/2, handler.getHeight()/2-50, true, Color.LIGHT_GRAY, Assets.font28);
		Text.drawString(g, "You now get awarded by the queen.", handler.getWidth()/2, handler.getHeight()/2, true, Color.LIGHT_GRAY, Assets.font28);
		Text.drawString(g, "You compeleted in "+gsm.min+" min & "+gsm.sec+" sec", handler.getWidth()/2, handler.getHeight()/2+50, true, Color.LIGHT_GRAY, Assets.font28);
		Text.drawString(g, "Press Enter to Proceed..", handler.getWidth()/2, handler.getHeight()/2+220, true, Color.CYAN, Assets.font28);
	}

	
	
}
