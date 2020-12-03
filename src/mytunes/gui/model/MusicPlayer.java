/**
 * @author kjell
 */

package mytunes.gui.model;

import mytunes.be.Song;
import mytunes.bll.MusicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class MusicPlayer {

    private MusicManager musicManager;
    private String currentlyPlaying;
    private Media media;
    private MediaPlayer audioPlayer;
    private Song song;
    private Path filePath;
    private ObservableList<Song> songList;

    public MusicPlayer() {
        musicManager = new MusicManager();
        songList = FXCollections.observableArrayList();
        songList.addAll(musicManager.getAllSongs());
    }

    /**
     * @return list of all songs
     */
    public ObservableList<Song> getSongList() {
        return songList;
    }

    /**
     * Checks if song is paused, if it is, then play.
     */
    public void play(){
        if (audioPlayer != null){
            if (isPaused()) {
                audioPlayer.play();
            } else {
                audioPlayer.pause();
            }
        }
    }

    /**
     * Returns true if audio player is paused
     * @return boolean
     */
    public boolean isPaused() {
        return audioPlayer.getStatus() != MediaPlayer.Status.PLAYING;
    }

    /**
     * Loads music file into the audio player by it's path
     * @param song song to be loaded
     * @throws MalformedURLException
     */
    public void loadMedia(Song song) throws MalformedURLException {
        this.song = song;
        filePath = FileSystems.getDefault().getPath(song.getFilePath());
        media = new Media(filePath.toUri().toURL().toExternalForm());
       // System.out.println(audioPath.toUri().toURL().toExternalForm());
        audioPlayer = new MediaPlayer(media);
        currentlyPlaying = song.getTitle();
    }

    /**
     * Sets the current playing song based on the songs title
     * and if paused, it sets the string to paused.
     */
    public void setCurrentlyPlaying() {
        if (!isPaused()) {
            currentlyPlaying = "Paused";
        } else {
            currentlyPlaying = song.getTitle();
        }
    }

    /**
     * Updates the current playing song and returns it.
     * @return the current playing song
     */
    public String getCurrentlyPlaying() {
        setCurrentlyPlaying();
        return currentlyPlaying;
    }

    /**
     * Sets the volume based on the volume slider value
     * @param sliderValue value of slider
     */
    public void setVolume(double sliderValue) {
        audioPlayer.setVolume(sliderValue);
    }
}

//    /**
//     * Checks if file is an mp3 or wav audio file.
//     * @param name song name in directory
//     * @return boolean
//     */
//    public boolean acceptFile(String name) {
//        return name.endsWith(".wav") || name.endsWith(".mp3");
//    }

// Convert from multiple decimals to 2.
//AudioFormat format = audioInput.getFormat();
//double frames = audioInput.getFrameLength();
//double durationInSeconds = (frames+0.0) / format.getFrameRate();
//double durationInMinutes = (durationInSeconds/60);
//DecimalFormat dF = new DecimalFormat("#.##");
//System.out.println("Seconds: " + dF.format(durationInSeconds));
//System.out.println("Minutes: " + dF.format(durationInMinutes));
