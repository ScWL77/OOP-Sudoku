package sudoku;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class music {
	
	private Clip clip;
	private long clipTimePosition;
	
	void playMusic(String musicFile) {
		try {
			File musicPath = new File(musicFile);
			
			if(musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				
			}else {
				System.out.println("Can't find file");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void pauseMusic() {
		if(clip!=null && clip.isRunning()) {
			clipTimePosition = clip.getMicrosecondPosition();
			clip.stop();
		}
	}
	
	public void resumeMusic() {
		if (clip != null && !clip.isRunning()) {
            clip.setMicrosecondPosition(clipTimePosition);
            clip.start();
        }
	}
	
}
