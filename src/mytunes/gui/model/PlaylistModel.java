package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.bll.BLLFacade;
import mytunes.bll.BLLcontroller;
import mytunes.bll.PlaylistManager;
import mytunes.bll.exeption.BLLexception;

public class PlaylistModel {

    private BLLFacade bllAccess;
    private ObservableList<Playlist> playlists;

    public PlaylistModel() {
        bllAccess = new BLLcontroller();
        playlists = FXCollections.observableArrayList();
        /*
        try {
            playlists.addAll(bllAccess.getAllPlaylists());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }

         */
    }



/**
 * Class plays important role in creating a TableView Playlists'
 * objects. Class is responsible for one directional communication with
 * Business logic layer
 */
public class PlaylistModel {

    private PlaylistManager playlistManager;// class from BLL
    private ObservableList<Playlist> playlists;
    private static PlaylistModel playlistModelInstance;

    public PlaylistModel() {
       playlistManager = new PlaylistManager();
        playlists = FXCollections.observableArrayList();
    }

    /**
     * method used to create instance. when created in this
     * way we ensure that we only use one instance when its needed
     * @return PlaylistModel
     */
    public static PlaylistModel createOrGetInstance() {
        if(  playlistModelInstance== null)
        {
            playlistModelInstance = new PlaylistModel();
        }
        return playlistModelInstance;

    }

    /**
     * method loads all playlist objects into observable list
     */
    public void load()
    {
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


    /**
     * method is used to delete object from both TableView and
     * database
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
     * @param name
     */
    public void newPlaylist(String name) {
        try {
            playlists.add( playlistManager.newPlaylist(name));
            playlistManager.newPlaylist(name);

        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }


    /**
     * method is used to update a playlist in both TableView
     * and Database
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

    private void updateListOfPlaylists(Playlist playlist)
    {
        int index = playlists.indexOf(playlist);

        playlists.set(index, new Playlist(playlist.getId(),playlist.getName(),playlist.getSongs(),
                playlist.getNumberOfSongs(),playlist.getTotalPlaytime()));

    }

    /**
     *
     */

   
    
    public int getNumberOfSongsOnPlaylist(Playlist playlist)
    {
        try {
            return playlistManager.getNumberOfSongsOnPlaylist(playlist);

        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return -1;
    }


    /**
     *
     */
  
    public double getTotalTimeOnPlaylist(Playlist playlist)
    {
        try {
            return  playlistManager.getTotalTimeOnPlaylist(playlist);

        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return -1;

    }

    public int updateNumberOfSongsOnPlaylist(Playlist playlist)
    {
        return -1;
    }



    }


    //updateNumberOfSongsOnPlaylist

    //updateTotalTimeOnPlaylist

}
