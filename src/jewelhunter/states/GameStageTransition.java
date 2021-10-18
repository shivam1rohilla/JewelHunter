package jewelhunter.states;

import java.awt.Color;
import java.awt.Graphics;

import jewelhunter.Handler;
import jewelhunter.gfx.Animation;
import jewelhunter.gfx.Assets;
import jewelhunter.gfx.Text;
import jewelhunter.item.Item;

public class GameStageTransition extends State {

	private int ticks;
	private boolean perfect,pCheck;
	int upX,upY,width, height,aX,aY;
	
	private Animation crystAnim,crystgreyAnim,crystgreenAnim;
	
	private String s,s1;
	
	public GameStageTransition(Handler handler,GameStateManager gsm) {
		super(handler,gsm);
		
		crystAnim = new Animation(100, Assets.cryst);
		crystgreyAnim = new Animation(100, Assets.cryst_grey);
		crystgreenAnim = new Animation(100, Assets.cryst_green);
		aX = 80;
		aY = 80;
		width = 500;
		height= 380;
	}
	@Override
	public void init() {
	}

	@Override
	public void tick() {
		crystAnim.tick();
		crystgreyAnim.tick();
		crystgreenAnim.tick();
		
		if((height-2*upY)!=50)
			upY++;
		if((width-2*upX)!=100)
			upX++;
		
		if(gsm.stage==GameStateManager.STAGE1TO2){
			stage1To2Tick();
		}
		if(gsm.stage==GameStateManager.STAGE2TO3){
			stage2To3Tick();
		}
		if(gsm.stage==GameStateManager.STAGE3TO4){
			stage3To4Tick();
		}
		if(handler.getKeyManager().escape&&ticks>550){
			ticks=1000;
		}
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(Assets.back, 0, 0,640,480, null);
		g.setColor(new Color(0, 0, 0 , 200));
		g.fillRoundRect(5, 5, handler.getWidth()-10, handler.getHeight()-10, 15, 15);
		if(handler.getWorld()!=null)
		if(handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.crystgreenItem)%10==0&&
				handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.crystgreyItem)%10==0&&
					handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.crystItem)%10==0&&!pCheck){
			perfect=true;
			handler.getWorld().getEntityManager().getPlayer().oneUpPlayerHealth(1);
			pCheck=true;
		}
		
		if(gsm.stage==GameStateManager.STAGE1TO2){
			if(ticks<500){
				if(handler.getWorld()!=null)
				Text.drawString(g, handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.compass)+"", 400, 400, true, Color.WHITE, Assets.bloc_r);
				g.drawImage(Assets.compasspick, 500, 360, 64, 85, null);
			}
		}
		
		if(ticks<500){
			Text.drawString(g, s+"", handler.getWidth()/2, 40, true, Color.LIGHT_GRAY, Assets.bloc_r);
			g.drawImage(crystAnim.getCurrentFrame(), 200, 160, 64, 85, null);
			g.drawImage(crystgreyAnim.getCurrentFrame(), 500, 160, 64, 85, null);
			g.drawImage(crystgreenAnim.getCurrentFrame(), 200, 360, 64, 85, null);
			Text.drawString(g, "Complete", handler.getWidth()/2, 115, true, Color.LIGHT_GRAY, Assets.bloc_r);
			if(handler.getWorld()!=null){
				Text.drawString(g, ""+handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.crystItem), 100, 200, true, Color.WHITE, Assets.bloc_r);
				Text.drawString(g, handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.crystgreyItem)+"", 400, 200, true, Color.WHITE, Assets.bloc_r);
				Text.drawString(g, handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(Item.crystgreenItem)+"", 100, 400, true, Color.WHITE, Assets.bloc_r);
			}
			if(perfect)
			g.drawImage(Assets.oneUp, aX+upX, aY+upY, width-2*upX, height-2*upY, null);
		}
		if(ticks>500){
			Text.drawString(g, s1+"...", handler.getWidth()/2, handler.getHeight()/2-150, true, Color.LIGHT_GRAY, Assets.bloc_r);
			if(gsm.stage==GameStateManager.STAGE3TO4)
				g.drawImage(Assets.player_right_hood[1], handler.getWidth()/2-130, handler.getHeight()/2-40, 80, 80,null);
			else
				g.drawImage(Assets.player_right[1], handler.getWidth()/2-130, handler.getHeight()/2-40, 80, 80,null);
			g.drawImage(Assets.player_right[1], handler.getWidth()/2-130, handler.getHeight()/2-40, 80, 80,null);
			Text.drawString(g, "X", handler.getWidth()/2, handler.getHeight()/2, true, Color.WHITE, Assets.head72);
			if(handler.getWorld()!=null)
				Text.drawString(g, handler.getWorld().getEntityManager().getPlayer().getPlayerHealth()+"", handler.getWidth()/2+100, handler.getHeight()/2, true, Color.WHITE, Assets.bloc_r);
		}
		
	}

	private void stage1To2Tick(){
		ticks++;
		if(ticks>=1000)
			gsm.setState(GameStateManager.STAGE2);
		s="Stage 1";
		s1="Stage 2";
	}
	private void stage2To3Tick(){
		ticks++;
		if(ticks>=1000)
			gsm.setState(GameStateManager.STAGE3);
		s="Stage 2";
		s1="Stage 3";
	}
	private void stage3To4Tick(){
		ticks++;
		if(ticks>=1000)
			gsm.setState(GameStateManager.STAGE4);
		s="Stage 3";
		s1="Stage 4";
	}
	
	
}
