package jewelhunter.audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.sound.sampled.*;

public class Sound {
	
	private static HashMap<String, Clip> clips;
	public static void init() {
		clips = new HashMap<>();
	}
	public static void loadAudio(String id, String url) {
		Clip clip;
		try {
			InputStream in = Sound.class.getResourceAsStream(url);
			InputStream bin = new BufferedInputStream(in);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bin);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			clips.put(id, clip);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
	}
	public static void playAudio(String id) {
		Clip c = clips.get(id);
		if(c == null) return;
		if(c.isRunning()) c.stop();
		c.setFramePosition(0);
		while(!c.isRunning()) c.start();
	}

	public static void loopAudio(String id, boolean loop) {
		if (loop) {
			clips.get(id).loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			clips.get(id).loop(0);
		}
	}

	public static void unloadAudio(String id) {
		clips.remove(id);
	}
	
	public static void setVolume(String id, float f) {
		Clip c = clips.get(id);
		if(c == null) return;
		FloatControl vol = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		vol.setValue(f);
	}
	public static void stop(String s) {
		Clip clip = clips.get(s);
		if(clip.isRunning())
			clip.stop();
	}
	public static void muteAll(){
		for (Entry<String, Clip> clip : clips.entrySet())
		{
			BooleanControl bc = (BooleanControl) clip.getValue().getControl(BooleanControl.Type.MUTE);
			bc.setValue(true); 
		}
	}
	public static void unMuteAll(){
		for (Entry<String, Clip> clip : clips.entrySet())
		{
			BooleanControl bc = (BooleanControl) clip.getValue().getControl(BooleanControl.Type.MUTE);
			bc.setValue(false); 
		}
	}
}
