package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.BLLFacade;
import mytunes.bll.BLLcontroller;
import mytunes.bll.PlaylistManager;
import mytunes.bll.exeption.BLLexception;
import mytunes.bll.util.TimeConverter;

/**
 * Class plays important role in creating a TableView Playlists'
 * objects. Class is responsible for one directional communication with
 * Business logic layer
 */
public class PlaylistModel {

    private PlaylistManager playlistManager;// class from BLL
    private ObservableList<Playlist> playlists;
    private static PlaylistModel playlistModelInstance;
    private TimeConverter timeConverter;
    private PlaylistItemModel playlistItemModel;

    public PlaylistModel() {
        playlistManager = new PlaylistManager();
        playlists = FXCollections.observableArrayList();
        timeConverter = new TimeConverter();
        //playlistItemModel = PlaylistItemModel.createOrGetInstance();
        playlistItemModel = new PlaylistItemModel();
    }

    /**
     * method used to create instance. when created in this
     * way we ensure that we only use one instance when its needed
     *
     * @return PlaylistModel
     */
    public static PlaylistModel createOrGetInstance() {
        if (playlistModelInstance == null) {
            playlistModelInstance = new PlaylistModel();
        }
        return playlistModelInstance;
    }

    public String convertToString(int timeInMilis) {
        return timeConverter.convertToString(timeInMilis);
    }

    /**
     * method loads all playlist objects into observable list
     */
    public void load() {
        try {
            playlists.clear();
            playlists.addAll(playlistManager.getAllPlaylists());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public ObservableList<Playlist> getAllPlaylists() {
        return playlists;
    }

    public void addSongToPlaylist(Playlist playlist, Song song)
    {
        //in the memory
        playlist.addSongToPlaylist(song);
        //in the DB
        playlistItemModel.addPlaylistItem(playlist, song);
    }

    public void removeSongFromPlaylist(Playlist playlist, Song song)
    {
        playlist.removeSongFromPlaylist(song);

        //in the DB
        playlistItemModel.deletePlaylistItem(playlist, song);
    }

    /**
     * method is used to delete object from both TableView and
     * database
     *
     * @param playlist
     */
    public void deletePlaylist(Playlist playlist) {
        try {
            playlistManager.deletePlaylist(playlist);

        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        playlists.remove(playlist);
    }

    /**
     * method creates a new playlist that will be both in
     * Database and TableView
     *
     * @param name
     */
    public void newPlaylist(String name) {
        try {
            playlists.add(playlistManager.newPlaylist(name));
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    /**
     * method is used to update a playlist in both TableView
     * and Database
     *
     * @param name
     * @param playlist
     */
    public void updatePlaylist(String name, Playlist playlist) {
        try {
            playlistManager.updatePlaylist(name, playlist);
            updateListOfPlaylists(playlist);

        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }

    }

    private void updateListOfPlaylists(Playlist playlist) {
        int index = playlists.indexOf(playlist);

        playlists.set(index, new Playlist(playlist.getId(), playlist.getName(), playlist.getSongs(),
                playlist.getNumberOfSongs(), playlist.getTotalPlaytime()));
    }

    //methods turned out to be not needed
    /*
    public int getNumberOfSongsOnPlaylist(Playlist playlist)
    {
        try {
            return playlistManager.getNumberOfSongsOnPlaylist(playlist);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return -1;
    }


    public double getTotalTimeOnPlaylist(Playlist playlist)
    {
        try {
            return  playlistManager.getTotalTimeOnPlaylist(playlist);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return -1;

    }

 */

    public void updateTotalTimeOnPlaylistADD(Playlist playlist, int addedSongTime) {
        try {
            playlistManager.updateTotalTimeOnPlaylistADD(playlist, addedSongTime);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public void updateTotalTimeOnPlaylistRemove(Playlist playlist, int removedSongTime) {
        playlistManager.updateTotalTimeOnPlaylistRemove(playlist, removedSongTime);
    }

    public void incrementNumberOfSongsOnPlaylist(Playlist playlist) {
        try {
            playlistManager.increamentNumberOfSongsOnPlaylist(playlist);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public void decrementNumberOfSongsOnPlaylist(Playlist playlist) {
        playlistManager.decrementNumberOfSongsOnPlaylist(playlist);
    }

}



