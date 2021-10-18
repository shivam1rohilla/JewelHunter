package jewelhunter.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import jewelhunter.Handler;
import jewelhunter.audio.Sound;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;

public class PauseState extends State {

	private boolean active = false;
	public PauseState(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
		
	}
	
	@Override
	public void init() {
	
	}

	@Override
	public void tick() {
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			Sound.loopAudio("background", true);
			gsm.setPaused(false);
		}
		if(handler.getKeyManager().endk){
			//Sound.loopAudio("background", true);
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENU);
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.back, 0, 0,640,480, null);
		g.setColor(new Color(0, 0, 0 , 200));
		g.fillRoundRect(5, 5, handler.getWidth()-10, handler.getHeight()-10, 15, 15);
		Text.drawString(g, "Paused", handler.getWidth()/2, 80, true, Color.BLACK, Assets.firecat72);
		Text.drawString(g, "Action", handler.getWidth()/2, 180, true, Color.white, Assets.head72);
		Text.drawString(g, "W - A - S - D", handler.getWidth()/2,260, true, Color.white, Assets.head72);
		Text.drawString(g, "Move", handler.getWidth()/2, 340, true, Color.white, Assets.head72);
		Text.drawString(g, "Arrow keys", handler.getWidth()/2, 420, true, Color.white, Assets.head72);
		Text.drawString(g, "Press End for Main Menu", handler.getWidth()/2, 460, true, Color.cyan, Assets.font28bold);
		
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	


}
