package jewelhunter.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import jewelhunter.Handler;
import jewelhunter.gfx.FontLoader;
import jewelhunter.gfx.Text;

public class ExitState extends State {

	private int alpha;
	private int ticks;
	private Random rand = new Random();
	
	private final int FADE_IN = 60;
	private final int LENGTH = 30;
	private final int FADE_OUT = 60;
	
	private static Font font;
	
	public ExitState(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
		ticks = 0;
		font = FontLoader.loadFont("fonts/FirecatMedium.ttf", 100);
	}
	@Override
	public void init() {
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
			System.exit(0);
		}
	}

	@Override
	public void render(Graphics g) {
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		Text.drawString(g, "BYE", handler.getWidth()/2, handler.getHeight()/2, true, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()), font);
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		
	}

	
	
}
