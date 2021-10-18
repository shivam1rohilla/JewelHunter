package jewelhunter;

import jewelhunter.audio.Sound;
import jewelhunter.gfx.Assets;

public class Launcher {
	public static void main(String[] args){
		Sound.init();
		Assets.init();
		Game game = new Game("JeWeL HuNTeR",640,480);
		
		game.start();
	}
}
