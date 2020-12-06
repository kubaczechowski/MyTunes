package mytunes.be;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private int id;
    private String name;
    private List<Song> songs;
    private int numberOfSongs;
    private int totalPlaytime; // result in seconds

    public Playlist(int id, String name, List<Song> songs, int numberOfSongs, int totalPlaytime) {
        this.id = id;
        this.name = name;
        this.songs = songs;
        this.numberOfSongs = numberOfSongs;
        this.totalPlaytime = totalPlaytime;
    }

    /**
     * I don't know what this method does
     * @return
     */
    public ObservableList<Song> getSongs() {
        return FXCollections.observableArrayList(songs);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }

    public int getTotalPlaytime() {
        return totalPlaytime;
    }

    public void setTotalPlaytime(int totalPlaytime) {
        this.totalPlaytime = totalPlaytime;
    }
}


