package mytunes.bll;

import mytunes.be.Playlist;
import mytunes.dal.PlaylistDAO;

import java.util.List;

public class PlaylistManager {

    private PlaylistDAO playlistDAO;

    public PlaylistManager() {
        playlistDAO = new PlaylistDAO();
    }

    public List<Playlist> getAllPlaylists() {
        return playlistDAO.getAllPlaylists();
    }

    public void deletePlaylist(Playlist playlist) {
        playlistDAO.deletePlaylist(playlist);
    }

    public Playlist newPlaylist(String name) {
        return playlistDAO.createPlaylist(name);
    }

    public void updatePlaylist(String name, Playlist playlist) {
        playlistDAO.updatePlaylistName(playlist, name);
    }
}
