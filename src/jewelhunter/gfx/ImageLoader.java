package jewelhunter.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {
	public static BufferedImage loadImage(String path){
		try {
			InputStream in = ImageLoader.class.getResourceAsStream(path);
			return ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
