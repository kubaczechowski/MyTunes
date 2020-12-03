/**
 * @author kjell
 */

package mytunes.gui.model;

<<<<<<< Updated upstream
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
=======
import mytunes.be.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mytunes.bll.SongManager;
import mytunes.bll.exeption.BLLexception;

import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class MusicPlayer {

    private SongManager songManager;
    private String currentlyPlaying;
    private Media media;
    private MediaPlayer audioPlayer;
    private Song song;
    private Path filePath;
    private ObservableList<Song> songList;

    public MusicPlayer() {
        songManager = new SongManager();
        songList = FXCollections.observableArrayList();
        try {
            songList.addAll(songManager.getAllSongs());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    /**
     * @return list of all songs
     */
    public ObservableList<Song> getSongList() {
        return songList;
    }
>>>>>>> Stashed changes

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