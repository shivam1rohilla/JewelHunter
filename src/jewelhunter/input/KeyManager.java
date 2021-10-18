package jewelhunter.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private boolean[] keys,justPressed, cantPressed;
	public boolean up,down,left,right,aUp,aDown,aLeft,aRight,endk,escape,r,enter,c;
	
	public KeyManager() {
		keys = new boolean[256];
		justPressed = new boolean[keys.length];
		cantPressed = new boolean[keys.length];
	}
	
	public void tick(){
		
		for(int i=0; i<keys.length;i++){
			if(cantPressed[i] && !keys[i]){
				cantPressed[i] = false;
			}else if (justPressed[i]) {
				cantPressed[i] = true;
				justPressed[i] = false;
			}
			if(!cantPressed[i] && keys[i]){
				justPressed[i] = true;
			}
			
		}
		
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		aUp = keys[KeyEvent.VK_W];
		aDown = keys[KeyEvent.VK_S];
		aLeft = keys[KeyEvent.VK_A];
		aRight = keys[KeyEvent.VK_D];
		endk = keys[KeyEvent.VK_END];
		escape=keys[KeyEvent.VK_ESCAPE];
		r=keys[KeyEvent.VK_R];
		enter=keys[KeyEvent.VK_ENTER];
		c=keys[KeyEvent.VK_C];
	}
	
	public boolean keyJustPressed(int keyCode) {
		if(keyCode < 0 || keyCode >= keys.length)
			return false;
		return justPressed[keyCode];
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()<0 || arg0.getKeyCode() >=keys.length)
			return;
		keys[arg0.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode()<0 || arg0.getKeyCode() >=keys.length)
			return;
		keys[arg0.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	

}
