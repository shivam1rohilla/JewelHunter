package jewelhunter.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import jewelhunter.Handler;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.FontLoader;
import jewelhunter.gfx.Text;

public class ControlsState extends State {

	
	
	private static Font font;
	
	public ControlsState(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
		font = FontLoader.loadFont("fonts/FirecatMedium.ttf", 82);
	}
	@Override
	public void init() {
	}

	@Override
	public void tick() {
		
		if(handler.getKeyManager().escape){
			gsm.setState(GameStateManager.MENU);
		}
	}

	@Override
	public void render(Graphics g) {
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		g.drawImage(Assets.keyARROW, 120, 120, 200, 125, null);
		Text.drawString(g,"Move", 220,270, true, Color.WHITE, Assets.font28bold);
		g.drawImage(Assets.keyWASD, 340, 120, 200, 120, null);
		Text.drawString(g,"ATTACK", 440,270, true, Color.WHITE, Assets.font28bold);
		g.drawImage(Assets.keyESC, 50, 320, 50, 50, null);
		Text.drawString(g,"Pause", 170,345, true, Color.WHITE, Assets.font28bold);
		g.drawImage(Assets.keyR, 240, 320, 50, 50, null);
		Text.drawString(g,"SpeedSlower", 420,345, true, Color.WHITE, Assets.font28bold);
		g.drawImage(Assets.keyC, 220, 390, 50, 50, null);
		Text.drawString(g,"Crate Reset", 390,415, true, Color.WHITE, Assets.font28bold);
		g.setColor(new Color(51, 51, 51).brighter());
		g.fillRect(0, 0, handler.getWidth(), 100);
		Text.drawString(g,"Controls", handler.getWidth()/2,50, true, Color.WHITE, font);
		Text.drawString(g,"Press ESC to go back", handler.getWidth()/2,handler.getHeight()-10, true, Color.CYAN, Assets.font28bold);
	}
}
