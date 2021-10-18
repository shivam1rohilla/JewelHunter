package jewelhunter.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import jewelhunter.Handler;
import jewelhunter.audio.Sound;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;
import jewelhunter.ui.ClickListener;
import jewelhunter.ui.UIImageButton;
import jewelhunter.ui.UIManager;

public class MenuState extends State {
	
	private UIManager uiManager;
	private BufferedImage img;

	public MenuState(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
		gsm.min=0;
		gsm.sec=0;
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/resources/menuscreen.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		uiManager =new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.addOject(new UIImageButton(handler.getWidth()/2-71, handler.getHeight()/2/*+20*/, 142, 48,Assets.btn_play,new ClickListener() {
			
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				Sound.playAudio("option");
				gsm.setState(GameStateManager.GAMESTART);
			}
		}));
		
		uiManager.addOject(new UIImageButton(handler.getWidth()/2-71, handler.getHeight()/2+56, 142, 48,Assets.btn_blank,new ClickListener() {
			
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				Sound.playAudio("option");
				gsm.setState(GameStateManager.CONTROLS);
			}
		}));
		
		uiManager.addOject(new UIImageButton(handler.getWidth()/2-71, handler.getHeight()/2/*+20*/+56*2, 142, 48,Assets.btn_credits,new ClickListener() {
			
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				Sound.playAudio("option");
				gsm.setState(GameStateManager.CREDITS);
			}
		}));
		uiManager.addOject(new UIImageButton(handler.getWidth()/2-71, handler.getHeight()/2/*+20*/+56*3, 142, 48,Assets.btn_exit,new ClickListener() {
			
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				Sound.playAudio("option");
				gsm.setState(GameStateManager.EXIT);
			}
		}));
		uiManager.addOject(new UIImageButton(10, 10, 64, 64,Assets.sound,new ClickListener() {
			
			@Override
			public void onClick() {
				change();
				Sound.playAudio("option");
				handler.getGame().setMute(!handler.getGame().isMute());
			}
		}));
		
	}
	@Override
	public void init() {
		
	}
	@Override
	public void tick() {
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, 0, 0, handler.getWidth(), handler.getHeight(), null);
		uiManager.render(g);
		Text.drawString(g,"CONTROLS", handler.getWidth()/2, handler.getHeight()/2+25+56, true, Color.BLACK, new Font ("Arial", Font.BOLD , 20));
	}
	
	public void change(){
		BufferedImage b=Assets.sound[0];
		Assets.sound[0]=Assets.sound[1];
		Assets.sound[1]=b;
	}

}
