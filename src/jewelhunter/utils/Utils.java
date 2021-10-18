package jewelhunter.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
	
	public static String loadFileAsString(String path){
		StringBuilder builder = new StringBuilder();
		InputStream in = null;
		try{
			if(path.startsWith("worlds")) {
				path="resources/"+path;
			}
			else if(path.startsWith("Credits")) {
				path="resources/"+path;
			}
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			
			BufferedReader br= new BufferedReader(new InputStreamReader(in));
			String line;
			while((line=br.readLine())!=null)
				builder.append(line+"\n");
			br.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	public static int parseInt(String number){
		try{
			return Integer.parseInt(number);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
		
	}
	
}
