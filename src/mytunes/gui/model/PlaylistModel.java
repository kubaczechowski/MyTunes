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
    private ObservableList playlists;

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

    public void load()
    {
        try {
           //if(bllAccess.getAllPlaylists()!=null)
                playlists.addAll(bllAccess.getAllPlaylists());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public ObservableList<Playlist> getAllPlaylists() {
        return playlists;
    }

    public void deletePlaylist(Playlist playlist) {
        try {
            bllAccess.deletePlaylist(playlist);

        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        playlists.remove(playlist);
    }

    public void newPlaylist(String name) {
        try {
            playlists.add(bllAccess.newPlaylist(name));
            bllAccess.newPlaylist(name);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public void updatePlaylist(String name, Playlist playlist) {
        try {
           bllAccess.updatePlaylist(name, playlist);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    /**
     *
     */
    public int getNumberOfSongsOnPlaylist(Playlist playlist)
    {
        try {
            return bllAccess.getNumberOfSongsOnPlaylist(playlist);
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
            return bllAccess.getTotalTimeOnPlaylist(playlist);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return -1;

    }

    //updateNumberOfSongsOnPlaylist

    //updateTotalTimeOnPlaylist
}
