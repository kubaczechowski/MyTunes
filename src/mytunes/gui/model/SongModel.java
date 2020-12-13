package mytunes.gui.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Song;
import mytunes.bll.SongManager;
import mytunes.bll.exeption.BLLexception;

import java.net.URL;


import java.util.ArrayList;
import java.util.List;

/**
 * Class is responsible for one directional communication with
 * Business logic layer. Also it provides Observable list that
 * is used in the TableView Songs.
 * @author kuba @ kamila potasiak
 */
public class SongModel {

    private static SongModel songModelInstance;

    private SongManager songManager;
    private ObservableList<Song> songs;

    public SongModel() {
        songManager = new SongManager();
        songs = FXCollections.observableArrayList();
    }

    /**
     * method used to create instance. when created in this
     * way we ensure that we only use one instance when its needed
     * @return SongModel
     */
    public static SongModel createOrGetInstance()
    {
        if( songModelInstance == null)
        {
            songModelInstance = new SongModel();
        }
        return songModelInstance;

    }

    /**
     * method loads all playlist objects into observable list
     */
    public void load()
    {
        try {
            songs.clear();
            songs.addAll(songManager.getAllSongs());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }

    }

    /**
     * searching funcionality in the TableView Songs
     * @param text
     * @return ObservableList<Song>
     * @author kamila
     */
    // meybe we should move searching funcitnoality to BLL?
    //here its extremely simple
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

    //Below are methods for crud operations

    public ObservableList<Song> getAllSongs() {
        return songs;
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
            load();
           //updateSong(song);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
       // songs.remove(song);
       /* songs.add(new Song(song.getId(), song.getTitle(),
                song.getArtist(), song.getCategory(), song.getPlaytime(),
                song.getFilePath()));

        */
    }

   /* private void updateSong(Song song) {
        int index = songs.indexOf(song);
        songs.set(index, new Song(song.getId(), song.getTitle(),
                song.getArtist(), song.getCategory(), song.getPlaytime(),
                song.getFilePath()));

    }

    */
}
