/**
 * @author kjell
 */

package mytunes.gui.model;

import mytunes.be.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;


public class MusicPlayer {
    private MediaPlayer audioPlayer;
    private Song song;
    private List<Song> songList;


    // Starts the media player
    public void play() {
        audioPlayer.play();
    }

    // Pauses the media player
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
     * Loads music file into the media player by it's path
     * @param song song to be loaded
     * @throws MalformedURLException
     */
    public void loadMedia(Song song) throws MalformedURLException {
        this.song = song;

        // Disposes the old media player instance to avoid multiple songs playing
        if (audioPlayer != null) {
            audioPlayer.dispose();
        }

        try {
            Path filePath = FileSystems.getDefault().getPath(song.getFilePath());
            Media media = new Media(filePath.toUri().toURL().toExternalForm());
            audioPlayer = new MediaPlayer(media);
        } catch (Exception e) {
            System.out.println("Unable to load song file");
        }

    /**
     * Updates the current playing song and returns it.
     * @return the current playing song
     */
    public Song getCurrentlyPlaying() {
        setCurrentlyPlaying();
        return currentlyPlaying;
    }

    // Sets the songList
    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    /**
     * Sets the volume based a sliders value
     * @param sliderValue value of slider
     */
    public void setVolume(double sliderValue) {
        audioPlayer.setVolume(sliderValue);
    }

    // Returns the current song
    public Song getSong() {
        return song;
    }

    /**
     * If the index iterator has reached the size of the list, it returns the first index of the songList.
     * Else it returns the next index of songList.
     * @return index of next song
     */
    public Song getNextSongInList() {
        if (songList.indexOf(song) == songList.size()-1) {
            return songList.get(0);
        }
        return songList.get(songList.indexOf(song) + 1);
    }

    public MediaPlayer getAudioPlayer() {
        return audioPlayer;
    }

    /**
     * If the index iterator has reached the first index in the list, it returns the last index of the songList.
     * Else it returns the previous index of songList
     * @return index of previous song
     */
    public Song getPreviousSongInList() {
        if (songList.indexOf(song) == 0) {
            return songList.get(songList.size() - 1);
        }
        return songList.get(songList.indexOf(song) - 1);
    }

}