package jewelhunter;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import jewelhunter.audio.Sound;
import jewelhunter.display.Display;
import jewelhunter.gfx.GameCamera;
import jewelhunter.input.KeyManager;
import jewelhunter.input.MouseManager;
import jewelhunter.states.GameStateManager;


public class Game implements Runnable {
	private Display display;
	private int width, height;
	public String title;
	private Thread thread;
	private boolean running = false;
	
	private BufferStrategy bs;
	private Graphics g;
		
	private GameStateManager gsm;
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private GameCamera gameCamera;
	private Handler handler;
	private boolean mute;
		
	public Game(String title,int width,int height){
		this.width=width;
		this.height=height;
		this.title=title;		
		keyManager = new KeyManager() ;
		mouseManager=new MouseManager();
	}
	private void init(){
		display = new Display(title, width, height);
		handler = new Handler(this);
		gsm = new GameStateManager(handler);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		gameCamera=new GameCamera(handler,0, 0);
		
	}
	
	private void tick(){
		keyManager.tick();
		gsm.tick();
	}
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		gsm.render(g);
		bs.show();
		//g.dispose();
	}
	public void run(){
		init();
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		@SuppressWarnings("unused")
		int ticks = 0;
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime)/timePerTick;
			timer +=now-lastTime;
			lastTime = now;
			
			if(delta >=1){
				tick();
				render();
				ticks ++;
				delta--;
			}	
			if(timer >=1000000000){
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	}
	
	public synchronized void start(){
		if(running)
			return;
		running=true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running =false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public KeyManager getKeyManager(){
		return keyManager;
	}
	public MouseManager getMouseManger() {
		return mouseManager;
	}
	public GameCamera getGameCamera() {
		return gameCamera;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public GameStateManager getGameStateManger() {
		return gsm;
	}
	public boolean isMute() {
		return mute;
	}
	public void setMute(boolean mute) {
		this.mute = mute;
		if(this.mute){
			Sound.muteAll();
		}
		else if(!this.mute){
			Sound.unMuteAll();
		}
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
