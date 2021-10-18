package jewelhunter.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;
import jewelhunter.audio.Sound;

public class Assets {
	
	private static final int width= 32,height =32;
	
	
	public static BufferedImage dirt,compass,signExit,compasspick,iceWall,iceLand,crate,steelWall;
	public static BufferedImage tLeft,tRight,tUp,tDown,flag,kDimond,back,oneUp,back1;
	public static BufferedImage[] player_down,player_up,player_right,player_left,cryst,cryst_green,cryst_grey,player_down_hood,player_up_hood,player_right_hood,player_left_hood;
	public static BufferedImage[] zombie_down,zombie_up,zombie_right,zombie_left,flameThrower,chest,teleportButton;
	public static BufferedImage[] btn_start,grass, stone,barrel,btn_play,btn_exit,btn_highscores,btn_credits,btn_blank,sound;
	public static BufferedImage[] player_attack_down,player_attack_up,player_attack_right,player_attack_left,healthHearts,speedItem,player_attack_down_hood,player_attack_up_hood,player_attack_right_hood,player_attack_left_hood;
	public static BufferedImage[] laserRight,laserLeft,laserBeamH,laserBeamV,laserBeamBurst;
	public static BufferedImage sword,playerAward,barrelGate;
	public static BufferedImage keyC,keyR,keyESC,keyWASD,keyARROW;
	public static Font font28,head72,firecat,firecat72,font28bold,bloc_r;
	
	
	public static void init(){
		Sound.loadAudio("background", "/resources/Music/levcomp.wav");
		Sound.setVolume("background",-25);
		Sound.loadAudio("collect", "/resources/SFX/collect.wav");
		Sound.loadAudio("option", "/resources/SFX/menuoption.wav");
		Sound.loadAudio("fanfare", "/resources/Music/fanfare.wav");
		Sound.loadAudio("finish", "/resources/Music/finish.wav");
		Sound.loadAudio("whoosh", "/resources/SFX/whoosh.wav");
		Sound.setVolume("option", -30);
		
		
		////////////////////////////////////
		
		laserRight = new BufferedImage[2];
		laserLeft = new BufferedImage[2];
		laserBeamH = new BufferedImage[2];
		laserBeamV = new BufferedImage[2];
		laserBeamBurst = new BufferedImage[2];
		
		for(int i = 0; i<2 ; i++){
			laserRight[i] = ImageLoader.loadImage("/resources/res/Laser/laserRight"+i+".png");
			laserLeft[i] = ImageLoader.loadImage("/resources/res/Laser/laserLeft"+i+".png");
			laserBeamH[i] = ImageLoader.loadImage("/resources/res/Laser/laserBeamH"+i+".png");
			laserBeamV[i] = ImageLoader.loadImage("/resources/res/Laser/laserBeamV"+i+".png");
			laserBeamBurst[i] = ImageLoader.loadImage("/resources/res/Laser/laserBurst"+i+".png");
		}
		
		////////////////////////////////////
		
		iceWall = ImageLoader.loadImage("/resources/res/iceBlockAlt1.png");
		iceLand = ImageLoader.loadImage("/resources/res/igloo.png");
		steelWall = ImageLoader.loadImage("/resources/res/metalMid.png");
				
		////////////////////////////////////
		
		crate = ImageLoader.loadImage("/resources/res/Crate.png");
		barrelGate = ImageLoader.loadImage("/resources/res/b.png");
		
		//////////////////////////////////////
		
		sword = ImageLoader.loadImage("/resources/Weapons.png");
		
		/////////////////////////////////////
		
		keyC = ImageLoader.loadImage("/resources/keyboard/c.png");
		keyARROW = ImageLoader.loadImage("/resources/keyboard/arrow.png");
		keyWASD = ImageLoader.loadImage("/resources/keyboard/wasd.png");
		keyR = ImageLoader.loadImage("/resources/keyboard/r.png");
		keyESC = ImageLoader.loadImage("/resources/keyboard/esc.png");
		
		
		/////////////////////////////////////
		
		tDown = ImageLoader.loadImage("/resources/res/Teleport/down.png");
		tUp = ImageLoader.loadImage("/resources/res/Teleport/up.png");
		tRight = ImageLoader.loadImage("/resources/res/Teleport/right.png");
		tLeft = ImageLoader.loadImage("/resources/res/Teleport/left.png");
		
		//////////////////////////////////////
		
		teleportButton = new BufferedImage[2];
		SpriteSheet tButtonSheet = new SpriteSheet(ImageLoader.loadImage("/resources/res/button.png"));
		
		teleportButton[0] = tButtonSheet.crop(0, 30, 64, 64);
		teleportButton[1] = tButtonSheet.crop(64, 30, 64, 64);
		
		//////////////////////////////////////////
		
		flag = ImageLoader.loadImage("/resources/res/Flag.png");
		kDimond = ImageLoader.loadImage("/resources/res/kDiamond.png");
		back = ImageLoader.loadImage("/resources/res/back.jpg");
		back1 = ImageLoader.loadImage("/resources/res/back1.jpg");
		oneUp = ImageLoader.loadImage("/resources/res/1UP.png");
		
		//////////////////////////////////////////
		
		bloc_r = FontLoader.loadFont("fonts/bloc/bloc-r.ttf", 80);
		
		font28 = FontLoader.loadFont("fonts/slkscr.ttf", 28);
		font28bold = FontLoader.loadFont("fonts/slkscr.ttf", 28,1);
		head72 = FontLoader.loadFont("fonts/Headhunter-Regular.ttf", 50);
		firecat = FontLoader.loadFont("fonts/FirecatMedium.ttf", 255);
		firecat72 = FontLoader.loadFont("fonts/FirecatMedium.ttf", 72);
		
		//////////////////////////////////////////////////////
		
		SpriteSheet sheet=new SpriteSheet(ImageLoader.loadImage("/resources/textures/sheet.png"));
		SpriteSheet crystal=new SpriteSheet(ImageLoader.loadImage("/resources/res/crystal_blue.png"));
		SpriteSheet crystal_green=new SpriteSheet(ImageLoader.loadImage("/resources/res/crystal_green.png"));
		SpriteSheet crystal_grey=new SpriteSheet(ImageLoader.loadImage("/resources/res/crystal_grey.png"));
		SpriteSheet tiles=new SpriteSheet(ImageLoader.loadImage("/resources/res/tiles.png"));
		SpriteSheet stoneSprite=new SpriteSheet(ImageLoader.loadImage("/resources/res/Stone.png"));
		compass = ImageLoader.loadImage("/resources/CompassN.png");
		compasspick = ImageLoader.loadImage("/resources/compass_pin.png");
		cryst = new BufferedImage[8];
		cryst_green = new BufferedImage[8];
		cryst_grey = new BufferedImage[8];
		
		for(int i = 0; i<8; i++){
			cryst[i] = crystal.crop(width*i, 0, width, height);
			cryst_green[i] = crystal_green.crop(width*i, 0, width, height);
			cryst_grey[i] = crystal_grey.crop(width*i, 0, width, height);
		}
		
		
		healthHearts = new BufferedImage[8];
		speedItem = new BufferedImage[10];
		for(int i = 0; i<10; i++){
			speedItem[i] = ImageLoader.loadImage("/resources/Speed/frame-"+(i+1)+".png");
		}
		for(int i = 0; i<8; i++){
			healthHearts[i] = ImageLoader.loadImage("/resources/Health/frame-"+(i+1)+".png");
		}		
		
		sound = new BufferedImage[2];
		sound[0] = ImageLoader.loadImage("/resources/buttons/Button_sound_on.png");
		sound[1] = ImageLoader.loadImage("/resources/buttons/Button_sound_off.png");
		
		grass = new BufferedImage[6];
		stone = new BufferedImage[4];
		barrel = new BufferedImage[32];
		for(int i = 0; i<6; i++){
			grass[i] = tiles.crop(width*i, 0, width, height);
		}
		for(int i = 0; i<4; i++){
			stone[i] = stoneSprite.crop((width)*i+1, 0 , width, height);
		}
		
		for(int i = 0;i<32;i++){
			
			barrel[i]=ImageLoader.loadImage("/resources/Barrel/Barrel "+(i+1)+".png");
		}
		
		btn_play = new BufferedImage[2];
		btn_highscores = new BufferedImage[2];
		btn_exit = new BufferedImage[2];
		btn_credits = new BufferedImage[2];
		btn_blank  = new BufferedImage[2];
		
		for(int i = 0; i<2 ; i++){
			btn_play[i] = ImageLoader.loadImage("/resources/buttons/Playgame "+(i+1)+".bmp");
			btn_highscores[i] = ImageLoader.loadImage("/resources/buttons/Highscores "+(i+1)+".bmp");
			btn_exit[i] = ImageLoader.loadImage("/resources/buttons/Exit "+(i+1)+".bmp");
			btn_credits[i] = ImageLoader.loadImage("/resources/buttons/Credits "+(i+1)+".bmp");
			btn_blank[i] = ImageLoader.loadImage("/resources/buttons/button blank "+(i+1)+".bmp");
		}
		
		SpriteSheet chestSprite = new SpriteSheet(ImageLoader.loadImage("/resources/Chest.png"));
		
		chest = new BufferedImage[2];
		chest[0] = chestSprite.crop(0, 0, 32, 32);
		chest[1] = chestSprite.crop(32, 0, 32, 32);
		
		player_down = new BufferedImage[8];
		player_up = new BufferedImage[8];
		player_left = new BufferedImage[8];
		player_right = new BufferedImage[8];
		player_down_hood = new BufferedImage[8];
		player_up_hood = new BufferedImage[8];
		player_left_hood = new BufferedImage[8];
		player_right_hood = new BufferedImage[8];
		SpriteSheet playerSpriteWithoutHood=new SpriteSheet(ImageLoader.loadImage("/resources/player/Player.png"));
		SpriteSheet playerSpriteWithHood=new SpriteSheet(ImageLoader.loadImage("/resources/player/Player1.png"));
		
		for(int i = 1; i<9; i++){
			player_up[i-1] = playerSpriteWithoutHood.crop((width*2)*i, (height*2)*8, width*2, height*2);
			player_left[i-1] = playerSpriteWithoutHood.crop((width*2)*i, (height*2)*9, width*2, height*2);
			player_down[i-1] = playerSpriteWithoutHood.crop((width*2)*i, (height*2)*10, width*2, height*2);
			player_right[i-1] = playerSpriteWithoutHood.crop((width*2)*i, (height*2)*11, width*2, height*2);
			player_up_hood[i-1] = playerSpriteWithHood.crop((width*2)*i, (height*2)*8, width*2, height*2);
			player_left_hood[i-1] = playerSpriteWithHood.crop((width*2)*i, (height*2)*9, width*2, height*2);
			player_down_hood[i-1] = playerSpriteWithHood.crop((width*2)*i, (height*2)*10, width*2, height*2);
			player_right_hood[i-1] = playerSpriteWithHood.crop((width*2)*i, (height*2)*11, width*2, height*2);
		}
		playerAward = playerSpriteWithoutHood.crop((width*2)*2, (height*2)*20, width*2, height*2);


		
		player_attack_down = new BufferedImage[6];
		player_attack_up = new BufferedImage[6];
		player_attack_left = new BufferedImage[6];
		player_attack_right = new BufferedImage[6];
		player_attack_down_hood = new BufferedImage[6];
		player_attack_up_hood = new BufferedImage[6];
		player_attack_left_hood = new BufferedImage[6];
		player_attack_right_hood = new BufferedImage[6];
		
		
		for(int i = 1; i<6; i++){
			player_attack_up[i-1] = playerSpriteWithoutHood.crop((width*2)*i, (height*2)*12, width*2, height*2);
			player_attack_left[i-1] = playerSpriteWithoutHood.crop((width*2)*i, (height*2)*13, width*2, height*2);
			player_attack_down[i-1] = playerSpriteWithoutHood.crop((width*2)*i, (height*2)*14, width*2, height*2);
			player_attack_right[i-1] = playerSpriteWithoutHood.crop((width*2)*i, (height*2)*15, width*2, height*2);
			player_attack_up_hood[i-1] = playerSpriteWithHood.crop((width*2)*i, (height*2)*12, width*2, height*2);
			player_attack_left_hood[i-1] = playerSpriteWithHood.crop((width*2)*i, (height*2)*13, width*2, height*2);
			player_attack_down_hood[i-1] = playerSpriteWithHood.crop((width*2)*i, (height*2)*14, width*2, height*2);
			player_attack_right_hood[i-1] = playerSpriteWithHood.crop((width*2)*i, (height*2)*15, width*2, height*2);
			
		}		
		
		zombie_down = new BufferedImage[2];
		zombie_up = new BufferedImage[2];
		zombie_left = new BufferedImage[2];
		zombie_right = new BufferedImage[2];
		
		zombie_down[0] = sheet.crop(width * 4, height * 2, width, height);
		zombie_down[1] = sheet.crop(width * 5, height * 2, width, height);
		zombie_up[0] = sheet.crop(width * 6, height * 2, width, height);
		zombie_up[1] = sheet.crop(width * 7, height * 2, width, height);
		zombie_right[0] = sheet.crop(width * 4, height * 3, width, height);
		zombie_right[1] = sheet.crop(width * 5, height * 3, width, height);
		zombie_left[0] = sheet.crop(width * 6, height * 3, width, height);
		zombie_left[1] = sheet.crop(width * 7, height * 3, width, height);
		
		flameThrower = new BufferedImage[4];
		for(int i = 0;i<4;i++){
			flameThrower[i]=ImageLoader.loadImage("/resources/flameThrower/flamethrower"+(i+1)+".png");
		}
		
	    		
		signExit = ImageLoader.loadImage("/resources/Sign/signExit.png");
		dirt = tiles.crop(0, height*2, width, height);
		
			
	}
}
