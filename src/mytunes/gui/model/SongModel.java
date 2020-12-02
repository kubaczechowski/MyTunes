package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Song;
import mytunes.bll.SongManager;

import java.util.ArrayList;
import java.util.List;

public class SongModel {

    private SongManager songManager;
    private ObservableList<Song> songs;

    public SongModel() {
        songManager = new SongManager();
        songs = FXCollections.observableArrayList();
        songs.addAll(songManager.getAllSongs());
    }

    public ObservableList<Song> getAllSongs() {
        return songs;
    }

    public ObservableList<Song> searchSongs(String text) {
        List<Song> searchedSongs = new ArrayList<>();
        for(Song song : songs) {
            if(song.getTitle().contains(text) || song.getArtist().contains(text)) searchedSongs.add(song);
        }
        return FXCollections.observableArrayList(searchedSongs);
    }

}
