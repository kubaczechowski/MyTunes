/**
 * @author kjell
 */

package mytunes.gui.model;


import mytunes.be.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mytunes.bll.BLLFacade;
import mytunes.bll.BLLcontroller;

import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class MusicPlayer {

    private BLLFacade bllFacade;
    private Song currentlyPlaying;
    private Media media;
    private MediaPlayer audioPlayer;
    private Song song;
    private Path filePath;
    private ObservableList<Song> songList;

    public MusicPlayer() {
        bllFacade = new BLLcontroller();
        songList = FXCollections.observableArrayList();
    }

    /**
     * Checks if song is paused, if it is, then play.
     */
    public void play() {
        audioPlayer.play();
    }

    public void pause() {
        audioPlayer.pause();
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
        currentlyPlaying = song;
    }

    /**
     * Sets the current playing song based on the songs title
     * and if paused, it sets the string to paused.
     */
    public void setCurrentlyPlaying() {
        if (!isPaused()) {
            currentlyPlaying.setTitle("Paused");
        } else {
            currentlyPlaying = song;
        }
    }

    /**
     * Updates the current playing song and returns it.
     * @return the current playing song
     */
    public Song getCurrentlyPlaying() {
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

    public Song getSong() {
        return song;
    }


    public MediaPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public boolean isOver() {
        return audioPlayer.onEndOfMediaProperty().isBound();
    }
}