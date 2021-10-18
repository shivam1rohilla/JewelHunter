
package jewelhunter.states;

import java.awt.Graphics;

import jewelhunter.Handler;

public class GameStateManager {
	
	private boolean paused;
	private PauseState pauseState;
	private State[] states;
	private int currentState;
	private int previousState;
	public int min,sec;
	
	public int stage;
	public static final int STAGE1TO2=1,STAGE2TO3=2,STAGE3TO4=3,STAGE4TOOVER=4;
	
	public static final int NUM_STATES = 13;
	public static final int INTRO = 0;
	public static final int MENU = 1;
	public static final int STAGE1 = 2;
	public static final int GAMEOVER = 6;
	public static final int GAMESTART = 7;
	public static final int GAMETRANSITION = 8;
	public static final int GAMECOMPLETE = 9;
	public static final int EXIT = 3;
	public static final int CREDITS = 4;
	public static final int STAGE2 = 5;
	public static final int STAGE3 = 10;
	public static final int STAGE4 = 11;
	public static final int CONTROLS = 12;
	protected Handler handler;
	
	public GameStateManager(Handler handler) {
		this.handler = handler; 
		paused = false;
		pauseState = new PauseState(handler,this);		
		states = new State[NUM_STATES];
		
		setState(INTRO);
		
	}
	
	public void setState(int i) {
		previousState = currentState;
		if(previousState==STAGE1){
			stage=STAGE1TO2;
		}if(previousState==STAGE2){
			stage=STAGE2TO3;
		}if(previousState==STAGE3){
			stage=STAGE3TO4;
		}
		unloadState(previousState);
		currentState = i;
		if(i == INTRO) {
			states[i] = new IntroState(handler,this);
			states[i].init();
		}
		else if(i == MENU) {
			states[i] = new MenuState(handler,this);
			states[i].init();
		}
		else if(i == STAGE1) {
			states[i] = new GameStateStage1(handler,this);
			states[i].init();		
		}
		else if(i == STAGE2) {
			states[i] = new GameStateStage2(handler,this);
			states[i].init();		
		}
		else if(i == STAGE3) {
			states[i] = new GameStateStage3(handler,this);
			states[i].init();		
		}
		else if(i == STAGE4) {
			states[i] = new GameStateStage4(handler,this);
			states[i].init();		
		}
		else if(i == EXIT) {
			states[i] = new ExitState(handler,this);
			states[i].init();		
		}
		else if(i == CREDITS) {
			states[i] = new CreditsState(handler,this);
			states[i].init();		
		}
		else if(i == GAMEOVER) {
			states[i] = new GameOver(handler,this);
			states[i].init();		
		}
		else if(i == GAMETRANSITION) {
			states[i] = new GameStageTransition(handler,this);
			states[i].init();		
		}
		else if(i == GAMECOMPLETE) {
			states[i] = new GameComplete(handler,this);
			states[i].init();		
		}
		else if(i == GAMESTART) {
			states[i] = new GameStart(handler,this);
			states[i].init();		
		}
		else if(i == CONTROLS) {
			states[i] = new ControlsState(handler,this);
			states[i].init();		
		}
	}
	
	public void unloadState(int i) {
		states[i] = null;
	}
	
	public void setPaused(boolean b) {
		paused = b;
	}
	
	public void tick() {
		if(sec>=60){
			min++;
			sec=sec%60;
		}
		if(paused) {
			pauseState.tick();
		}
		else if(states[currentState] != null) {
			states[currentState].tick();
		}
	}
	
	public void render(Graphics g) {
		if(paused) {
			pauseState.render(g);
		}
		else if(states[currentState] != null) {
			states[currentState].render(g);
		}
	}

	public State getState() {
		return states[currentState];
	}
}
