package mytunes.gui.model;

import mytunes.be.Song;
import mytunes.bll.BLLcontroller;
import mytunes.bll.SongManager;

import java.net.URL;

public class SongModel {
<<<<<<< Updated upstream
    private SongManager songManager;

    public SongModel()
    {
        this.songManager = new SongManager();
=======


    private BLLcontroller songAccessBLL;
    private ObservableList<Song> songsObservableList;

    public SongModel() {
       songAccessBLL = new BLLcontroller();
        songsObservableList = FXCollections.observableArrayList();
        try {
            songsObservableList.addAll(songAccessBLL.getAllSongs());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }
    public void loadSongs()
    {
        try {
            songsObservableList.addAll(songAccessBLL.getAllSongs());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public ObservableList<Song> getAllSongs() {
        return songsObservableList;
    }

    public ObservableList<Song> searchSongs(String text) {
        List<Song> searchedSongs = new ArrayList<>();
        for(Song song : songsObservableList) {
            if(song.getTitle().contains(text) || song.getArtist().contains(text)) searchedSongs.add(song);
        }
        return FXCollections.observableArrayList(searchedSongs);
>>>>>>> Stashed changes
    }

    public int getSongTime(String mediaStringUrl) {
       return songAccessBLL.getSongTime(mediaStringUrl);

    }

    public void save(Song song) {
<<<<<<< Updated upstream
        songManager.save(song);
=======
        try {
            songAccessBLL.save(song);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
>>>>>>> Stashed changes
    }
<<<<<<< Updated upstream
=======

    public void delete(Song songToBeDeleted) {
<<<<<<< Updated upstream
        songManager.delete(songToBeDeleted);
=======
        try {
            songAccessBLL.delete(songToBeDeleted);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
>>>>>>> Stashed changes
    }
>>>>>>> Stashed changes
}
