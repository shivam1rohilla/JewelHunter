package jewelhunter.display;
import java.awt.Canvas;
import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Display {
	
	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private int width,height;
	
	public Display(String title,int width, int height){
		this.title=title;
		this.width=width;
		this.height=height;
		
		createDisplay();
	}
	private void createDisplay(){
		frame=new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			frame.setIconImage(ImageIO.read(getClass().getResourceAsStream("/resources/menuscreen.gif")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
	}
	public Canvas getCanvas(){
		return canvas;
	}
	public JFrame getFrame() {
		return frame;
	}
}
