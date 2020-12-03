package mytunes.gui.controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import mytunes.be.Song;
import mytunes.gui.model.MusicPlayer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MusicPlayerController implements Initializable {

    private MusicPlayer musicPlayer = new MusicPlayer();
    private ObservableList<Song> allSongs;
    private Song song;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Text nowPlaying;


    public MusicPlayerController() {
        volumeSlider = new Slider(0.0,1.0,0.5);
        allSongs = musicPlayer.getSongList();
        song = new Song(1,"bla","blabla","pop","src/Music/song2.wav");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                musicPlayer.setVolume(volumeSlider.getValue());
            }
        });

        try {
            musicPlayer.loadMedia(song);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Sets the volume based on the slider value
     * plays the song and sets the text nowPlaying to the currently playing song
     */
    public void play(ActionEvent actionEvent) {
        musicPlayer.setVolume(volumeSlider.getValue());
        musicPlayer.play();
        nowPlaying.setText(musicPlayer.getCurrentlyPlaying());
    }
}
