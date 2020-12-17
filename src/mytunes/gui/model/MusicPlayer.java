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
import java.util.List;


public class MusicPlayer {

    private BLLFacade bllFacade;
    private Song currentlyPlaying;
    private Media media;
    private MediaPlayer audioPlayer;
    private Song song;
    private Path filePath;
    private List<Song> songList;

    public MusicPlayer() {
        bllFacade = new BLLcontroller();
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

        if (audioPlayer != null) {
            audioPlayer.dispose();
        }

        filePath = FileSystems.getDefault().getPath(song.getFilePath());
        media = new Media(filePath.toUri().toURL().toExternalForm());
       // System.out.println(audioPath.toUri().toURL().toExternalForm());
        audioPlayer = new MediaPlayer(media);
        setCurrentlyPlaying();
    }

    /**
     * Sets the current playing song based on the songs title
     * and if paused, it sets the string to paused.
     */
    public void setCurrentlyPlaying() {
        currentlyPlaying = song;
    }

    /**
     * Updates the current playing song and returns it.
     * @return the current playing song
     */
    public Song getCurrentlyPlaying() {
        setCurrentlyPlaying();
        return currentlyPlaying;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public List<Song> getSongList() {
        return songList;
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

    public Song getNextSongInList() {
        if (songList.indexOf(song) == songList.size()-1) {
            return songList.get(0);
        }
        return songList.get(songList.indexOf(song) + 1);
    }

    public Song getPreviousSongInList() {
        if (songList.indexOf(song) == 0) {
            return songList.get(songList.size() - 1);
        }
        return songList.get(songList.indexOf(song) - 1);
    }
}