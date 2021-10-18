package jewelhunter.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import jewelhunter.Handler;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.FontLoader;
import jewelhunter.gfx.Text;
import jewelhunter.utils.Utils;

public class CreditsState extends State {

	private int ticks;
	
	private static Font font;
	private static String[] credits;
	
	private int creditX,creditY,creditSpacing;
	
	public CreditsState(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
		String file = Utils.loadFileAsString("Credits/JewelHunter.crd");
		credits = file.split("\n");
		ticks = 0;
		font = FontLoader.loadFont("fonts/FirecatMedium.ttf", 100);
		creditX = handler.getWidth()/2;
		creditY = handler.getHeight()/2;
		creditSpacing = 30;
	}
	@Override
	public void init() {
	}

	@Override
	public void tick() {
		ticks++;
		
		if(ticks%2==0){
			creditY--;
		}
		if(creditY+creditSpacing*credits.length<100||handler.getKeyManager().escape){
			gsm.setState(GameStateManager.MENU);
		}
	}

	@Override
	public void render(Graphics g) {
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		for(int i=0; i<credits.length;i++)
		Text.drawString(g,credits[i], creditX, creditY + i*creditSpacing, true, Color.WHITE	, Assets.font28);
		g.setColor(new Color(51, 51, 51).brighter());
		g.fillRect(0, 0, handler.getWidth(), 100);
		Text.drawString(g,"CREDITS", creditX,50, true, Color.WHITE	, font);
		
	}

	
	
}
