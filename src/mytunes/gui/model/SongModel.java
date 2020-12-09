package mytunes.gui.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Song;
import mytunes.bll.SongManager;
import mytunes.bll.exeption.BLLexception;

import java.net.URL;


import java.util.ArrayList;
import java.util.List;

public class SongModel {

    private static SongModel songModelInstance;

    private SongManager songManager;
    private ObservableList<Song> songs;

    public SongModel() {
        songManager = new SongManager();
        songs = FXCollections.observableArrayList();
/*
        try {
            songs.addAll(songManager.getAllSongs());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }

 */

    }

    public static SongModel createOrGetInstance()
    {
        if( songModelInstance == null)
        {
            songModelInstance = new SongModel();
        }
        return songModelInstance;

    }

    public void load()
    {
        try {
            songs.addAll(songManager.getAllSongs());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }

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

    public int getSongTime(String mediaStringUrl) {
       return songManager.getSongTime(mediaStringUrl);

    }

    public void save(Song song) {
        try {

            songManager.save(song);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        songs.add(song);
    }


    public void delete(Song songToBeDeleted) {
        try {

            songManager.delete(songToBeDeleted);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        songs.remove(songToBeDeleted);
    }

    public void update(Song song) {
        try {
            songManager.update(song);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        songs.remove(song);
        songs.add(song);
    }
}
