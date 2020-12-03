package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.bll.PlaylistManager;
import mytunes.bll.exeption.BLLexception;

public class PlaylistModel {

    private PlaylistManager playlistManager;
    private ObservableList<Playlist> playlists;

    public PlaylistModel() {
        playlistManager = new PlaylistManager();
        playlists = FXCollections.observableArrayList();
        try {
            playlists.addAll(playlistManager.getAllPlaylists());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public ObservableList<Playlist> getAllPlaylists() {
        return playlists;
    }

    public void deletePlaylist(Playlist playlist) {
        try {
            playlistManager.deletePlaylist(playlist);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        playlists.remove(playlist);
    }

    public void newPlaylist(String name) {
        try {
            playlists.add(playlistManager.newPlaylist(name));
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public void updatePlaylist(String name, Playlist playlist) {
        try {
            playlistManager.updatePlaylist(name, playlist);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }
}
