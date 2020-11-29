/**
 * @author kjell
 */

package sample.gui.model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicPlayer {

    private AudioInputStream audioInput;
    private Clip clip;
    private File musicPath;

    // Starts playing the music
    public void play(String path) {
        try {
            musicPath = new File(path);

            if(musicPath.exists()) {
                audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Can't find file");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Stops playing the music
    public void stop() {
        clip.stop();
    }
}