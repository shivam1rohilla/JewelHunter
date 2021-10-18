package jewelhunter.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import jewelhunter.Handler;
import jewelhunter.audio.Sound;
import jewelhunter.gfx.FontLoader;
import jewelhunter.gfx.Text;

public class IntroState extends State {

	private int alpha;
	private int ticks;
	private Random rand = new Random();
	
	private final int FADE_IN = 60;
	private final int LENGTH = 30;
	private final int FADE_OUT = 60;
	
	private static Font font;
	
	public IntroState(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
		ticks = 0;
		font = FontLoader.loadFont("fonts/FirecatMedium.ttf", 255);
	}
	@Override
	public void init() {
		Sound.loopAudio("fanfare", true);
	}

	@Override
	public void tick() {
		ticks++;
		if(ticks < FADE_IN) {
			alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
			if(alpha < 0) alpha = 0;
		}
		if(ticks > FADE_IN + LENGTH) {
			alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
			if(alpha > 255) alpha = 255;
		}
		if(ticks > FADE_IN + LENGTH + FADE_OUT) {
			Sound.stop("fanfare");
			gsm.setState(GameStateManager.MENU);
		}
	}

	@Override
	public void render(Graphics g) {
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		Text.drawString(g, "S", handler.getWidth()/2-40, handler.getHeight()/2-70, true, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()), font);
		Text.drawString(g, "R", handler.getWidth()/2+50, handler.getHeight()/2+60, true, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()), font);
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		
	}

	
	
}
