package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.bll.PlaylistManager;

public class PlaylistModel {

    private PlaylistManager playlistManager;
    private ObservableList<Playlist> playlists;

    public PlaylistModel() {
        playlistManager = new PlaylistManager();
        playlists = FXCollections.observableArrayList();
        playlists.addAll(playlistManager.getAllPlaylists());
    }

    public ObservableList<Playlist> getAllPlaylists() {
        return playlists;
    }

    public void deletePlaylist(Playlist playlist) {
        playlistManager.deletePlaylist(playlist);
        playlists.remove(playlist);
    }

    public void newPlaylist(String name) {
        playlists.add(playlistManager.newPlaylist(name));
    }

    public void updatePlaylist(String name, Playlist playlist) {
        playlistManager.updatePlaylist(name, playlist);
    }
}
