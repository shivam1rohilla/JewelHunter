package jewelhunter.states;

import java.awt.Color;
import java.awt.Graphics;

import jewelhunter.Handler;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;

public class GameOver extends State {

	private int ticks;
	
	public GameOver(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
	}
	@Override
	public void init() {
	}

	@Override
	public void tick() {
		ticks++;
		if(handler.getKeyManager().escape){
			ticks=500;
		}
		if(ticks>=500){
			gsm.setState(GameStateManager.MENU);
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.back, 0, 0,640,480, null);
		g.setColor(new Color(0, 0, 0 , 200));
		g.fillRoundRect(5, 5, handler.getWidth()-10, handler.getHeight()-10, 15, 15);
		Text.drawString(g, "Game Over", handler.getWidth()/2, handler.getHeight()/2, true, Color.GRAY, Assets.bloc_r);
	}

}
