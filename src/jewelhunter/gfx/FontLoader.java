package jewelhunter.gfx;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
	
	public static Font loadFont(String path, float size){
		return loadFont(path, size, 0);		
	}
	public static Font loadFont(String path, float size,int type){
		
		try {
			
			if(path.startsWith("path")) {
				path="resources/"+path;
			}
			InputStream in = (InputStream) Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			
			if(type == 0)
				return Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(Font.PLAIN, size);
			else if(type == 1)
				return Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(Font.BOLD, size);
			else if(type == 1)
				return Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(Font.ITALIC, size);
			else if(type == 2)
				return Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(Font.CENTER_BASELINE, size);
			else if(type == 3)
				return Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(Font.HANGING_BASELINE, size);
				
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			System.exit(1);		
		}
		
		return null;
		
	}

}
